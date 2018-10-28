package com.ljh.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.URLDecoder;
import java.util.regex.Pattern;

/**
 * @author liujiahan
 * @Title: HttpFileServerHandler
 * @Copyright: Copyright (c) 2018
 * @Description: 具体的业务处理流程
 * @Created on 2018/10/27
 * @ModifiedBy:
 */
public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private String url;

    public HttpFileServerHandler(String url) {
        this.url = url;
    }

    /**
     * 1，接受到消息后的处理过程
     * @param ctx
     * @param request
     * @throws Exception
     */
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        //验证解码
        if (!request.decoderResult().isSuccess()) {
            sendError(ctx, HttpResponseStatus.BAD_REQUEST);
            return;
        }
        //验证请求方法
        if (request.method() != HttpMethod.GET) {
            sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
            return;
        }
        //uri地址合法的验证
        final String uri = request.uri();
        final String path = sanitizeUri(uri);
        if (path == null) {
            sendError(ctx, HttpResponseStatus.FORBIDDEN);
            return;
        }

        //file地址的验证
        File file = new File(path);
        if (file.isHidden() || !file.exists()) {
            sendError(ctx, HttpResponseStatus.NOT_FOUND);
            return;
        }
        //如果是个文件夹的处理内容，sendlist下文件夹下的内容
        if (file.isDirectory()) {
            if (uri.endsWith("/")) {
                sendListing(ctx, file);
            } else {
                sendRedirect(ctx, uri + "/");
            }
            return;
        }

        //如果不是文件类型请求，返回错误
        if (!file.isFile()) {
            sendError(ctx, HttpResponseStatus.FORBIDDEN);
            return;
        }

        //使用欧冠随机文件读写类以只读的方式打开文件
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException fnfd) {
            sendError(ctx, HttpResponseStatus.NOT_FOUND);
            return;
        }
        //如果直接打开或者下载文件，获取文件的长度，构造HTTP消息
        long fileLength = randomAccessFile.length();
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        HttpHeaderUtil.setContentLength(response, fileLength);
        setContentTypeHeader(response, file);
        //将应答header设置为keep-alive
        if (HttpHeaderUtil.isKeepAlive(request)) {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }

        ctx.write(response);
        ChannelFuture sendFileFuture = null;
        //将文件写到发送缓存区
        sendFileFuture = ctx.write(new ChunkedFile(randomAccessFile, 0, fileLength, 8192), ctx.newProgressivePromise());
        //添加ChannelProgressiveFutureListener 监听，operationProgressed、operationComplete两个方法
        sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
            @Override
            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
                if (total < 0) {
                    System.err.println("transfer progress:" + progress);
                } else {
                    System.err.println("transfer progress:" + progress + "/" + total);
                }
            }
            @Override
            public void operationComplete(ChannelProgressiveFuture future) throws Exception {
                System.out.println("transfer complete");
            }
        });

        //如果使用chunked编码，最后需要发送一个编码结束的空消息体，表示结束
        ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        if (!HttpHeaderUtil.isKeepAlive(request)) {
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
        }

    }


    /**
     * 2，异常处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
            sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*************************************下边为封装的工具方法**************************************/


    /**
     * 不安全URI的正则限制
     */
    private static final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");

    /**
     * 审核Uri
     *
     * @param uri
     * @return
     */
    private String sanitizeUri(String uri) {
        try {
            uri = URLDecoder.decode(uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            try {
                uri = URLDecoder.decode(uri, "ISO-8859-1");
            } catch (UnsupportedEncodingException e1) {
                throw new Error();
            }
        }

        //各种校验
        if (!uri.startsWith(url)) {
            return null;
        }
        if (!uri.startsWith("/")) {
            return null;
        }
        uri = uri.replace('/', File.separatorChar);
        if (uri.contains(File.separator + '.') || uri.contains('.' + File.separator) || uri.startsWith(".") || uri.endsWith(".") || INSECURE_URI.matcher(uri).matches()) {
            return null;
        }
        return System.getProperty("user.dir") + File.separator + uri;

    }


    /**
     * 正则限制
     */
    private static final Pattern ALLOWED_FILE_NAME = Pattern.compile("[A-Za-z0-9][-_A-Za-z0-9\\.]*");

    /**
     * 发送相应的信息
     *
     * @param ctx
     * @param dir
     */
    private static void sendListing(ChannelHandlerContext ctx, File dir) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");

        String dirPath = dir.getPath();
        //拼装响应体的信息
        StringBuilder builder = new StringBuilder();

        builder.append("<!DOCTYPE html>\r\n");
        builder.append("<html><head><title>");
        builder.append(dirPath);
        builder.append("目录:");
        builder.append("</title></head><body>\r\n");

        builder.append("<h3>");
        builder.append(dirPath).append(" 目录：");
        builder.append("</h3>\r\n");
        builder.append("<ul>");
        builder.append("<li>链接：<a href=\" ../\")..</a></li>\r\n");
        for (File f : dir.listFiles()) {
            if (f.isHidden() || !f.canRead()) {
                continue;
            }
            String name = f.getName();
            if (!ALLOWED_FILE_NAME.matcher(name).matches()) {
                continue;
            }

            builder.append("<li>链接：<a href=\"");
            builder.append(name);
            builder.append("\">");
            builder.append(name);
            builder.append("</a></li>\r\n");
        }
        builder.append("</ul></body></html>\r\n");

        ByteBuf buffer = Unpooled.copiedBuffer(builder, CharsetUtil.UTF_8);
        response.content().writeBytes(buffer);
        buffer.release();
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

    }

    /**
     * 重定向
     *
     * @param ctx
     * @param newUri
     */
    private static void sendRedirect(ChannelHandlerContext ctx, String newUri) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
        response.headers().set(HttpHeaderNames.LOCATION, newUri);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 返回错误信息
     * @param ctx
     * @param status
     */
    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer("Failure:" + status.toString() + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

    }

    /**
     * 设置ContentType
     * @param response
     * @param file
     */
    private static void setContentTypeHeader(HttpResponse response, File file) {
        MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, mimetypesFileTypeMap.getContentType(file.getPath()));
    }

}

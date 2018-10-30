package com.ljh.netty.http_xml.codec;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.mapper.MapperWrapper;

/**
 * @author liujiahan
 * @Title: XstreamUtil
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/30
 * @ModifiedBy:
 */
public class XstreamUtil {

    /**
     * java 转换成xml
     * @Title: toXml
     * @Description: TODO
     * @param obj 对象实例
     * @return String xml字符串
     */
    public static String toXml(Object obj){
        XStream xstream=new XStream();
        //通过注解方式的，一定要有这句话
        xstream.processAnnotations(obj.getClass());
        return xstream.toXML(obj);
    }

    /**
     *  将传入xml文本转换成Java对象
     * @Title: toBean
     * @Description: TODO
     * @param xmlStr
     * @param cls  xml对应的class类
     * @return T   xml对应的class类的实例对象
     *
     * 调用的方法实例：PersonBean person=XmlUtil.toBean(xmlStr, PersonBean.class);
     */
    public static <T> T  toBean(String xmlStr,Class<T> cls){
        XStream xstream = new XStream() {
            @Override
            protected MapperWrapper wrapMapper(MapperWrapper next) {
                return new MapperWrapper(next) {
                    @SuppressWarnings("rawtypes")
                    @Override
                    public boolean shouldSerializeMember(Class definedIn,
                                                         String fieldName) {
                        if (definedIn == Object.class) {
                            return false;
                        }
                        return super.shouldSerializeMember(definedIn, fieldName);
                    }
                };
            }
        };
        xstream.processAnnotations(cls);
        @SuppressWarnings("unchecked")
        T obj=(T)xstream.fromXML(xmlStr);
        return obj;
    }

}

package com.ljh.netty.http_xml.test;

import com.ljh.netty.http_xml.pojo.Order;
import org.jibx.runtime.*;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author liujiahan
 * @Title: TestOrder
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/29
 * @ModifiedBy:
 */
public class TestOrder {

    private IBindingFactory factory = null;
    private StringWriter writer = null;
    private StringReader reader = null;
    private final static String CHARSET_NAME = "UTF-8";

    private String encode2Xml(Order order) throws JiBXException, IOException {

        factory = BindingDirectory.getFactory(Order.class);
        writer = new StringWriter();
        IMarshallingContext mctx = factory.createMarshallingContext();
        mctx.setIndent(2);
        mctx.marshalDocument(order, CHARSET_NAME, null, writer);
        String xmlStr = writer.toString();
        writer.close();
        System.out.println(xmlStr.toString());
        return xmlStr;
    }


    private Order decode2Order(String xmlBody) throws JiBXException {
        reader = new StringReader(xmlBody);
        IUnmarshallingContext uctx = factory.createUnmarshallingContext();
        Order order = (Order) uctx.unmarshalDocument(reader);
        return order;
    }

    public static void main(String[] args) throws JiBXException, IOException {
        TestOrder testOrder = new TestOrder();
        Order order = OrderFactory.create(1111);
        String body = testOrder.encode2Xml(order);
        System.out.println(body);
        Order order1 = testOrder.decode2Order(body);
        System.out.println(order1);
    }
}

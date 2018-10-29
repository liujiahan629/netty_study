package com.ljh.netty.http_xml.test;

import com.ljh.netty.http_xml.pojo.Address;
import com.ljh.netty.http_xml.pojo.Customer;
import com.ljh.netty.http_xml.pojo.Order;
import com.ljh.netty.http_xml.pojo.Shipping;

/**
 * @author liujiahan
 * @Title: OrderFactory
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/29
 * @ModifiedBy:
 */
public class OrderFactory {

    public static Order create(long orderID) {
        Order order = new Order();
        order.setOrderNumber(orderID);
        order.setTotal(9999.999f);
        Address address = new Address();
        address.setCity("北京市");
        address.setCountry("中国");
        address.setPostCode("100000");
        address.setState("北京市");
        address.setStreet1("朝阳门朝外道");
        order.setBillTo(address);
        Customer customer = new Customer();
        customer.setCustomerNumber(orderID);
        customer.setFirstName("liu");
        customer.setLastName("jiahan");
        order.setCustomer(customer);
        order.setShipping(Shipping.INTERNATIONAL_MAIL);
        order.setShipTo(address);
        return order;
    }
}

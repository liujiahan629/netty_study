package com.ljh.netty.http_xml.pojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author liujiahan
 * @Title: Order
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/29
 * @ModifiedBy:
 */
@XStreamAlias("Order")
public class Order {

    private long orderNumber;
    private Customer customer;
    private Shipping shipping;
    private Address billTo;
    private Address shipTo;
    private Float total;

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public Address getBillTo() {
        return billTo;
    }

    public void setBillTo(Address billTo) {
        this.billTo = billTo;
    }

    public Address getShipTo() {
        return shipTo;
    }

    public void setShipTo(Address shipTo) {
        this.shipTo = shipTo;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Order [orderNumber=" + orderNumber + ", customer=" + customer + ", billTo=" + billTo + ", shipping="
                + shipping.toString() + ", shipTo=" + shipTo + ", total=" + total + "]";
    }
}

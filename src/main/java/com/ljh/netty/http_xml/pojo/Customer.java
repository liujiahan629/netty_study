package com.ljh.netty.http_xml.pojo;

import java.util.List;

/**
 * @author liujiahan
 * @Title: Customer
 * @Copyright: Copyright (c) 2018
 * @Description:
 * @Created on 2018/10/29
 * @ModifiedBy:
 */
public class Customer {
    private long customerNumber;
    private String firstName;
    private String lastName;
    private List<String> middleNames;

    public long getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(long customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<String> getMiddleNames() {
        return middleNames;
    }

    public void setMiddleNames(List<String> middleNames) {
        this.middleNames = middleNames;
    }
}

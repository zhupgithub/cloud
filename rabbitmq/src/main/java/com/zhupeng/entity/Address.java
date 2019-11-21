package com.zhupeng.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Address extends ApplicationMessage implements Serializable {
    public Address(Object source) {
        super(source);
    }

    public Address(Object source, String doorNum, String username) {
        super(source);
        this.doorNum = doorNum;
        this.username = username;
    }

    private String doorNum;

    private String username;


}

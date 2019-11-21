package com.zhupeng.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    private String username;

    private String password;

    private int age;

    private Date birthday;


}

package com.zhupeng.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * user
 * @author 朱朋
 * @date 2019-09-25
 */
@Data
public class User implements Serializable {
    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户名称
     */
    private String username;

    private String password;

    private Date birthday;

}
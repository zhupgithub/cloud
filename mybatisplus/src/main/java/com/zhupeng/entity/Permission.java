package com.zhupeng.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhupeng
 * @since 2019-09-26
 */
@TableName("permission")
public class Permission extends Model<Permission> {

    private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
    private Integer pid;

    /**
     * 权限名称
     */
	@TableField(value = "name")
    private String pname;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    @Override
    protected Serializable pkVal() {
        return this.pid;
    }

    @Override
    public String toString() {
        return "Permission{" +
            "pid=" + pid +
            ", pname=" + pname +
        "}";
    }
}

package com.ssh1y.paperrec.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author chenweihong
 * @TableName Users
 */
@TableName(value = "Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer userid;

    /**
     *
     */
    private String username;

    /**
     *
     */
    private String password;

    /**
     *
     */
    private String role;

    /**
     *
     */

    private String avatar;

    /**
     *
     */

    private String email;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
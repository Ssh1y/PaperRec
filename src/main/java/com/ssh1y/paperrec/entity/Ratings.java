package com.ssh1y.paperrec.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenweihong
 * @TableName Ratings
 */
@TableName(value = "Ratings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ratings implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     *
     */
    private Integer paperid;

    /**
     *
     */
    private Integer userid;

    /**
     *
     */
    private Integer query;

    /**
     *
     */
    private Integer rating;

    /**
     *
     */
    private Date timestamp;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
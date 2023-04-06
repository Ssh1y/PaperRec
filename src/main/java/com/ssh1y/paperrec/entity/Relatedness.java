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
 * @TableName Relatedness
 */
@TableName(value = "Relatedness")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Relatedness implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     *
     */
    private Integer paperid1;

    /**
     *
     */
    private Integer paperid2;

    /**
     *
     */
    private Double relatedness;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
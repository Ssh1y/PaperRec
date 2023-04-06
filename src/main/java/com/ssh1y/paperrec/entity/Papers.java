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
 * &#064;TableName  Papers
 */
@TableName(value = "Papers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Papers implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     *
     */
    private String title;

    /**
     *
     */
    private String author;

    /**
     *
     */
    private String organ;

    /**
     *
     */
    private String source;

    /**
     *
     */
    private String keywords;

    /**
     *
     */
    private String summary;

    /**
     *
     */
    private String publishdate;

    /**
     *
     */
    private String url;

    /**
     *
     */
    private String doi;

    /**
     *
     */
    private String srcdatabase;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
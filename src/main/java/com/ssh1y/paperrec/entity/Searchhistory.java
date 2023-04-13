package com.ssh1y.paperrec.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @author chenweihong
 * @TableName SearchHistory
 */
@TableName(value ="SearchHistory")
@Data
public class Searchhistory implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer userid;

    /**
     * 
     */
    private String query;

    /**
     * 
     */
    private Date timestamp;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
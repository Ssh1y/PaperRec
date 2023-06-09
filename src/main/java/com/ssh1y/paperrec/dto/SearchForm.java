package com.ssh1y.paperrec.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenweihong
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchForm {

    private Integer searchType;
    private String query;

    Boolean isUpdateCache;

    /**
     * 推荐文献的时候需要
     */
    private Integer paperId;
    private Integer page;
    private Integer limit;
}

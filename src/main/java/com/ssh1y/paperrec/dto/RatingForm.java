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
public class RatingForm {

    private Integer paperId;

    private Integer rating;
}

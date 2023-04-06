package com.ssh1y.paperrec.entity;


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

    private Integer recommendationId;
    private Integer ratedPaperId;
    private Integer rating;
}

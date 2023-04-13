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
public class UpdateInfoDto {

    private String username;
    private String email;
}

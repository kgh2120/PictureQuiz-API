package com.kk.picturequizapi.domain.admin.query.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class MemberCreateCountResponse {
    private Long count;
    private LocalDate createdDate;


    @QueryProjection
    public MemberCreateCountResponse(Long count, LocalDate createdDate) {
        this.count = count;
        this.createdDate = createdDate;
    }


}

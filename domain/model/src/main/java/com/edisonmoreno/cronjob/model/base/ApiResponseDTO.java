package com.edisonmoreno.cronjob.model.base;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiResponseDTO {
    private String aggregateId;
    private String typeName;
    private String status;
    private String message;
}

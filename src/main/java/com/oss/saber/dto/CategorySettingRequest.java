package com.oss.saber.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class CategorySettingRequest {
    private Long categoryId;

    private String productName;
}

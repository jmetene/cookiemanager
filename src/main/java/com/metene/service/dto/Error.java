package com.metene.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Error {
    @JsonProperty("code")
    private String code;
    @JsonProperty("dateTime")
    private String dateTime;
    @JsonProperty("description")
    private String description;
    @JsonProperty("extendedInfo")
    private List<String> extendedInfo;
}
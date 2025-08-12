package ru.arkhipov.MySecondTestAppSpringBoot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class Response {
    private String uid;
    private String operationUid;
    private String systemName;
    private String systemTime;
    private String code;
    private String errorCode;
    private String errorMessage;
}

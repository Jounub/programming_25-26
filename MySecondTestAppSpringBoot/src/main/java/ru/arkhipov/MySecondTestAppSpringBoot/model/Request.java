package ru.arkhipov.MySecondTestAppSpringBoot.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    @NotBlank(message = "uid is required")
    private String uid;

    @NotBlank(message = "operationUid is required")
    private String operationUid;

    private String systemName;

    @NotBlank(message = "systemTime is required")
    private String systemTime;

    private String source;
    private int communicationId;
    private int templateId;
    private int productCode;
    private int smsCode;
}

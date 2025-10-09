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
    private Codes code;
    private ErrorCodes errorCode;
    private ErrorMessages errorMessage;

    @Override
    public String toString(){
        return "{" +
                "uid='" + uid + '\'' +
                ", operationUid='" + operationUid + '\'' +
                ", systemName='" + systemName  + '\'' +
                ", systemTime='" + systemTime  + '\'' +
                ", code='" + code  + '\'' +
                ", errorCode='" + errorCode +
                ", errorMessage='" + errorMessage +
                '}';
    }
}

package ru.arkhipov.MySecondTestAppSpringBoot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    //Уникальный идентификатор сообщения
    private String uid;
    //Уникальный идентификатор операции
    private String operationUid;
    //Имя системы отправителя
    private Systems systemName;
    //Время создания сообщения
    private String systemTime;
    //Код
    private Codes code;
    //Годовой бонус
    private Double annualBonus;
    //Код ошибки
    private ErrorCodes errorCode;
    //Текст ошибки
    private ErrorMessages errorMessage;

    @Override
    public String toString(){
        return "{" +
                "uid='" + uid + '\'' +
                ", operationUid='" + operationUid + '\'' +
                ", systemName='" + systemName  + '\'' +
                ", systemTime='" + systemTime  + '\'' +
                ", code='" + code  + '\'' +
                ", annualBonus='" + annualBonus  + '\'' +
                ", errorCode='" + errorCode +
                ", errorMessage='" + errorMessage +
                '}';
    }
}

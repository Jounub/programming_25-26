package ru.arkhipov.MySecondTestAppSpringBoot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class Response {
    //Уникальный идентификатор сообщение
    private String uid;
    //Уникальный идентификатор операции
    private String operationUid;
    //Имя системы отправителя
    private String systemName;
    //Время создания сообщения
    private String systemTime;
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

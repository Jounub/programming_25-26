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

    //Уникальный идентификатор сообщения
    @NotBlank(message = "UID не может быть пустым")
    private String uid;

    //Уникальный идентификатор операции
    @NotBlank(message = "Operation UID не может быть пустым")
    private String operationUid;

    //Имя системы отправителя
    private Systems systemName;

    //Время создания сообщения
    private String systemTime;

    //Наименование ресурса
    private String source;
    //Должность
    private Positions position;
    //Зарплата
    private Double salary;
    //Размер бонуса
    private Double bonus;
    //Количество рабочих дней
    private Integer workDays;
    //Идентификатор коммуникации
    private int communicationId;
    //Идентификатор шаблона
    private int templateId;
    //Код продукта
    private int productCode;
    //Смс код
    private int smsCode;

    @Override
    public String toString(){
        return "{" +
                "uid='" + uid + '\'' +
                ", operationUid='" + operationUid + '\'' +
                ", systemName='" + systemName  + '\'' +
                ", systemTime='" + systemTime  + '\'' +
                ", source='" + source  + '\'' +
                ", position='" + position  + '\'' +
                ", salary='" + salary  + '\'' +
                ", bonus='" + bonus  + '\'' +
                ", workDays='" + workDays  + '\'' +
                ", communicationId='" + communicationId +
                ", templateId='" + templateId +
                ", productCode='" + productCode +
                ", smsCode='" + smsCode +
                '}';
            }
}

package ru.arkhipov.MySecondTestAppSpringBoot.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Systems {
    ERP("Enterprise Resource Planning"),
    CRM("Customer Relationship Management"),
    WMS("Warehouse Management System"),
    SRV1("Service 1");

    private final String name;

    Systems(String name){
        this.name = name;
    }

    @JsonValue
    public String getName(){
        return  name;
    }

    @Override
    public String toString(){
        return  name;
    }

}

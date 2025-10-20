package ru.arkhipov.MySecondTestAppSpringBoot.model;

import lombok.Getter;

@Getter
public enum Positions {
    //Developer
    DEV(2.2, false),
    //HR-specialist
    HR(1.2, false),
    //Manager
    TL(2.6, true),
    //Project manager
    PM(3.0, true),
    //Tester
    TS(2.0, false),
    //Chief executive officer
    CEO(10.0, true);

    private final double positionCoefficient;
    private final Boolean isManager;

    Positions(double positionCoefficient, Boolean isManager){
        this.positionCoefficient = positionCoefficient;
        this.isManager = isManager;
    }
}

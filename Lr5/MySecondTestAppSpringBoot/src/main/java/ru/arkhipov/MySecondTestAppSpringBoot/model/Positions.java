package ru.arkhipov.MySecondTestAppSpringBoot.model;

import lombok.Getter;

@Getter
public enum Positions {
    DEV(2.2, false),
    HR(1.2, false),
    TL(2.6, false),
    PM(3.0, true),
    OM(2.0, true),
    CEO(10.0, true);

    private final double positionCoefficient;
    private final Boolean isManager;

    Positions(double positionCoefficient, Boolean isManager){
        this.positionCoefficient = positionCoefficient;
        this.isManager = isManager;
    }
}

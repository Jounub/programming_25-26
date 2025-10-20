package ru.arkhipov.MySecondTestAppSpringBoot.exception;

public class BonusCalculationException extends RuntimeException {
    public BonusCalculationException(String message) {
        super(message);
    }
}

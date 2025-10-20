package ru.arkhipov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.arkhipov.MySecondTestAppSpringBoot.exception.BonusCalculationException;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Positions;

import java.rmi.UnexpectedException;
import java.time.Year;

@Service
public class AnnualBonusServiceImpl implements AnnualBonusService{
    @Override
    public double calculate(Positions positions, double salary, double bonus, int workDays){
        return salary * bonus * Year.of(Year.now().getValue()).length() * positions.getPositionCoefficient() / workDays;
    }

    @Override
    public double calculateQuarterlyBonus(Positions positions, double salary, double bonus, int workDays)
            throws BonusCalculationException {
        if(!positions.getIsManager()) throw new BonusCalculationException("Not a manager");
        return salary * bonus * Year.of(Year.now().getValue()).length()/4 * positions.getPositionCoefficient() / workDays;
    }
}

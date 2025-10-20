package ru.arkhipov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.arkhipov.MySecondTestAppSpringBoot.exception.BonusCalculationException;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Positions;

@Service
public interface AnnualBonusService {
    double calculate(Positions positions, double salary, double bonus, int workdays);
    double calculateQuarterlyBonus(Positions positions, double salary, double bonus, int workDays)
            throws BonusCalculationException;
}

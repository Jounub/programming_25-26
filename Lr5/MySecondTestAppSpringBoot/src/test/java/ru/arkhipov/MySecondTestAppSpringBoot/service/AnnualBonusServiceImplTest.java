package ru.arkhipov.MySecondTestAppSpringBoot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.arkhipov.MySecondTestAppSpringBoot.exception.BonusCalculationException;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Positions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AnnualBonusServiceImplTest {
    @Test
    void calculate() {

        //given
        Positions positions = Positions.HR;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;

        //when
        double result = new AnnualBonusServiceImpl().calculate(positions, salary, bonus, workDays);

        //then
        double expected = 360493.8271604938;
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @EnumSource(Positions.class)
    void calculateQuarterlyBonus(Positions positions){
        //given
        double salary = 100000.00;
        double bonus = 2.0;
        int workDays = 60;

        //when and then
        if(!positions.getIsManager())
        {
            assertThrows(BonusCalculationException.class,
                    () -> new AnnualBonusServiceImpl().calculateQuarterlyBonus(positions, salary, bonus, workDays)
            );
        }
        else {
            double result = new AnnualBonusServiceImpl().calculateQuarterlyBonus(positions, salary, bonus, workDays);
            assertTrue(result > 0);
        }
    }
}
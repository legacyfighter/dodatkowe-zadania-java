package legacyfighter.dietary;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaxRuleTest {
    //wszystkie data driven


    @Test
    void shouldCalculateLinear() {
        assertEquals(new BigDecimal(20), TaxRule.linearRule(2, 10, "code").calculate(new BigDecimal(5)));
        assertEquals(new BigDecimal(10), TaxRule.linearRule(2, 0, "code").calculate(new BigDecimal(5)));
    }

    @Test
    void shouldCalculateSquare() {
        assertEquals(new BigDecimal(103), TaxRule.squareRule(2, 10, 3, "code").calculate(new BigDecimal(5)));
        assertEquals(new BigDecimal(25), TaxRule.squareRule(1, 0, 0, "code").calculate(new BigDecimal(5)));
    }

    @Test
    void shouldCalculateAggregation() {
        TaxRule one = TaxRule.linearRule(2, 10, "code");
        TaxRule two = TaxRule.linearRule(2, 0, "code");
        TaxRule three = TaxRule.squareRule(2, 10, 3, "code");

        assertEquals(BigDecimal.ZERO, new TaxRulesAggregation(List.of()).calculate(BigDecimal.TEN));
        assertEquals(new BigDecimal(14), new TaxRulesAggregation(List.of(one, two)).calculate(BigDecimal.ONE));
        assertEquals(new BigDecimal(29), new TaxRulesAggregation(List.of(one, two, three)).calculate(BigDecimal.ONE));

    }

}
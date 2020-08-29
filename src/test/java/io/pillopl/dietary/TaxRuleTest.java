package io.pillopl.dietary;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TaxRuleTest {


    @Test
    void test() {
        assertEquals(new BigDecimal(20), TaxRule.linearRule(2, 10, "code").calculate(new BigDecimal(5)));
        assertEquals(new BigDecimal(10), TaxRule.linearRule(2, 0, "code").calculate(new BigDecimal(5)));

        assertEquals(new BigDecimal(103), TaxRule.squareRule(2, 10, 3, "code").calculate(new BigDecimal(5)));
        assertEquals(new BigDecimal(25), TaxRule.squareRule(1, 0, 0, "code").calculate(new BigDecimal(5)));
    }

}
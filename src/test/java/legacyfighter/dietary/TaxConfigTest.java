package legacyfighter.dietary;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class TaxConfigTest {

    String countryCode = "country-code";

    @Test
    void canAddLimitedRulesToCountry() {
        //given
        TaxConfig config = newConfigWithRuleAndMaxRules(countryCode, 2, TaxRule.linearRule(5, 6, "tax-code1"));

        //when
        config.add(TaxRule.linearRule(1, 2, "tax-code2"));

        //then
        assertThat(config.getCurrentRulesCount()).isEqualTo(2);
    }

    @Test
    void removingRuleShouldBeTakenIntoAccount() {
        //given
        TaxConfig config = newConfigWithRuleAndMaxRules(countryCode, 2, TaxRule.linearRule(5, 6, "tax-code3"));

        //and
        TaxRule taxRule = TaxRule.linearRule(1, 2, "tax-code2");
        config.add(taxRule);
        //and
        config.remove(taxRule);

        //then
        assertThat(config.getCurrentRulesCount()).isEqualTo(1);

        //when
        config.add(TaxRule.linearRule(1, 2, "tax-code5"));

        //then
        assertThat(config.getCurrentRulesCount()).isEqualTo(2);

    }

    @Test
    void cannotAddMoreThanLimitedRulesToCountry() {
        //given
        TaxConfig config = newConfigWithRuleAndMaxRules(countryCode, 2, TaxRule.linearRule(5, 6, "tax-code3"));

        //and
        config.add(TaxRule.linearRule(1, 2, "tax-code2"));

        //expect
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() ->
                config.add(TaxRule.linearRule(1, 2, "tax-code5")));
    }


    @Test
    void countryConfigHasAtLeast1Rule() {
        //given
        TaxRule aTaxRuleWithParams = TaxRule.linearRule(5, 6, "tax-code9");
        TaxConfig config = newConfigWithRuleAndMaxRules(countryCode, 2, aTaxRuleWithParams);

        //expect
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() ->
                config.remove(aTaxRuleWithParams));
    }

    @Test
    void countryCodeIsAlwaysValid() {
        //expect
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() ->
                CountryCode.of(null));

        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() ->
                CountryCode.of(""));

        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() ->
                CountryCode.of("1"));
    }

    @Test
    void aFactorIsAlwaysNotZero() {


    }


    TaxConfig newConfigWithRuleAndMaxRules(String countryCode, int maxRules, TaxRule aTaxRuleWithParams) {
        return new TaxConfig(countryCode, maxRules, aTaxRuleWithParams);
    }

}
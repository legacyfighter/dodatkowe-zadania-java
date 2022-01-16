package legacyfighter.dietary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class TaxRuleServiceTest {

    String countryCode = "country-code";

    @Autowired
    TaxRuleService taxRuleService;

    @Autowired
    TaxConfigRepository taxConfigRepository;

    @Autowired
    TaxRuleRepository ruleRepository;

    @BeforeEach
    void setup() {
        countryCode = UUID.randomUUID().toString();
    }

    @Test
    void canAddLimitedRulesToCountry() {
        //given
        newConfigWithRuleAndMaxRules(countryCode, 2, TaxRule.linearRule(5, 6, "tax-code1"));

        //when
        taxRuleService.addTaxRuleToCountry(countryCode, 1, 2, "tax-code2");

        //then
        assertThat(configBy(countryCode).getCurrentRulesCount()).isEqualTo(2);
    }

    @Test
    void removingRuleShouldBeTakenIntoAccount() {
        //given
        TaxConfig config = newConfigWithRuleAndMaxRules(countryCode, 2, TaxRule.linearRule(5, 6, "tax-code3"));

        //and
        taxRuleService.addTaxRuleToCountry(countryCode, 1, 2, "tax-code4");
        //and
        taxRuleService.deleteRule(ruleByTaxCode("tax-code3"), config.getId());

        //then
        assertThat(configBy(countryCode).getCurrentRulesCount()).isEqualTo(1);

        //when
        taxRuleService.addTaxRuleToCountry(countryCode, 1, 2, "tax-code5");

        //then
        assertThat(configBy(countryCode).getCurrentRulesCount()).isEqualTo(2);

    }

    Long ruleByTaxCode(String taxCode) {
        return ruleRepository.findByTaxCodeContaining(taxCode).getId();
    }


    @Test
    void cannotAddMoreThanLimitedRulesToCountry() {
        //given
        newConfigWithRuleAndMaxRules(countryCode, 2, TaxRule.linearRule(5, 6, "tax-code6"));

        //and
        taxRuleService.addTaxRuleToCountry(countryCode, 1, 2, "tax-code7");

        //expect
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() ->
                taxRuleService.addTaxRuleToCountry(countryCode, 4, 99, "tax-code8"));
    }


    @Test
    void countryConfigHasAtLeast1Rule() {
        //given
        TaxConfig config = newConfigWithRuleAndMaxRules(countryCode, 2, TaxRule.linearRule(5, 6, "tax-code9"));

        //expect
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() ->
                taxRuleService.deleteRule(ruleByTaxCode("tax-code9"), config.getId()));
    }

    @Test
    void countryCodeIsAlwaysValid() {
        //expect
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() ->
                taxRuleService.createTaxConfigWithRule(null,  TaxRule.linearRule(5, 6, "tax-code10")));

        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() ->
                taxRuleService.createTaxConfigWithRule("",  TaxRule.linearRule(5, 6, "tax-code11")));

        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() ->
                taxRuleService.createTaxConfigWithRule("1",  TaxRule.linearRule(5, 6, "tax-code12")));
    }

    @Test
    void aFactorIsAlwaysNotZero() {
        //given
        TaxConfig config = newConfigWithRuleAndMaxRules(countryCode, 2, TaxRule.linearRule(5, 6, "tax-code13"));
        
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() ->
                taxRuleService.addTaxRuleToCountry(countryCode, 0, 2, "tax-code14"));
    }

    TaxConfig configBy(String countryCode) {
        return taxConfigRepository.findByCountryCode(CountryCode.of(countryCode));
    }

    TaxConfig newConfigWithRuleAndMaxRules(String countryCode, int maxRules, TaxRule aTaxRuleWithParams) {
        TaxConfig taxConfig = new TaxConfig(countryCode, maxRules, aTaxRuleWithParams);
        return taxConfigRepository.save(taxConfig);
    }

}
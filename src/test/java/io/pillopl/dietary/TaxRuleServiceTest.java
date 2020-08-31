package io.pillopl.dietary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaxRuleServiceTest {

    @Autowired
    TaxRuleService service;

    @Autowired
    TaxConfigRepository taxConfigRepository;

    @Autowired
    TaxRuleRepository taxRuleRepository;


    @Test
    void countryCodeIsAlwaysValid() {

    }

    @Test
    void aFactorIsNotZero() {

    }

    @Test
    void shouldNotHaveMoreThanMaximumNumberOfRules() {
        //given
        TaxRule rule = TaxRule.linearRule(10, 10, "taxCode");
        TaxConfig config = createConfigWithInitialRule("PL1", 2, rule);
        //and
        service.addTaxRuleToCountry("PL1", 2, 4, "taxRule2");

        //expect
        assertThrows(IllegalStateException.class, () -> service.addTaxRuleToCountry("PL1", 2, 4, "taxRule3"));
    }

    @Test
    void canAddARule() {
//given
        TaxRule rule = TaxRule.linearRule(10, 10, "taxCode");
        TaxConfig config = createConfigWithInitialRule("PL2", 2, rule);

        //when
        service.addTaxRuleToCountry("PL2", 2, 4, "taxRule2");

        //then
        assertThat(service.rulesCount("PL2")).isEqualTo(2);
    }

    @Test
    void canDeleteARule() {
//given
        TaxRule rule = TaxRule.linearRule(10, 10, "taxCode");
        TaxConfig config = createConfigWithInitialRule("PL3", 2, rule);
        //and
        service.addTaxRuleToCountry("PL3", 2, 4, "taxRule2");

        //when
        service.deleteRule(rule.getId(), config.getId());

        //expect
        assertThat(service.rulesCount("PL3")).isEqualTo(1);
    }

    @Test
    void cannotDeleteARuleIfThatIsTheLastOne() {
//given
        TaxRule rule = TaxRule.linearRule(10, 10, "taxCode");
        TaxConfig config = createConfigWithInitialRule("PL4", 2, rule);


        //expect
        assertThrows(IllegalStateException.class, () -> service.deleteRule(rule.getId(), config.getId()));
    }

    TaxConfig createConfigWithInitialRule(String countryCode, int maxRules, TaxRule rule) {
        TaxConfig config = new TaxConfig();
        config.setCountryCode(countryCode);
        config.setTaxRules(List.of(rule));
        config.setMaxRulesCount(maxRules);
        config.setCurrentRulesCount(1);
        config.setLastModifiedDate(Instant.now());
        return taxConfigRepository.save(config);
    }

}
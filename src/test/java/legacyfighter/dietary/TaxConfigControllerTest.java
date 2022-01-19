package legacyfighter.dietary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TaxConfigControllerTest {

    String countryCode = "_country-code";
    String countryCode2 = "_country-code2";

    @Autowired
    TaxRuleService taxRuleService;

    @Autowired
    TaxConfigRepository taxConfigRepository;

    @Autowired
    TaxConfigController taxConfigController;

    @Test
    void shouldReturnCorrectMapOfConfigs() {
        //given
        newConfigWithRuleAndMaxRules(countryCode, 2, TaxRule.linearRule(1, 6, "tax1"));
        //and
        newConfigWithRuleAndMaxRules(countryCode, 2, TaxRule.squareRule(1, 5, 6, "tax2"));
        //and
        newConfigWithRuleAndMaxRules(countryCode2, 2, TaxRule.linearRule(1, 6, "tax3"));




        //then
        Map<String, List<TaxRule>> configMap = taxConfigController.taxConfigs();
        assertThat(configMap.entrySet()).size().isEqualTo(2);
        assertThat(configMap.get(countryCode)).size().isEqualTo(2);
        assertThat(configMap.get(countryCode)).contains(TaxRule.linearRule(1, 6, "tax1"));
        assertThat(configMap.get(countryCode)).contains(TaxRule.squareRule(1, 5, 6, "tax2"));

        assertThat(configMap.get(countryCode2)).size().isEqualTo(1);
        assertThat(configMap.get(countryCode2)).contains(TaxRule.squareRule(1, 5, 6, "tax3"));
    }

    TaxConfig newConfigWithRuleAndMaxRules(String countryCode, int maxRules, TaxRule aTaxRuleWithParams) {
        TaxConfig taxConfig = new TaxConfig(countryCode, maxRules, aTaxRuleWithParams);
        return taxConfigRepository.save(taxConfig);
    }

}
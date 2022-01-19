package legacyfighter.dietary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TaxConfigControllerTest {

    String countryCode = "country-code";
    String countryCode2 = "country-code2";

    @Autowired
    TaxRuleService taxRuleService;

    @Autowired
    TaxConfigRepository taxConfigRepository;

    @Autowired
    TaxConfigController taxConfigController;

    @Test
    void shouldReturnCorrectMapOfConfigs() {
        //given
        newConfigWithRuleAndMaxRules(countryCode, 2, TaxRule.linearRule(1, 6, "_tax-code1"));
        //and
        newConfigWithRuleAndMaxRules(countryCode, 2, TaxRule.squareRule(1, 5, 6, "_tax-code2"));
        //and
        newConfigWithRuleAndMaxRules(countryCode2, 2, TaxRule.linearRule(1, 6, "_tax-code3"));




        //then
        Map<String, List<TaxRule>> configMap = taxConfigController.taxConfigs();
        assertThat(configMap.entrySet()).size().isEqualTo(2);
        assertThat(configMap.get(countryCode)).size().isEqualTo(2);
        assertThat(configMap.get(countryCode)).contains(TaxRule.linearRule(1, 6, "tax-code1"));
        assertThat(configMap.get(countryCode)).contains(TaxRule.squareRule(1, 5, 6, "tax-code2"));

        assertThat(configMap.get(countryCode2)).size().isEqualTo(1);
        assertThat(configMap.get(countryCode2)).contains(TaxRule.squareRule(1, 5, 6, "tax-code3"));
    }


    TaxConfig configBy(String countryCode) {
        return taxConfigRepository.findByCountryCode(CountryCode.of(countryCode));
    }

    TaxConfig newConfigWithRuleAndMaxRules(String countryCode, int maxRules, TaxRule aTaxRuleWithParams) {
        TaxConfig taxConfig = new TaxConfig(countryCode, maxRules, aTaxRuleWithParams);
        return taxConfigRepository.save(taxConfig);
    }

}
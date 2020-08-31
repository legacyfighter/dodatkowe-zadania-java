package io.pillopl.dietary;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.Year;
import java.util.List;

@Service
public class TaxRuleService {

    @Autowired
    private TaxRuleRepository taxRuleRepository;

    @Autowired
    private TaxConfigRepository taxConfigRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public void addTaxRuleToCountry(String countryCode, int aFactor, int bFactor, String taxCode) {
        if (countryCode == null || countryCode.equals("") || countryCode.length() == 1) {
            throw new IllegalStateException("Invalid country code");
        }
        if (aFactor == 0) {
            throw new IllegalStateException("Invalid aFactor");

        }
        TaxRule taxRule = new TaxRule();
        taxRule.setaFactor(aFactor);
        taxRule.setbFactor(bFactor);
        taxRule.setLinear(true);
        int year = Year.now().getValue();
        taxRule.setTaxCode("A. 899. " + year + taxCode);
        TaxConfig taxConfig = taxConfigRepository.findByCountryCode(CountryCode.of(countryCode));
        if (taxConfig == null) {
            taxConfig = createTaxConfigWithRule(countryCode, taxRule);
            return;
        }


        taxConfig.add(taxRule);

        List<Order> byOrderState = orderRepository.findByOrderState(Order.OrderState.Initial);

        byOrderState.forEach(order -> {
            if (order.getCustomerOrderGroup().getCustomer().getType().equals(Customer.Type.Person)) {
                order.getTaxRules().add(taxRule);
                orderRepository.save(order);
            }

        });
    }

    @Transactional
    public TaxConfig createTaxConfigWithRule(String countryCode, TaxRule taxRule) {
        TaxConfig taxConfig = new TaxConfigBuilder()
                .withCountryCode(countryCode)
                .withMaxRulesCount(10)
                .build();
        taxConfig.add(taxRule);


        taxConfigRepository.save(taxConfig);
        return taxConfig;
    }

    @Transactional
    public TaxConfig createTaxConfigWithRule(String countryCode, int maxRulesCount, TaxRule taxRule) {
        TaxConfig taxConfig = new TaxConfigBuilder()
                .withCountryCode(countryCode)
                .withMaxRulesCount(maxRulesCount)
                .build();
        taxConfig.add(taxRule);
        return taxConfigRepository.save(taxConfig);
    }

    @Transactional
    public void addTaxRuleToCountry(String countryCode, int aFactor, int bFactor, int cFactor, String taxCode) {
        if (aFactor == 0) {
            throw new IllegalStateException("Invalid aFactor");
        }
        TaxRule taxRule = new TaxRule();
        taxRule.setaSquareFactor(aFactor);
        taxRule.setbSquareFactor(bFactor);
        taxRule.setcSuqreFactor(cFactor);
        taxRule.setSquare(true);
        int year = Year.now().getValue();
        taxRule.setTaxCode("A. 899. " + year + taxCode);
        TaxConfig taxConfig = taxConfigRepository.findByCountryCode(countryCode);
        if (taxConfig == null) {
            createTaxConfigWithRule(countryCode, taxRule);
        }
        taxConfig.add(taxRule);

    }

    @Transactional
    public void deleteRule(Long taxRuleId, Long configId) {
        TaxRule taxRule = taxRuleRepository.getOne(taxRuleId);
        TaxConfig taxConfig = taxConfigRepository.getOne(configId);

        taxConfig.remove(taxRule);

    }

    @Transactional
    public List<TaxRule> findRules(String countryCode) {
        return taxConfigRepository.findByCountryCode(countryCode).getTaxRules();
    }

    @Transactional
    public int rulesCount(String countryCode) {
        return taxConfigRepository.findByCountryCode(countryCode).getCurrentRulesCount();
    }

    public List<TaxConfig> findAllConfigs() {
        return taxConfigRepository.findAll();
    }
}

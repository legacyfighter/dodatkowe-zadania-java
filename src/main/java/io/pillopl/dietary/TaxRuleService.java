package io.pillopl.dietary;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        TaxConfig taxConfig = taxConfigRepository.findByCountryCode(countryCode);
        if (taxConfig == null) {
            taxConfig = createTaxConfigWithRule(countryCode, taxRule);
        }

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
        TaxConfig taxConfig = new TaxConfig();
        taxConfig.setCountryCode(countryCode);
        taxConfig.getTaxRules().add(taxRule);
        taxConfig.setCurrentRulesCount(taxConfig.getTaxRules().size());
        if (countryCode == null || countryCode.equals("") || countryCode.length() == 1) {
            throw new IllegalStateException("Invalid country code");
        }

        taxConfigRepository.save(taxConfig);
        return taxConfig;
    }

    @Transactional
    public void addTaxRuleToCountry(String countryCode, int aFactor, int bFactor, int cFactor, String taxCode) {
        if (aFactor == 0) {
            throw new IllegalStateException("Invalid aFactor");
        }
        if (countryCode == null || countryCode.equals("") || countryCode.length() == 1) {
            throw new IllegalStateException("Invalid country code");
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
    }

    @Transactional
    public void deleteRule(Long taxRuleId) {
        TaxRule taxRule = taxRuleRepository.getOne(taxRuleId);
        TaxConfig config = taxRule.getTaxConfig();
        if (config.getTaxRules().size() == 1) {
            throw new IllegalStateException("Last rule in country config");
        }
        taxRuleRepository.delete(taxRule);
    }


    public List<TaxConfig> findAllConfigs() {
        return taxConfigRepository.findAll();
    }
}

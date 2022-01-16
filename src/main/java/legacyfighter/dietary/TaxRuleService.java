package legacyfighter.dietary;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        TaxRule taxRule = TaxRule.linearRule(aFactor, bFactor, taxCode);
        TaxConfig taxConfig = taxConfigRepository.findByCountryCode(CountryCode.of(countryCode));
        if (taxConfig == null) {
            createTaxConfigWithRule(countryCode, taxRule);
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
        TaxConfig taxConfig = new TaxConfig(countryCode, 1, taxRule);
        taxConfigRepository.save(taxConfig);
        return taxConfig;
    }

    @Transactional
    public void addTaxRuleToCountry(String countryCode, int aFactor, int bFactor, int cFactor, String taxCode) {
        TaxRule taxRule = TaxRule.squareRule(aFactor, bFactor, cFactor, taxCode);
        TaxConfig taxConfig = taxConfigRepository.findByCountryCode(CountryCode.of(countryCode));
        if (taxConfig == null) {
            createTaxConfigWithRule(countryCode, taxRule);
        } else {
            taxConfig.add(taxRule);
        }
    }

    @Transactional
    public void deleteRule(Long taxRuleId, Long configId) {
        TaxRule taxRule = taxRuleRepository.getOne(taxRuleId);
        TaxConfig taxConfig = taxConfigRepository.getOne(configId);
        taxConfig.remove(taxRule);

    }

    public List<TaxConfig> findAllConfigs() {
        return taxConfigRepository.findAll();
    }
}

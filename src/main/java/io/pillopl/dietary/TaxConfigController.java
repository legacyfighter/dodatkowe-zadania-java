package io.pillopl.dietary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
public class TaxConfigController {

    @Autowired
    private TaxRuleService taxRuleService;

    @GetMapping("/config")
    public Map<String, List<TaxRule>> taxConfigs() {
        List<TaxConfig> taxConfigs = taxRuleService.findAllConfigs();

        Map<String, List<TaxRule>> map = new HashMap<>();
        for (TaxConfig tax : taxConfigs) {
            if (map.get(tax.getCountryCode()) == null) {
                if (tax.getTaxRules() == null) {
                    map.put(tax.getCountryCode(), new ArrayList<>());
                } else {
                    map.put(tax.getCountryCode(), tax.getTaxRules());
                }


            } else {
                map.get(tax.getCountryCode()).addAll(tax.getTaxRules());
            }
        }
        Map<String, List<TaxRule>> newRuleMap= new HashMap<>();
        for (Map.Entry<String, List<TaxRule>> taxList : map.entrySet()) {
            Collection<TaxRule> values = taxList.getValue();
            List<TaxRule> newList = values
                    .stream()
                    .filter(Utils.distinctByKey(TaxRule::getTaxCode))
                    .collect(Collectors.toList());
            newRuleMap.put(taxList.getKey(), newList);
        }

        return newRuleMap;

    }


}

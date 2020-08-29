package io.pillopl.dietary;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaxRuleRepository extends JpaRepository<TaxRule, Long> {

    TaxRule findByTaxCodeContaining(String taxCode);
}

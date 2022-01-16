package legacyfighter.dietary;

import org.springframework.data.jpa.repository.JpaRepository;


public interface TaxRuleRepository extends JpaRepository<TaxRule, Long> {

    TaxRule findByTaxCodeContaining(String taxCode);

}

package legacyfighter.dietary;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxConfigRepository extends JpaRepository<TaxConfig, Long> {
    TaxConfig findByCountryCode(CountryCode countryCode);

}

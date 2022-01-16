package legacyfighter.dietary;

import javax.persistence.*;
import java.time.Instant;
import java.util.*;

@Entity
public class TaxConfig {

    @Id
    @GeneratedValue
    private Long id;

    private String description;

    private String countryReason;

    @Embedded
    private CountryCode countryCode;

    private Instant lastModifiedDate;

    private int currentRulesCount;

    private int maxRulesCount;

    @OneToMany(cascade = CascadeType.ALL)
    private List<TaxRule> taxRules = new ArrayList<>(); //usuwanieo z lastModifiedDate + liczniki,

    public TaxConfig() {
    }

    TaxConfig(int maxRulesCount, String countryCode) {
        this.maxRulesCount = maxRulesCount;
        this.countryCode = CountryCode.of(countryCode);
    }

    void add(TaxRule taxRule) {
        if (maxRulesCount <= taxRules.size()) {
            throw new IllegalStateException("Too many rules");
        }
        this.taxRules.add(taxRule);
        this.currentRulesCount++;
        this.lastModifiedDate = Instant.now();
    }

    void remove(TaxRule taxRule) {
        if (taxRules.contains(taxRule)) {
            if (taxRules.size() == 1) {
                throw new IllegalStateException("Last rule in country config");
            }
        }
        taxRules.remove(taxRule);
        this.currentRulesCount--;
        lastModifiedDate = Instant.now();
    }

    int getCurrentRulesCount() {
        return currentRulesCount;
    }

    String getCountryCode() {
        return countryCode.asString();
    }

    List<TaxRule> getTaxRules() {
        return Collections.unmodifiableList(taxRules);
    }

    Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxConfig taxConfig = (TaxConfig) o;
        return id.equals(taxConfig.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

@Embeddable
class CountryCode {

    private String code;

    public CountryCode() {

    }

    CountryCode(String code) {
        this.code = code;
    }

    static CountryCode of(String code) {
        if (code == null || code.equals("") || code.length() == 1) {
            throw new IllegalStateException("Invalid country code");
        }
        return new CountryCode(code);
    }

    String asString() {
        return code;
    }
}
package legacyfighter.dietary;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taxConfig")
    private List<TaxRule> taxRules = new ArrayList<>(); //usuwanieo z lastModifiedDate + liczniki,

    public TaxConfig(String countryCode, int maxRules, TaxRule aTaxRuleWithParams) {
        this.countryCode = CountryCode.of(countryCode);
        this.maxRulesCount = maxRules;
        add(aTaxRuleWithParams);
    }

    public TaxConfig() {

    }


    public List<TaxRule> getTaxRules() {
        return Collections.unmodifiableList(taxRules);
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

    public Long getId() {
        return id;
    }

    public void remove(TaxRule taxRule) {
        if (taxRules.contains(taxRule)) {
            if (getTaxRules().size() == 1) {
                throw new IllegalStateException("Last rule in country config");
            }
            taxRules.remove(taxRule);
            currentRulesCount--;
            lastModifiedDate = Instant.now();
        }
    }

    public void add(TaxRule taxRule) {
        if (maxRulesCount <= currentRulesCount) {
            throw new IllegalStateException("Too many rules");
        }
        taxRule.setTaxConfig(this);
        taxRules.add(taxRule);
        currentRulesCount++;
        lastModifiedDate = Instant.now();
    }

    public int getCurrentRulesCount() {
        return currentRulesCount;
    }

    public String getCountryCode() {
        return countryCode.asString();
    }
}

@Embeddable
class CountryCode {

    CountryCode() {

    }

    CountryCode(String code) {
        this.code = code;
    }

    private String code;

    static CountryCode of(String code) {
        if (code == null || code.equals("") || code.length() == 1) {
            throw new IllegalStateException("Invalid country code");
        }
        return new CountryCode(code);
    }

    public String asString() {
        return code;
    }
}

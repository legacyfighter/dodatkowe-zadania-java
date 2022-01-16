package legacyfighter.dietary;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class TaxConfig {

    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private String countryReason;
    private String countryCode;
    private Instant lastModifiedDate;
    private int currentRulesCount;
    private int maxRulesCount;

    @OneToMany(cascade = CascadeType.ALL)
    private List<TaxRule> taxRules = new ArrayList<>(); //usuwanieo z lastModifiedDate + liczniki,

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountryReason() {
        return countryReason;
    }

    public void setCountryReason(String countryReason) {
        this.countryReason = countryReason;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }


    public int getCurrentRulesCount() {
        return currentRulesCount;
    }

    public void setCurrentRulesCount(int currentRulesCount) {
        this.currentRulesCount = currentRulesCount;
    }

    public int getMaxRulesCount() {
        return maxRulesCount;
    }

    public void setMaxRulesCount(int maxRulesCount) {
        this.maxRulesCount = maxRulesCount;
    }



    public List<TaxRule> getTaxRules() {
        return taxRules;
    }

    public void setTaxRules(List<TaxRule> taxRules) {
        this.taxRules = taxRules;
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
}

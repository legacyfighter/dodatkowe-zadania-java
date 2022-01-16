package legacyfighter.dietary;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.Objects;

@Entity
public class TaxRule {

    public TaxRule() {

    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String taxCode;

    private boolean isLinear;
    private Integer aFactor;
    private Integer bFactor;

    private boolean isSquare;
    private Integer aSquareFactor;
    private Integer bSquareFactor;
    private Integer cSuqreFactor;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "config_id",
            referencedColumnName = "id"
    )
    private TaxConfig taxConfig;

    public static TaxRule linearRule(int a, int b, String taxCode) {
        if (a == 0) {
            throw new IllegalStateException("Invalid aFactor");

        }
        int year = Year.now().getValue();
        return new TaxRuleBuilder()
                .withIsLinear(true)
                .withAFactor(a)
                .withBFactor(b)
                .withTaxCode("A. 899. " + year + taxCode)
                .build();
    }

    public static TaxRule squareRule(int a, int b, int c, String taxCode) {
        if (a == 0) {
            throw new IllegalStateException("Invalid aFactor");
        }
        int year = Year.now().getValue();
        return new TaxRuleBuilder()
                .withIsSquare(true)
                .withASquareFactor(a)
                .withBSquareFactor(b)
                .withCSuqreFactor(c)
                .withTaxCode("A. 899. " + year + taxCode)
                .build();
    }


    public BigDecimal calculate(BigDecimal x) {
        if (isLinear && aFactor != null && bFactor != null) {
            return (x.multiply(new BigDecimal(aFactor))).add(new BigDecimal(bFactor));
        }
        if (isSquare && aSquareFactor != null && bSquareFactor != null && cSuqreFactor != null) {
            return x.pow(2).multiply(new BigDecimal(aSquareFactor)).add((x.multiply(new BigDecimal(bSquareFactor)))).add(new BigDecimal(cSuqreFactor));
        }
        return x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaxRule)) {
            return false;
        }
        TaxRule that = (TaxRule) o;
        return taxCode.equals(that.getTaxCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(taxCode);
    }


    public String getTaxCode() {
        return taxCode;
    }

    public Long getId() {
        return id;
    }

    public void setTaxConfig(TaxConfig taxConfig) {
        this.taxConfig = taxConfig;
    }

    public static final class TaxRuleBuilder {
        private Long id;
        private String taxCode;
        private boolean isLinear;
        private Integer aFactor;
        private Integer bFactor;
        private boolean isSquare;
        private Integer aSquareFactor;
        private Integer bSquareFactor;
        private Integer cSuqreFactor;
        private TaxConfig taxConfig;

        private TaxRuleBuilder() {
        }

        public static TaxRuleBuilder aTaxRule() {
            return new TaxRuleBuilder();
        }

        public TaxRuleBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public TaxRuleBuilder withTaxCode(String taxCode) {
            this.taxCode = taxCode;
            return this;
        }

        public TaxRuleBuilder withIsLinear(boolean isLinear) {
            this.isLinear = isLinear;
            return this;
        }

        public TaxRuleBuilder withAFactor(Integer aFactor) {
            this.aFactor = aFactor;
            return this;
        }

        public TaxRuleBuilder withBFactor(Integer bFactor) {
            this.bFactor = bFactor;
            return this;
        }

        public TaxRuleBuilder withIsSquare(boolean isSquare) {
            this.isSquare = isSquare;
            return this;
        }

        public TaxRuleBuilder withASquareFactor(Integer aSquareFactor) {
            this.aSquareFactor = aSquareFactor;
            return this;
        }

        public TaxRuleBuilder withBSquareFactor(Integer bSquareFactor) {
            this.bSquareFactor = bSquareFactor;
            return this;
        }

        public TaxRuleBuilder withCSuqreFactor(Integer cSuqreFactor) {
            this.cSuqreFactor = cSuqreFactor;
            return this;
        }

        public TaxRuleBuilder withTaxConfig(TaxConfig taxConfig) {
            this.taxConfig = taxConfig;
            return this;
        }

        public TaxRule build() {
            TaxRule taxRule = new TaxRule();
            taxRule.cSuqreFactor = this.cSuqreFactor;
            taxRule.id = this.id;
            taxRule.isSquare = this.isSquare;
            taxRule.bFactor = this.bFactor;
            taxRule.aSquareFactor = this.aSquareFactor;
            taxRule.taxCode = this.taxCode;
            taxRule.aFactor = this.aFactor;
            taxRule.taxConfig = this.taxConfig;
            taxRule.isLinear = this.isLinear;
            taxRule.bSquareFactor = this.bSquareFactor;
            return taxRule;
        }
    }

    public TaxConfig getTaxConfig() {
        return taxConfig;
    }


}

class TaxRulesAggregation {

    private final List<TaxRule> rules;

    TaxRulesAggregation(List<TaxRule> rules) {
        this.rules = rules;
    }

    BigDecimal calculate(BigDecimal x) {
        BigDecimal result = BigDecimal.ZERO;
        for (TaxRule taxRule : rules) {
            result = result.add(taxRule.calculate(x));
        }
        return result;
    }
}
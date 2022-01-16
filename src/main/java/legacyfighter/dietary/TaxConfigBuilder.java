package legacyfighter.dietary;

public final class TaxConfigBuilder {
    private String countryCode;
    private int maxRulesCount;

    public TaxConfigBuilder() {
    }

    public static TaxConfigBuilder aTaxConfig() {
        return new TaxConfigBuilder();
    }

    public TaxConfigBuilder withCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public TaxConfigBuilder withMaxRulesCount(int maxRulesCount) {
        this.maxRulesCount = maxRulesCount;
        return this;
    }

    public TaxConfig build() {
        return new TaxConfig(maxRulesCount, countryCode);
    }
}

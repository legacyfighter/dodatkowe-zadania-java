package legacyfighter.dietary;

import javax.persistence.*;
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

    @ManyToOne
    private TaxConfig taxConfig;

    public static TaxRule linearRule(int a, int b, String taxCode) {
        TaxRule rule = new TaxRule();
        rule.setLinear(true);
        rule.setTaxCode(taxCode);
        rule.setaFactor(a);
        rule.setbSquareFactor(b);
        rule.setTaxCode(taxCode);
        return rule;
    }



    public boolean isLinear() {
        return isLinear;
    }

    public void setLinear(boolean linear) {
        isLinear = linear;
    }

    public Integer getaFactor() {
        return aFactor;
    }

    public void setaFactor(int aFactor) {
        this.aFactor = aFactor;
    }

    public Integer getbFactor() {
        return bFactor;
    }

    public void setbFactor(int bFactor) {
        this.bFactor = bFactor;
    }

    public boolean isSquare() {
        return isSquare;
    }

    public void setSquare(boolean square) {
        isSquare = square;
    }

    public Integer getaSquareFactor() {
        return aSquareFactor;
    }

    public void setaSquareFactor(int aSquareFactor) {
        this.aSquareFactor = aSquareFactor;
    }

    public Integer getbSquareFactor() {
        return bSquareFactor;
    }

    public void setbSquareFactor(int bSquareFactor) {
        this.bSquareFactor = bSquareFactor;
    }

    public Integer getcSuqreFactor() {
        return cSuqreFactor;
    }

    public void setcSuqreFactor(int cSuqreFactor) {
        this.cSuqreFactor = cSuqreFactor;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
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
}

package io.pillopl.dietary;

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

    public TaxConfig getTaxConfig() {
        return taxConfig;
    }

    public void setTaxConfig(TaxConfig taxConfig) {
        this.taxConfig = taxConfig;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxRule taxRule = (TaxRule) o;
        return taxCode.equals(taxRule.taxCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public String getTaxCode() {
        return taxCode;
    }
}

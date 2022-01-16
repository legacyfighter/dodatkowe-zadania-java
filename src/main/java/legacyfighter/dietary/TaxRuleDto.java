package legacyfighter.dietary;

import java.util.Objects;

public class TaxRuleDto {

    public TaxRuleDto(TaxRule taxRule) {
        this.formattedTaxCode = " informal 671 " + taxRule.getTaxCode() + " *** ";
        this.isLinear = taxRule.isLinear();
        this.aFactor = taxRule.getaFactor();
        this.bFactor = taxRule.getbFactor();
        this.isSquare = taxRule.isSquare();
        this.aSquareFactor = taxRule.getaSquareFactor();
        this.bSquareFactor = taxRule.getbSquareFactor();
        this.cSquareFactor = taxRule.getbSquareFactor();
    }

    public String formattedTaxCode;


    private Long id;

    private boolean isLinear;
    private int aFactor;
    private int bFactor;

    private boolean isSquare;
    private int aSquareFactor;
    private int bSquareFactor;
    private int cSquareFactor;

    public boolean isLinear() {
        return isLinear;
    }

    public void setLinear(boolean linear) {
        isLinear = linear;
    }

    public int getaFactor() {
        return aFactor;
    }

    public void setaFactor(int aFactor) {
        this.aFactor = aFactor;
    }

    public int getbFactor() {
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

    public int getaSquareFactor() {
        return aSquareFactor;
    }

    public void setaSquareFactor(int aSquareFactor) {
        this.aSquareFactor = aSquareFactor;
    }

    public int getbSquareFactor() {
        return bSquareFactor;
    }

    public void setbSquareFactor(int bSquareFactor) {
        this.bSquareFactor = bSquareFactor;
    }

    public int getcSquareFactor() {
        return cSquareFactor;
    }

    public void setcSquareFactor(int cSquareFactor) {
        this.cSquareFactor = cSquareFactor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxRuleDto taxRule = (TaxRuleDto) o;
        return id.equals(taxRule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}

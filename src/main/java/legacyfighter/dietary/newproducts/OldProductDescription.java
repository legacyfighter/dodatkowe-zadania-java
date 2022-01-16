package legacyfighter.dietary.newproducts;


public class OldProductDescription {

    public OldProductDescription(OldProduct oldProduct) {
        this.oldProduct = oldProduct;
    }

    private OldProduct oldProduct;

    void replaceCharFromDesc(char charToReplace, char replaceWith) {
        oldProduct.replaceCharFromDesc(charToReplace, replaceWith);
    }

    String formatDesc() {
        return oldProduct.formatDesc();
    }


}


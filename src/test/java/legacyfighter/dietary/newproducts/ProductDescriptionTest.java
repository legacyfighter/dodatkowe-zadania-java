package legacyfighter.dietary.newproducts;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductDescriptionTest {


    @Test
    void canFormatDescription() {
        //expect
        assertEquals("short *** long", productWithDesc("short", "long").formatDesc());
        assertEquals("", productWithDesc("short", "").formatDesc());
        assertEquals("", productWithDesc("", "long2").formatDesc());
    }

    @Test
    void canChangeCharInDescription() {
        //given
        OldProductDescription oldProduct = productWithDesc("short", "long");

        //when
        oldProduct.replaceCharFromDesc('s', 'z');

        //expect
        assertEquals("zhort *** long", oldProduct.formatDesc());

    }


    OldProductDescription productWithDesc(String desc, String longDesc) {
        return new OldProductDescription(new OldProduct(BigDecimal.TEN, desc, longDesc, 10));
    }
}
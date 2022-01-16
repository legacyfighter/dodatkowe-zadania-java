package legacyfighter.dietary.newproducts;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OldProductTest {


    @Test
    void priceCannotBeNull() {
        assertThrows(IllegalStateException.class, () -> Price.of(null));
    }


    @Test
    void canIncrementCounterIfPriceIsPositive() {
        //given
        OldProduct p = productWithPriceAndCounter(BigDecimal.TEN, 10);

        //when
        p.incrementCounter();

        //then
        assertEquals(11, p.getCounter());
    }



    @Test
    void cannotIncrementCounterIfPriceIsNotPositive() {
        //given
        OldProduct p = productWithPriceAndCounter(BigDecimal.ZERO, 10);

        //expect
        assertThrows(IllegalStateException.class, p::incrementCounter);

    }

    @Test
    void canDecrementCounterIfPriceIsPositive() {
        //given
        OldProduct p = productWithPriceAndCounter(BigDecimal.TEN, 10);

        //when
        p.decrementCounter();

        //then
        assertEquals(9, p.getCounter());
    }

    @Test
    void cannotDecrementCounterIfPriceIsNotPositive() {
        //given
        OldProduct p = productWithPriceAndCounter(BigDecimal.ZERO, 0);

        //expect
        assertThrows(IllegalStateException.class, p::decrementCounter);

    }

    @Test
    void canChangePriceIfCounterIsPositive() {
        //given
        OldProduct p = productWithPriceAndCounter(BigDecimal.ZERO, 10);

        //when
        p.changePriceTo(BigDecimal.TEN);

        //then
        assertEquals(BigDecimal.TEN, p.getPrice());
    }

    @Test
    void cannotChangePriceIfCounterIsNotPositive() {
        //given
        OldProduct p = productWithPriceAndCounter(BigDecimal.ZERO, 0);

        //when
        p.changePriceTo(BigDecimal.TEN);

        //then
        assertEquals(BigDecimal.ZERO, p.getPrice());
    }



    OldProduct productWithPriceAndCounter(BigDecimal price, int counter) {
        return new OldProduct(price, "desc", "longDesc", counter);
    }

    OldProduct productWithDesc(String desc, String longDesc) {
        return new OldProduct(BigDecimal.TEN, desc, longDesc, 10);
    }
}
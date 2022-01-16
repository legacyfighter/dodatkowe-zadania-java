package legacyfighter.dietary.newproducts;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;

@SpringBootTest
class OldProductServiceTest {

    @Autowired
    OldProductService oldProductService;

    @Autowired
    OldProductRepository oldProductRepository;

    @Test
    void canListAllProductsDecsriptions() {
        //given
        oldProductRepository.save(productWithDesc("desc1", "longDesc1"));
        oldProductRepository.save(productWithDesc("desc2", "longDesc2"));

        //when
        List<String> allDescriptions = oldProductService.findAllDescriptions();

        //then
        Assertions.assertThat(allDescriptions).contains("desc1 *** longDesc1");
        Assertions.assertThat(allDescriptions).contains("desc2 *** longDesc2");

    }

    @Test
    void canDecrementCounter() {
        //given
        OldProduct oldProduct = oldProductRepository.save(productWithPriceAndCounter(BigDecimal.TEN, 10));

        //when
        oldProductService.decrementCounter(oldProduct.serialNumber());

        //then
        Assertions.assertThat(oldProductService.getCounterOf(oldProduct.serialNumber())).isEqualTo(9);
    }

    @Test
    void canIncrementCounter() {
        //given
        OldProduct oldProduct = oldProductRepository.save(productWithPriceAndCounter(BigDecimal.TEN, 10));

        //when
        oldProductService.incrementCounter(oldProduct.serialNumber());

        //then
        Assertions.assertThat(oldProductService.getCounterOf(oldProduct.serialNumber())).isEqualTo(11);
    }

    @Test
    void canChangePrice() {
        //given
        OldProduct oldProduct = oldProductRepository.save(productWithPriceAndCounter(BigDecimal.TEN, 10));

        //when
        oldProductService.changePriceOf(oldProduct.serialNumber(), ZERO);

        //then
        Assertions.assertThat(oldProductService.getPriceOf(oldProduct.serialNumber())).isZero();
    }

    OldProduct productWithPriceAndCounter(BigDecimal price, int counter) {
        return new OldProduct(price, "desc", "longDesc", counter);
    }

    OldProduct productWithDesc(String desc, String longDesc) {
        return new OldProduct(BigDecimal.TEN, desc, longDesc, 10);
    }

}
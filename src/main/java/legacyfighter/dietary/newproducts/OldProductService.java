
package legacyfighter.dietary.newproducts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OldProductService {

    @Autowired
    OldProductRepository oldProductRepository;

    public List<String> findAllDescriptions() {
        return
                oldProductRepository
                .findAll()
                .stream()
                .map(OldProduct::formatDesc)
                .collect(Collectors.toList());
    }

    @Transactional
    public void replaceCharInDesc(UUID productId, char oldChar, char newChar) {
        OldProduct product = oldProductRepository.getOne(productId);
        product.replaceCharFromDesc(oldChar, newChar);
    }

    @Transactional
    public void incrementCounter(UUID productId) {
        OldProduct product = oldProductRepository.getOne(productId);
        product.incrementCounter();
    }

    @Transactional
    public void decrementCounter(UUID productId) {
        OldProduct product = oldProductRepository.getOne(productId);
        product.decrementCounter();
    }

    @Transactional
    public void changePriceOf(UUID productId, BigDecimal newPrice) {
        OldProduct product = oldProductRepository.getOne(productId);
        product.changePriceTo(newPrice);
    }

    @Transactional
    public int getCounterOf(UUID serialNumber) {
        return oldProductRepository.getOne(serialNumber).getCounter();
    }

    @Transactional
    public BigDecimal getPriceOf(UUID serialNumber) {
        return oldProductRepository.getOne(serialNumber).getPrice();
    }

}

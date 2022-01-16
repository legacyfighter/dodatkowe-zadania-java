package legacyfighter.dietary.newproducts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OldProductRepository extends JpaRepository<OldProduct, UUID> {

}

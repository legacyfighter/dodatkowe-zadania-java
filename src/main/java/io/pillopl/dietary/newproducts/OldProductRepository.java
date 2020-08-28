package io.pillopl.dietary.newproducts;

import io.pillopl.dietary.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OldProductRepository extends JpaRepository<OldProduct, UUID> {

}

package org.bsuir.repository;

import org.bsuir.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Orders o WHERE o.idOrder NOT IN :ids")
    void deleteAllNotIn(@Param("ids") List<Long> ids);
}

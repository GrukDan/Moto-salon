package org.bsuir.repository;

import org.bsuir.model.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProducerRepository extends JpaRepository<Producer,Long> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Producer p WHERE p.idProducer NOT IN :ids")
    void deleteAllNotIn(@Param("ids") List<Long> ids);
}

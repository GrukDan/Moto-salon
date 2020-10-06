package org.bsuir.repository;

import org.bsuir.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByEmailAndPassword(String email,String password);

    User findByEmailAndPassword(String email,String password);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM User u WHERE u.idUser NOT IN :ids")
    void deleteAllNotIn(@Param("ids") List<Long> ids);
}

package com.example.todolist.Repository;

import com.example.todolist.model.People;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface peopleRepository extends CrudRepository<People,Integer> {
    @Query("select u from People u where u.login = ?1")
    Optional<People> findByUsername(String username);
    @Modifying
    @Transactional
    @Query("update People set password=:password where email=:email")
    void changePassword(@Param("email") String email,@Param("password") String encode);
}

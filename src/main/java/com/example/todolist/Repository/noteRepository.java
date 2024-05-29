package com.example.todolist.Repository;

import com.example.todolist.model.Notes;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface noteRepository extends JpaRepository<Notes, Integer> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Notes (notes,description, date,people_id) VALUES (:notes,:description, :date,:id)", nativeQuery = true)
    void addNotes(@Param("notes") String notes,@Param("description") String description, @Param("date") LocalDateTime date, @NotNull @Param("id")  long id);
    @Modifying
    @Transactional
    @Query(value = "UPDATE Notes Set notes=:text, description=:description, date=:datetima where id=:id")
    void updateNotes(@Param("id") int id, @Param("text") String notesText, @Param("description") String description, @Param("datetima") LocalDateTime now);
}

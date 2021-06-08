package com.example.project_web.repository;

import com.example.project_web.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {
    Trainer findTrainerById(int id);

    void deleteById(int id);

    @Query(value = "SELECT name FROM trainer group by name", nativeQuery = true)
    List<String> findAllByName();
}

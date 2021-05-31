package com.example.project_web.repository;

import com.example.project_web.entity.Invoice;
import com.example.project_web.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Trainerrepository extends JpaRepository<Trainer, Integer> {
    Trainer findTrainerById(int id);

    void deleteById(int id);
}

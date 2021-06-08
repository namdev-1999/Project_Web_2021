package com.example.project_web.repository;

import com.example.project_web.entity.Course;
import com.example.project_web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    Course findCourseById(int id);

    void deleteById(int id);

    @Query(value = "SELECT name FROM course group by name", nativeQuery = true)
    List<String> findAllByName();
}

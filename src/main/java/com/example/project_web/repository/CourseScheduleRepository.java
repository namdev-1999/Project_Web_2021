package com.example.project_web.repository;


import com.example.project_web.entity.CourseSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseScheduleRepository extends JpaRepository<CourseSchedule, Integer> {

    CourseSchedule findCourseScheduleById(int id);

    void deleteById(int id);

    @Query(value = "SELECT * FROM course_schedule where day = ?1", nativeQuery = true)
    List<CourseSchedule> findCourseScheduleByTH(String th);
}

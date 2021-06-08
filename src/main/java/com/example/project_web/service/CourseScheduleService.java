package com.example.project_web.service;

import com.example.project_web.entity.CourseSchedule;
import com.example.project_web.repository.CourseScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseScheduleService {
    @Autowired
    CourseScheduleRepository courseScheduleRepository;

    public List<CourseSchedule> findCourseScheduleByTH(String th) {
        return courseScheduleRepository.findCourseScheduleByTH(th);
    }
    public List<CourseSchedule> findCourseSchedule() {
        return courseScheduleRepository.findAll();
    }

    public CourseSchedule addCourseSchedule(CourseSchedule course_schedule) {
        return courseScheduleRepository.save(course_schedule);
    }

    public CourseSchedule findCourseScheduleById(int id) {
        return courseScheduleRepository.findCourseScheduleById(id);
    }

    public void update(CourseSchedule course_schedule) {
        System.out.println(course_schedule);
        CourseSchedule c = courseScheduleRepository.findCourseScheduleById(course_schedule.getId());
        c.setCourse(course_schedule.getCourse());
        c.setRoom(course_schedule.getRoom());
        c.setTrainer(course_schedule.getTrainer());
        c.setTime_from(course_schedule.getTime_from());
        c.setTime_to(course_schedule.getTime_to());
        courseScheduleRepository.save(course_schedule);
    }

    public void deleteById(int id) {
        courseScheduleRepository.deleteById(id);
    }
}

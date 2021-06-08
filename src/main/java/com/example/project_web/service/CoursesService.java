package com.example.project_web.service;

import com.example.project_web.entity.Course;
import com.example.project_web.entity.Trainer;
import com.example.project_web.entity.User;
import com.example.project_web.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoursesService {
    @Autowired
    CourseRepository courseRepository;

    // Lấy danh sách các khóa học
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    //Lấy tên các khóa học
    public List<String> findAllByName() {
        return courseRepository.findAllByName();
    }

    //Thêm course mới
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    // Tìm course theo id
    public Course findCourseById(int id) {
        return courseRepository.findCourseById(id);
    }

    // Chỉnh sửa thông tin user
    public void update(Course course) {
        Course c = courseRepository.findCourseById(course.getId());
        c.setName(course.getName());
        c.setDescription(course.getDescription());
        c.setStart_date(course.getStart_date());
        c.setEnd_date(course.getEnd_date());
        c.setPrice(course.getPrice());
        //note
//        c.setTrainer(new Trainer());
        courseRepository.save(course);
    }

    // Xóa course theo id
    public void deleteById(int id) {
        courseRepository.deleteById(id);
    }
}

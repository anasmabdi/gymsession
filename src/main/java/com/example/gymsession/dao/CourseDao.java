package com.example.gymsession.dao;

import com.example.gymsession.entity.Course;
import com.example.gymsession.entity.Trainee;
import com.example.gymsession.entity.Trainer;

import java.util.List;

public interface CourseDao {
    Course getCourseById(int id);
    List<Course> getAllCourses();
    Course addCourse(Course course);
    void updateCourse(Course course);
    void deleteCourseById(int id);

    List<Course> getCoursesForTrainer(Trainer trainer);
    List<Course> getCoursesForTrainee(Trainee trainee);
}
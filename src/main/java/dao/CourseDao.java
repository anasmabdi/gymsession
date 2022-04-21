package dao;

import entity.Course;
import entity.Trainee;
import entity.Trainer;

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
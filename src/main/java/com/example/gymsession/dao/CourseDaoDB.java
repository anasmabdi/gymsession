package com.example.gymsession.dao;

import com.example.gymsession.entity.Trainer;
import com.example.gymsession.entity.Course;
import com.example.gymsession.entity.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CourseDaoDB implements CourseDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Course getCourseById(int id) {
        try {
            final String SELECT_COURSE_BY_ID = "SELECT * FROM course WHERE id = ?";
            Course course = jdbc.queryForObject(SELECT_COURSE_BY_ID, new CourseMapper(), id);
            course.setTrainer(getTrainerForCourse(id));
            course.setTrainees(getTraineesForCourse(id));
            return course;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    private Trainer getTrainerForCourse(int id) {
        final String SELECT_TRAINER_FOR_COURSE = "SELECT t.* FROM trainer t "
                + "JOIN course c ON c.trainerId = t.id WHERE c.id = ?";
        return jdbc.queryForObject(SELECT_TRAINER_FOR_COURSE, new TrainerDaoDB.TrainerMapper(), id);
    }

    private List<Trainee> getTraineesForCourse(int id) {
        final String SELECT_TRAINEES_FOR_COURSE = "SELECT s.* FROM trainee s "
                + "JOIN course_trainee cs ON cs.traineeId = s.id WHERE cs.courseId = ?";
        return jdbc.query(SELECT_TRAINEES_FOR_COURSE, new TraineeDaoDB.TraineeMapper(), id);
    }

    @Override
    public List<Course> getAllCourses() {
        final String SELECT_ALL_COURSES = "SELECT * FROM course";
        List<Course> courses = jdbc.query(SELECT_ALL_COURSES, new CourseMapper());
        associateTrainerAndTrainees(courses);
        return courses;
    }

    private void associateTrainerAndTrainees(List<Course> courses) {
        for (Course course : courses) {
            course.setTrainer(getTrainerForCourse(course.getId()));
            course.setTrainees(getTraineesForCourse(course.getId()));
        }
    }

    @Override
    @Transactional
    public Course addCourse(Course course) {
        final String INSERT_COURSE = "INSERT INTO course(name, description, trainerId) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_COURSE,
                course.getName(),
                course.getDescription(),
                course.getTrainer().getId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        course.setId(newId);
        insertCourseTrainee(course);
        return course;
    }

    private void insertCourseTrainee(Course course) {
        final String INSERT_COURSE_TRAINEE = "INSERT INTO "
                + "course_trainee(courseId, traineeId) VALUES(?,?)";
        for(Trainee trainee : course.getTrainee()) {
            jdbc.update(INSERT_COURSE_TRAINEE,
                    course.getId(),
                    trainee.getId());
        }
    }

    @Override
    @Transactional
    public void updateCourse(Course course) {
        final String UPDATE_COURSE = "UPDATE course SET name = ?, description = ?, "
                + "trainerId = ? WHERE id = ?";
        jdbc.update(UPDATE_COURSE,
                course.getName(),
                course.getDescription(),
                course.getTrainer().getId(),
                course.getId());

        final String DELETE_COURSE_TRAINEE = "DELETE FROM course_trainer WHERE courseId = ?";
        jdbc.update(DELETE_COURSE_TRAINEE, course.getId());
        insertCourseTrainee(course);
    }

    @Override
    @Transactional
    public void deleteCourseById(int id) {
        final String DELETE_COURSE_TRAINEE = "DELETE FROM course_trainee WHERE courseId = ?";
        jdbc.update(DELETE_COURSE_TRAINEE, id);

        final String DELETE_COURSE = "DELETE FROM course WHERE id = ?";
        jdbc.update(DELETE_COURSE, id);
    }

    @Override
    public List<Course> getCoursesForTrainer(Trainer trainer) {
        final String SELECT_COURSES_FOR_TRAINER = "SELECT * FROM course WHERE trainerId = ?";
        List<Course> courses = jdbc.query(SELECT_COURSES_FOR_TRAINER,
                new CourseMapper(), trainer.getId());
        associateTrainerAndTrainees(courses);
        return courses;
    }

    @Override
    public List<Course> getCoursesForTrainee(Trainee trainee) {
        final String SELECT_COURSES_FOR_TRAINEE = "SELECT c.* FROM course c JOIN "
                + "course_trainee cs ON cs.courseId = c.Id WHERE cs.traineeId = ?";
        List<Course> courses = jdbc.query(SELECT_COURSES_FOR_TRAINEE,
                new CourseMapper(), trainee.getId());
        associateTrainerAndTrainees(courses);
        return courses;
    }

    public static final class CourseMapper implements RowMapper<Course> {

        @Override
        public Course mapRow(ResultSet rs, int index) throws SQLException {
            Course course = new Course();
            course.setId(rs.getInt("id"));
            course.setName(rs.getString("name"));
            course.setDescription(rs.getString("description"));
            return course;
        }
    }
}

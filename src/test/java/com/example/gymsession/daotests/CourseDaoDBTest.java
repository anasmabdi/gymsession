package com.example.gymsession.daotests;

import com.example.gymsession.dao.CourseDao;
import com.example.gymsession.dao.TraineeDao;
import com.example.gymsession.dao.TrainerDao;
import com.example.gymsession.entity.Course;
import com.example.gymsession.entity.Trainee;
import com.example.gymsession.entity.Trainer;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseDaoDBTest {

    @Autowired
    TrainerDao trainerDao;

    @Autowired
    TraineeDao traineeDao;

    @Autowired
    CourseDao courseDao;

    public CourseDaoDBTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        List<Trainer> trainers = trainerDao.getAllTrainers();
        for (Trainer trainer : trainers) {
            trainerDao.deleteTrainerById(trainer.getId());
        }

        List<Trainee> trainees = traineeDao.getAllTrainees();
        for (Trainee trainee : trainees) {
            traineeDao.deleteTraineeById(trainee.getId());
        }

        List<Course> courses = courseDao.getAllCourses();
        for (Course course : courses) {
            courseDao.deleteCourseById(course.getId());
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddAndGetCourse() {
        Trainer trainer = new Trainer();
        trainer.setFirstName("Test Trainer First");
        trainer.setLastName("Test Trainer Last");
        trainer.setStrength("Test Trainer Strength");
        trainer = trainerDao.addTrainer(trainer);

        Trainee trainee = new Trainee();
        trainee.setFirstName("Test Trainee First");
        trainee.setLastName("Test Trainee Last");
        trainee = traineeDao.addTrainee(trainee);

        List<Trainee> trainees = new ArrayList<>();
        trainees.add(trainee);

        Course course = new Course();
        course.setName("Test Course Name");
        course.setTrainer(trainer);
        course.setTrainees(trainees);
        course = courseDao.addCourse(course);

        Course fromDao = courseDao.getCourseById(course.getId());
        assertEquals(course, fromDao);
    }

    @Test
    public void testGetAllCourses() {
        Trainer trainer = new Trainer();
        trainer.setFirstName("Test Trainer First");
        trainer.setLastName("Test Trainer Last");
        trainer.setStrength("Test Trainer Strength");
        trainer = trainerDao.addTrainer(trainer);

        Trainee trainee = new Trainee();
        trainee.setFirstName("Test Trainee First");
        trainee.setLastName("Test Trainee Last");
        trainee = traineeDao.addTrainee(trainee);

        List<Trainee> trainees = new ArrayList<>();
        trainees.add(trainee);

        Course course = new Course();
        course.setName("Test Course Name");
        course.setTrainer(trainer);
        course.setTrainees(trainees);
        course = courseDao.addCourse(course);

        Course course2 = new Course();
        course2.setName("Test Course Name 2");
        course2.setTrainer(trainer);
        course2.setTrainees(trainees);
        course2 = courseDao.addCourse(course2);

        List<Course> courses = courseDao.getAllCourses();
        assertEquals(2, courses.size());
        assertTrue(courses.contains(course));
        assertTrue(courses.contains(course2));
    }

}
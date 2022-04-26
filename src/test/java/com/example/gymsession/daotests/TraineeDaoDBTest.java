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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TraineeDaoDBTest {

    @Autowired
    TrainerDao trainerDao;

    @Autowired
    TraineeDao traineeDao;

    @Autowired
    CourseDao courseDao;

    public TraineeDaoDBTest() {
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
    public void testAddAndGetTrainee() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("Test Trainee First");
        trainee.setLastName("Test Trainee Last");
        trainee = traineeDao.addTrainee(trainee);

        Trainee fromDao = traineeDao.getTraineeById(trainee.getId());
        assertEquals(trainee, fromDao);
    }

    @Test
    public void testGetAllTrainees() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("Test Trainee First");
        trainee.setLastName("Test Trainee Last");
        trainee = traineeDao.addTrainee(trainee);

        Trainee trainee2 = new Trainee();
        trainee2.setFirstName("Test Trainee First 2");
        trainee2.setLastName("Test Trainee Last 2");
        trainee2 = traineeDao.addTrainee(trainee2);

        List<Trainee> trainees = traineeDao.getAllTrainees();

        assertEquals(2, trainees.size());
        assertTrue(trainees.contains(trainee));
        assertTrue(trainees.contains(trainee2));
    }

    @Test
    public void testUpateTrainee() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("Test Trainee First");
        trainee.setLastName("Test Trainee Last");
        trainee = traineeDao.addTrainee(trainee);

        Trainee fromDao = traineeDao.getTraineeById(trainee.getId());
        assertEquals(trainee, fromDao);

        trainee.setFirstName("New Test Trainee First");
        traineeDao.updateTrainee(trainee);

        assertNotEquals(trainee, fromDao);

        fromDao = traineeDao.getTraineeById(trainee.getId());

        assertEquals(trainee, fromDao);
    }

    @Test
    public void testDeleteTraineeById() {
        Trainer trainer = new Trainer();
        trainer.setFirstName("Test First");
        trainer.setLastName("Test Last");
        trainer.setStrength("Test Strength");
        trainer = trainerDao.addTrainer(trainer);

        Trainee trainee = new Trainee();
        trainee.setFirstName("Test Trainee First");
        trainee.setLastName("Test Trainee Last");
        trainee = traineeDao.addTrainee(trainee);
        List<Trainee> trainees = new ArrayList<>();
        trainees.add(trainee);

        Course course = new Course();
        course.setName("Test Course");
        course.setTrainer(trainer);
        course.setTrainees(trainees);
//        course = courseDao.addCourse(course);

        Trainee fromDao = traineeDao.getTraineeById(trainee.getId());
        assertEquals(trainee, fromDao);

        traineeDao.deleteTraineeById(trainee.getId());

        fromDao = traineeDao.getTraineeById(trainee.getId());
        assertNull(fromDao);
    }

}
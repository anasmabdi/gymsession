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
public class TrainerDaoDBTest {

    @Autowired
    TrainerDao trainerDao;

    @Autowired
    TraineeDao traineeDao;

    @Autowired
    CourseDao courseDao;


    public TrainerDaoDBTest() {
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
        for(Trainer trainer : trainers) {
            trainerDao.deleteTrainerById(trainer.getId());
        }

        List<Trainee> trainees = traineeDao.getAllTrainees();
        for(Trainee trainee : trainees) {
            traineeDao.deleteTraineeById(trainee.getId());
        }

        List<Course> courses = courseDao.getAllCourses();
        for(Course course : courses) {
            courseDao.deleteCourseById(course.getId());
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddAndGetTrainer() {
        Trainer trainer = new Trainer();
        trainer.setFirstName("Test First");
        trainer.setLastName("Test Last");
        trainer.setStrength("Test Strength");
        trainer = trainerDao.addTrainer(trainer);

        Trainer fromDao = trainerDao.getTrainerById(trainer.getId());

        assertEquals(trainer, fromDao);
    }

    @Test
    public void testGetAllTrainers() {
        Trainer trainer = new Trainer();
        trainer.setFirstName("Test First");
        trainer.setLastName("Test Last");
        trainer.setStrength("Test Strength");
        trainer = trainerDao.addTrainer(trainer);

        Trainer trainer2 = new Trainer();
        trainer2.setFirstName("Test First 2");
        trainer2.setLastName("Test Last 2");
        trainer2.setStrength("Test Strength 2");
        trainer2 = trainerDao.addTrainer(trainer2);

        List<Trainer> trainers = trainerDao.getAllTrainers();

        assertEquals(2, trainers.size());
        assertTrue(trainers.contains(trainer));
        assertTrue(trainers.contains(trainer2));
    }

    @Test
    public void testUpdateTrainer() {
        Trainer trainer = new Trainer();
        trainer.setFirstName("Test First");
        trainer.setLastName("Test Last");
        trainer.setStrength("Test Strength");
        trainer = trainerDao.addTrainer(trainer);

        Trainer fromDao = trainerDao.getTrainerById(trainer.getId());
        assertEquals(trainer, fromDao);

        trainer.setFirstName("New Test First");
        trainerDao.updateTrainer(trainer);

        assertNotEquals(trainer, fromDao);

        fromDao = trainerDao.getTrainerById(trainer.getId());

        assertEquals(trainer, fromDao);
    }

    @Test
    public void testDeleteTrainerById() {
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

        Trainer fromDao = trainerDao.getTrainerById(trainer.getId());
        assertEquals(trainer, fromDao);

        trainerDao.deleteTrainerById(trainer.getId());

        fromDao = trainerDao.getTrainerById(trainer.getId());
        assertNull(fromDao);
    }
}

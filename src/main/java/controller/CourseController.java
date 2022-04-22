package controller;

import dao.CourseDao;
import dao.TraineeDao;
import dao.TrainerDao;
import entity.Course;
import entity.Trainee;
import entity.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CourseController {

    @Autowired
    TrainerDao trainerDao;

    @Autowired
    TraineeDao traineeDao;

    @Autowired
    CourseDao courseDao;

    @GetMapping("courses")
    public String displayCourses(Model model) {
        List<Course> courses = courseDao.getAllCourses();
        List<Trainer> trainers = trainerDao.getAllTrainers();
        List<Trainee> trainees = traineeDao.getAllTrainees();
        model.addAttribute("courses", courses);
        model.addAttribute("trainers", trainers);
        model.addAttribute("trainees", trainees);
        return "courses";
    }

    @PostMapping("addCourse")
    public String addCourse(Course course, HttpServletRequest request) {
        String trainerId = request.getParameter("trainerId");
        String[] traineeIds = request.getParameterValues("traineeId");

        course.setTrainer(trainerDao.getTrainerById(Integer.parseInt(trainerId)));

        List<Trainee> trainees = new ArrayList<>();
        for(String traineeId : traineeIds) {
            trainees.add(traineeDao.getTraineeById(Integer.parseInt(traineeId)));
        }
        course.setTrainees(trainees);
        courseDao.addCourse(course);

        return "redirect:/courses";
    }

    @GetMapping("courseDetail")
    public String courseDetail(Integer id, Model model) {
        Course course = courseDao.getCourseById(id);
        model.addAttribute("course", course);
        return "courseDetail";
    }

    @GetMapping("deleteCourse")
    public String deleteCourse(Integer id) {
        courseDao.deleteCourseById(id);
        return "redirect:/courses";
    }

    @GetMapping("editCourse")
    public String editCourse(Integer id, Model model) {
        Course course = courseDao.getCourseById(id);
        List<Trainee> trainees = traineeDao.getAllTrainees();
        List<Trainer> trainers = trainerDao.getAllTrainers();
        model.addAttribute("course", course);
        model.addAttribute("trainees", trainees);
        model.addAttribute("trainers", trainers);
        return "editCourse";
    }
    @PostMapping("editCourse")
    public String performEditCourse(Course course, HttpServletRequest request) {
        String trainerId = request.getParameter("trainerId");
        String[] traineeIds = request.getParameterValues("studentId");

        course.setTrainer(trainerDao.getTrainerById(Integer.parseInt(trainerId)));

        List<Trainee> trainees = new ArrayList<>();
        for(String traineeId : traineeIds) {
            trainees.add(traineeDao.getTraineeById(Integer.parseInt(traineeId)));
        }
        course.setTrainees(trainees);
        courseDao.updateCourse(course);

        return "redirect:/courses";
    }

}

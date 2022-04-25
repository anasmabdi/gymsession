package com.example.gymsession.controller;

import com.example.gymsession.dao.CourseDao;
import com.example.gymsession.dao.TraineeDao;
import com.example.gymsession.dao.TrainerDao;
import com.example.gymsession.entity.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TraineeController {

    @Autowired
    TrainerDao trainerDao;

    @Autowired
    TraineeDao traineeDao;

    @Autowired
    CourseDao courseDao;

    @GetMapping("trainees")
    public String displayTrainees(Model model) {
        List<Trainee> trainees = traineeDao.getAllTrainees();
        model.addAttribute("trainees", trainees);
        return "trainees";
    }

    @PostMapping("addTrainee")
    public String addTrainee(String firstName, String lastName) {
        Trainee trainee = new Trainee();
        trainee.setFirstName(firstName);
        trainee.setLastName(lastName);
        traineeDao.addTrainee(trainee);

        return "redirect:/trainees";
    }

    @GetMapping("deleteTrainee")
    public String deleteTrainee(Integer id) {
        traineeDao.deleteTraineeById(id);
        return "redirect:/trainees";
    }

    @GetMapping("editTrainee")
    public String editTrainee(Integer id, Model model) {
        Trainee trainee = traineeDao.getTraineeById(id);
        model.addAttribute("trainee", trainee);
        return "editTrainee";
    }

    @PostMapping("editTrainee")
    public String performEditTrainee(Trainee trainee) {
        traineeDao.updateTrainee(trainee);
        return "redirect:/trainees";
    }
}

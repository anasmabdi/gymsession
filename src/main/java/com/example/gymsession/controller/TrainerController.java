package com.example.gymsession.controller;

import com.example.gymsession.dao.CourseDao;
import com.example.gymsession.dao.TraineeDao;
import com.example.gymsession.dao.TrainerDao;
import com.example.gymsession.entity.Trainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@Slf4j
public class TrainerController {

    @Autowired
    TrainerDao trainerDao;

    @Autowired
    TraineeDao traineeDao;

    @Autowired
    CourseDao courseDao;

//    @GetMapping("/home")
//    public ModelAndView index() throws Exception {
//        return new ModelAndView("index");
//    }


    @GetMapping("trainers")
    public String displayTrainers(Model model) {
        List<Trainer> trainers = trainerDao.getAllTrainers();
        model.addAttribute("trainers", trainers);
        log.error(String.valueOf(model));
        return "trainers";
    }

    @PostMapping("addTrainer")
    public String addTrainer(HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String strength = request.getParameter("strength");

        Trainer trainer = new Trainer();
        trainer.setFirstName(firstName);
        trainer.setLastName(lastName);
        trainer.setStrength(strength);

        trainerDao.addTrainer(trainer);

        return "redirect:/trainers";
    }

    @GetMapping("deleteTrainer")
    public String deleteTrainer(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        trainerDao.deleteTrainerById(id);

        return "redirect:/trainers";
    }

    @GetMapping("editTrainer")
    public String editTrainer(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Trainer trainer = trainerDao.getTrainerById(id);

        model.addAttribute("trainer", trainer);
        return "editTrainer";
    }


    @PostMapping("editTrainer")
    public String performEditTrainer(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Trainer trainer = trainerDao.getTrainerById(id);


        trainer.setFirstName(request.getParameter("firstName"));
        trainer.setLastName(request.getParameter("lastName"));
        trainer.setStrength(request.getParameter("strength"));

        trainerDao.updateTrainer(trainer);

        return "redirect:/trainers";
    }
}

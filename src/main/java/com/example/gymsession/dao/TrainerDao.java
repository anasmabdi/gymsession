package com.example.gymsession.dao;

import com.example.gymsession.entity.Trainer;

import java.util.List;

public interface TrainerDao {
    Trainer getTrainerById(int id);
    List<Trainer> getAllTrainers();
    Trainer addTrainer(Trainer trainer);
    void updateTrainer(Trainer trainer);
    void deleteTrainerById(int id);
}
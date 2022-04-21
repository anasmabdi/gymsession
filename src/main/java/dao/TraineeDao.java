package dao;

import entity.Trainee;

import java.util.List;

public interface TraineeDao {
    Trainee getTraineeById(int id);
    List<Trainee> getAllTrainees();
    Trainee addTrainee(Trainee trainee);
    void updateTrainee(Trainee trainee);
    void deleteTraineeById(int id);
}

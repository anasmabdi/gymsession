package dao;

import entity.Trainer;
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
public class TrainerDaoDB implements TrainerDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Trainer getTrainerById(int id) {
        try {
            final String GET_TRAINER_BY_ID = "SELECT * FROM trainer WHERE id = ?";
            return jdbc.queryForObject(GET_TRAINER_BY_ID, new TrainerMapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Trainer> getAllTrainers() {
        final String GET_ALL_TRAINERS = "SELECT * FROM trainer";
        return jdbc.query(GET_ALL_TRAINERS, new TrainerMapper());
    }

    @Override
    @Transactional
    public Trainer addTrainer(Trainer trainer) {
        final String INSERT_TRAINER = "INSERT INTO trainer(firstName, lastName, specialty) " +
                "VALUES(?,?,?)";
        jdbc.update(INSERT_TRAINER,
                trainer.getFirstName(),
                trainer.getLastName(),
                trainer.getStrength());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        trainer.setId(newId);
        return trainer;
    }

    @Override
    public void updateTrainer(Trainer trainer) {
        final String UPDATE_TRAINER = "UPDATE trainer SET firstName = ?, lastName = ?, " +
                "specialty = ? WHERE id = ?";
        jdbc.update(UPDATE_TRAINER,
                trainer.getFirstName(),
                trainer.getLastName(),
                trainer.getStrength(),
                trainer.getId());
    }

    @Override
    @Transactional
    public void deleteTrainerById(int id) {
        final String DELETE_COURSE_TRAINEE = "DELETE cs.* FROM course_trainee cs "
                + "JOIN course c ON cs.courseId = c.Id WHERE c.trainerId = ?";
        jdbc.update(DELETE_COURSE_TRAINEE, id);

        final String DELETE_COURSE = "DELETE FROM course WHERE trainerId = ?";
        jdbc.update(DELETE_COURSE, id);

        final String DELETE_TRAINER = "DELETE FROM trainer WHERE id = ?";
        jdbc.update(DELETE_TRAINER, id);
    }

    public static final class TrainerMapper implements RowMapper<Trainer> {

        @Override
        public Trainer mapRow(ResultSet rs, int index) throws SQLException {
            Trainer trainer = new Trainer();
            trainer.setId(rs.getInt("id"));
            trainer.setFirstName(rs.getString("firstName"));
            trainer.setLastName(rs.getString("lastName"));
            trainer.setStrength(rs.getString("specialty"));

            return trainer;
        }
    }
}
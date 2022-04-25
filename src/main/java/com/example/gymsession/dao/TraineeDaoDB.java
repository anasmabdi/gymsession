package com.example.gymsession.dao;

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
public class TraineeDaoDB implements TraineeDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Trainee getTraineeById(int id) {
        try {
            final String SELECT_TRAINEE_BY_ID = "SELECT * FROM trainee WHERE id = ?";
            return jdbc.queryForObject(SELECT_TRAINEE_BY_ID, new TraineeMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Trainee> getAllTrainees() {
        final String SELECT_ALL_TRAINEES = "SELECT * FROM trainee";
        return jdbc.query(SELECT_ALL_TRAINEES, new TraineeMapper());
    }

    @Override
    @Transactional
    public Trainee addTrainee(Trainee trainee) {
        final String INSERT_TRAINEE = "INSERT INTO trainee(firstName, lastName) "
                + "VALUES(?,?)";
        jdbc.update(INSERT_TRAINEE,
                trainee.getFirstName(),
                trainee.getLastName());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        trainee.setId(newId);
        return trainee;
    }

    @Override
    public void updateTrainee(Trainee trainee) {
        final String UPDATE_TRAINEE = "UPDATE trainee SET firstName = ?, lastName = ? "
                + "WHERE id = ?";
        jdbc.update(UPDATE_TRAINEE,
                trainee.getFirstName(),
                trainee.getLastName(),
                trainee.getId());
    }

    @Override
    @Transactional
    public void deleteTraineeById(int id) {
        final String DELETE_COURSE_TRAINEE = "DELETE FROM course_trainee WHERE traineeId = ?";
        jdbc.update(DELETE_COURSE_TRAINEE, id);

        final String DELETE_TRAINEE = "DELETE FROM trainee WHERE id = ?";
        jdbc.update(DELETE_TRAINEE, id);
    }

    public static final class TraineeMapper implements RowMapper<Trainee> {

        @Override
        public Trainee mapRow(ResultSet rs, int index) throws SQLException {
            Trainee trainee = new Trainee();
            trainee.setId(rs.getInt("id"));
            trainee.setFirstName(rs.getString("firstName"));
            trainee.setLastName(rs.getString("lastName"));

            return trainee;
        }
    }
}

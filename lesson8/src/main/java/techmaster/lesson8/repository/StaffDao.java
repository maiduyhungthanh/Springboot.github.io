package techmaster.lesson8.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import lombok.AllArgsConstructor;
import techmaster.lesson8.model.StaffEntity;

@Repository
@AllArgsConstructor
public class StaffDao implements Dao<StaffEntity> {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final String TABLENAME = "person";
    private final RowMapper<StaffEntity> rowMapper = (resultSet, rowNum) -> {
        StaffEntity staffEntity = StaffEntity.builder()
                .setId(resultSet.getLong("id"))
                .setFirstName(resultSet.getString("firstName"))
                .setLastName(resultSet.getString("lastName"))
                .setEmail(resultSet.getString("email"))
                .setPassport(resultSet.getString("passport"))
                .setAvatar(resultSet.getBytes("avatar"))
                .build();
        return staffEntity;
    };

    @Override
    public List<StaffEntity> list() {
        String sql = "SELECT * FROM " + TABLENAME;
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public java.util.List<StaffEntity> list(Integer limit, Integer offset) {
        String sql = "SELECT * FROM " + TABLENAME + " LIMIT ?, ?";
        return jdbcTemplate.query(sql, rowMapper, offset, limit);
    }

    @Override
    public StaffEntity getID(Long id) throws DataAccessException {
        String sql = "SELECT * FROM " + TABLENAME + " WHERE id = ?";
        StaffEntity staffEntity = jdbcTemplate.queryForObject(sql, rowMapper, id);
        return staffEntity;
    }

    @Override
    public void update(Long id, StaffEntity object) {
        String sql =
                "UPDATE " + TABLENAME
                        + " SET firstName = ?, lastName = ?, email = ?, passport = ?,  avatar = ? WHERE id = ?";
        jdbcTemplate.update(sql, object.getFirstName(), object.getLastName(),
                object.getEmail(), object.getPassport(), 
                object.getAvatar(), id);
    }

    @Override
    public void add(StaffEntity object) {
        String sql =
                "INSERT INTO " + TABLENAME
                        + " (firstName,lastName,email,passport,avatar) values(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, object.getFirstName(), object.getLastName(),
                object.getEmail(), object.getPassport(),
                object.getAvatar());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM " + TABLENAME + " WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}

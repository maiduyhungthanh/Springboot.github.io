# Thiết kế web quản lý nhân viên + upload file
## Bước 1: tạo chương trinh SpringBoot với Spring Web, Lombok, H2 Database, Spring Data JDBC, Spring Boot DevTools, Thymeleaf
## Bước 2: tạo các file StaffEntity.java và StaffModel.java trong model
```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true)
public class StaffEntity {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String passport;
    private byte[] avatar;
}
```
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true)
public class StaffModel {
    private Long id;

    @NotBlank(message = "First Name is required")
    @Size(max = 20, message = "First Name must less than 20 characters")
    private String firstName;

    @NotBlank(message = "First Name is required")
    @Size(max = 50, message = "Last Name must less than 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Size(max = 50, message = "Email must less than 50 characters")
    @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    private String email;

    @Min(value = 11, message = "Age must be larger than 11")
    private String passport;

    private byte[] avatar;

    private MultipartFile avatarUpload;
    private String avatarBase64;
    private String fullName;

    public String standardizeFirstName(String firstName) {
        return String.join("", firstName.substring(0, 1).toUpperCase(),
                firstName.substring(1).toLowerCase());
    }

    public String standardizeLastName(String lastName) {
        return lastName.toUpperCase();
    }

    public void setFullName() {
        this.fullName = String.join(" ", standardizeFirstName(this.firstName),
                standardizeLastName(this.lastName));
    }
}
```
## Bước 3: tạo file Dao.java và StaffDao.java trong repository
```java
public interface Dao <T>{
    
    List<T> list();

    List<T> list(Integer limit, Integer offset);

    T getID(Long id);

    void update(Long id, T object);

    void add(T object);

    void delete(Long id);

}
```
```java
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
```
## tạo service




package techmaster.lesson16.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;

@Table(name = "user")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    @Email(message = "định dạng email sai")
    private String email;

    @OneToMany(mappedBy = "userId")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "userId")
    private List<Comment> comments = new ArrayList<>();

    public boolean matchWithKeyWord(String keyword) {
        String lowerKeyWord = keyword.toLowerCase();
        return name.toLowerCase().contains(lowerKeyWord);
    }
}


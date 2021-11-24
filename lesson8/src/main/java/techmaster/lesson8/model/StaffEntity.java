package techmaster.lesson8.model;

import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Controller
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

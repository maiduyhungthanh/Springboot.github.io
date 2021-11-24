package techmaster.lesson8.controller.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class View {
    private Integer limit;
    private Integer offset;
}

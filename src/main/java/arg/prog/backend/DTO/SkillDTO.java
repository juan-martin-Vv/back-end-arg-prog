package arg.prog.backend.DTO;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillDTO {
    private long id;
    @NonNull
    private String skill;
    @Min(0)
    @Max(100)
    private int value;
}

package arg.prog.backend.sercurity.DTO;

import lombok.Data;

@Data
public class JwtDto {

    private String token;
    public static final String bearer = "Bearer";

    public JwtDto(String token) {
        this.token = token;
    }

}

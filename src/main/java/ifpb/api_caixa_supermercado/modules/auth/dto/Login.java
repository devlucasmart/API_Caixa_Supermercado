package ifpb.api_caixa_supermercado.modules.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {
    private String username;
    private String password;
}
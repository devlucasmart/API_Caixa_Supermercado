package ifpb.api_caixa_supermercado.modules.auth.dto;

import ifpb.api_caixa_supermercado.modules.auth.enums.RoleName;

public record CreateUserDto(

        String email,
        String password,
        RoleName role

) {
}

package ifpb.api_caixa_supermercado.modules.auth.dto;

import ifpb.api_caixa_supermercado.modules.auth.enums.RoleName;

public record CreateUserDto(
        String name,
        String email,
        String phone,
        String password,
        String position
) {
}

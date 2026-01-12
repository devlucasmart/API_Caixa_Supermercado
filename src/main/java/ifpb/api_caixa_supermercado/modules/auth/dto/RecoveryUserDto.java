package ifpb.api_caixa_supermercado.modules.auth.dto;

import ifpb.api_caixa_supermercado.modules.auth.entity.Role;

import java.util.List;

public record RecoveryUserDto(

        Long id,
        String email,
        List<Role> roles

) {
}

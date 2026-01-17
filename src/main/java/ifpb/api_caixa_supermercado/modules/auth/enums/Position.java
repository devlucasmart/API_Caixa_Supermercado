package ifpb.api_caixa_supermercado.modules.auth.enums;

import lombok.Getter;

@Getter
public enum Position {

    ADMINISTRADOR("administrador", RoleName.ROLE_ADMINISTRATOR),
    GERENTE("gerente", RoleName.ROLE_MANAGER),
    CAIXA("caixa", RoleName.ROLE_EMPLOYEE),
    CONVIDADO("convidado", RoleName.ROLE_CUSTOMER);

    private final String position;
    private final RoleName role;

    Position(String position, RoleName role) {
        this.position = position;
        this.role = role;
    }

    public static Position fromPosition(String position) {
        for (Position c : values()) {
            if (c.position.equalsIgnoreCase(position)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Position inválido: " + position);
    }

    public static RoleName toRoleName(String position) {
        return fromPosition(position).role;
    }

}
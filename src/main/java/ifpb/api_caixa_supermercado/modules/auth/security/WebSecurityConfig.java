package ifpb.api_caixa_supermercado.modules.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Habilita anotações como @PreAuthorize
public class WebSecurityConfig {

    // Configuração de CORS
    private static final List<String> ALLOWED_ORIGINS = Arrays.asList(
            "http://localhost:3000",    // React
            "http://localhost:4200",    // Angular
            "http://localhost:8080",    // Vue
            "http://localhost:5173"     // Vite
    );

    private static final List<String> ALLOWED_METHODS = Arrays.asList(
            "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"
    );

    private static final List<String> ALLOWED_HEADERS = Arrays.asList(
            "Authorization", "Content-Type", "X-Requested-With", "Accept", "Origin",
            "Access-Control-Request-Method", "Access-Control-Request-Headers"
    );

    // Endpoints públicos (sem autenticação)
    public static final String[] PUBLIC_ENDPOINTS = {
            // Autenticação
            "/api/users/login",
            "/api/users/register",
            "/api/auth/login",
            "/api/auth/register",
            "/api/auth/refresh-token",

            // Documentação
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",

            // H2 Console (apenas desenvolvimento)
            "/h2-console/**",

            // Health check
            "/actuator/health",
            "/actuator/info",

            // Recursos estáticos
            "/error",
            "/favicon.ico"
    };

    // Endpoints para clientes
    public static final String[] CUSTOMER_ENDPOINTS = {
            "/api/caixa/**",
            "/api/vendas/**",
            "/api/carrinho/**"
    };

    // Endpoints para administradores
    public static final String[] ADMIN_ENDPOINTS = {
            "/api/admin/**",
            "/api/users/**",
            "/api/produtos/gerenciar/**",
            "/api/relatorios/**"
    };

    // Endpoints para gerentes
    public static final String[] MANAGER_ENDPOINTS = {
            "/api/gerente/**",
            "/api/estoque/**"
    };

    // Endpoints comuns que requerem autenticação
    public static final String[] AUTHENTICATED_ENDPOINTS = {
            "/api/perfil/**",
            "/api/pedidos/**"
    };

    private final JWTFilter jwtFilter;

    public WebSecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Configura CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Desabilita CSRF para APIs REST
                .csrf(AbstractHttpConfigurer::disable)

                // Configura frames (para H2 Console)
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )

                // Configura sessão como stateless
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Configura autorizações
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()

                        // Autenticação - POST pública
                        .requestMatchers(HttpMethod.POST, "/api/users/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()

                        // Endpoints por roles
                        .requestMatchers(ADMIN_ENDPOINTS).hasRole("ADMINISTRATOR")
                        .requestMatchers(MANAGER_ENDPOINTS).hasRole("MANAGER")
                        .requestMatchers(CUSTOMER_ENDPOINTS).hasRole("CUSTOMER")

                        // Endpoints com múltiplas roles
                        .requestMatchers(HttpMethod.GET, "/api/users").hasAnyRole("ADMINISTRATOR", "MANAGER")
                        .requestMatchers("/api/relatorios/**").hasAnyRole("ADMINISTRATOR", "MANAGER")

                        // Endpoints autenticados (qualquer usuário logado)
                        .requestMatchers(AUTHENTICATED_ENDPOINTS).authenticated()

                        // Health checks
                        .requestMatchers(HttpMethod.GET, "/actuator/health").permitAll()
                        .requestMatchers(HttpMethod.GET, "/actuator/info").permitAll()

                        // Qualquer outra requisição requer autenticação
                        .anyRequest().authenticated()
                )

                // Adiciona filtro JWT
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                // Configura tratamento de exceções
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(401);
                            response.setContentType("application/json");
                            response.getWriter().write(
                                    "{\"error\": \"Não autorizado\", \"message\": \"Token de autenticação inválido ou ausente\"}"
                            );
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(403);
                            response.setContentType("application/json");
                            response.getWriter().write(
                                    "{\"error\": \"Acesso negado\", \"message\": \"Você não tem permissão para acessar este recurso\"}"
                            );
                        })
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Configura origens permitidas
        configuration.setAllowedOrigins(ALLOWED_ORIGINS);
        configuration.setAllowedMethods(ALLOWED_METHODS);
        configuration.setAllowedHeaders(ALLOWED_HEADERS);
        configuration.setExposedHeaders(Arrays.asList(
                "Authorization", "Content-Disposition"
        ));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L); // 1 hora

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
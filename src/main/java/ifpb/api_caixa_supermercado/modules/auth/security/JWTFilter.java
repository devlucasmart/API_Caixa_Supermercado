package ifpb.api_caixa_supermercado.modules.auth.security;

import ifpb.api_caixa_supermercado.modules.auth.entity.User;
import ifpb.api_caixa_supermercado.modules.auth.repository.UserRepository;
import ifpb.api_caixa_supermercado.modules.auth.service.JwtTokenService;
import ifpb.api_caixa_supermercado.modules.auth.service.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;

    // Cache de endpoints públicos para melhor performance
    private static final List<String> PUBLIC_PATTERNS = Arrays.asList(
            "/api/users/login",
            "/api/users/register",
            "/swagger-ui/",
            "/v3/api-docs",
            "/h2-console/",
            "/actuator/health"
    );

    public JWTFilter(JwtTokenService jwtTokenService, UserRepository userRepository) {
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        // Log da requisição
        logger.debug("Processando requisição {} {}", method, requestURI);

        // Verifica se é endpoint público
        if (isPublicEndpoint(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Extrai e valida o token
            String token = extractToken(request);

            if (token == null || token.isBlank()) {
                logger.warn("Token ausente para endpoint protegido: {}", requestURI);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write(
                        "{\"error\": \"Token ausente\", \"message\": \"Token de autenticação é obrigatório\"}"
                );
                return;
            }

            // Valida token e obtém usuário
            String username = jwtTokenService.getSubjectFromToken(token);
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            // Cria autenticação
            UserDetailsImpl userDetails = new UserDetailsImpl(user);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            // Define no contexto de segurança
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("Usuário autenticado: {}", username);

        } catch (Exception e) {
            logger.error("Erro na autenticação: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"error\": \"Autenticação falhou\", \"message\": \"" + e.getMessage() + "\"}"
            );
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            return authHeader.substring(BEARER_PREFIX.length());
        }

        return null;
    }

    private boolean isPublicEndpoint(String requestURI) {
        // Verifica se a URI começa com algum padrão público
        return PUBLIC_PATTERNS.stream()
                .anyMatch(requestURI::startsWith) ||
                Arrays.asList(WebSecurityConfig.PUBLIC_ENDPOINTS).contains(requestURI);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/webjars/") ||
                path.startsWith("/h2-console");
    }
}
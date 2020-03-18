package pl.journeyplanner.gatewayservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Check header
        String header = request.getHeader(jwtProperties.getHeader());

        if (header == null || !header.startsWith(jwtProperties.getPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get token from header
        String token = header.replace(jwtProperties.getPrefix(), "").trim();

        try {
            // Parse token
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecret().getBytes())
                    .parseClaimsJws(token)
                    .getBody();
            // Get username
            String username = claims.getSubject();

            if (username != null) {
                // Get roles
                @SuppressWarnings("unchecked")
                List<String> authorities = (List<String>) claims.get("authorities");

                // Create auth object
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null ,
                                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

                // Authenticate user
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}

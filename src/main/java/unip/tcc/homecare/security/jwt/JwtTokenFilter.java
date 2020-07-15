package unip.tcc.homecare.security.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JwtTokenFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        final List<String> excludedAuthUrl = Arrays.asList("/auth/login", "/auth/register");

        String url = "";
        if(req instanceof HttpServletRequest) {
            url = (((HttpServletRequest) req).getRequestURI());
        }

        if(excludedAuthUrl.contains(url)) {
            filterChain.doFilter(req, res);
        } else {
            String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);

            HttpServletResponse response = (HttpServletResponse) res;

            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
                SecurityContextHolder.getContext().setAuthentication(auth);
                filterChain.doFilter(req, response);
            } else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Token invalido ou expirado");
            }
        }
    }
}

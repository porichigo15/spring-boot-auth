package com.example.assignmentapi.filter;

import com.example.assignmentapi.model.User;
import com.example.assignmentapi.util.ConvertUtil;
import com.example.assignmentapi.util.UserUtil;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    @Setter
    private ConvertUtil convertUtil;

    @Setter
    private UserUtil userUtil;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws IOException, ServletException
    {
        String bearer = "Bearer ";
        String secret = "kbank-token-secret";

        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith(bearer)) {
            String token = header.replace(bearer, "");
            String subject;

            try {
                subject = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();

            }catch(JwtException jx) {
                if (jx.getMessage().contains("JWT expired")) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
                return;
            }

            if (subject != null) {
                List<User> userList = userUtil.getUser(subject);
                if (!userList.isEmpty()) {
                    User user = userList.get(0);
                    List<GrantedAuthority> roles = user.getRoleList(user.getRoles());
                    UsernamePasswordAuthenticationToken authen = new UsernamePasswordAuthenticationToken(subject, user.getPassword(), roles);
                    authen.setDetails(user);
                    SecurityContextHolder.getContext().setAuthentication(authen);
                }
            }

        }

        chain.doFilter(request, response);
    }
}

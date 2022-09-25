package com.example.assignmentapi.filter;

import com.example.assignmentapi.model.User;
import com.example.assignmentapi.util.UserUtil;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Setter
    private UserUtil userUtil;

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException
    {
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        List<User> userList = userUtil.getUser(username);

        if (userList.isEmpty()) {
            throw new AuthenticationServiceException("Username is required");
        }

        if (password != null) {
            List<GrantedAuthority> roles = new User().getRoleList(userList.get(0).getRoles());
            UsernamePasswordAuthenticationToken authen = new UsernamePasswordAuthenticationToken(username, password, roles);
            this.setDetails(request, authen);
            return this.getAuthenticationManager().authenticate(authen);
        } else {
            throw new AuthenticationServiceException("Password is required");
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication auth) throws IOException
    {
        String username = auth.getName();
        if (username != null) {
            this.setResponse(username, response);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }

    private void setResponse(String username, HttpServletResponse response) {
        String secret = "kbank-token-secret";
        Date expire = new Date(System.currentTimeMillis() + 30 * 60 * 1000);

        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(expire)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        response.addHeader("token", token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

}

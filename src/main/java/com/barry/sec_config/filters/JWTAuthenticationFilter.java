package com.barry.sec_config.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.barry.util.ParamUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;
    private final ObjectMapper mapper = new ObjectMapper();

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        System.out.println("attemptAuthentication");
        final String username = request.getParameter("username");
        final String password = request.getParameter("password");
        System.out.println(username);
        System.out.println(password);
        UsernamePasswordAuthenticationToken authenticationToken =
              new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        System.out.println("successfulAuthentication");
        User user = (User) authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(ParamUtils.SECRET_KEY);
        Date now = new Date();
        Date validity = new Date(now.getTime() + 1*60*1000); // 1 minute

        String jwtAccessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(validity)
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles",user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);


        String jwtRefreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+ 15*60*1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        Map<String, String> idToken = new HashMap<>();
        idToken.put("Access-Token",jwtAccessToken);
        idToken.put("Refresh-Token",jwtRefreshToken);
        response.setContentType("application/json");
        mapper.writeValue(response.getOutputStream(),idToken);
    }
}

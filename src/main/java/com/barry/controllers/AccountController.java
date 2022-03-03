package com.barry.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.barry.entities.AppRole;
import com.barry.entities.AppUser;
import com.barry.models.RoleUserForm;
import com.barry.services.AccountService;
import com.barry.util.ParamUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "/users")
    public List<AppUser> listUsers() {
        return accountService.listUsers();
    }

    @PostMapping(path = "/users")
    public AppUser saveUser(@RequestBody AppUser user) {
        return accountService.addNewUser(user);
    }

    @PostMapping(path = "/roles")
    public AppRole saveRole(@RequestBody AppRole role) {
        return accountService.addNewRole(role);
    }

    @PostMapping(path = "/addRoleToUser")
    public void saveRole(@RequestBody RoleUserForm roleUserForm) {
        accountService.addRoleToUser(roleUserForm.getUsername(),roleUserForm.getRoleName());
    }


    /* @GetMapping(path = "/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response ){
        String authenticationToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authenticationToken != null && authenticationToken.startsWith("Bearer ")){
            try {
                String refreshToken = authenticationToken.substring(7);
                Algorithm algorithm = Algorithm.HMAC256(ParamUtils.SECRET_KEY);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                AppUser appUser = accountService.loadUserByUsername(username);

            }catch(RuntimeException e){

            }



        }
    }*/
}

package com.barry.services;

import com.barry.entities.AppRole;
import com.barry.entities.AppUser;

import java.util.List;

public interface AccountService {

    AppUser addNewUser(AppUser appUser);

    //public AppUser addNewUser(String username,String password,String confirmedPassword);

    AppRole addNewRole(AppRole appRole);

    void addRoleToUser(String username, String roleName);

    AppUser loadUserByUsername(String username);

    List<AppUser> listUsers();
}

package com.barry;

import com.barry.entities.AppRole;
import com.barry.entities.AppUser;
import com.barry.repositories.AppRoleRepository;
import com.barry.repositories.AppUserRepository;
import com.barry.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class SecServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(AccountService accountService,
                            AppUserRepository appUserRepository,
                            AppRoleRepository appRoleRepository){
        return  args -> {
            accountService.addNewRole(new AppRole(null,"USER"));
            accountService.addNewRole(new AppRole(null,"ADMIN"));
            accountService.addNewRole(new AppRole(null,"CUSTOMER-MANAGER"));
            accountService.addNewRole(new AppRole(null,"PRODUCT-MANAGER"));
            accountService.addNewRole(new AppRole(null,"BILLS-MANAGER"));

            accountService.addNewUser(new AppUser(null,"user1","1234",new ArrayList<>()));
            accountService.addNewUser(new AppUser(null,"admin","1234",new ArrayList<>()));
            accountService.addNewUser(new AppUser(null,"user2","1234",new ArrayList<>()));
            accountService.addNewUser(new AppUser(null,"user3","1234",new ArrayList<>()));
            accountService.addNewUser(new AppUser(null,"user4","1234",new ArrayList<>()));

            accountService.addRoleToUser("user1","USER");
            accountService.addRoleToUser("admin","USER");
            accountService.addRoleToUser("admin","ADMIN");
            accountService.addRoleToUser("user2","USER");
            accountService.addRoleToUser("user2","CUSTOMER-MANAGER");
            accountService.addRoleToUser("user3","USER");
            accountService.addRoleToUser("user3","PRODUCT-MANAGER");
            accountService.addRoleToUser("user4","USER");
            accountService.addRoleToUser("user4","BILLS-MANAGER");

            AppUser user = appUserRepository.findByUsername("user1");
            AppRole role = appRoleRepository.findByRoleName("USER");
            System.out.println("------------------------------------------------------");
            System.out.println("Username: "+user.getUsername());
            System.out.println("roles: "+ new ArrayList<>(user.getAppRoles()));
            System.out.println("------------------------------------------------------");
            System.out.println("roleName: "+role.getRoleName());
            System.out.println("ID: "+role.getId());

        };
    }

}

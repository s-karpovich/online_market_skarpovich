package by.tut.mdcatalog.project2.service.impl;

import by.tut.mdcatalog.project2.service.model.AppUserPrincipal;
import by.tut.mdcatalog.project2.service.model.UserDTO;
import by.tut.mdcatalog.project2.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public AppUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDTO user = userService.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new AppUserPrincipal(user);
    }
}

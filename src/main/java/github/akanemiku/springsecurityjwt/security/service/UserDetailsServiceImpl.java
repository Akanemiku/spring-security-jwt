package github.akanemiku.springsecurityjwt.security.service;

import github.akanemiku.springsecurityjwt.security.entity.JwtUser;
import github.akanemiku.springsecurityjwt.system.entity.User;
import github.akanemiku.springsecurityjwt.system.service.UserService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userService.findUserByUserName(name);
        return new JwtUser(user);
    }

}

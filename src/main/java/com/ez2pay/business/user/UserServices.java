package com.ez2pay.business.user;

import com.ez2pay.business.role.Role;
import com.ez2pay.business.role.RoleRepository;
import com.ez2pay.security.JwtTokenProvider;
import com.ez2pay.util.DozerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServices implements UserDetailsService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
    }

    public void signUp(UserDTO userDTO){
        var user = DozerConverter.parseObject(userDTO, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUsername(userDTO.getUsername());
        user.setEnabled(true);

        Role userRole = roleRepository.findByDescription("ADMIN");
        user.setRoles(Arrays.asList(userRole));
        userRepository.save(user);
    }

    public UserDTO signIn(UserDTO userDTO) {
        try {
            var username = userDTO.getUsername();
            var password = userDTO.getPassword();
            var token = "";

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            var user = userRepository.findByUsername(username);

            if (user != null) {
                token = jwtTokenProvider.creatToken(username, user.getRoles());

                userDTO.setPassword("");
                userDTO.setToken(token);

                return userDTO;

            } else {
                throw new UsernameNotFoundException("Username " + username + " not found.");
            }

        } catch (AuthenticationException authenticationException) {
            throw new BadCredentialsException("Invalid username/password");
        }
    }
}

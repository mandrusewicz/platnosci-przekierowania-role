package com.zespol11.programowanienzespolowe.userRegistration.appuser;


import com.zespol11.programowanienzespolowe.userRegistration.registration.token.ConfirmationToken;
import com.zespol11.programowanienzespolowe.userRegistration.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "appuser with email %s not found";
    private final UserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final UserRepository userRepository;




    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findUserByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(User appUser) {
        boolean userExists = appUserRepository.findUserByEmail(appUser.getEmail())
                .isPresent();
        if(userExists) {
            //TODO check if attributes are the same and
            //TODO if email not confirmed send confirmation email
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
               appUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        //TODO: send email

        return token;
    }
    public List<User> getUser(){return appUserRepository.findAll();}

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }

    public void addNewUser(User user){
////        Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
//
//        if(userOptional.isPresent()){
//            throw new IllegalStateException("email taken");
//        }

        userRepository.save(user);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fillDB(){
        addNewUser(new User(1L, "Andrzej"));
        addNewUser(new User(2L, "Jacek"));
        addNewUser(new User(3L, "Barbara"));
        addNewUser(new User(4L, "Małgorzata"));
        addNewUser(new User(5L, "Jerzy"));
        addNewUser(new User(6L, "Wiesław"));
        addNewUser(new User("TEST","TTT","test@test.pl","123",UserRole.USER));
    }
}
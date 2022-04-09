package com.zespol11.programowanienzespolowe.userRegistration.security.config;

import com.zespol11.programowanienzespolowe.userRegistration.appuser.UserService;
import com.zespol11.programowanienzespolowe.userRegistration.login.CustomFilter;
import com.zespol11.programowanienzespolowe.userRegistration.login.CustomSuccessHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {




    private final UserService appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomFilter mupaf = new CustomFilter();
        mupaf.setAuthenticationManager(authenticationManager());

        CustomSuccessHandler successHandler = new CustomSuccessHandler();


        http
                .csrf().disable()
                .addFilterAt(
                        mupaf,
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v1/restaurant/hello").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/restaurant/protected").authenticated()
                .antMatchers(HttpMethod.POST, "/api/v1/restaurant/kucharz").hasAuthority("COOK")
                .antMatchers(HttpMethod.GET, "/api/v1/restaurant/kucharz").hasAuthority("COOK")
                .antMatchers(HttpMethod.POST, "/login").permitAll();




           }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(appUserService);
        return provider;
    }


}

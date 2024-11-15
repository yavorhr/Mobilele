package com.example.mobilele.config;

import com.example.mobilele.model.entity.enums.UserRoleEnum;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfiguration {
  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  public ApplicationSecurityConfiguration(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests(authz ->
                    authz.
                            // with this line we allow access to all static resources
                                    requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
                            // allow actuator endpoints
                                    requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll().
                            // the next line allows access to the home page, login page and registration for everyone
                                    requestMatchers("/", "/users/login", "/users/register", "/logger/**").permitAll().
                            // we permit the page below only for admin users
                                    requestMatchers("/statistics").hasRole(UserRoleEnum.ADMIN.name()).
                            // next we forbid all other pages for unauthenticated users.
                                    requestMatchers("/**").authenticated()).
            // configure login with login HTML form with two input fields
                    formLogin(form -> form.
                    // our login page is located at http://<serveraddress>>:<port>/users/login
                            loginPage("/users/login").
                    // this is the name of the <input..> in the login form where the user enters her email/username/etc
                    // the value of this input will be presented to our User details service
                    // those that want to name the input field differently, e.g. email may change the value below
                            usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
                    // the name of the <input...> HTML filed that keeps the password
                            passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
                    // The place where we should land in case that the login is successful
                            defaultSuccessUrl("/").
                    // the place where I should land if the login is NOT successful
                            failureForwardUrl("/users/login-error").permitAll()).
            logout(logout -> logout.
                    // This is the URL which spring will implement for me and will log the user out.
                            logoutUrl("/users/logout").
                    // where to go after the logout.
                            logoutSuccessUrl("/").
                    // remove the session from server
                            invalidateHttpSession(true).
                    //delete the cookie that references my session
                            deleteCookies("JSESSIONID"));

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder auth =
            http.getSharedObject(AuthenticationManagerBuilder.class);

    auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);

    return auth.build();
  }
}

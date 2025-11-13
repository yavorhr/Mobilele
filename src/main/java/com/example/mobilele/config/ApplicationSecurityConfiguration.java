package com.example.mobilele.config;

import com.example.mobilele.model.entity.enums.UserRoleEnum;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration {
  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;
  private final CustomLoginFailureHandler failureHandler;
  private final CustomLoginSuccessHandler successHandler;

  public ApplicationSecurityConfiguration(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, CustomLoginFailureHandler failureHandler, CustomLoginSuccessHandler successHandler) {
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
    this.failureHandler = failureHandler;
    this.successHandler = successHandler;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authz ->
            authz.
                    requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
                    requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll().
                    requestMatchers("/", "/error", "/users/login", "/users/register", "/logger/**", "/models/**").permitAll().
                    requestMatchers("/statistics").hasRole(UserRoleEnum.ADMIN.name()).
                    requestMatchers("/static/**").permitAll().
                    requestMatchers("/**").authenticated())
            .formLogin(form -> form
                    .loginPage("/users/login")
                    .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                    .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                    .failureHandler(failureHandler)
                    .successHandler(successHandler))
            .logout(logout -> logout.
                    logoutUrl("/users/logout").
                    logoutSuccessUrl("/").
                    invalidateHttpSession(true).
                    deleteCookies("JSESSIONID"))
            .securityContext(securityContext ->
                    securityContext.requireExplicitSave(false));

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {

    // WARNING: This exposes whether a username exists and should not be used in production without caution.
    // Used here for educational/demo purposes to show custom error handling.
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder);

    authProvider.setHideUserNotFoundExceptions(false);

    AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
    auth.authenticationProvider(authProvider);

    return auth.build();
  }
}

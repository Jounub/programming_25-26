package ru.urfu.CourseProject.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    @Bean
    public static PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/", "/home", "/about", "/h2-console/**", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/clients/view/**").authenticated()
                        .requestMatchers("/clients/list")
                                .hasAnyRole("READ_ONLY", "USER","ADMIN", "SUPERVISOR")

                        .requestMatchers("/clients/new", "/clients/create").hasAnyRole("USER","ADMIN", "SUPERVISOR")
                        .requestMatchers("/clients/edit/**", "/clients/update/**", "/clients/delete/**")
                            .hasAnyRole("SUPERVISOR", "ADMIN")
                        .requestMatchers("/admin/**", "/clients/**", "/users/roles").hasRole("ADMIN")

                        .requestMatchers("/clients/my").hasRole("USER")



                        .requestMatchers("/admin/**", "/users/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedPage("/access-denied")
                );

        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));

        return http.build();
    }
}

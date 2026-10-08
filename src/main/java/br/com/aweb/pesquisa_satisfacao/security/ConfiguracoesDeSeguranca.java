package br.com.aweb.pesquisa_satisfacao.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfiguracoesDeSeguranca {
    @Bean
    public UserDetailsService dadosUsuarios(){
        UserDetails usuario1 = User.builder()
            .username("andre@email.com")
            .password("{noop}andre123")
            .build();

        return new InMemoryUserDetailsManager(usuario1);
    }

    @Bean
    public SecurityFilterChain filtroSeguranca(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/css/**", "/js/**", "/img/**").permitAll();
                    req.anyRequest().authenticated();
                })
                .formLogin(form -> form.loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll())
                .logout(logout -> logout
                    .logoutSuccessUrl("/login?logout")
                    .permitAll())                
                .build();
    }
}

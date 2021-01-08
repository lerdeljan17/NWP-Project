package com.edu.raf.NWP_Projekat.config;

import com.edu.raf.NWP_Projekat.filter.CorsFilter;
import com.edu.raf.NWP_Projekat.filter.JwtTokenAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("userDetails")
    @Autowired
    private UserDetailsService myUserDetailsService;

    @Autowired
    private JwtTokenAuthFilter jwtTokenAuthFilter;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        "/api/users/register"
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.DELETE,"/api/companies/**").hasAuthority("admin")
                .antMatchers(HttpMethod.POST,"/api/companies/**").hasAuthority("admin")
                .antMatchers(HttpMethod.PUT,"/api/companies/**").hasAuthority("admin")

                .antMatchers(HttpMethod.DELETE,"/api/tickets/**").hasAuthority("admin")
                .antMatchers(HttpMethod.POST,"/api/tickets/**").hasAuthority("admin")
                .antMatchers(HttpMethod.PUT,"/api/tickets/**").hasAuthority("admin")

                .antMatchers(HttpMethod.POST,"/api/reservations/**").hasAuthority("user")

                .antMatchers("api/users/register").hasAuthority("admin")

                .antMatchers("/api/users/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtTokenAuthFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class);
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}

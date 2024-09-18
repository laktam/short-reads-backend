package org.mql.laktam.speedreadbackend.config;

import org.mql.laktam.speedreadbackend.jwtutils.JwtAuthenticationEntryPoint;
import org.mql.laktam.speedreadbackend.jwtutils.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
 * Configures Spring Security settings,
 *  including security filters, exception handling,
 *   session management, and password encoding.
 */
@Configuration 
@EnableWebSecurity
public class WebSecurityConfig {

   @Autowired
   private JwtAuthenticationEntryPoint authenticationEntryPoint;

   @Autowired
   private JwtFilter filter;

   @Autowired
   private UserDetailsService userDetailsService;

   @Bean 
   protected PasswordEncoder passwordEncoder() { 
      return new BCryptPasswordEncoder(); 
   }

   @Bean
   protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       return http
           .csrf(AbstractHttpConfigurer::disable)
           .authorizeHttpRequests(authorizeRequests ->
               authorizeRequests
                   .antMatchers("/login", "/signup").permitAll()
                   .anyRequest().authenticated()
           )
           .exceptionHandling(exceptionHandling ->
               exceptionHandling.authenticationEntryPoint(authenticationEntryPoint)
           )
           .sessionManagement(sessionManagement ->
               sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
           )
           .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
           .build();
   }

   @Bean
   public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
      return http.getSharedObject(AuthenticationManagerBuilder.class)
         .userDetailsService(userDetailsService)
         .passwordEncoder(passwordEncoder())
         .and()
         .build();
   }
   
//   @Bean
//   public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//       AuthenticationManagerBuilder authenticationManagerBuilder = 
//           new AuthenticationManagerBuilder(http.getSharedObject(BeanFactory.class));
//       authenticationManagerBuilder
//           .userDetailsService(userDetailsService)
//           .passwordEncoder(passwordEncoder());
//       return authenticationManagerBuilder.build();
//   }
//   @Bean
//   AuthenticationManager customAuthenticationManager() {
//      return authentication -> new UsernamePasswordAuthenticationToken("laktam1","hash");
//   }
}

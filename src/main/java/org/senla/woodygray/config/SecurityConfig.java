package org.senla.woodygray.config;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.service.UserService;
import org.springframework.context.annotation.Bean;


//@EnableWebSecurity
//@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {
//    private final UserService userService;

//    @Bean
//    public BCryptPasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(daoAuthenticationProvider());
//    }


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .cors().disable()
//                .authorizeRequests()
//                    .antMatchers("/user/secured").authenticated()
//                    .antMatchers("/user/admin").hasRole("ADMIN")
//                    .anyRequest().permitAll()
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
//        //TODO: add filter before
//    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http
//                .csrf().disable()
//                .cors().disable()
//                .authorizeRequests()
//                .antMatchers("/user/secured").authenticated()
//                .antMatchers("/user/admin").hasRole("ADMIN")
//                .anyRequest().permitAll()
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
//                //TODO: add filter before
//        return http.build();
//
//    }

//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider(){
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        daoAuthenticationProvider.setUserDetailsService(userService);
//        return daoAuthenticationProvider;
//    }

//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
//        return authenticationConfiguration.getAuthenticationManager();
//    }

}

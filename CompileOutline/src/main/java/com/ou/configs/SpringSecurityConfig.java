package com.ou.configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ou.filter.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
        "com.ou.controllers",
        "com.ou.repositories",
        "com.ou.services"
})
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http)
            throws Exception {
        http.formLogin()
                .loginPage("/login").permitAll()
                .usernameParameter("username")
                .passwordParameter("password").successHandler( new CustomAuthenticationSuccessHandler());

        http.logout().logoutSuccessUrl("/login");

        http.authorizeRequests()
//                .anyRequest().permitAll()
                .antMatchers("/").hasRole("ADMIN")
                .antMatchers("/users/**").hasRole("ADMIN")
                .antMatchers("/subject/**").hasRole("ADMIN")
                .antMatchers("/faculty/**").hasRole("ADMIN")
                .antMatchers("/assignment/**").hasRole("ADMIN")
                .antMatchers("/specification/**").hasRole("LECTURER")
                .anyRequest().authenticated()
                .and().csrf().disable();


//        http.formLogin().defaultSuccessUrl("/")
//                .failureUrl("/login?error");
//        http.logout().logoutSuccessUrl("/login");
//
//        http.exceptionHandling()
//                .accessDeniedPage("/login?accessDenied");
//        http.authorizeRequests().antMatchers("/").permitAll()
//                .antMatchers("/**/add")
//                .access("hasRole('ROLE_ADMIN')");
//        .antMatchers("/**/pay")
//                .access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
//        http.csrf().disable();
    }


    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary
                = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dmdljcwau",
                "api_key", "751729637915529",
                "api_secret", "-9greGmb76IjiPCDjePMEPuuz1I",
                "secure", true));
        return cloudinary;
    }
}

package com.mike.xie.maple_tiger_sys.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.mike.xie.maple_tiger_sys.security.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoderBean());
	}
	
	@Bean
	public PasswordEncoder passwordEncoderBean() {
		return new BCryptPasswordEncoder();
	}
	
	 @Override
     protected void configure(HttpSecurity http) throws Exception {
         // @formatter:off
         http
             .httpBasic().and()
             .authorizeRequests()
             	.antMatchers("/api/department/**").access("hasRole('ROLE_ADMIN')")
             	.antMatchers("/api/employee/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_ACCOUNTANT')")
                .antMatchers("/index.html", "/", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
             .formLogin()
             	.loginPage("/login")
             	.permitAll()
             	.and()
             .logout()
             	.invalidateHttpSession(true)
             	.deleteCookies("JSESSIONID")
             	.and()
             .csrf()
                 .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
         // @formatter:on
     }
     
     @Override
     public void configure(WebSecurity web) throws Exception {
	        // AuthenticationTokenFilter will ignore the below paths
	        web
	            .ignoring()
	            .antMatchers(
	                HttpMethod.GET,
	                "/"
	            )

	            // allow anonymous resource requests
	            .and()
	            .ignoring()
	            .antMatchers(
	                HttpMethod.GET,
	                "/",	              
	                "/static/**",
	                "/*.bundle.*",
	                "/*.html",
	                "/*.ico",
	                "/**/*.html",
	                "/**/*.css",
	                "/**/*.woff",
	                "/**/*.woff2",
	                "/**/*.ttf",
	                "/**/*.svg",
	                "/**/*.eot",
	                "/**/*.png",
	                "/**/*.txt",
	                "/**/*.js"
	            );

	            // Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
	           
	    }
}

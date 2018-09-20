package com.asraf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.asraf.constants.ConfigExecutionOrder;

@Configuration
@EnableWebSecurity
@Order(ConfigExecutionOrder.WEB_SECURITY_CONFIG)
@Import(Encoders.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
	@Autowired
    private UserDetailsService userDetailsServiceImpl;

	@Autowired
    private PasswordEncoder userPasswordEncoder;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
		  .antMatchers("/login", "/oauth/authorize")
		  .and()
		  .authorizeRequests()
		  .anyRequest().authenticated()
		  .and()
		  .formLogin().permitAll();
    }
 
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
        	.antMatchers("/accounts/forgot-password", "/accounts/change-password/*")
        	.antMatchers(HttpMethod.POST, "/accounts");
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl)
        	.passwordEncoder(userPasswordEncoder);
    }
    
}
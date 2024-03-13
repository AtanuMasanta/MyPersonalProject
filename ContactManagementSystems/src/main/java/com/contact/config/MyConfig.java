package com.contact.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class MyConfig
{
	@Bean
	public UserDetailsService getUserDetailService()
	{
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(this.getUserDetailService());
		authenticationProvider.setPasswordEncoder(this.passwordEncoder());
		return authenticationProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception 
	{
	     return config.getAuthenticationManager();
	}
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception 
    {
		  return http
                .authorizeHttpRequests().requestMatchers("/contact/**","/css/**","/js/**","/img/**","/**")
                .permitAll().and().authorizeHttpRequests().requestMatchers("/user/**")
                .authenticated().and().formLogin().loginPage("/contact/login").
                loginProcessingUrl("/dologin").defaultSuccessUrl("/user/index").and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/contact/home").
                and().build();
		
		/*return http.authorizeHttpRequests().requestMatchers("/user/**").authenticated().and().formLogin().loginPage("/contact/login").and()
				.authorizeRequests().requestMatchers("/**").permitAll().and().build();*/
		
		//
    }
	
	
}

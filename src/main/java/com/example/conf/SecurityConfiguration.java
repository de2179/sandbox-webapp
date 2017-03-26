/**
 * 
 */
package com.example.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.example.security.AdalSecurityFilter;

/**
 * @author de2179
 *
 */
@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	            .authorizeRequests()
	            	.antMatchers("/","/secure").authenticated()
	            	.anyRequest().permitAll()
	                .and()
	            .logout()
	                .permitAll()
	                .and()
	            //.csrf().disable()
	            ;
	    }

//	    @Bean
//	    public AdalSecurityFilter adalSecurityFilter() {
//	      return new AdalSecurityFilter();
//	    }

	
}

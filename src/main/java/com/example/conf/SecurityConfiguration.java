/**
 * 
 */
package com.example.conf;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @author de2179
 *
 */
@Configuration
@EnableOAuth2Client
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	OAuth2ClientContext oauth2ClientContext;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/", "/secure")
				.authenticated()
				.anyRequest()
				.permitAll()
			.and()
				.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			.and()
				.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
		// .logout()
		// .permitAll()
		;
	}

	private Filter ssoFilter() {
		OAuth2ClientAuthenticationProcessingFilter azureFilter = new OAuth2ClientAuthenticationProcessingFilter(
				"/login/azure");
		OAuth2RestTemplate azureTemplate = new OAuth2RestTemplate(azure(), oauth2ClientContext);
		azureFilter.setRestTemplate(azureTemplate);
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(azureResource().getUserInfoUri(),
				azure().getClientId());
		tokenServices.setRestTemplate(azureTemplate);
		azureFilter.setTokenServices(tokenServices);
		return azureFilter;
	}

	@Bean
	@ConfigurationProperties("azure.client")
	public AuthorizationCodeResourceDetails azure() {
		return new AuthorizationCodeResourceDetails();
	}

	@Bean
	@ConfigurationProperties("azure.resource")
	public ResourceServerProperties azureResource() {
		return new ResourceServerProperties();
	}

	// @Bean
	// public AdalSecurityFilter adalSecurityFilter() {
	// return new AdalSecurityFilter();
	// }

}

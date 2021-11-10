package tn.esps.security;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import lombok.RequiredArgsConstructor;

import tn.esps.security.csrf.SecurityCsrfFilter;
import tn.esps.security.jwt.JwtAuthenticationEntryPoint;
import tn.esps.security.jwt.JwtAuthorizationFilter;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {	
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;
    
    @Autowired
    private DataSource dataSource;
    

	@Autowired
	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
			throws Exception {
		authenticationManagerBuilder
		     .userDetailsService(userDetailsService)
		     .passwordEncoder(passwordEncoder());

	}
	

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
	http.cors().and().csrf().disable();
	/*
		http.csrf().disable()
		.authorizeRequests()
		        .anyRequest()
		            .authenticated()
		                .and()
		         .formLogin();

	*/
		
		  http.csrf() 
		         .ignoringAntMatchers(WebSecurityConstant.CSRF_IGNORE)
		            .csrfTokenRepository(csrfTokenRepository()) 
		                .and() 
		         .addFilterAfter(new SecurityCsrfFilter(), CsrfFilter.class);
		
		  http.cors()
		          .and()
		      .authorizeRequests() 
		          .antMatchers(WebSecurityConstant.PUBLIC_URLS).permitAll()
		          .antMatchers(HttpMethod.POST, "/api/user/add").permitAll()
		          .antMatchers(HttpMethod.POST, "/api/employee/add").permitAll()
		          .antMatchers(HttpMethod.POST, "/api/mail/send").permitAll()
		          .antMatchers(HttpMethod.GET, "/api/users").permitAll()
		               .anyRequest() .authenticated() 
		        .and()
		     .exceptionHandling()
		         .authenticationEntryPoint(authenticationEntryPoint)
		       .and()
		     .sessionManagement()
		         .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		  
		  
	        /********* Remember Me *********/
		
		
	        http.authorizeRequests().and()
	                .rememberMe().tokenRepository(this.persistentTokenRepository())
	                .tokenValiditySeconds(1 * 24 * 60 * 60);  //24h
		 
		 
		  http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		  

	}
	
	@Bean
	public JwtAuthorizationFilter authenticationJwtTokenFilter() {
		return new JwtAuthorizationFilter();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName(SecurityCsrfFilter.CSRF_COOKIE_NAME);
		return repository;
	}

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }

}

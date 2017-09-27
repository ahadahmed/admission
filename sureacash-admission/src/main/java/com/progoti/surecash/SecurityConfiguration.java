package com.progoti.surecash;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	private String usersQuery = "select email, password, active from student_info where application_id=?";

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		super.configure(auth);
		auth
			.jdbcAuthentication().usersByUsernameQuery(usersQuery)
			//.authoritiesByUsernameQuery(rolesQuery)
			.dataSource(dataSource);
			//.passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
//		super.configure(http);
		http
			.authorizeRequests()
			.antMatchers("/","/resources/**","/home","/academic/**").permitAll()
			.antMatchers("/login").permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.csrf().disable()
			.formLogin()
			.loginPage("/login")
			.permitAll()
			.and()
			.logout()
			.permitAll();
//			.antMatchers("/registration").permitAll()
//			.antMatchers("/admin/**").hasAuthority("ADMIN")
/*			.anyRequest()
			.authenticated()
			.and().csrf().disable()
			.formLogin()
			.loginPage("/login")
			.permitAll()
			.and()
			.logout()
			.permitAll();*/
//			.failureUrl("/login?error=true");
//			.defaultSuccessUrl("/admin/home")
//			.usernameParameter("email")
//			.passwordParameter("password")
//			.and().logout()
//			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//			.logoutSuccessUrl("/").and().exceptionHandling()
//			.accessDeniedPage("/access-denied");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		super.configure(web);
	}

}
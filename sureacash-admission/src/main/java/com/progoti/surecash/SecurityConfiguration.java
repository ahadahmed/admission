package com.progoti.surecash;

import com.progoti.surecash.admission.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserLoginService userDetailsService;
	@Autowired
	private CustomAuthenticationFilter customAuthenticationFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*auth
			.jdbcAuthentication()
			.usersByUsernameQuery(usersQuery)
			.authoritiesByUsernameQuery(rolesQuery)
			.dataSource(dataSource);
			.passwordEncoder(bCryptPasswordEncoder);
		 */		
//		auth.inMemoryAuthentication().withUser("testuser").password("123456").roles("USER");
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
//		super.configure(http);
		http
			.addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers("/", "/resources/**", "/home", "/academic/**", "/submit-enquiry", "/contact", "payment/doPayment", "payment/reconcilePayment").permitAll()
			.antMatchers("/admin/**").hasAuthority("ADMIN")
			.antMatchers("/login").permitAll()
			.antMatchers("/registration").permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.csrf().disable()
			.formLogin()
			.loginPage("/login")
			.failureUrl("/login?error=true")
			.defaultSuccessUrl("/general-enquiry",true)
			.usernameParameter("applicantId")
			.passwordParameter("password")
			.permitAll()
			.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/").and().exceptionHandling()
			.accessDeniedPage("/accessdenied");
//			.permitAll();
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
			.permitAll();
*/
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
		web
			.ignoring()
			.antMatchers("/resources/**", "/static/**","/templates/**", "/css/**", "/js/**", "/images/**");
	}

}

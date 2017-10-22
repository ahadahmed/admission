package com.progoti.surecash;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	private String userDefaultSuccessUrl = "/user/dashboard";
	private String adminDefaultSuccessUrl = "/admin/dashboard";

	@SuppressWarnings("unchecked")
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		
		String targetUrl =  determineTargetUrl(authentication);
		redirectStrategy.sendRedirect(request, response, targetUrl);

	}
	
	private String determineTargetUrl(Authentication authentication) {
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for(GrantedAuthority authority : authorities) {
			if(authority.getAuthority().equalsIgnoreCase("Admin")){
				return adminDefaultSuccessUrl;
			}else {
				return userDefaultSuccessUrl;
			}
		}
        
		throw new IllegalStateException();
    }

}

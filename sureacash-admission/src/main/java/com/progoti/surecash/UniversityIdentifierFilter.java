package com.progoti.surecash;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.progoti.surecash.admission.domain.University;
import com.progoti.surecash.admission.repository.UniversityRepository;

@Component
public class UniversityIdentifierFilter extends GenericFilterBean{
	@Autowired
	UniversityRepository universityRepository;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest= (HttpServletRequest) request;
		University univ = null;
		ServletContext servletContext = httpRequest.getServletContext();
		if(servletContext.getAttribute(httpRequest.getServerName()) == null ){
			univ = universityRepository.findOneByDomainName(httpRequest.getServerName());
			servletContext.setAttribute(httpRequest.getServerName(), univ);
			
		}else {
			univ = (University) servletContext.getAttribute(request.getServerName());
		}
		if(univ == null) {
			return;
		}
		chain.doFilter(request, response);
	}
	

	

}

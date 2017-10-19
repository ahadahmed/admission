package com.progoti.surecash;

import com.progoti.surecash.admission.domain.University;
import com.progoti.surecash.admission.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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

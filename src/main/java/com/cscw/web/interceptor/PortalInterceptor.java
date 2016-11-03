package com.cscw.web.interceptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cscw.entity.vo.LoginInfoVo;
import com.cscw.web.common.MVCUtil;

public class PortalInterceptor implements HandlerInterceptor {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PortalInterceptor.class);
	//普通管理员（userType为B的user） 的权限列表
	 private Set<String> authoritysB = new HashSet<String>();
	 
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) throws Exception {
		if(MVCUtil.getSessionAttribute("admin")==null){
			response.sendRedirect(request.getContextPath()+"/portal/login");
			return false;
		}
		LoginInfoVo o = (LoginInfoVo)MVCUtil.getSessionAttribute("admin");
	
		//当管理员类型是B的时候，只允许使用菜单的权限
		if("B".equals(o.getUser_type())){ 
			String[] uris = request.getRequestURI().split("/");
			if(uris.length>3){
				for(String authority : authoritysB){
					if(authority.equals(uris[2]))
						return true;
				}
				return false;
			}
		}
		return true;	
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2, ModelAndView model)
			throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception e)
			throws Exception {
	}

	public void setAuthoritysB(String s) {
		if (StringUtils.isNotBlank(s)) {
			String[] paths = s.split(",");
			for (String path : paths)
				this.authoritysB.add(path);
		}
	}

}

package com.cscw.web.controller.portal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import org.springframework.web.bind.annotation.ResponseBody;









import com.cscw.entity.po.UserInfo;
import com.cscw.entity.vo.LoginInfoVo;
import com.cscw.entity.vo.QueryUserVo;
import com.cscw.service.UserService;
import com.cscw.web.common.AjaxData;
import com.cscw.web.common.MVCUtil;
import com.huisa.common.beans.mapping.BeanMapper;
import com.huisa.common.database.model.PageBean;
import com.huisa.common.exception.ServiceException;


@Controller
@RequestMapping(value = "/portal")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	BeanMapper beanMapper;
	private static final String USER_SEARCH = "USER_SEARCH";
	private static final int PAGESIZE = 15;
	  
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		LoginInfoVo loginInfo = (LoginInfoVo) MVCUtil.getUserSession();
		if (loginInfo != null) {
			return "portal/index";
		}
		return "portal/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String check(Model model) throws ServiceException {
		String uname = MVCUtil.getParam("uname");
		String passwd = MVCUtil.getParam("passwd");
		if (StringUtils.isBlank(uname) || StringUtils.isBlank(passwd)) {
			model.addAttribute("msg", "用户名或密码不能为空!");
			return "portal/login";
		}
	//	passwd = DigestUtils.md5Hex(passwd);
		UserInfo userInfo = userService.login(uname, passwd);
		if (userInfo == null) {
			model.addAttribute("msg", "用户名或密码错误");
			return "portal/login";
		} 
		LoginInfoVo loginInfo = beanMapper.map(userInfo, LoginInfoVo.class);
		MVCUtil.setUserSession(loginInfo);
		return "portal/index";
	}

	@RequestMapping(value = "/logout")
	public String logout(Model model) {
		MVCUtil.removeUserSession();
		return "redirect:/portal/login";
	}

	@RequestMapping(value = "/home")
	public String home(Model model) {
		return "portal/home";
	}
	
	/**
	 * 获取用户列表
	 */
	@RequestMapping(value = "/user/show_user_list",method = RequestMethod.GET)
    public String showUserList(Model model) throws ServiceException {
        int pageid = MVCUtil.getIntParam("pageid");
        QueryUserVo queryUserVo = null;
        if (pageid <= 0) {
            pageid = 1;
            MVCUtil.removeSessionAttribute(USER_SEARCH);
        } else {
            Object obj = MVCUtil
                    .getSessionAttribute(USER_SEARCH);
            if (obj != null) {
                queryUserVo = (QueryUserVo) obj;
            }
        }
        PageBean pagebean = new PageBean(pageid, PAGESIZE);
        List<UserInfo> userList = userService.queryUserInfoListByPage(queryUserVo, pagebean);
       // List<QueryUserVo> userVoList=userserService.getQueryUserVoList(portaltUserList);
        model.addAttribute("userVoList", userList);
        model.addAttribute("queryUserVo", queryUserVo);
        model.addAttribute("pagebean", pagebean);
        model.addAttribute("pageuri", "/portal/user/show_user_list?");
        return "portal/user/show_user_list";
    }
	
	/**
	 * 查询用户
	 */
	@RequestMapping(value = "user/search", method = RequestMethod.POST)
    public String searchUser(QueryUserVo queryUserVo,Model model) throws ServiceException {
	    int pageid = 0;
        if (queryUserVo != null) {
            MVCUtil.setSessionAttribute(USER_SEARCH, queryUserVo);
            pageid = 1;
        }
        return "redirect:/portal/user/show_user_list?pageid=" + pageid;
    }
	@RequestMapping(value = "user/delete", method = RequestMethod.GET)
    public String deletePaymentUser(Model model) throws ServiceException {
       int id = MVCUtil.getIntParam("id");
       if(id>0)
           userService.deleteUser(id);
       return "redirect:/portal/user/show_user_list";
    }
	
	/**
	 * 添加新用户
	 */

    @RequestMapping(value = "user/add", method = RequestMethod.POST)
	@ResponseBody
    public void addPortalUser(UserInfo portalUser,Model model){
	   AjaxData ajaxdata;
	   String msg = null;
	   String rePasswd=MVCUtil.getParam("rePasswd");
	
      
       if (StringUtils.isBlank(portalUser.getAccount())) {
           msg = "账号不能为空！";
       } else if (StringUtils.isBlank(portalUser.getUserName())) {
           msg = "用户姓名不能为空！";
       } else if(StringUtils.isBlank(portalUser.getUserType())){
           msg = "请选择用户的角色！";
       } else if (StringUtils.isBlank(portalUser.getPasswd())) {
           msg = "密码不能为空！";       
       } else if (StringUtils.isBlank(rePasswd)) {
           msg = "新密码没有重复输入！";
       } else if (!portalUser.getPasswd().equals(rePasswd)) {
           msg = "两次输入的密码不相同！";
       }

       if (StringUtils.isNotBlank(msg)) {
           ajaxdata = new AjaxData(false, null, msg);
           MVCUtil.ajaxJson(ajaxdata);
           return;
       }
       try {
           portalUser.setCreateTime(new Date());
           userService.addUser(portalUser);
           ajaxdata = new AjaxData(true, null, null);
       } catch (ServiceException e){
           ajaxdata = new AjaxData(false, null, "该帐号已经存在！");
        //   LOGGER.error(e.getMessage());
       }
       MVCUtil.ajaxJson(ajaxdata);
    }

}

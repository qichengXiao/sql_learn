package com.cscw.entity.vo;

import com.cscw.common.AppContext;
import com.huisa.common.beans.mapping.annotation.MapClass;
import com.huisa.common.beans.mapping.annotation.MapField;



@MapClass("com.portal.entity.po.UserInfo")
public class LoginInfoVo {
	@MapField("id")
	private String user_id;
	@MapField("userName")
	private String user_name;
	@MapField("userType")
	private String user_type;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}



	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

}

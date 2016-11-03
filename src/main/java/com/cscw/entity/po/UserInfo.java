package com.cscw.entity.po;

import com.huisa.common.reflection.annotations.huisadb_alias;
import com.huisa.common.reflection.annotations.huisadb_ignore;

/* 管理员  */
@huisadb_alias("user_info")
public class UserInfo {
	@huisadb_ignore
	private java.lang.Integer id;//remark:id，自增;length:10; not null,default:null

	private java.lang.String account;//remark:账号，D端注册的用户该值和手机号相同;length:24; not null,default:null
	@huisadb_alias("user_name")
	private java.lang.String userName;//remark:账号，D端注册的用户该值和手机号相同;length:24; not null,default:null

	private java.lang.String passwd;//remark:密码，MD5加密;length:32; not null,default:null
	@huisadb_alias("user_type")
	private java.lang.String userType;//remark:用户类型，值域【A，B，C，D，E】;length:1; not null,default:null
	

	private java.lang.String sessionid;//remark:用户类型，值域【A，B，C，D，E】;length:1; not null,default:null
	
	@huisadb_alias("create_time")
	private java.util.Date createTime;//remark:创建时间;length:19
	
	@huisadb_ignore
	@huisadb_alias("update_time")
	private java.util.Date updateTime;//remark:更新时间;length:19; not null,default:CURRENT_TIMESTAMP

	public UserInfo() {
	}

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getAccount() {
		return account;
	}

	public void setAccount(java.lang.String account) {
		this.account = account;
	}

	public java.lang.String getPasswd() {
		return passwd;
	}

	public void setPasswd(java.lang.String passwd) {
		this.passwd = passwd;
	}

	public java.lang.String getUserType() {
		return userType;
	}

	public void setUserType(java.lang.String userType) {
		this.userType = userType;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.lang.String getSessionid() {
		return sessionid;
	}

	public void setSessionid(java.lang.String sessionid) {
		this.sessionid = sessionid;
	}

	public java.lang.String getUserName() {
		return userName;
	}

	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}

}
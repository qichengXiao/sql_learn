package com.cscw.entity.po;

import org.springframework.format.annotation.DateTimeFormat;

import com.huisa.common.reflection.annotations.huisadb_alias;
import com.huisa.common.reflection.annotations.huisadb_ignore;

/* 用户 */
@huisadb_alias("customer_info")
public class Customer {
	@huisadb_ignore
	private java.lang.Integer id;//remark:id，自增;length:10; not null,default:null
	@huisadb_alias("phone_num")
	private java.lang.String phoneNum;//remark:手机号;length:16; not null,default:null
	private java.lang.String email;//remark:邮箱;length:40
	private java.lang.String passwd;//remark:密码，MD5加密;length:32; not null,default:null
	@huisadb_alias("user_name")
	private java.lang.String userName;//remark:用户名/项目名;length:24; not null,default:null
	@huisadb_alias("pro_name")
	private java.lang.String proName;//remark:用户名/项目名;length:24; not null,default:null

	@huisadb_alias("data_id")
	private java.lang.Integer dataId;//remark: 数据表id
	
	private java.lang.String sessionid;//remark:sessionid;length:32
	@huisadb_alias("last_login_time")
	private java.util.Date lastLoginTime;//remark:最后一次登录时间;length:19
	
	@huisadb_alias("count_num")
	private java.lang.Integer countNum;//remark:点击“确定”（计算）次数 ;length:11; not null,default:0
	@huisadb_alias("login_num")
	private java.lang.Integer loginNum;//remark:最后一次登录的手机系统类型，值域【0:未知，1:ios，2：android】;length:11; not null,default:0

	@huisadb_alias("vip_deadline")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private  java.util.Date vipDeadline;//remark:身份标识【0:免费用户,1:付费用户,2:封禁用户】length:1; ,default:0
	
	@huisadb_alias("status")
	private java.lang.Integer status;//remark:身份标识【0:免费用户,1:付费用户,2:封禁用户】length:1; ,default:0
	
	
	@huisadb_alias("create_time")
	private java.util.Date createTime;//remark:注册时间;length:19
	@huisadb_ignore
	@huisadb_alias("update_time")
	private java.util.Date updateTime;//remark:更新时间;length:19; not null,default:CURRENT_TIMESTAMP
	
	
	public Customer() {
		super();
	}


	public java.lang.Integer getId() {
		return id;
	}


	public void setId(java.lang.Integer id) {
		this.id = id;
	}


	public java.lang.String getPhoneNum() {
		return phoneNum;
	}


	public void setPhoneNum(java.lang.String phoneNum) {
		this.phoneNum = phoneNum;
	}


	public java.lang.String getEmail() {
		return email;
	}


	public void setEmail(java.lang.String email) {
		this.email = email;
	}


	public java.lang.String getPasswd() {
		return passwd;
	}


	public void setPasswd(java.lang.String passwd) {
		this.passwd = passwd;
	}


	public java.lang.String getUserName() {
		return userName;
	}


	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}


	public java.lang.String getProName() {
		return proName;
	}


	public void setProName(java.lang.String proName) {
		this.proName = proName;
	}


	public java.lang.Integer getDataId() {
		return dataId;
	}


	public void setDataId(java.lang.Integer dataId) {
		this.dataId = dataId;
	}


	public java.lang.String getSessionid() {
		return sessionid;
	}


	public void setSessionid(java.lang.String sessionid) {
		this.sessionid = sessionid;
	}


	public java.util.Date getLastLoginTime() {
		return lastLoginTime;
	}


	public void setLastLoginTime(java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}


	public java.lang.Integer getCountNum() {
		return countNum;
	}


	public void setCountNum(java.lang.Integer countNum) {
		this.countNum = countNum;
	}


	public java.lang.Integer getLoginNum() {
		return loginNum;
	}


	public void setLoginNum(java.lang.Integer loginNum) {
		this.loginNum = loginNum;
	}


	public java.util.Date getVipDeadline() {
		return vipDeadline;
	}


	public void setVipDeadline(java.util.Date vipDeadline) {
		this.vipDeadline = vipDeadline;
	}


	public java.lang.Integer getStatus() {
		return status;
	}


	public void setStatus(java.lang.Integer status) {
		this.status = status;
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
	
	
}
package com.cscw.entity.po;

import java.util.ArrayList;
import java.util.List;

import com.huisa.common.reflection.annotations.huisadb_alias;
import com.huisa.common.reflection.annotations.huisadb_ignore;

/* 菜单 */
@huisadb_alias("menu")
public class Menu {
	@huisadb_ignore
	private java.lang.Integer id;//remark:id，自增;length:10; not null,default:null
	private java.lang.String name;//remark:菜单名;length:24; not null,default:null
	@huisadb_alias("parent_id")
	private java.lang.Integer parentId;//remark:父菜单全编号;length:16
//	@huisadb_alias("material_id")
//	private java.lang.Integer materialId;//remark:文章id;length:11; ,default:null
	
	@huisadb_alias("article_title")
	private java.lang.String articleTitle;//remark:菜单名;length:24; not null,default:null
	@huisadb_alias("article_content")
	private java.lang.String articleContent;//remark:菜单名;length:24; not null,default:null
	
	@huisadb_alias("create_time")
	private java.util.Date createTime;//remark:注册时间;length:19
	@huisadb_ignore
	@huisadb_alias("update_time")
	private java.util.Date updateTime;//remark:更新时间;length:19; not null,default:CURRENT_TIMESTAMP
	@huisadb_ignore
	private List<Menu> childMenus;//二级菜单
	
	@huisadb_ignore
	private int childNum;//二级菜单个数

	
	public Menu() {
		super();
	}

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.Integer getParentId() {
		return parentId;
	}

	public void setParentId(java.lang.Integer parentId) {
		this.parentId = parentId;
	}

	

	public java.lang.String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(java.lang.String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public java.lang.String getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(java.lang.String articleContent) {
		this.articleContent = articleContent;
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


	public List<Menu> getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(List<Menu> childMenus) {
		this.childMenus = childMenus;
	}
	
	public void addChildMenu(Menu menu) {
		if(childMenus==null){
			childMenus = new ArrayList<Menu>();
		}
		childMenus.add(menu);
	}

	public int getChildNum() {
		return childNum;
	}

	public void setChildNum(int childNum) {
		this.childNum = childNum;
	}

	
	
}
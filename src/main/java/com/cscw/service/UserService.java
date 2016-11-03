package com.cscw.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cscw.dao.common.UserInfoDao;
import com.cscw.entity.po.UserInfo;
import com.cscw.entity.vo.QueryUserVo;
import com.huisa.common.beans.mapping.BeanMapper;
import com.huisa.common.database.model.PageBean;
import com.huisa.common.exception.ServiceException;

@Service
public class UserService {
	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	BeanMapper beanMapper;

	public UserInfo login(String account, String passwd)
			throws ServiceException {
		return userInfoDao.getLoginInfoByAccountAndPasswd(account, passwd);
	}


	
	  
    /**
     * 根据account,user_name,role来分页查询用户
     */
    public List<UserInfo> queryUserInfoListByPage(QueryUserVo queryUserVo,
            PageBean pageBean) throws ServiceException {
        StringBuffer querySqlBuffer = new StringBuffer();
        List<Object> queryParams = new ArrayList<Object>();

        querySqlBuffer.append("SELECT pu.* FROM user_info AS pu  WHERE 1=1");
        if (queryUserVo != null) {
            if (StringUtils.isNotBlank(queryUserVo.getUser_name())) {
                querySqlBuffer.append(" AND user_name LIKE ?");
                queryParams.add("%" + queryUserVo.getUser_name() + "%");
            }
            if (StringUtils.isNotBlank(queryUserVo.getAccount())) {
                querySqlBuffer.append(" AND account LIKE ?");
                queryParams.add("%" + queryUserVo.getAccount() + "%");
            }
            if (StringUtils.isNotBlank(queryUserVo.getRole())) {
                querySqlBuffer.append(" AND user_type=?");
                queryParams.add(queryUserVo.getRole());
            }
        }
        List<UserInfo> UserInfoList = userInfoDao.queryUserByPage(pageBean, querySqlBuffer.toString(), queryParams);

        return UserInfoList;
    }




	public void deleteUser(int id) throws ServiceException {
		userInfoDao.delete("DELETE FROM user_info WHERE id=?", id);
		
		
	}




	public void addUser(UserInfo portalUser) throws ServiceException {
		userInfoDao.add(portalUser);
		
	}
	
    /**
     * 获取用于页面显示的QueryUserVo列表
     */

}

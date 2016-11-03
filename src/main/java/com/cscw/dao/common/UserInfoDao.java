package com.cscw.dao.common;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cscw.entity.po.UserInfo;
import com.huisa.common.database.BaseDao;
import com.huisa.common.database.model.PageBean;
import com.huisa.common.exception.ServiceException;

@Repository
public class UserInfoDao extends BaseDao {

	public UserInfo getLoginInfoByAccountAndPasswd(String account, String passwd)
			throws ServiceException {
		return get(
				"SELECT id,account,user_name,user_type FROM user_info"
						+ " WHERE account=? AND passwd=? LIMIT 1",
				UserInfo.class,  account, passwd);
	}

	public List<UserInfo> queryUserByPage(PageBean pageBean, String sql,
            List<Object> params) throws ServiceException {
        return listByPage(pageBean, sql, UserInfo.class, params);
    }

}

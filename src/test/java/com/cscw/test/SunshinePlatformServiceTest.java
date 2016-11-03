package com.cscw.test;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.count.entity.vo.response.login.LoginResponse;
import com.count.entity.vo.response.project.AllReviewProjectResponse;
import com.count.entity.vo.response.project.InvitationResponse;
import com.count.entity.vo.response.project.MyProjectResponse;
import com.count.entity.vo.response.project.PendingReviewProjectResponse;
import com.count.entity.vo.response.project.ProjectDetailsResponse;
import com.count.entity.vo.response.project.WorkRemarkResponse;
import com.count.service.platform.SunshinePlatformService;
import com.huisa.common.exception.ServiceException;

public class SunshinePlatformServiceTest {
    private static Logger LOGGER = LoggerFactory
            .getLogger(SunshinePlatformServiceTest.class);
    
    private SunshinePlatformService sunshinePlatformService = new SunshinePlatformService();;

    @Before
    public void init() {
        sunshinePlatformService.init();
    }

    @org.junit.Test
    public void testLoginAndWordRemark() {
        LoginResponse loginResponse;
        try {
            loginResponse = sunshinePlatformService.login("493411276@qq.com", "kjt1228");
        
            LOGGER.info(loginResponse.getStatus() + "  " + loginResponse.getRole_list() + "  "
                    + loginResponse.getPsn_code()+"  "+loginResponse.getException_desc());
    
            WorkRemarkResponse workRemarkResponse = sunshinePlatformService.getWorkRemark(
                    loginResponse.getPsn_code(), "3", "1", "20");
            LOGGER.info(workRemarkResponse.getStatus() + "  "
                    + workRemarkResponse.getException_code()+"  "+workRemarkResponse.getPrp_counts());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testPendingProjectAndInvitation()
    {
        LoginResponse loginResponse;
        try {
            //loginResponse = sunshinePlatformService.login("p78789", "111111");
       
        //LOGGER.info(loginResponse.getStatus() + "  " + loginResponse.getRole_list() + "  "
                //+ loginResponse.getPsn_code()+"  "+loginResponse.getException_desc());
        
        InvitationResponse invitationResponse = sunshinePlatformService.getInvitation(
                "28765", "1", "20");
        LOGGER.info(invitationResponse.getStatus() + "  "
                + invitationResponse.getException_code()+" "+invitationResponse.getInvite_counts());

        PendingReviewProjectResponse pendingReviewProjectResponse = sunshinePlatformService
                .getPendingReviewProject("28765", "1", "20");
        LOGGER.info(pendingReviewProjectResponse.getStatus() + "  "
                + pendingReviewProjectResponse.getException_code()+" "+pendingReviewProjectResponse.getInvite_counts());
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Test
    public void testMyProjectAndProjectDetail()
    {
        LoginResponse loginResponse;
        try {
            loginResponse = sunshinePlatformService.login("p78789", "111111");
        
            LOGGER.info(loginResponse.getStatus() + "  " + loginResponse.getRole_list() + "  "
                    + loginResponse.getPsn_code()+"  "+loginResponse.getException_desc());
            
            MyProjectResponse myProjectResponse = sunshinePlatformService.getMyProject(
                    loginResponse.getPsn_code(), "2", "10");
            LOGGER.info(myProjectResponse.getStatus() + "  "
                    + myProjectResponse.getException_code()+" "+myProjectResponse.getPrp_counts()+" "+myProjectResponse.getPage_counts());
    
            ProjectDetailsResponse projectDetailsResponse = sunshinePlatformService.getProjectDetail(
                    loginResponse.getPsn_code(), "1", "20");
            LOGGER.info(projectDetailsResponse.getStatus() + " "
                    + projectDetailsResponse.getPrp_counts());
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Test
    public void testAllReviewProject()
    {
          try {
               //LoginResponse loginResponse = sunshinePlatformService.login("swj14 ", "39323332");  
               //LOGGER.info(loginResponse.getStatus() + "  " + loginResponse.getRole_list() + "  "
                       //+ loginResponse.getPsn_code()+"  "+loginResponse.getException_desc());
               //PendingReviewProjectResponse pendingReviewProjectResponse = sunshinePlatformService
                      //.getPendingReviewProject("28765", "1", "20");
               //LOGGER.info(pendingReviewProjectResponse.getInvite_counts());
               
               AllReviewProjectResponse allReviewProjectResponse=sunshinePlatformService.getAllReviewProject("28765");
               LOGGER.info(allReviewProjectResponse.getReview_counts());
               
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Test
    public void test()
    {
         String s1="100";
         String s2="45";
         int result=Integer.valueOf(s1)-Integer.valueOf(s2);
         System.out.println(result);
    }
}

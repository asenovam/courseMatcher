/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.beans;

import co.uk.city.london.entities.CourseEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mar_9
 */
public class UserDashboardBean {
    
       private String userName;
       private String nickname;
       
       private List<CourseEntity> myCourses = new ArrayList<>();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<CourseEntity> getMyCourses() {
        return myCourses;
    }

    public void setMyCourses(List<CourseEntity> myCourses) {
        this.myCourses = myCourses;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
       
       
}

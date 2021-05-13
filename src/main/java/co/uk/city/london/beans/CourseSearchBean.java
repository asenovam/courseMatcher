/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.beans;

import co.uk.city.london.entities.CourseEntity;
import java.util.List;

/**
 *
 * @author mar_9
 */
public class CourseSearchBean {
    
    private String courseTitle;
    private String nickname;
    private String universityDegree;
    
    private String errorMesssage = "";
    
    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getErrorMesssage() {
        return errorMesssage;
    }

    public void setErrorMesssage(String errorMesssage) {
        this.errorMesssage = errorMesssage;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUniversityDegree() {
        return universityDegree;
    }

    public void setUniversityDegree(String universityDegree) {
        this.universityDegree = universityDegree;
    }

  
    
    
}

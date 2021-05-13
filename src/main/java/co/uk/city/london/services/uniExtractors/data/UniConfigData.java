/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.services.uniExtractors.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mar_9
 */
public class UniConfigData {
    
    private String uniName;
    private List<String> courseUrls = new ArrayList<>();

    public String getUniName() {
        return uniName;
    }

    public void setUniName(String uniName) {
        this.uniName = uniName;
    }

    public List<String> getCourseUrls() {
        return courseUrls;
    }

    public void setCourseUrls(List<String> courseUrls) {
        this.courseUrls = courseUrls;
    }
    
    
    public String toString() {
        return "uniName " + uniName + " " + courseUrls;
    }
    
}

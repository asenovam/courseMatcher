/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.beans;

import co.uk.city.london.entities.ModuleEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 *
 * @author mar_9
 */
public class CourseBean {
    
    private String id;
    
    private String title;
    
    private String url;
    
    private String universityName;
    
    private String universityDegree;
    
    private List<ModuleBean> modules = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public List<ModuleBean> getModules() {
        return modules;
    }

    public void setModules(List<ModuleBean> modules) {
        this.modules = modules;
    }

    public String getUniversityDegree() {
        return universityDegree;
    }

    public void setUniversityDegree(String universityDegree) {
        this.universityDegree = universityDegree;
    }
    
    
}

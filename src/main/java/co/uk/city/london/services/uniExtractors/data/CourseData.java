/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.services.uniExtractors.data;

import co.uk.city.london.entities.UniversityDegree;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mar_9
 */
public class CourseData {
    
     private String title;
    
    private String url;
    
    private List<ModuleData> modules = new ArrayList<>();
    
    private String universityName;
    
    private UniversityDegree degree;

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

    public List<ModuleData> getModules() {
        return modules;
    }

    public void setModules(List<ModuleData> modules) {
        this.modules = modules;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public UniversityDegree getDegree() {
        return degree;
    }

    public void setDegree(UniversityDegree degree) {
        this.degree = degree;
    }
    
    
    
}

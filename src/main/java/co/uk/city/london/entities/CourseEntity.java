/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.entities;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 *
 * @author mar_9
 */
public class CourseEntity {
    
    public static Long idGenerator = Long.valueOf(0);

    @Indexed(unique = true)
    @Id
    private String id;
    
    @Indexed
    private String title;
    
    private String url;
    
    private String universityName;
   
    private UniversityDegree degree;
    
    private List<ModuleEntity> modules = new ArrayList<>();
            

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

    public List<ModuleEntity> getModules() {
        return modules;
    }

    public void setModules(List<ModuleEntity> modules) {
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

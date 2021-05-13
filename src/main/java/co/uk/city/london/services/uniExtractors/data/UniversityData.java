/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.services.uniExtractors.data;

import co.uk.city.london.entities.CourseEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mar_9
 */
public class UniversityData {
    
    private String name;
    
    private List<String> urls = new ArrayList<>();
    
    private List<CourseData> courses = new ArrayList<>();

   
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CourseData> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseData> courses) {
        this.courses = courses;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
    
    
    
    
}

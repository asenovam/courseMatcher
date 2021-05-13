/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.builder;

import co.uk.city.london.entities.CourseEntity;
import co.uk.city.london.entities.ModuleEntity;
import co.uk.city.london.entities.UniversityDegree;


import co.uk.city.london.beans.CourseBean;
import co.uk.city.london.beans.ModuleBean;

/**
 *
 * @author mar_9
 */
public class CourseBeanBuilder {
    
   
     private CourseBean courseEntity = new CourseBean();
    
    public CourseBeanBuilder setTitle(String title) {
            courseEntity.setTitle(title);
            
            return this;
    }
    
    
    public CourseBeanBuilder addModule(String name, String color) {
            ModuleBean module1 = new ModuleBean();
             module1.setName(name);
             module1.setColor(color);
             courseEntity.getModules().add(module1);
            
            return this;
    }
    
    public CourseBean build() {
        return this.courseEntity;
    }
        
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.builder;

import co.uk.city.london.entities.CourseEntity;
import co.uk.city.london.entities.ModuleEntity;
import co.uk.city.london.entities.UniversityDegree;

/**
 *
 * @author mar_9
 */
public class EntityCourseBuilder {
    
    private CourseEntity courseEntity = new CourseEntity();
    
    public EntityCourseBuilder setTitle(String title) {
            courseEntity.setTitle(title);
            
            return this;
    }
    
    public EntityCourseBuilder setDegree(UniversityDegree degree) {
            courseEntity.setDegree(degree);
            
            return this;
    }
    
    public EntityCourseBuilder addModule(String name) {
            ModuleEntity module1 = new ModuleEntity();
             module1.setName(name);
             courseEntity.getModules().add(module1);
            
            return this;
    }
    
    public CourseEntity build() {
        return this.courseEntity;
    }
        
}

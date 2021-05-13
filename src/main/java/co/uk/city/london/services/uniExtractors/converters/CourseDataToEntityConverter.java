/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.services.uniExtractors.converters;

import co.uk.city.london.entities.CourseEntity;
import co.uk.city.london.entities.ModuleEntity;
import co.uk.city.london.entities.UniversityDegree;
import co.uk.city.london.entities.UniversityEntity;
import co.uk.city.london.services.uniExtractors.data.CourseData;
import co.uk.city.london.services.uniExtractors.data.ModuleData;
import co.uk.city.london.services.uniExtractors.data.UniversityData;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author mar_9
 */
@Component
public class CourseDataToEntityConverter {
    
    @Autowired
    private ModuleDataToEntityConverter moduleDataToEntityConverter;
    
    public void convert(CourseData courseData, CourseEntity courseEntity) {
        courseEntity.setUrl(courseData.getUrl());
        courseEntity.setTitle(courseData.getTitle());
        courseEntity.setUniversityName(courseData.getUniversityName());
        courseEntity.setDegree(courseData.getDegree());
        
         final List<ModuleEntity> moduleEntities = courseEntity.getModules();
        for (ModuleData moduleData : courseData.getModules()) {
           final String moduleName = moduleData.getName();
           // find moudle by name, if there is in  the DB, then it will update the module, otherwise, it will create a new one
           ModuleEntity moduleEntity = moduleEntities.stream().filter(m-> moduleName.equals(m.getName())).findAny().orElse(null);
           if (moduleEntity == null) {
               moduleEntity = new ModuleEntity();
               courseEntity.getModules().add(moduleEntity);
           }
           moduleDataToEntityConverter.convert(moduleData, moduleEntity);          
        }
        
        removeNotExistingAnymoreModules(courseData, courseEntity);
    }
    
     private void removeNotExistingAnymoreModules(CourseData courseData, CourseEntity courseEntity) {
        List<String> moduleDbNames = courseEntity.getModules().stream().map(ModuleEntity::getName)
                .collect(Collectors.toList());
        for (String moduleName : moduleDbNames) {
            boolean isModuleExistsInTheCourse = courseData.getModules().stream().anyMatch
        (m -> m.getName().equals(moduleName));
            if (!isModuleExistsInTheCourse) {
                ModuleEntity moduleEntity = courseEntity.getModules().stream().filter
        (m -> m.getName().equals(moduleName)).findAny().orElse(null);
                courseEntity.getModules().remove(moduleEntity);
            }
        }
    }
    
}

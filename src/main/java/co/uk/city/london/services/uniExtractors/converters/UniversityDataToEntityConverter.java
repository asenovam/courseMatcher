/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.services.uniExtractors.converters;

import co.uk.city.london.entities.CourseEntity;
import co.uk.city.london.entities.UniversityEntity;
import co.uk.city.london.services.uniExtractors.data.UniversityData;
import co.uk.city.london.services.uniExtractors.data.CourseData;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author mar_9
 */
@Component
public class UniversityDataToEntityConverter {
    
    @Autowired
    private CourseDataToEntityConverter courseDataToEntityConverter;
    
    public void  convert(UniversityData universityData, UniversityEntity universityEntity) {
        universityEntity.setName(universityData.getName());
        universityEntity.setUrls(universityData.getUrls());
        
        final List<CourseEntity> courseEntities = universityEntity.getCourses();
        for (CourseData courseData : universityData.getCourses()) {
           final String courseUrl = courseData.getUrl();
           // find course by url, if there is in  the DB, then it will update the course, otherwise, it will create a new one
           CourseEntity courseEntity = courseEntities.stream().filter(c-> courseUrl.equals(c.getUrl())).findAny().orElse(null);
           if (courseEntity == null) {
               courseEntity = new CourseEntity();
               universityEntity.getCourses().add(courseEntity);
           }
           courseDataToEntityConverter.convert(courseData, courseEntity);          
        }
        
        removeNotExistingAnymoreCourses(universityData, universityEntity);
    }
    
    private void removeNotExistingAnymoreCourses(UniversityData universityData, UniversityEntity universityEntity) {
        List<String> courseDbUrls = getUrls(universityEntity);
        for (String courseDbUrl : courseDbUrls) {
             boolean isInTheScrapedResult = universityData.getCourses().stream().anyMatch(c -> c.getUrl().equals(courseDbUrl));
             if (!isInTheScrapedResult) {
                CourseEntity courseEntity =  universityEntity.getCourses().stream().filter(c-> courseDbUrl.equals(c.getUrl())).findAny().orElse(null);
                universityEntity.getCourses().remove(courseEntity);
             }
        }
    }
    
    private List<String> getUrls(UniversityEntity universityEntity) {
        List<String> dbCourseUrls = new ArrayList<>();
        for (CourseEntity courseentity : universityEntity.getCourses()) {
            dbCourseUrls.add(courseentity.getUrl());
        }
        return dbCourseUrls;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london;

import co.uk.city.london.controllers.CoursesController;
import co.uk.city.london.controllers.SurveyController;
import co.uk.city.london.services.UniversityManagement;
import co.uk.city.london.services.uniExtractors.converters.CourseDataToEntityConverter;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.TokenCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.web.context.annotation.ApplicationScope;

/**
 *
 * @author mar_9
 */
// fromhere the app starts 
@SpringBootApplication
public class ApplicationStartUp implements CommandLineRunner {
    
     
     @Autowired
     private UniversityManagement universityManagement;
    
      public static void main(String[] args) {
           SpringApplication.run(ApplicationStartUp.class, args);         
           
       
      
           
      }
      
       @Override
        public void run(String... args) throws Exception {
            //universityManagement.loadAndSaveUniversitiesData();  
        }
}

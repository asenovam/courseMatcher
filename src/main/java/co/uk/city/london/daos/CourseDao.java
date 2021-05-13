/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.daos;

import co.uk.city.london.entities.CourseEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * @author mar_9
 */
public interface CourseDao extends MongoRepository<CourseEntity, String> {
    public List<CourseEntity> findByTitle(String title);
    
    public List<CourseEntity> findByUrl(String title);
   
    
    @Query("{ 'title':{$regex:?0,$options:'i'}}")
    public List<CourseEntity> findByTitleRegex(String titleRegex);
}

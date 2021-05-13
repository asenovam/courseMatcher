/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.daos;

import co.uk.city.london.entities.UniversityEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author mar_9
 */
public interface UniversityDao extends MongoRepository<UniversityEntity, String> {
    public List<UniversityEntity> findByName(String name);
}


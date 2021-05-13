/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.daos;

import co.uk.city.london.entities.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author mar_9
 */
public interface UserDao extends MongoRepository<UserEntity, String> {

    public UserEntity findByNickname(String nickname);

    public UserEntity findByEmail(String email);

}

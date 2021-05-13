/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.entities;

/**
 *
 * @author mar_9
 */
public enum UniversityDegree {
    
    BACHELOR,
    MASTER,
    DOCTOR;
    
    public static UniversityDegree getByName(String name) {
        for (UniversityDegree degree : UniversityDegree.values()) {
            if (degree.name().equals(name)) {
                return degree;
            }
        }

        return null;
    }
}

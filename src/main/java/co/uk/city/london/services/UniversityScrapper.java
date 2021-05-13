/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.services;

import co.uk.city.london.services.uniExtractors.data.UniversityData;
import java.util.List;

/**
 *
 * @author mar_9
 */
public interface UniversityScrapper {
    
     UniversityData dataScraping(final List<String> universityCoursesLink);
}

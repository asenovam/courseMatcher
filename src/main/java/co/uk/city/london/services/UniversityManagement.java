/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.services;

import co.uk.city.london.daos.CourseDao;
import co.uk.city.london.services.uniExtractors.converters.UniversityDataToEntityConverter;
import co.uk.city.london.daos.UniversityDao;
import co.uk.city.london.entities.CourseEntity;
import co.uk.city.london.entities.UniversityEntity;
import co.uk.city.london.services.uniExtractors.data.ModuleData;
import co.uk.city.london.services.uniExtractors.data.UniConfigData;
import co.uk.city.london.services.uniExtractors.data.UniversityData;
import co.uk.city.london.services.uniExtractors.universityScrappers.AweBristol;

import co.uk.city.london.services.uniExtractors.universityScrappers.KingstonUniversity;
import co.uk.city.london.services.uniExtractors.universityScrappers.YorkUniversityScrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.uk.city.london.services.uniExtractors.universityScrappers.KingstonUniversity;
import co.uk.city.london.services.uniExtractors.universityScrappers.GloucestershireUniversity;
import co.uk.city.london.services.uniExtractors.universityScrappers.LancasterUniversity;
import co.uk.city.london.services.uniExtractors.universityScrappers.NewcastleUniversity;
import co.uk.city.london.services.uniExtractors.universityScrappers.NuiGalway;
import co.uk.city.london.services.uniExtractors.universityScrappers.UniversityOfBirmingham;
import co.uk.city.london.services.uniExtractors.universityScrappers.UniversityOfWolverhampton;

/**
 *
 * @author mar_9
 */
@Component
public class UniversityManagement {

    @Autowired
    private UniversityDataToEntityConverter universityDataToEntityConverter;

    @Autowired
    private UniversityDao universityDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private UrlReader urlReader;

    private Map<String, UniversityScrapper> universityScrappers = new HashMap<>();

    @PostConstruct
    public void init() {
        // name of the Uni - key in the map - is the same which is in the universitioslist.txt file 
        universityScrappers.put("University Of York", new YorkUniversityScrapper());
        universityScrappers.put("Kingston University", new KingstonUniversity());
        universityScrappers.put("Gloucestershire University", new GloucestershireUniversity());
        universityScrappers.put("AWE Bristol", new AweBristol());
        universityScrappers.put("University Of Wolverhampton", new UniversityOfWolverhampton());
        universityScrappers.put("NUI Galway", new NuiGalway());
        universityScrappers.put("Newcastle University", new NewcastleUniversity());
        universityScrappers.put("University Of Birmingham", new UniversityOfBirmingham());
        universityScrappers.put("Lancaster University", new LancasterUniversity());

    }

    public void loadAndSaveUniversitiesData() {
        List<UniConfigData> uniConfigDatas = urlReader.readUniConfigs();

        for (UniConfigData uniConfigData : uniConfigDatas) {
            System.out.println("\n\n\n\n\n " + uniConfigData.toString());
            final String uniName = uniConfigData.getUniName();
            final List<String> courseUrls = uniConfigData.getCourseUrls();
            List<UniversityEntity> univerities = universityDao.findByName(uniName);
            UniversityEntity university = univerities != null && !univerities.isEmpty() ? univerities.get(0) : null;
            if (university == null) {
                university = new UniversityEntity();
            }
            UniversityScrapper universityScrapper = universityScrappers.get(uniName);
            UniversityData universityData = universityScrapper.dataScraping(courseUrls);
            universityData.setName(uniName);
            universityDataToEntityConverter.convert(universityData, university);
            universityDao.save(university);
            for (CourseEntity course : university.getCourses()) {
                this.courseDao.save(course);
            }
        }
    }

}

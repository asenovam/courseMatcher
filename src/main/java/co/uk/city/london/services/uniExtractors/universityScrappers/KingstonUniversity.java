/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.services.uniExtractors.universityScrappers;

import co.uk.city.london.entities.UniversityDegree;
import co.uk.city.london.services.UniversityScrapper;
import co.uk.city.london.services.uniExtractors.data.CourseData;
import co.uk.city.london.services.uniExtractors.data.ModuleData;
import co.uk.city.london.services.uniExtractors.data.UniversityData;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author mar_9
 */
public class KingstonUniversity implements UniversityScrapper {

    public UniversityData dataScraping(final List<String> universityCoursesLink) {
        UniversityData universityData = new UniversityData();
        universityData.setUrls(universityCoursesLink);
        universityData.setName("Kingston University");

        try {
            Document document = Jsoup.connect(universityCoursesLink.get(0)).get();
            Set<String> ugLinks = new HashSet<>();
            Elements coursesLinks = document.select("a[href]");
            for (Element link : coursesLinks) {
                String currentLink = link.absUrl("href");
                if (currentLink.contains("undergraduate/courses") && currentLink.length() > 50) {
                    if (!currentLink.contains("fees-and-funding") && !currentLink.contains("/access/") && !currentLink.contains("/accommodation/") && !currentLink.contains("why-choose-")
                            && !currentLink.contains("/applicants/") && !currentLink.contains("/how-to-apply/")
                            && !currentLink.contains("twitter") && !currentLink.contains("facebook")) {
                        ugLinks.add(currentLink);
                        System.out.println(currentLink);
                    }
                }
            }

            Set<String> pgLinks = new HashSet<>();
            Document postgraduate = Jsoup.connect(universityCoursesLink.get(1)).get();
            Elements courseLinks = postgraduate.select("a[href]");
            for (Element ls : courseLinks) {
                String currentLinkPg = ls.absUrl("href");
                if (currentLinkPg.contains("postgraduate/courses") && currentLinkPg.length() > 50) {
                    if (!currentLinkPg.contains("fees-and-funding") && !currentLinkPg.contains("/access/") && !currentLinkPg.contains("/accommodation/") && !currentLinkPg.contains("why-choose-")
                            && !currentLinkPg.contains("/applicants/") && !currentLinkPg.contains("/how-to-apply/")
                            && !currentLinkPg.contains("twitter") && !currentLinkPg.contains("facebook")) {
                        pgLinks.add(currentLinkPg);
                        System.out.println(currentLinkPg);
                    }
                }
            }

            for (String ugUrl : ugLinks) {
                   CourseData courseData = new CourseData();
                   
                   courseData.setDegree(UniversityDegree.BACHELOR);
                   Document ug = Jsoup.connect(ugUrl).get();
                   Elements courseTitleUg = ug.select("h1");
                   courseData.setTitle(courseTitleUg.text());
                   courseData.setUrl(ugUrl);
                   courseData.setUniversityName("Kingston University");
                   
                   Elements ugModules = ug.select("h5");
                   for (Element ugMod : ugModules) {
                        String mod = String.valueOf(ugMod);
                        if (!mod.contains("Year 1") && !mod.contains("Year 2") && !mod.contains("Year 3") && !mod.contains("Final year")) {
                                System.out.println(ugMod.text());
                                ModuleData module = new ModuleData();
                                module.setName(ugMod.text());
                                courseData.getModules().add(module);
                         }
                   }
                   universityData.getCourses().add(courseData);
             }

             for (String pgUrl : pgLinks) {                 
                 Document pg = Jsoup.connect(pgUrl).get();
                 Elements courseTitle = pg.select("h1");
                 String courseTitlePg = String.valueOf(courseTitle);
                  if (!courseTitlePg.contains("Technology (Maritime Operations) MSc") && !courseTitlePg.contains("Assessment Only route leading to Qualified Teacher Status")) {
                            CourseData courseData = new CourseData();
                            courseData.setDegree(UniversityDegree.MASTER);
                            courseData.setUrl(pgUrl);
                            courseData.setTitle(courseTitle.text());
                            courseData.setUniversityName("Kingston University");

                             Elements ugModules = pg.select("h5");
                             for (Element pgMod : ugModules) {
                                   String mod = String.valueOf(pgMod);
                                    if (mod.length() > 15 && !mod.contains("Year ") && !mod.contains("Year 2") && !mod.contains("Year 3") && !mod.contains("Final year")) {
                                         System.out.println(pgMod.text());
                                         ModuleData module = new ModuleData();
                                         module.setName(pgMod.text());
                                         courseData.getModules().add(module);
                                    }
                             }
                             universityData.getCourses().add(courseData);
                  }
            }

        } catch (Exception e) {
            System.err.println("Error during the scraing of the university data "  + e.getMessage());
        }
        
        return universityData;
    }
}
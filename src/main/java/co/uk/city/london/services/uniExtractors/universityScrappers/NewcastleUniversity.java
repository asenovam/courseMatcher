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
import java.io.IOException;
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
public class NewcastleUniversity implements UniversityScrapper {

    @Override
    public UniversityData dataScraping(List<String> universityCoursesLink) {
        UniversityData universityData = new UniversityData();
        universityData.setUrls(universityCoursesLink);
        universityData.setName("Newcastle University");
        try {
            Set<String> ugMainPageLinks = new HashSet<>();
            Set<String> pgMainPageLinks = new HashSet<>();

            Document UG = Jsoup.connect(universityCoursesLink.get(0)).get();
            Elements ugLinks = UG.select("a[href]");
            for (Element link : ugLinks) {
                String href = link.absUrl("href");
                if (href.contains("https://www.ncl.ac.uk/undergraduate/degrees/") && href.length() > 45) {
                    if (!href.contains("&searchSubmit")) {
                        //  System.out.println(href);
                        ugMainPageLinks.add(href);
                    }
                }

            }
            Document PG = Jsoup.connect(universityCoursesLink.get(1)).get();
            Elements urls = PG.select("a[href]");
            for (Element url : urls) {
                String hrf = url.absUrl("href");
                if (hrf.contains("https://www.ncl.ac.uk/postgraduate/courses/degrees/")) {
                    if (!hrf.contains("phd")) {
                        //   System.out.println(hrf);
                        pgMainPageLinks.add(hrf);
                    }
                }
            }
            for (String courseLink : ugMainPageLinks) {
                CourseData courseData = new CourseData();
                courseData.setDegree(UniversityDegree.BACHELOR);
                Document course = Jsoup.connect(courseLink).get();
                Elements courseName = course.select("h1");
                System.out.println("TITLE" + " " + courseName.text());
                courseData.setTitle(courseName.text());
                courseData.setUrl(courseLink);
                courseData.setUniversityName("Newcastle University");
                Elements modules = course.select("a[href]");
                for (Element mod : modules) {
                    String ugStr = String.valueOf(mod);
                    if (ugStr.contains("<a href=\"/undergraduate/degrees/module/?code=")) {
                        System.out.println(mod.text());
                        ModuleData module = new ModuleData();
                        module.setName(mod.text());
                        courseData.getModules().add(module);
                    }
                }
                universityData.getCourses().add(courseData);
            }
            for (String pgLinks : pgMainPageLinks) {
                Document pgCourse = Jsoup.connect(pgLinks).get();
                CourseData courseData = new CourseData();
                courseData.setDegree(UniversityDegree.MASTER);
                Elements courseTitle = pgCourse.select("h1");
                System.out.println("TITLE" + " " + courseTitle.text());
                courseData.setTitle(courseTitle.text());
                courseData.setUrl(pgLinks);
                courseData.setUniversityName("Newcastle University");
                Elements pgMods = pgCourse.select("a[href]");
                for (Element modules : pgMods) {
                    String pgStr = String.valueOf(modules);
                    if (pgStr.contains("<a href=\"/postgraduate/modules/")) {

                        System.out.println(modules.text());
                        ModuleData module = new ModuleData();
                        module.setName(modules.text());
                        courseData.getModules().add(module);
                    }
                }
                universityData.getCourses().add(courseData); 
            }

        } catch (IOException ioe) {
            System.err.println("The uni data can not be read at the moment. Maybe site is down " + universityCoursesLink);
        }
        return universityData;

    }
}

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
public class UniversityOfWolverhampton implements UniversityScrapper {

    @Override
    public UniversityData dataScraping(List<String> universityCoursesLink) {
        UniversityData universityData = new UniversityData();
        universityData.setUrls(universityCoursesLink);
        universityData.setName("University Of Wolverhampton");
        Set<String> allMainPageLinks = new HashSet<>();
        try {
            Document doc = Jsoup.connect(universityCoursesLink.get(0)).get();
            Elements hrefs = doc.select("a[href]");
            for (Element element : hrefs) {
                String href = element.absUrl("href");

                if (href.contains("//wlv.ac.uk/courses/")) {
                    //   System.out.println(href);
                    allMainPageLinks.add(href);
                }
            }
            for (String link : allMainPageLinks) {
                Document course = Jsoup.connect(link).get();
                String title = course.select("h1").text();
                System.out.println("TITLE:" + " " + title);
                
               CourseData courseData = null;
                if (title != null && (title.startsWith("BSc") || title.startsWith("BA") || title.startsWith("BEng") 
                        || title.startsWith("MEng")) ) {
                    courseData = new CourseData();
                    courseData.setDegree(UniversityDegree.BACHELOR);
                }
                 if (title != null && (title.startsWith("PG") || title.startsWith("MSc") || title.startsWith("MSci") ) ) {
                    courseData = new CourseData();
                    courseData.setDegree(UniversityDegree.MASTER);
                }
                
                if (courseData != null) {
                        courseData.setUrl(link);
                        courseData.setTitle(title);
                        courseData.setUniversityName(universityData.getName());
                        courseData.setUniversityName("University Of Wolverhampton");
                        Elements mods = course.select("button");
                        for (Element bt : mods) {
                            String buttons = String.valueOf(bt);
                            if (buttons.contains("btn btn-link collapsed")) {
                                if (!buttons.contains("accredits")
                                        && !buttons.contains("Funding")
                                        && !buttons.contains("Contact")
                                        && !buttons.contains("Requirements")
                                        && !buttons.contains(" Finance")
                                        && !buttons.contains(" Wolverhampton")
                                        && !buttons.contains("What skills")
                                        && !buttons.contains("What our students say")) {
                                    System.out.println(bt.text());
                                    ModuleData moduleData = new ModuleData();
                                    System.err.println("module name: " + bt.text());
                                    moduleData.setName(bt.text());
                                    courseData.getModules().add(moduleData);
                                }
                            }
                        }
                        universityData.getCourses().add(courseData);
                }
                
            }
        } catch (IOException ioe) {
            System.err.println("The uni data can not be read at the moment. Maybe site is down " + universityCoursesLink);
        }
        return universityData;

    }
}

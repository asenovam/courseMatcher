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
public class GloucestershireUniversity implements UniversityScrapper {

    public UniversityData dataScraping(final List<String> universityCoursesLink) {

        UniversityData universityData = new UniversityData();
        universityData.setUrls(universityCoursesLink);
        universityData.setName("Gloucestershire University");

        try {
            Document coursesDocument = Jsoup.connect(universityCoursesLink.get(0)).get();
            Set<String> urlOnMainPage = new HashSet<>();
            Elements coursesLinks = coursesDocument.select("a[href]");
            for (Element link : coursesLinks) {
                //   System.out.println(link);
                String currentLink = link.absUrl("href");
                if (currentLink.contains("/courses/undergraduate/")) {
                    urlOnMainPage.add(currentLink);

                }
            }

            Set<String> modulesPage = new HashSet<>();
            for (String mLink : urlOnMainPage) {
                Document toModulesUrl = Jsoup.connect(mLink).get();
                Elements moduleLinks = toModulesUrl.select("a[href]");
                String ready = "";
                for (Element e : moduleLinks) {
                    String filter = String.valueOf(e);
                    if (filter.contains("/maps/pages/") && !filter.contains("course content") && !filter.contains("course-maps")) {
                        for (int i = 0; i < filter.length(); i++) {
                            ready = filter.substring(9, filter.length());
                            String u = "https://www.glos.ac.uk" + ready;
                            String n = "";
                            int index = 0;
                            for (int j = 0; j < u.length() - 4; j++) {
                                if (u.substring(j, j + 5).equals("class")) {
                                    index = j - 2;
                                }
                                n = u.substring(0, index);
                            }
                            modulesPage.add(n);
                        }

                    }

                }

            }

            for (String moduleUrl : modulesPage) {
                CourseData courseData = new CourseData();
                courseData.setDegree(UniversityDegree.BACHELOR);

                Document modulePage = Jsoup.connect(moduleUrl).get();
                Elements title = modulePage.getElementsByTag("h1");

                System.out.println("TITLE" + " " + title.text());
                courseData.setUrl(moduleUrl);
                courseData.setTitle(title.text());
                courseData.setUniversityName("Glouchestershire University");

                Elements modules = modulePage.select("a[href]");
                for (Element mods : modules) {
                    String relLinks = String.valueOf(mods);
                    if (relLinks.contains("/courses/descriptors/pages")) {
                        String trim = String.valueOf(mods.text());
                        String readyMod = trim.substring(7, trim.length());
                        System.out.println(readyMod);

                        ModuleData moduleData = new ModuleData();
                        System.out.println("module name: " + readyMod);
                        moduleData.setName(readyMod);
                        courseData.getModules().add(moduleData);
                    }
                }

                universityData.getCourses().add(courseData);

            }

        } catch (Exception e) {
            System.err.println("Error during the scraing of the university data " + e.getMessage());
        }

        return universityData;

    }
}

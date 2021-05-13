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
public class UniversityOfBirmingham implements UniversityScrapper {

    @Override
    public UniversityData dataScraping(List<String> universityCoursesLink) {
        UniversityData universityData = new UniversityData();
        universityData.setUrls(universityCoursesLink);
        universityData.setName("University Of Birmingham");
        try {
            Set<String> coursesLinks = new HashSet<>();
            Set<String> htmlLink = new HashSet<>();

            Document doc = Jsoup.connect(universityCoursesLink.get(0)).get();
            Elements links = doc.select("a[href]");
            for (Element url : links) {
                String urls = url.absUrl("href");
                if (urls.contains("courses") && urls.contains("-2021-22")) {
                    coursesLinks.add(urls);

                }
            }

            for (String ls : coursesLinks) {
                Document course = Jsoup.connect(ls).get();
                Elements mod = course.select("a[href]");
                for (Element s : mod) {
                    String url = s.absUrl("href");
                    if (url.contains("#tab-Course_in_Depth")) {
                        htmlLink.add(url);
                    }
                }
                for (String courseUrl : htmlLink) {
                    Document document = Jsoup.connect(courseUrl).get();
                    Elements courseTitle = document.getElementsByTag("h1");
                    String title = courseTitle.text();

                    System.out.println("TITLE" + " " + title);
                    CourseData courseData = null;
                    if (title != null && (title.contains("BSc") || title.contains("BA"))) {
                        courseData = new CourseData();
                        courseData.setDegree(UniversityDegree.BACHELOR);
                    }
                    if (title != null && (title.contains("MSc") || title.contains("PgDip")
                            || title.contains("MArch"))) {
                        courseData = new CourseData();
                        courseData.setDegree(UniversityDegree.MASTER);
                    }

                    if (courseData != null) {
                        System.out.println("TITLE" + " " + courseTitle.text());
                        courseData.setTitle(courseTitle.text());
                        courseData.setUrl(courseUrl);
                        courseData.setUniversityName("University Of Birmingham");
                        Elements els = document.getElementsByTag("button");
                        for (Element mods : els) {
                            String element = String.valueOf(mods);
                            if (element.contains("button aria-expanded")) {
                                System.out.println(mods.text());
                                ModuleData module = new ModuleData();
                                module.setName(mods.text());
                                courseData.getModules().add(module);
                            }
                            universityData.getCourses().add(courseData);
                        }
                    }

                }

            }
        } catch (Exception e) {
            System.err.println("Error during the scraing of the university data " + e.getMessage());
        }
        return universityData;
    }

}

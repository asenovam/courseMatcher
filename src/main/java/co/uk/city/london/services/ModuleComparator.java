/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.services;

import co.uk.city.london.beans.CourseBean;
import co.uk.city.london.beans.ModuleBean;
import co.uk.city.london.converters.CourseEntityToBeanConverter;
import com.mchange.v2.coalesce.CoalesceChecker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 *
 * @author mar_9
 */
@Component
public class ModuleComparator {

    private static final int DISGARD_SIZE = 15;

    private static final int MAX_CHAR = 26;

    static enum Color {

        ORANGE("#FFA500"),
        RED("#DC143C"),
        YELLOW("#e4f777"),
        GREEN("#00FF7F"),
        PURPLE("#800080"),
        BRAWN("#F4A460"),
        SADDLEBROWN("#8B4513"),
        OLIVE("#808000"),
        VALE("#DB7093"),
        STEELBLUE("#4682B4"),
        SEAGREEN("#2E8B57"),
        ROSY_BROWN("#BC8F8F"),
        SALMON("#FA8072"),
        LIGHT_BLUW("#ADD8E6"),
        LIGHT_GREEN("#99ff66"),
        LIGHT_YELLOW("#ffff66"),
        LIGHT_OLIVE("#e6e600"),
        GREEN_BLUE("#99ffcc"),
        LIG_BLUE("#00ccff"),
        LIGHT_ORANGE("#ffaa00"),
        WHITE("#ffffff"),
        YELLOW_SHADOW("#ffff00"),
        PINK_SHADOW("#ffb3ff"),
        BROWN_SHADOW("#ff4000"),
        BLUE_SHADOW("#3399ff"),
        GREEN_SHADOW("#33ffcc"),
        WHITE_SHADOW("#ffffe6");

        private String code;

        Color(String code) {
            this.code = code;
        }

    }

    private static List<Color> colors = new ArrayList<>();

    static {
        colors.add(Color.ORANGE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.PURPLE);
        colors.add(Color.BRAWN);
        colors.add(Color.SADDLEBROWN);
        colors.add(Color.OLIVE);
        colors.add(Color.VALE);
        colors.add(Color.SEAGREEN);
        colors.add(Color.ROSY_BROWN);
        colors.add(Color.SALMON);
        colors.add(Color.LIGHT_BLUW);
        colors.add(Color.WHITE);
        colors.add(Color.LIGHT_ORANGE);
        colors.add(Color.LIG_BLUE);
        colors.add(Color.GREEN_BLUE);
        colors.add(Color.LIGHT_OLIVE);
        colors.add(Color.LIGHT_YELLOW);
        colors.add(Color.LIGHT_GREEN);
        colors.add(Color.WHITE_SHADOW);
        colors.add(Color.BLUE_SHADOW);
        colors.add(Color.BROWN_SHADOW);
        colors.add(Color.GREEN_SHADOW);
        colors.add(Color.PINK_SHADOW);
        colors.add(Color.YELLOW_SHADOW);

    }

    public void compareModules(CourseBean course1, CourseBean course2) {

        final IntegerWrraper colorCounter = new IntegerWrraper();
        colorCounter.counter = 0;
        List<ModuleBean> modules1 = course1.getModules();
        List<ModuleBean> modules2 = course2.getModules();

        if (modules2 != null && modules1 != null && modules2.size() > 0 && modules1.size() > 0) {
            modules1.stream().forEach(module1 -> {
                System.out.println("number of matched modules " + modules2.stream().filter(module2 ->
                        compareTwoModules(module1, module2)).count());
                System.out.println("matched modules for " + module1.getName());
                modules2.stream().filter(module2 -> compareTwoModules(module1, module2)).collect
        (Collectors.toSet()).forEach(m -> System.out.println(m.getName()));
                System.out.println("\n");

                ModuleBean matchedModule = modules2.stream().filter(module2 -> compareTwoModules
        (module1, module2))
                        .findAny().orElse(null);
                if (matchedModule != null) {
                    matchedModule.setColor(colors.get(colorCounter.counter).code);
                    module1.setColor(colors.get(colorCounter.counter).code);
                    colorCounter.increment();
                    if (colorCounter.counter == colors.size()) {
                        colorCounter.reset();
                    }
                }
            });

            sortModules(modules1, modules2);
        }
    }

    private boolean compareTwoModules(ModuleBean module1, ModuleBean module2) {
        return module2.getName() != null && module2.getName().equals(module1.getName());
    }

    private void sortModules(List<ModuleBean> modules1, List<ModuleBean> modules2) {
        Collections.sort(modules1, new ModuleColorComparator());
        Collections.sort(modules2, new ModuleColorComparator());
    }

    static class IntegerWrraper {

        int counter;

        public void increment() {
            counter++;
        }

        public void reset() {
            counter = 0;
        }
    }
}

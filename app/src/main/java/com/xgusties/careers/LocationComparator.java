package com.xgusties.careers;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by craiga01 on 07/11/2016.
 */

public class LocationComparator implements Comparator<Job> {

    //TODO - this could be loaded from a config file on init
    static ArrayList<String> jobLocations = new ArrayList<>();
    static {
        jobLocations.add("London");
        jobLocations.add("MediaCity UK, Salford Quays");
        jobLocations.add("Birmingham");
        jobLocations.add("English Regions");
        jobLocations.add("Scotland");
        jobLocations.add("Wales");
        jobLocations.add("Northern Ireland");
        jobLocations.add("International");
        jobLocations.add("Other");
    };

    @Override
    public int compare(Job a, Job b) {
        try {
            String a2 = LocationSectionCalculator.groupingOf(a.location);
            String b2 = LocationSectionCalculator.groupingOf(b.location);

            return Integer.compare(jobLocations.indexOf(a2), jobLocations.indexOf(b2));

        } catch (NullPointerException e) {
            return 0;
        }
    }
}
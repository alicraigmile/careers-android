package com.xgusties.careers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */

class LexicographicComparator implements Comparator<Job> {
    @Override
    public int compare(Job a, Job b) {
        return a.title.compareToIgnoreCase(b.title);
    }
}

class DivisionComparator implements Comparator<Job> {
    @Override
    public int compare(Job a, Job b) {
        try {
            return a.division.compareToIgnoreCase(b.division);
        } catch (NullPointerException e) {
            return 0;
        }
    }
}


class GradeComparator implements Comparator<Job> {
    @Override
    public int compare(Job a, Job b) {
        try {
            return a.grade.compareToIgnoreCase(b.grade);
        } catch (NullPointerException e) {
            return 0;
        }
    }
}

class LocationComparator implements Comparator<Job> {

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


public class Jobs {

    /**
     * An array of sample (dummy) items.
     */
    public static List<Job> JOBS = new ArrayList<Job>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<Integer, Job> JOB_MAP = new HashMap<Integer, Job>();

    private static void addItem(Job job) {
        JOBS.add(job);
        JOB_MAP.put(Integer.valueOf(job.id), job);
    }

    public static void Store(ArrayList<Job> jobs) {
        JOBS.clear();
        JOB_MAP.clear();

        Iterator itr = jobs.iterator();
        while (itr.hasNext()) {
            Job job = (Job) itr.next();
            addItem(job);
        }
    }

    public static List<Job> AllJobs() {
        return JOBS;
    }

    public static ArrayList<Job> JobsByTitle() {
        ArrayList<Job> jobs = (ArrayList<Job>) JOBS;
        Collections.sort(jobs, new LexicographicComparator());
        return jobs;
    }

    public static ArrayList<Job> JobsByDivision() {
        ArrayList<Job> jobs = (ArrayList<Job>) JOBS;
        Collections.sort(jobs, new DivisionComparator());
        return jobs;
    }

}

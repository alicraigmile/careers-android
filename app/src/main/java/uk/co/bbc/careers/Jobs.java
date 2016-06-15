package uk.co.bbc.careers;

import java.net.MalformedURLException;
import java.net.URL;
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

public class Jobs {

    /**
     * An array of sample (dummy) items.
     */
    public static List<Job> JOBS = new ArrayList<Job>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<Integer, Job> JOB_MAP = new HashMap<Integer, Job>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyJob(i));
        }
    }

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

    private static Job createDummyJob(int position) {
                Job job = new Job(position, "Job " + position, makeURL(position));
        job.division = "Position " + String.valueOf(Math.round(position % 2));
        return job;
    }

    private static URL makeURL(int position) {
        try {
            return new URL("http://_x_fake_domain.com/job/" + String.valueOf(position));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

}

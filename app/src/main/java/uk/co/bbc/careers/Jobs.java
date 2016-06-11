package uk.co.bbc.careers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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

    private static void clearJobs() {
        JOBS = new ArrayList<Job>();
        JOB_MAP = new HashMap<Integer, Job>();
    }

    private static void addItem(Job job) {
        JOBS.add(job);
        JOB_MAP.put(Integer.valueOf(job.id), job);
    }

    public static void store(ArrayList<Job> jobs) {
        clearJobs();

        Iterator itr = jobs.iterator();
        while (itr.hasNext()) {
            Job job = (Job) itr.next();
            addItem(job);
        }
    }

    private static Job createDummyJob(int position) {
        return new Job(position, "Job " + position, makeURL(position));
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

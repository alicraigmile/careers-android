package com.xgusties.careers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Jobs {

    public static List<Job> JOBS = new ArrayList<Job>();
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

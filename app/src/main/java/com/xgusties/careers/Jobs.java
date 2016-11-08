package com.xgusties.careers;

import android.text.format.Time;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Jobs {

    private static List<Job> JOBS = new ArrayList<Job>();
    private static Map<Integer, Job> JOB_MAP = new HashMap<Integer, Job>();
    public static Date generatedTimestamp;
    public static Date lastUpdatedTimestamp;

    public static void Store(ArrayList<Job> jobs, Date ts) {
        JOBS.clear();
        JOB_MAP.clear();

        generatedTimestamp = ts;

        Calendar c = Calendar.getInstance();
        Date now = c.getTime();
        lastUpdatedTimestamp = now;

        Iterator itr = jobs.iterator();
        while (itr.hasNext()) {
            Job job = (Job) itr.next();
            AddItem(job);
        }
    }

    public static Job get(int index) {
        return JOB_MAP.get(index);
    }

    private static void AddItem(Job job) {
        JOBS.add(job);
        JOB_MAP.put(Integer.valueOf(job.id), job);
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

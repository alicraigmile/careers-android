package com.xgusties.careers;

import java.util.Comparator;

/**
 * Created by craiga01 on 07/11/2016.
 */
public class LexicographicComparator implements Comparator<Job> {
    @Override
    public int compare(Job a, Job b) {
        return a.title.compareToIgnoreCase(b.title);
    }
}
package com.xgusties.careers;

import java.util.Comparator;

/**
 * Created by craiga01 on 07/11/2016.
 */

public class DivisionComparator implements Comparator<Job> {
    @Override
    public int compare(Job a, Job b) {
        try {
            return a.division.compareToIgnoreCase(b.division);
        } catch (NullPointerException e) {
            return 0;
        }
    }
}
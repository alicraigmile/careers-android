package com.xgusties.careers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by craiga01 on 14/06/2016.
 */
public class GradeSectionCalculator implements SectionCalculator {

    @Override
    public String type() {
        return "grade";
    }

    @Override
    public List<SimpleSectionedRecyclerViewAdapter.Section> calculateSections(Iterator<Job> jobIterator) {

        List<SimpleSectionedRecyclerViewAdapter.Section> sections = new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();

        Job lastJob = null;
        int position = 0;

        while (jobIterator.hasNext()) {
            Job thisJob = jobIterator.next();

            if (lastJob == null) { // first item on list is a new section
                sections.add(new SimpleSectionedRecyclerViewAdapter.Section(position, groupingOf(thisJob.grade)));
            } else if (! groupingOf(thisJob.grade).toString().equals(groupingOf(lastJob.grade).toString())) {
                sections.add(new SimpleSectionedRecyclerViewAdapter.Section(position, groupingOf(thisJob.grade)));
            }
            lastJob = thisJob;
            position++;

        }


        return sections;
    }

    private String groupingOf(String grade) {
        if (grade == null) {
            return "Unknown";
        }

        //Log.d("GRADE: ", "'" + grade + "'");
        // TODO - this is not working
        if (grade == "-") {
            return "Unknown";
        }

        return "Grade " + grade;
    }
}

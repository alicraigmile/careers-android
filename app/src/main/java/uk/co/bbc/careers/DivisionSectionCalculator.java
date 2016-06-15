package uk.co.bbc.careers;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by craiga01 on 14/06/2016.
 */
public class DivisionSectionCalculator implements SectionCalculator {

    @Override
    public String type() {
        return "division";
    }

    @Override
    public List<SimpleSectionedRecyclerViewAdapter.Section> calculateSections(Iterator<Job> jobIterator) {

        List<SimpleSectionedRecyclerViewAdapter.Section> sections = new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();

        Job lastJob = null;
        int position = 0;

        String TAG = "DivisionSectionCalculator";
        while (jobIterator.hasNext()) {
            Job thisJob = jobIterator.next();

            if (lastJob == null) { // first item on list is a new section
                sections.add(new SimpleSectionedRecyclerViewAdapter.Section(position, thisJob.division));
            } else if (! thisJob.division.toString().equals(lastJob.division.toString())) {
                sections.add(new SimpleSectionedRecyclerViewAdapter.Section(position, thisJob.division));
            }
            lastJob = thisJob;
            position++;

        }

        return sections;
    }
}

package uk.co.bbc.careers;

import android.support.v7.widget.RecyclerView;

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
                sections.add(new SimpleSectionedRecyclerViewAdapter.Section(position, thisJob.grade));
            } else if (!thisJob.grade.toString().equals(lastJob.grade.toString())) {
                sections.add(new SimpleSectionedRecyclerViewAdapter.Section(position, thisJob.grade));
            }
            lastJob = thisJob;
            position++;

        }

        return sections;
    }
}

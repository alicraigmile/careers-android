package uk.co.bbc.careers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by craiga01 on 14/06/2016.
 */
public class AtozSectionCalculator implements SectionCalculator {

    @Override
    public String type() {
        return "atoz";
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
                sections.add(new SimpleSectionedRecyclerViewAdapter.Section(position, groupingOf(thisJob.title)));
            } else if (! groupingOf(thisJob.title).toString().equals(groupingOf(lastJob.title).toString())) {
                sections.add(new SimpleSectionedRecyclerViewAdapter.Section(position, groupingOf(thisJob.title)));
            }
            lastJob = thisJob;
            position++;

        }

        return sections;
    }

    private String groupingOf(String title) {
        Character firstChar = title.charAt(0);
        if (Character.isDigit(firstChar)) {
            return "0-9";
        }

        return String.valueOf(firstChar);
    }
}

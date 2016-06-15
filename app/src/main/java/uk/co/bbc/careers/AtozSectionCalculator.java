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

        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0,"A"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(5,"B"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(12,"C"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(14,"D"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(15,"E"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(22,"F"));

        return sections;
    }
}

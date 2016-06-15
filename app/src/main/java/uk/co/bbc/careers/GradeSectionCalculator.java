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

        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0,"7"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(5,"8"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(12,"9"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(14,"10"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(15,"11"));

        return sections;
    }
}

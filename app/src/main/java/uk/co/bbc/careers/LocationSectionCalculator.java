package uk.co.bbc.careers;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by craiga01 on 14/06/2016.
 */
public class LocationSectionCalculator implements SectionCalculator {

    @Override
    public String type() {
        return "location";
    }

    @Override
    public List<SimpleSectionedRecyclerViewAdapter.Section> calculateSections(Iterator<Job> jobIterator) {
        
        List<SimpleSectionedRecyclerViewAdapter.Section> sections = new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();

        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0,"London"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(5,"MediaCityUK"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(12,"Scotland"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(14,"Wales"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(15,"Northern Ireland"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(20,"English Regions"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(22,"International"));

        return sections;
    }
}

package uk.co.bbc.careers;

import android.support.v7.widget.RecyclerView;

import java.util.Iterator;
import java.util.List;

/**
 * Created by craiga01 on 14/06/2016.
 */
public interface SectionCalculator {

    public String type();

    List<SimpleSectionedRecyclerViewAdapter.Section> calculateSections(Iterator<Job> jobIterator);
}

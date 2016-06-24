package uk.co.bbc.careers;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

        Job lastJob = null;
        int position = 0;

        while (jobIterator.hasNext()) {
            Job thisJob = jobIterator.next();

            if (lastJob == null) { // first item on list is a new section
                sections.add(new SimpleSectionedRecyclerViewAdapter.Section(position, groupingOf(thisJob.location)));
            } else if (! groupingOf(thisJob.location).toString().equals(groupingOf(lastJob.location).toString())) {
                sections.add(new SimpleSectionedRecyclerViewAdapter.Section(position, groupingOf(thisJob.location)));
            }
            lastJob = thisJob;
            position++;

        }


        return sections;
    }

    public static String groupingOf(String location) {

        try {

            if (!location.contains("United Kingdom")) {
                return "International";
            }

            Map<String, String> map = new HashMap<String, String>();
            map.put("Brussels", "International");
            map.put("London", "London");
            map.put("Brock House", "London");
            map.put("Elstree", "London");
            map.put("Manchester", "MediaCity UK, Salford Quays");
            map.put("Salford", "MediaCity UK, Salford Quays");
            map.put("MediaCity", "MediaCity UK, Salford Quays");
            map.put("Scotland", "Scotland");
            map.put("Glasgow", "Scotland");
            map.put("Edinburgh", "Scotland");
            map.put("Edinburgh", "Scotland");
            map.put("Pacific Quay", "Scotland");
            map.put("Stornoway", "Scotland");
            map.put("Cardiff", "Wales");
            map.put("Wales", "Wales");
            map.put("Northern Ireland", "Northern Ireland");
            map.put("Belfast", "Northern Ireland");
            map.put("Birmingham", "Birmingham");
            map.put("Norwich", "English Regions");
            map.put("Caversham", "English Regions");
            map.put("Carlisle", "English Regions");
            map.put("Gloucester", "English Regions");
            map.put("Bristol", "English Regions");
            map.put("Lancashire", "English Regions");
            map.put("Nottingham", "English Regions");
            map.put("Cornwall", "English Regions");
            map.put("Blackburn", "English Regions");
            map.put("Truro", "English Regions");
            map.put("Leicester", "English Regions");
            map.put("Sheffield", "English Regions");
            map.put("Tunbridge Wells", "English Regions");
            map.put("Derby", "English Regions");
            map.put("Leeds", "English Regions");
            map.put("Cambridge", "English Regions");
            map.put("Northampton", "English Regions");
            map.put("Norfolk", "English Regions");
            map.put("York", "English Regions");
            map.put("Dumbarton", "Scotland");
            map.put("Newcastle", "English Regions");
            map.put("Southampton", "English Regions");
            map.put("Swansea", "Wales");
            map.put("Wrexham", "Wales");
            map.put("Lincolnshire", "English Regions");
            map.put("Jersey", "English Regions");

            for (Map.Entry<String, String> e : map.entrySet()) {
                if (location.toLowerCase().contains(e.getKey().toLowerCase())) {
                    return e.getValue();
                }
            }
        } catch (NullPointerException e) {
            return "Unknown";
        }

        return "Other";
    }

}

package uk.co.bbc.careers;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by craiga01 on 14/06/2016.
 */
public class JobListRecyclerViewAdapter extends SimpleSectionedRecyclerViewAdapter {

    private static final String TAG = "JobListRVA";
    private Context mContext;
    private SectionCalculator mSectionCalculator;
    private RecyclerView.Adapter mBaseAdapter;
    private List<Job> mJobs;

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    public JobListRecyclerViewAdapter(Context context, int sectionResourceId, int textResourceId, RecyclerView.Adapter baseAdapter, List<Job> jobs, SectionCalculator sectionCalculator) {
        super(context, sectionResourceId, textResourceId, baseAdapter);

        mSectionCalculator = sectionCalculator;
        mContext = context;
        mBaseAdapter = baseAdapter;
        mJobs = jobs;
        Log.d(TAG, "Group by : " + mSectionCalculator.type());

        Log.d(TAG, "adapter is a:" +
                mBaseAdapter.getClass());

        //update sections when data changes
        mBaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                Log.d(TAG, "data has changed");
                updateSections();
            }
        });

        //update Sections on initialisation too
        Log.d(TAG, "initialisation");
        updateSections();
    }

    private void updateSections() {

        Log.d(TAG, "updateSections");
        String mGroupBy = mSectionCalculator.type();

        //This is the code to provide a sectioned list
        List<Section> sections =
                new ArrayList<Section>();

        sections = mSectionCalculator.calculateSections(mJobs.iterator());

        SimpleSectionedRecyclerViewAdapter.Section[] dummy =
                new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];

        setSections(sections.toArray(dummy));

    }

}

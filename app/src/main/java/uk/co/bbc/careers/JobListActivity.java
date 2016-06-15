package uk.co.bbc.careers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * An activity representing a list of Jobs. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link JobDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class JobListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    public SimpleItemRecyclerViewAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SimpleSectionedRecyclerViewAdapter mSectionedAdapter;

//    private static Comparator<Job> mJobsComparator = new DivisionComparator();
  //  private static SectionCalculator mJobsSectionCalculator = new DivisionSectionCalculator();

    private static Comparator<Job> mJobsComparator = new LexicographicComparator();
  private static SectionCalculator mJobsSectionCalculator = new AtozSectionCalculator();

    //sectionCalculator = new AtozSectionCalculator();
    //sectionCalculator = new DivisionSectionCalculator();
   // sectionCalculator = new GradeSectionCalculator();
    //sectionCalculator = new LocationSectionCalculator();


    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<Job> jobs = (ArrayList<Job>) Jobs.AllJobs();
        Collections.sort(jobs, mJobsComparator);

        // populate with jobs data (assuming its been retrieved already)
        mAdapter.swap((ArrayList<Job>) jobs);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        View recyclerView = findViewById(R.id.job_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        // Swipe Refresh Layout
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swifeRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doAsyncRefresh();
            }
        });

        // Load the jobs on startup
        doAsyncRefresh();

        if (findViewById(R.id.job_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }



    public void doAsyncRefresh() {


        String jobsUrl = getResources().getString(R.string.jobs_url);

        JobsRequest jobsRequest = new JobsRequest(

                jobsUrl,
                new Response.Listener<ArrayList<Job>>() {
                    @Override
                    public void onResponse(ArrayList<Job> jobsResponse) {

                        //update the jobs database
                        Jobs.Store(jobsResponse);
                        //update the view

                        ArrayList<Job> jobs = (ArrayList<Job>) Jobs.AllJobs();
                        Collections.sort(jobs, mJobsComparator);

                        mAdapter.swap(jobs);

                        // Stop refresh animation
                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Stop refresh animation
                mSwipeRefreshLayout.setRefreshing(false);
                // Tell us why it failed
                Toast.makeText(JobListActivity.this, "Fetch jobs failed"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        // Add the request to the RequestQueue.
        NetworkManager.getInstance(getApplicationContext()).addToRequestQueue(jobsRequest);
    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        mAdapter = new SimpleItemRecyclerViewAdapter(new ArrayList<Job>()); //initial setup = no items in list

        mSectionedAdapter = new
                JobListRecyclerViewAdapter(this,R.layout.section,R.id.section_text, mAdapter, mAdapter.getItems(), mJobsSectionCalculator);

        recyclerView.setAdapter(mSectionedAdapter);


    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Job> mValues;

        public SimpleItemRecyclerViewAdapter(List<Job> jobs) {
            mValues = jobs;
        }

        public void swap(ArrayList<Job> jobs){
            mValues.clear();
            mValues.addAll(jobs);
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.job_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            int a = 0;
            holder.mIdView.setText(String.valueOf(mValues.get(position).id));
            holder.mTitleView.setText(mValues.get(position).title);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putInt(JobDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        JobDetailFragment fragment = new JobDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.job_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, JobDetailActivity.class);
                        intent.putExtra(JobDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public List<Job> getItems() {
            return mValues;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mTitleView;
            public Job mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mTitleView = (TextView) view.findViewById(R.id.title);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTitleView.getText() + "'";
            }
        }
    }
}

package com.xgusties.careers;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.xgusties.careers.LexicographicComparator;
import com.xgusties.careers.LocationComparator;
import com.xgusties.careers.NotConnectedToNetworkException;

/**
 * An activity representing a list of Jobs. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link JobDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class JobListActivity extends AppCompatActivity {

    private static final String TAG = "JobListActivity";
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    public SimpleItemRecyclerViewAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SimpleSectionedRecyclerViewAdapter mSectionedAdapter;
    private String mSearchQuery;

//   private static Comparator<Job> mJobsComparator = new DivisionComparator();
//   private static SectionCalculator mJobsSectionCalculator = new DivisionSectionCalculator();

//    private static Comparator<Job> mJobsComparator = new LexicographicComparator();
//    private static SectionCalculator mJobsSectionCalculator = new AtozSectionCalculator();

    private static Comparator<Job> mJobsComparator = new LocationComparator();
    private static SectionCalculator mJobsSectionCalculator = new LocationSectionCalculator();

//    private static Comparator<Job> mJobsComparator = new GradeComparator();
//    private static SectionCalculator mJobsSectionCalculator = new GradeSectionCalculator();

    //sectionCalculator = new AtozSectionCalculator();
    //sectionCalculator = new DivisionSectionCalculator();
   // sectionCalculator = new GradeSectionCalculator();
    //sectionCalculator = new LocationSectionCalculator();


    @Override
    protected void onResume() {
        super.onResume();

        populateListWithJobs();
        checkForCrashes();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterManagers();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterManagers();
    }

    private void populateListWithJobs() {

        List<Job> jobs = Jobs.AllJobs();
        List<Job> filteredJobs = new ArrayList<>();

        if (mSearchQuery != null) {

            for (Job job: jobs) {
                if (job.title.toLowerCase().contains(mSearchQuery.toLowerCase())) {
                    filteredJobs.add(job);
                } else if (job.division.toLowerCase().contains(mSearchQuery.toLowerCase())) {
                    filteredJobs.add(job);
                } else if (job.location.toLowerCase().contains(mSearchQuery.toLowerCase())) {
                    filteredJobs.add(job);
                }
            }

            //TODO - cancel searh + show all results
            //TODO - show search term in dialog
            Integer number = filteredJobs.toArray().length;
            if (number > 0) {
                Toast.makeText(this, "Found " + number + " results for " + mSearchQuery, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();

            }

        } else {
            filteredJobs = jobs;

            Integer number = filteredJobs.toArray().length;
            if (number > 0) {
                Toast.makeText(this, "Showing all " + number + " jobs", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No vacancies!", Toast.LENGTH_SHORT).show();

            }

        }
        Collections.sort(filteredJobs, new LexicographicComparator());
        Collections.sort(filteredJobs, mJobsComparator);

        mAdapter.swap((ArrayList<Job>) filteredJobs);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);

        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String name = pInfo.versionName;
            int code = pInfo.versionCode;
            Log.d(TAG, "App Version: " + name + " (" + code + ")");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d(TAG, "Search query: " + query);
            mSearchQuery = query;
        }

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
        checkForUpdates();
    }

    void showVersionInformation() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String text = String.format(getResources().getString(R.string.version_toast),
                    pInfo.versionName,
                    pInfo.versionCode);

            Toast.makeText(JobListActivity.this, text, Toast.LENGTH_SHORT).show();
        } catch (PackageManager.NameNotFoundException e) {
            // do nothing
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_version:
                showVersionInformation();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    public void doAsyncRefresh() {


        String jobsUrl = getResources().getString(R.string.jobs_url);

        JobsRequest jobsRequest = new JobsRequest(

                jobsUrl,
                new Response.Listener<JobsData>() {
                    @Override
                    public void onResponse(JobsData jobsResponse) {

                        //update the jobs database
                        Jobs.Store(jobsResponse.jobs);
                        //update the view
                        populateListWithJobs();

                        // Stop refresh animation
                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Stop refresh animation
                mSwipeRefreshLayout.setRefreshing(false);
                // Tell us why it failed
                Log.d(TAG, "Fetch jobs failed: " + error.toString());
                Toast.makeText(JobListActivity.this, R.string.fetch_jobs_failed, Toast.LENGTH_SHORT).show();
            }
        });


        // Add the request to the RequestQueue.
        try {
            NetworkManager.getInstance(getApplicationContext()).addToRequestQueue(jobsRequest);
        } catch (NotConnectedToNetworkException e) {
            // Stop refresh animation
            mSwipeRefreshLayout.setRefreshing(false);
            // Tell us why it failed
            Toast.makeText(JobListActivity.this, R.string.not_connected, Toast.LENGTH_SHORT).show();
        }
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
            //holder.mIdView.setText(String.valueOf(mValues.get(position).id));
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
            //public final TextView mIdView;
            public final TextView mTitleView;
            public Job mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                //mIdView = (TextView) view.findViewById(R.id.id);
                mTitleView = (TextView) view.findViewById(R.id.title);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTitleView.getText() + "'";
            }
        }
    }


    private void checkForCrashes() {
        CrashManager.register(this);
    }

    private void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(this);
    }

    private void unregisterManagers() {
        UpdateManager.unregister();
    }

}

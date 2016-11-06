package com.xgusties.careers;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.xgusties.careers.Job;
import com.xgusties.careers.JobsData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

public class JobsRequest extends Request<JobsData> {

    private static final String TAG = "Job";
    private Response.Listener<JobsData> mListener;
    private String mUrl;

    public JobsRequest(String jobsUrl, Response.Listener<JobsData> listener, Response.ErrorListener errorListener){
        super(Method.GET, jobsUrl, errorListener);
        mListener = listener;
    }

    @Override
    protected Response<JobsData> parseNetworkResponse(NetworkResponse response) {

        int statusCode = response.statusCode;
        Log.d(TAG, "networkresponse statuscode:"+ statusCode);
        JobsData jobsData = new JobsData();

        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            JSONObject jobsRawData = new JSONObject(jsonString);

            String timestamp = jobsRawData.getString("timestamp");
            jobsData.timestamp = timestamp;

            int version = jobsRawData.getInt("version");
            jobsData.version = version;

            JSONArray results = jobsRawData.getJSONArray("jobs");
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);

                String location = result.getString("location");
                String division = result.getString("division");
                String grade = result.getString("grade");
                String term = result.getString("term");
                String url = result.getString("url");
                URL jobURL = new URL(url);
                String type = result.getString("type");
                int jobId = result.getInt("id");
                String title = result.getString("title");

                Job job = new Job();
                job.location = location;
                job.division = division;
                job.grade = grade;
                job.term = term;
                job.url = jobURL;
                job.type = type;
                job.id = jobId;
                job.title = title;

                jobsData.jobs.add(job);
            }
            Log.d(TAG, "data timestamp: "+ timestamp);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }

        return Response.success(jobsData, HttpHeaderParser.parseCacheHeaders(response));


    }

    @Override
    protected void deliverResponse(JobsData response) {
        mListener.onResponse(response);
    }


}

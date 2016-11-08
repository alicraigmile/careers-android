package com.xgusties.careers;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class JobsRequest extends Request<JobsResponse> {

    private static final String TAG = "Job";
    private Response.Listener<JobsResponse> mListener;
    private String mUrl;

    public JobsRequest(String jobsUrl, Response.Listener<JobsResponse> listener, Response.ErrorListener errorListener){
        super(Method.GET, jobsUrl, errorListener);
        mListener = listener;
    }

    private Date parseTimestamp(String timestamp) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date convertedDate = new Date();
        convertedDate = dateFormat.parse(timestamp);
        return convertedDate;
    }



    @Override
    protected Response<JobsResponse> parseNetworkResponse(NetworkResponse response) {

        int statusCode = response.statusCode;
        Log.d(TAG, "networkresponse statuscode:"+ statusCode);
        JobsResponse jobsResponse = new JobsResponse();

        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            JSONObject jobsRawData = new JSONObject(jsonString);

            String timestamp = jobsRawData.getString("timestamp");
            try {
                jobsResponse.timestamp = parseTimestamp(timestamp);
            } catch (ParseException e) {
                Log.d(TAG, e.toString());
                //e.printStackTrace();
            }

            int version = jobsRawData.getInt("version");
            jobsResponse.version = version;

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

                jobsResponse.jobs.add(job);
            }
        } catch (JSONException e) {
            return Response.error(new ParseError(e));
        } catch (MalformedURLException e) {
            return Response.error(new ParseError(e));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }

        return Response.success(jobsResponse, HttpHeaderParser.parseCacheHeaders(response));


    }

    @Override
    protected void deliverResponse(JobsResponse response) {
        mListener.onResponse(response);
    }


}

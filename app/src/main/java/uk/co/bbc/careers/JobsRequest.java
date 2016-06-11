package uk.co.bbc.careers;

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
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.android.volley.toolbox.HttpHeaderParser.parseCharset;

public class JobsRequest extends Request<ArrayList<Job>> {

    private static final String TAG = "Job";
    // private static final String URL = "http://scot-dev0.national.core.bbc.co.uk/bbcjobs/jobs.json";
    private static final String URL = "http://192.168.1.82:9000/jobs.json";

    private Response.Listener<ArrayList<Job>> mListener;
    private String mUrl;

    public JobsRequest(Response.Listener<ArrayList<Job>> listener, Response.ErrorListener errorListener){
        super(Method.GET, URL, errorListener);
        mListener = listener;
    }

    @Override
    protected Response<ArrayList<Job>> parseNetworkResponse(NetworkResponse response) {

        int statusCode = response.statusCode;
        Log.d(TAG, "networkresponse statuscode:"+ statusCode);
        ArrayList<Job> cleanResults = new ArrayList<Job>();

        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            JSONArray results = new JSONArray(jsonString);

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

                cleanResults.add(job);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
        return Response.success(cleanResults, HttpHeaderParser.parseCacheHeaders(response));


    }

    @Override
    protected void deliverResponse(ArrayList<Job> response) {
        mListener.onResponse(response);
    }


}

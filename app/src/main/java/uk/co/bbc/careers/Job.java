package uk.co.bbc.careers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class Job {

    public String location;
    public String division;
    public String grade;
    public String term;
    public URL url;
    public String type;
    public int id;
    public String title;

    public Job() {
    }

    public JSONObject toJSONObject() throws JSONException{
        JSONObject jobJson = new JSONObject();
        jobJson.put("location", location);
        jobJson.put("division", division);
        jobJson.put("grade", grade);
        jobJson.put("term", term);
        jobJson.put("url", url.toString());
        jobJson.put("type", type);
        jobJson.put("id", id);
        jobJson.put("title", title);
        return jobJson;
    }
}

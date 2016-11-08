package com.xgusties.careers;

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

    public Job(int s, String s1, URL u) {
        id = s;
        title = s1;
        url = u;
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

    // TODO -  this is view stuff, this shouldn't be here!
    public String getDetails() {
        StringBuilder builder = new StringBuilder();
        builder.append("Division:\n");
        builder.append(division);
        builder.append("\n\n");
        builder.append("Location:\n");
        builder.append(location);
        builder.append("\n\n");
        builder.append("Term:\n");
        builder.append(term);
        builder.append("\n\n");
        builder.append("Grade:\n");
        builder.append(grade);
        builder.append("\n\n");
        return builder.toString();
    }

    @Override
    public String toString() {
        return title;
    }
}

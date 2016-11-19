package com.xgusties.careers;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by craiga01 on 18/11/2016.
 */

public class JobSearchSuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.xgusties.careers.JobSearchSuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public JobSearchSuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}

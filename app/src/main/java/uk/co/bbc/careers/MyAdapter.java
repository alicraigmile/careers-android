package uk.co.bbc.careers;

import android.support.v7.widget.RecyclerView;

import java.util.Iterator;

/**
 * Created by craiga01 on 15/06/2016.
 */
public abstract class MyAdapter<V> extends RecyclerView.Adapter {
    
    abstract Iterator<Job> getIterator();

}

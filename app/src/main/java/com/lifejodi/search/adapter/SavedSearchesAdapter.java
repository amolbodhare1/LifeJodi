package com.lifejodi.search.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lifejodi.R;
import com.lifejodi.search.data.SearchData;
import com.lifejodi.search.interfaces.SavedSearchInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 3/5/2018.
 */

public class SavedSearchesAdapter extends RecyclerView.Adapter<SavedSearchesAdapter.SearchHolder> {

    Context context;
    ArrayList<HashMap<String,Object>> dataList = new ArrayList<>();
   SavedSearchInterface savedSearchInterface;
    public SavedSearchesAdapter(Context con,ArrayList<HashMap<String,Object>> list)
    {
        context = con;
        dataList = list;
    }

    @Override
    public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_saved_searches_list,parent,false);
        SearchHolder searchHolder = new SearchHolder(view);
        return searchHolder;
    }

    @Override
    public void onBindViewHolder(SearchHolder holder, final int position) {
        final HashMap<String,Object> dataMap = dataList.get(position);
        holder.tvSearchName.setText(String.valueOf(dataMap.get(SearchData.SEARCHNAME)));

        holder.layoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject dataObject = (JSONObject) dataMap.get(SearchData.SAVEDSEARCHOBJECT);
                    dataObject.remove(SearchData.SEARCHNAME);
                    dataObject.remove(SearchData.RELIGION);
                    dataObject.remove(SearchData.MOTHERTONGUE);
                    dataObject.remove(SearchData.CASTE);
                    dataObject.put(SearchData.SEARCH_NOW,"1");

                    savedSearchInterface = (SavedSearchInterface)context;
                    savedSearchInterface.getSavedSearchFromList(dataObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class SearchHolder extends RecyclerView.ViewHolder
    {
        TextView tvSearchName;
        LinearLayout layoutMain;
        public SearchHolder(View itemView) {
            super(itemView);
            tvSearchName = (TextView)itemView.findViewById(R.id.text_row_saved_searches);
            layoutMain = (LinearLayout)itemView.findViewById(R.id.layout_row_saved_searches);
        }
    }
}

package com.lifejodi.login.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.lifejodi.login.data.AutoComplete;
import com.lifejodi.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by parikshit on 16/3/17.
 */

public class AutoCompleteAdapter extends ArrayAdapter implements Filterable {

    private ArrayList<String> resultList;
    private ArrayList<HashMap<String,String>> placesList = new ArrayList<>();
    private Context mContext;
    AutoComplete autoComplete = AutoComplete.getInstance();

        public AutoCompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            mContext = context;
        }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView tv = new TextView(mContext);

        tv.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
        tv.setTextSize(14);
        tv.setGravity(Gravity.LEFT);
        tv.setText(placesList.get(position).get(autoComplete.KEY_PLACE_ADDRESS));
        tv.setPadding(20,20,20,20);
        tv.setTextColor(Color.GRAY);

        return tv;
    }

    @Override
        public int getCount() {
            return placesList.size();
        }

        @Override
        public String getItem(int index) {
            return "" + placesList.get(index).get(autoComplete.KEY_PLACE_ADDRESS);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        placesList = autoCompleteString(constraint.toString());
                        autoComplete.setPlacesList(placesList);

                        // Assign the data to the FilterResults
                        filterResults.values = placesList;
                        filterResults.count = placesList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }

    public ArrayList<HashMap<String,String>> autoCompleteString(String input) {
        ArrayList resultList = null;
        ArrayList<HashMap<String,String>> placesList = new ArrayList<>();

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {

            URL url = new URL(Constants.PLACE_API_URL + URLEncoder.encode(input, "utf8"));
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
        //    Log.e(Constants.LOG_TAG, "Error processing Places API URL", e);
            return placesList;
        } catch (IOException e) {
        //    Log.e(Constants.LOG_TAG, "Error connecting to Places API", e);
            return placesList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList(predsJsonArray.length());
  //          place_id = new ArrayList(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {

                HashMap<String,String> map = new HashMap<>();

                String place = predsJsonArray.getJSONObject(i).getString(autoComplete.KEY_PLACE_ADDRESS);
                map.put(autoComplete.KEY_PLACE_ADDRESS,place);
                map.put(autoComplete.KEY_PLACE_ID,predsJsonArray.getJSONObject(i).getString(autoComplete.KEY_PLACE_ID));


                /*String[] arraySplit = predsJsonArray.getJSONObject(i).getString("description").split(",");
                String place = "";
                for (int j = 0; j < arraySplit.length; j++) {
                    if (j <= 2) {
                        if (j == 2) {
                            place = place + arraySplit[j];
                        } else {
                            place = place + arraySplit[j] + ",";
                        }
                    }
                }*/
                placesList.add(map);
                resultList.add(place);
    ///            place_id.add(predsJsonArray.getJSONObject(i).getString("id"));
            }
        } catch (JSONException e) {
        //    Log.e(Constants.LOG_TAG, "Cannot process JSON results", e);
        }

        return placesList;
    }

}

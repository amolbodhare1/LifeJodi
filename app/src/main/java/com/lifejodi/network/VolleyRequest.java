package com.lifejodi.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by parikshit on 12/3/17.
 */

public class VolleyRequest {

    private VolleyResponse mVolleyResponse;
    private Context mContext;
    private String TAG = VolleyRequest.class.getSimpleName();
    private static String TAG_REQUEST = "RequestCancel";


    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public VolleyRequest(VolleyResponse volleyResponse)
    {
        this.mVolleyResponse = volleyResponse;
    }


    public void volleyStringRequest(int Method , String url , final String tag , final Map<String,String> inputParams)  {

        StringRequest stringRequest = new StringRequest(Method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    sendCallBack(response, tag);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "volleyStringRequest " + "Success");
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {

                    sendError(error, tag);
                Log.d(TAG, "volleyStringRequest " + "Fail");
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return inputParams;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(mContext).add(stringRequest);

    }

    private void sendError(VolleyError error, String tag) {

        mVolleyResponse.error(error.toString(),tag);
    }

    private void sendCallBack(String response, String tag) {

        mVolleyResponse.getResponse(response,tag);

    }


    public void volleyJsonRequest(int Method , String url , final String tag , JSONObject inputParams){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method, url, inputParams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    sendCallBack(response.toString(), tag);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "volleyStringRequest " + "Success");
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        sendError(error, tag);
                        Log.d(TAG, "volleyStringRequest " + "Fail");
                    }
                });


        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(mContext).add(jsonObjectRequest);
    }




}

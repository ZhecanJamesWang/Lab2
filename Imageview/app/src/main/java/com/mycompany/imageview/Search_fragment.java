package com.mycompany.imageview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appdatasearch.GetRecentContextCall;

import org.json.JSONObject;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * A placeholder fragment containing a simple view.
 */
public class Search_fragment extends Fragment {

    public Search_fragment() {
    }

    private WebView mWebView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_search_fragment, container, false);
//        text_input(rootView);
        final EditText editText = (EditText)rootView.findViewById(R.id.google_search);
        create_button(rootView, "library", editText);
        create_button(rootView, "search", editText);
        create_webview(rootView);

        return rootView;
    }


    public void create_button(View v, String button, EditText editText ){
        final EditText ED = editText;
        if (button == "library" ){
            Button button_library;
            button_library = (Button) v.findViewById(R.id.button_library);

            button_library.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    transitionToFragment();
                }
            });
        }
        if (button == "search" ){
            Button button_search;
            button_search = (Button) v.findViewById(R.id.button_search);

            button_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String keyword = ED.getText().toString();
                    Log.d("gettext", keyword);
                    google_search(keyword);
                }
            });
        }
    }


    public void transitionToFragment() {
        Library_fragment library_fragment = new Library_fragment();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container,library_fragment);
        transaction.commit();
    }

    public  void create_webview(View view){
        mWebView = (WebView)view.findViewById(R.id.search_webview);
        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        mWebView.loadUrl("http://i.ytimg.com/vi/tntOCGkgt98/maxresdefault.jpg");
    }


    public void google_search(String search) {
        // setup requestqueue here. Usually you should set up one queue for global use
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        // setup the request data
        search = search.replaceAll(" ","+");
        String URL = "https://www.googleapis.com/customsearch/v1?";
        URL+="key=AIzaSyAw-vsCUjYacbHHVDLeSUZPynir7gyGSwE"+"&";
        URL+="cx=003197243441389598668:b-nzlg3snm8"+"&";
        URL+="searchType=image&";
        URL+="q="+search;

        // setup the necessary json body for google search
        JSONObject body = new JSONObject();
        try {
            body.put("random", "thing"); // unnecessary, but I wanted to show you how to include body data
        } catch (Exception e) {
            Log.e("JSONException", e.getMessage());
        }

        JsonObjectRequest getRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do something with response
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.getMessage());
                    }
                }
        );

        queue.add(getRequest);
    }
}



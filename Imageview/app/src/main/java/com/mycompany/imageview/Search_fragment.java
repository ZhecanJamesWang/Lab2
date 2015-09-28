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

import org.json.JSONArray;
import org.json.JSONObject;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class Search_fragment extends Fragment {

    public Search_fragment() {
    }

    private WebView mWebView;
    private EditText editText;
    private ArrayList<String> link_array = new ArrayList<>();
    private int pointer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_search_fragment, container, false);
//        text_input(rootView);
        editText = (EditText)rootView.findViewById(R.id.google_search);
        create_button(rootView, "library");
        create_button(rootView, "search");
        create_button(rootView, "next");
        create_button(rootView, "previous");
        create_webview(rootView);

        return rootView;
    }


    public void create_button(View v, String button){
        if (button.equals("library") ){
            Button button_library;
            button_library = (Button) v.findViewById(R.id.button_library);

            button_library.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    transitionToFragment();
                }
            });
        }
        if (button.equals("search") ){
            Button button_search;
            button_search = (Button) v.findViewById(R.id.button_search);

            button_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String keyword = editText.getText().toString();
                    Log.d("gettext", keyword);
                    google_search(keyword);
                }
            });
        }
        if (button.equals("next") ){
            Button button_search;
            button_search = (Button) v.findViewById(R.id.button_search_next);

            button_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pointer+=1;
                    reload_webview();
                }
            });
        }
        if (button.equals("previous") ){
            Button button_search;
            button_search = (Button) v.findViewById(R.id.button_search_previous);

            button_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pointer-=1;
                    reload_webview();
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
        String link;
        link ="http://i.ytimg.com/vi/tntOCGkgt98/maxresdefault.jpg";
        mWebView.loadUrl(link);
    }

    public  void reload_webview(){
        String link;
        if (link_array.size()>0) {
            link = link_array.get(pointer % link_array.size());
        }
        else{
            link ="http://i.ytimg.com/vi/tntOCGkgt98/maxresdefault.jpg";
        }
        mWebView.loadUrl(link);
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

        JsonObjectRequest getRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do something with response
                        parse_json(response);
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
    public void parse_json(JSONObject response){
        try {
            JSONArray items = response.getJSONArray("items");
            for(int i=0; i<items.length();i+=1){
                JSONObject item = (JSONObject) items.get(i);
                link_array.add(item.getString("link"));
            }

        }
        catch (Exception error){
            Log.e("Error", error.getMessage());
        }

    }
}



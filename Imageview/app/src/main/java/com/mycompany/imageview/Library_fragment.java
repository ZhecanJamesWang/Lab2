package com.mycompany.imageview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

/**
 * A placeholder fragment containing a simple view.
 */
public class Library_fragment extends Fragment {

    public Library_fragment() {
    }
    private WebView mWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_library_fragment, container, false);
        create_webview(rootview);
        create_button(rootview);

        return rootview;
    }


    public  void create_webview(View view){
        mWebView = (WebView)view.findViewById(R.id.library_webview);

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        mWebView.loadUrl("http://beta.html5test.com/");
    }


    public void create_button(View view){
        Button button_search;
        button_search=(Button)view.findViewById(R.id.button_search);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transitionToFragment();
            }
        });
    }

    public void transitionToFragment() {
        Search_fragment search_fragment = new Search_fragment();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container,search_fragment);
        transaction.commit();
    }
}

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
        create_webview();
        create_button();

        return inflater.inflate(R.layout.fragment_search_fragment, container, false);
    }


    public  void create_webview(){
        mWebView = (WebView)getView().findViewById(R.id.activity_main_webview);

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("http://beta.html5test.com/");
    }


    public void create_button(){
        Button button_search;
        button_search=(Button)getView().findViewById(R.id.button_search);
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

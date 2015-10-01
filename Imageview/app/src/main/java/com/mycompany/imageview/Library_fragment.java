package com.mycompany.imageview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class Library_fragment extends Fragment {

    public Library_fragment() {
    }
    private String TAG = "library_fragment";
    private WebView mWebView;
    private int pointer;
    private String link;
    private MySQLiteHelper db;
    List<Image_DB> Image_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_library_fragment, container, false);

        db = new MySQLiteHelper(getActivity());
        Image_list = db.get_all_images();

        create_webview(rootview);
        create_button(rootview, "google");
        create_button(rootview, "next");
        create_button(rootview, "previous");
        create_button(rootview, "delete");

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
        link ="http://www.canon-europe.com/images/Android-logo_tcm13-1232684.png";
        mWebView.loadUrl(link);
    }


    public void create_button(View v, String button){
        Button button_search;
        button_search=(Button)v.findViewById(R.id.button_search);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transitionToFragment();
            }
        });

        if (button.equals("next") ){
            Button button_next;
            button_search = (Button) v.findViewById(R.id.button_library_next);

            button_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "next ");
                    pointer+=1;
                    reload_webview();
                }
            });
        }
        if (button.equals("previous") ){
            Button button_previous;
            button_search = (Button) v.findViewById(R.id.button_library_previous);

            button_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "previous");
                    pointer-=1;
                    reload_webview();
                }
            });
        }
        if (button.equals("delete") ){
            Button button_delete;
            button_search = (Button) v.findViewById(R.id.button_delete);

            button_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        db.deleteImage(Image_list.get(pointer % Image_list.size()));
                    }
                    catch (Exception e){
                        Log.d(TAG, e.getMessage());
                    };
                    Image_list = db.get_all_images();
                }
            });
        }

    }


    public  void reload_webview(){
        if (pointer<0){
            pointer=0;
        }
        if (Image_list.size()>0) {
            link = Image_list.get(pointer % Image_list.size()).getLink();
        }
        else
        {
            link ="http://www.canon-europe.com/images/Android-logo_tcm13-1232684.png";
        }
        Log.d("imagelist_size", String.valueOf(Image_list.size()));
        Log.d("link", link);
        mWebView.loadUrl(link);
    }

    public void transitionToFragment() {
        Search_fragment search_fragment = new Search_fragment();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container,search_fragment);
        transaction.commit();
    }
}

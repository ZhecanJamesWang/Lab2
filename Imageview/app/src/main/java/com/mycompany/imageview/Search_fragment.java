package com.mycompany.imageview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class Search_fragment extends Fragment {

    public Search_fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        create_button();
        text_input();

        return inflater.inflate(R.layout.fragment_search_fragment, container, false);
    }


    public void create_button(){
        Button button_library;
        button_library=(Button)getView().findViewById(R.id.button_library);

        button_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transitionToFragment();
            }
        });
    }

    public boolean text_input(){
        boolean handled = false;
        final EditText editText = (EditText)getView().findViewById(R.id.google_search);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    String keyword = editText.getText().toString();
//                    google(keyword);
                    handled = true;
                }
                return handled;
            }
        });
        return handled;
    }


    public void transitionToFragment() {
        Library_fragment library_fragment = new Library_fragment();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container,library_fragment);
        transaction.commit();
    }

}



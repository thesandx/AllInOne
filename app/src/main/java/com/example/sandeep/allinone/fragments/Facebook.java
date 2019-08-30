package com.example.sandeep.allinone.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.sandeep.allinone.Activities.MainActivity;
import com.example.sandeep.allinone.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Facebook extends Fragment {


    public Facebook() {
        // Required empty public constructor
    }
    WebView mWebView;

    public long startTime,endTime;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        startTime=System.currentTimeMillis();

        View view=inflater.inflate(R.layout.fragment_facebook,container,false);
        // Inflate the layout for this fragment
        mWebView=(WebView) view.findViewById(R.id.FbwebView);
        mWebView.loadUrl("https://mbasic.facebook.com");

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient());





        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //This is the filter
                if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();

                    } else {

                        ((MainActivity)getActivity()).onBackPressed();
                    }
                    return true;
                }
                return false;
            }
        });







        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        endTime=System.currentTimeMillis();
        long timeSpent=endTime-startTime;

    }
}
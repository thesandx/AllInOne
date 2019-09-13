package com.example.sandeep.allinone.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.example.sandeep.allinone.Activities.MainActivity;
import com.example.sandeep.allinone.R;

public class WebviewUrl extends Fragment {



    String url;
    Context context;


    public WebviewUrl(Context context,String url) {
        this.context =context;
        this.url = url;
    }
    WebView mWebView;

    ProgressDialog pd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.webview_url,container,false);
        // Inflate the layout for this fragment
        mWebView=(WebView) view.findViewById(R.id.masterwebView);
        mWebView.loadUrl(url);

        pd = new ProgressDialog(getActivity());

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pd.show();
                pd.setMessage("Loading page");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pd.dismiss();
            }
        });










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





}
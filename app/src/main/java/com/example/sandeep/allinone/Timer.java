package com.example.sandeep.allinone;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class Timer extends Fragment {

    private EditText UrlEditText;
    private Button UrlButton;
    WebView mWebView;
    String url;

    public Timer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_timer, container, false);

        //write your code here

        UrlEditText = view.findViewById(R.id.editTextUrl);
        UrlButton = view.findViewById(R.id.BtnUrl);
        mWebView=view.findViewById(R.id.urlWebView);

        UrlButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d("Timer", "clicked on go button");
                UrlButton.setVisibility(View.INVISIBLE);

                url = "https://www." + UrlEditText.getText().toString();
                Log.d("Timer", "get the string url which is " + url);
                try {
                    mWebView.loadUrl(url);
                }
                catch (Exception e){
                    Toast.makeText(getContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show();
                }

                // Enable Javascript
                WebSettings webSettings = mWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);

                // Force links and redirects to open in the WebView instead of in a browser
                mWebView.setWebViewClient(new WebViewClient());


                mWebView.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        //This is the filter
                        if (event.getAction() != KeyEvent.ACTION_DOWN)
                            return true;

                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            if (mWebView.canGoBack()) {
                                mWebView.goBack();

                            } else {

                                ((MainActivity) getActivity()).onBackPressed();
                            }
                            return true;
                        }
                        return false;
                    }
                });

            }

        });

     return view;


    }}



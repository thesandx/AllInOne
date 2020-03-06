package com.thesandx.sandeep.allinone.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.thesandx.sandeep.allinone.Activities.MainActivity;
import com.thesandx.sandeep.allinone.R;
import com.thesandx.sandeep.allinone.databinding.FragmentHomeBinding;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    private FragmentHomeBinding binding;
    FragmentTransaction fragmentTransaction;


    public Home() {
        // Required empty public constructor
    }

    public static String urlString = null;
    HashMap<String, String> urlMap = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        urlMap.put("facebook", "https://www.facebook.com/");
        urlMap.put("insta", "https://www.instagram.com/");
        urlMap.put("twitter", "https://www.twitter.com/");
        urlMap.put("linkedin", "https://www.linkedin.com/");
        urlMap.put("tiktok", "https://www.tiktok.com/trending/?lang=en");
        urlMap.put("youtube", "https://m.youtube.com/");

        ((MainActivity)getActivity()).getSupportActionBar();

        binding.fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlString = urlMap.get("facebook");
                openFragment(urlString);
                ((MainActivity)getActivity()).getSupportActionBar().setTitle("Facebook");

            }
        });

        binding.instaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlString = urlMap.get("insta");
                openFragment(urlString);
                ((MainActivity)getActivity()).getSupportActionBar().setTitle("Instagram");
            }
        });

        binding.twitterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlString = urlMap.get("twitter");
                openFragment(urlString);
                ((MainActivity)getActivity()).getSupportActionBar().setTitle("Twitter");
            }
        });

        binding.linkedinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlString = urlMap.get("linkedin");
                openFragment(urlString);
                ((MainActivity)getActivity()).getSupportActionBar().setTitle("LinkedIn");
            }
        });
        binding.tikTok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlString = urlMap.get("tiktok");
                openFragment(urlString);
                ((MainActivity) getActivity()).getSupportActionBar().setTitle("TikTok");
            }
        });
        binding.youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlString = urlMap.get("youtube");
                openFragment(urlString);
                ((MainActivity) getActivity()).getSupportActionBar().setTitle("Youtube");
            }
        });



        return  binding.getRoot();
    }


    public void openFragment(String url) {
        fragmentTransaction = requireFragmentManager().beginTransaction();
        WebviewUrl webviewUrl = new WebviewUrl();
        Bundle args = new Bundle();
        args.putString("url", url);
        webviewUrl.setArguments(args);
        fragmentTransaction.replace(R.id.main_container, webviewUrl);
        fragmentTransaction.commit();


    }


}

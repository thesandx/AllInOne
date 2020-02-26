package com.thesandx.sandeep.allinone.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thesandx.sandeep.allinone.Activities.MainActivity;
import com.thesandx.sandeep.allinone.R;
import com.thesandx.sandeep.allinone.databinding.FragmentHomeBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    private FragmentHomeBinding binding;
    FragmentTransaction fragmentTransaction;


    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false);

        ((MainActivity)getActivity()).getSupportActionBar();

        binding.fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragment("https://www.facebook.com/");
                ((MainActivity)getActivity()).getSupportActionBar().setTitle("Facebook");

            }
        });

        binding.instaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragment("https://www.instagram.com/");
                ((MainActivity)getActivity()).getSupportActionBar().setTitle("Instagram");
            }
        });

        binding.twitterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragment("https://www.twitter.com/");
                ((MainActivity)getActivity()).getSupportActionBar().setTitle("Twitter");
            }
        });

        binding.linkedinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragment("https://www.linkedin.com/");
                ((MainActivity)getActivity()).getSupportActionBar().setTitle("LinkedIn");
            }
        });



        return  binding.getRoot();
    }


    public void openFragment(String url) {
        fragmentTransaction = requireFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, new WebviewUrl(getContext(), url));
        fragmentTransaction.commit();


    }


}

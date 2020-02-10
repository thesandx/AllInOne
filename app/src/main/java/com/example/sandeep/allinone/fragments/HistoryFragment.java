package com.example.sandeep.allinone.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sandeep.allinone.Adapters.HistoryAdapter;
import com.example.sandeep.allinone.Models.HistoryModel;
import com.example.sandeep.allinone.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView noDiagnostics;

    private DatabaseReference database;

    private HistoryAdapter historyAdapter;

    List<HistoryModel> historyModelList = new ArrayList<>();


    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_history, container, false);

        database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference historyDb = database.child("history");
        DatabaseReference userDb = historyDb.child("sandeep");

        userDb.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                getHistoryFromFirebase(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mRecyclerView = view.findViewById(R.id.history_list);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        historyAdapter = new HistoryAdapter();
        mRecyclerView.setAdapter(historyAdapter);



        return  view;
    }

    private void getHistoryFromFirebase(DataSnapshot dataSnapshot) {

        for (DataSnapshot ds :dataSnapshot.getChildren()){

            HistoryModel historyModel = ds.getValue(HistoryModel.class);
            historyModelList.add(historyModel);

        }
        historyAdapter.setHistoryModels(historyModelList);
        historyAdapter.notifyDataSetChanged();

    }

}

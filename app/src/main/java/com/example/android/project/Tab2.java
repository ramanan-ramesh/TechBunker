package com.example.android.project;

/**
 * Created by ramanan_ramesh on 04-Nov-17.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.project.techbunker.TechBunker;
import com.example.android.project.techbunker.TechBunkerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.project.MainActivity.USN;

/**
 * Created by jauhar xlr on 18/4/2016.
 */
public class Tab2 extends Fragment {
    List<TechBunker> techBunkers=new ArrayList<>();
    TechBunkerAdapter techBunkerAdapter;
    static String usn;
    static Context context;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2,null);

        context=getContext();
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        SharedPreferences sharedpreferences =getActivity().getPreferences(Context.MODE_PRIVATE);
        usn=sharedpreferences.getString(USN,"");
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("techbunker").child(usn);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                techBunkers.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    TechBunker techBunker=dataSnapshot1.getValue(TechBunker.class);
                    techBunkers.add(techBunker);
                }
                techBunkerAdapter=new TechBunkerAdapter(getContext(),techBunkers,getActivity());
                recyclerView.setAdapter(techBunkerAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootView;
    }

}

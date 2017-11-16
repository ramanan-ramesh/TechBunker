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
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.android.project.TimeTable.TimeTableAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.android.project.MainActivity.departmentID;
import static com.example.android.project.MainActivity.section;
import static com.example.android.project.MainActivity.semester;
/**
 * Created by Jauhar xlr on 4/18/2016.
 * mycreativecodes.in
 */
public class Tab1 extends Fragment {
    RecyclerView recyclerView;
    TimeTableAdapter adapter;
    List<Timetable> timetableList =new ArrayList<>();
    final Calendar calendar = Calendar.getInstance();
    final SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
    Spinner spinner1;
    String weekday;
    public Tab1(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1, container, false);

        spinner1 = (Spinner) rootView.findViewById(R.id.spinner1);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.main_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        //getShit();
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                weekday=spinner1.getSelectedItem().toString();
                if(weekday.equals("Select"))
                    weekday = dayFormat.format(calendar.getTime());
                getShit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return rootView;
    }
    void getShit()
    {
        SharedPreferences sharedpreferences =getActivity().getPreferences(Context.MODE_PRIVATE);
        String sem=sharedpreferences.getString(semester,"");
        String sec=sharedpreferences.getString(section,"");
        String dep_id=sharedpreferences.getString(departmentID,"");
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("timetable").child(dep_id).child(sem).child(sec).child(weekday);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                timetableList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Timetable timetable = dataSnapshot1.getValue(Timetable.class);
                    timetableList.add(timetable);
                }
                adapter = new TimeTableAdapter(getActivity(),timetableList,recyclerView,getActivity());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
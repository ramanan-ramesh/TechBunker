package com.example.android.project;

/**
 * Created by ramanan_ramesh on 04-Nov-17.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.android.project.calendarofevents.CalendarOfEvents;
import com.example.android.project.calendarofevents.CalendarOfEventsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jauhar xlr on 4/18/2016.
 * mycreativecodes.in
 */
public class Tab3 extends Fragment {
    RecyclerView recyclerView;
    List<CalendarOfEvents> calendarOfEventsList=new ArrayList<>();
    int i;
    CalendarOfEvents[] calendarOfEvents1 =new CalendarOfEvents[200];
    CalendarOfEvents[] calendarOfEvents2 =new CalendarOfEvents[200];
    List<CalendarOfEvents> calendarOfEventsArrayList= new ArrayList<>();
    List<CalendarOfEvents> calendarOfEventsArrayList1=new ArrayList<>();
    CalendarOfEventsAdapter calendarOfEventsAdapter;
    AppCompatSpinner appCompatSpinner;

    public Tab3(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.tab3_recycler);
        appCompatSpinner=(AppCompatSpinner)rootView.findViewById(R.id.cal_spinner);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setNestedScrollingEnabled(false);

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("calendar of events");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                calendarOfEventsList.clear();
                for(DataSnapshot eventSnapshot:dataSnapshot.getChildren())
                {
                    CalendarOfEvents calendarOfEvents=eventSnapshot.getValue(CalendarOfEvents.class);
                    calendarOfEventsList.add(calendarOfEvents);
                }
                calendarOfEventsAdapter = new CalendarOfEventsAdapter(getActivity(),getShit(calendarOfEventsList));
                recyclerView.setAdapter(calendarOfEventsAdapter);
                appCompatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        calendarOfEventsAdapter=new CalendarOfEventsAdapter(getContext(),getStuff(appCompatSpinner.getSelectedItem().toString(),calendarOfEventsList));
                        recyclerView.setAdapter(calendarOfEventsAdapter);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootView;
    }

    public List<CalendarOfEvents> getStuff(String a,List<CalendarOfEvents> calendarOfEventsBoii)
    {
        List<CalendarOfEvents> list=calendarOfEventsBoii;
        if(a.equals("Default"))
            return getShit(calendarOfEventsBoii);
        if(a.equals("Holidays"))
            return getHolidays(calendarOfEventsBoii);
        return list;
    }

    public List<CalendarOfEvents> getHolidays(List<CalendarOfEvents> calendarOfEventsList1)
    {
        i=0;
        Iterator<CalendarOfEvents> iter=calendarOfEventsList1.iterator();
        while(iter.hasNext())
        {
            CalendarOfEvents coe=iter.next();
            calendarOfEvents2[i]=coe;
            i++;
        }

        add1("Aug");
        add1("Sep");
        add1("Oct");
        add1("Nov");
        add1("Dec");
        add1("Jan");

        return calendarOfEventsArrayList1;
    }

    public void add1(String s)
    {
        for(int j=0;j<calendarOfEventsList.size();j++)
            if(calendarOfEvents2[j].getDay().contains(s)&&calendarOfEvents2[j].getEvent().contains("Holiday"))
                calendarOfEventsArrayList1.add(calendarOfEvents2[j]);
    }

    public List<CalendarOfEvents> getShit(List<CalendarOfEvents> calendarOfEventses1)
    {


        i=0;
        Iterator<CalendarOfEvents> iter=calendarOfEventses1.iterator();
        while(iter.hasNext())
        {
            CalendarOfEvents coe=iter.next();
            calendarOfEvents1[i]=coe;
            i++;
        }

        add("Aug");
        add("Sep");
        add("Oct");
        add("Nov");
        add("Dec");
        add("Jan");

        return calendarOfEventsArrayList;
    }

    public void add(String s)
    {
        for(int j=0;j<calendarOfEventsList.size();j++)
            if(calendarOfEvents1[j].getDay().contains(s))
                calendarOfEventsArrayList.add(calendarOfEvents1[j]);
    }
}
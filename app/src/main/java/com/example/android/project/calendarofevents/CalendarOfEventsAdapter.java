package com.example.android.project.calendarofevents;

/**
 * Created by ramanan_ramesh on 02-Nov-17.
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.project.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramanan_ramesh on 02-Nov-17.
 */

public class CalendarOfEventsAdapter extends RecyclerView.Adapter<CalendarOfEventsAdapter.VersionViewHolder> {

    public static List<CalendarOfEvents> calendarOfEventsListses=new ArrayList<>();
    //public int[] imgSrcs=new int[100];
    //int index=0;
    Context context;

    public void setHomeActivitiesList(Context context, List<CalendarOfEvents> calendarOfEventsList) {
        this.calendarOfEventsListses=calendarOfEventsList;
    }

    public CalendarOfEventsAdapter(Context context,  List<CalendarOfEvents> calendarOfEventsList) {
        this.context = context;
        //imgSrcs=getSrcs(calendarOfEventsList);
        setHomeActivitiesList(context,calendarOfEventsList);
    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.calendarofevents_card, viewGroup, false);
        return new VersionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VersionViewHolder versionViewHolder, int i) {
        CalendarOfEvents calendarOfEvents=calendarOfEventsListses.get(i);
        String x=calendarOfEvents.getDay();
        versionViewHolder.title.setText(x);
        versionViewHolder.subTitle.setText(calendarOfEvents.getEvent());
       versionViewHolder.imageView.setImageResource(getImgSrc(calendarOfEvents.getDOW()));
    }
    public int getImgSrc(int dayofweek)
    {
        int x=0;
        switch(dayofweek)
        {
            case 0: x=R.drawable.sunday;break;
            case 1: x=R.drawable.monday;break;
            case 2: x=R.drawable.tuesday;break;
            case 3: x=R.drawable.wednesday;break;
            case 4: x=R.drawable.thursday;break;
            case 5: x=R.drawable.friday;break;
            case 6: x=R.drawable.saturday;break;
        }
        return x;
    }

    @Override
    public int getItemCount() {
        return calendarOfEventsListses.size();
    }

    class VersionViewHolder extends RecyclerView.ViewHolder {
        CardView cardItemLayout;
        TextView title;
        TextView subTitle;
        ImageView imageView;

        public VersionViewHolder(View itemView) {
            super(itemView);

            cardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);
            title = (TextView) itemView.findViewById(R.id.calendarofevents_date);
            subTitle = (TextView) itemView.findViewById(R.id.calendarofevents_event);
            imageView=(ImageView)itemView.findViewById(R.id.calendarofevents_image);

        }
    }
}
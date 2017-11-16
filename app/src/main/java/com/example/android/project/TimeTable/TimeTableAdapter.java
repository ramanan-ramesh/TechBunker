package com.example.android.project.TimeTable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.android.project.R;
import com.example.android.project.ScrollingActivity;
import com.example.android.project.Timetable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.project.MainActivity.departmentID;
import static com.example.android.project.MainActivity.semester;

/**
 * Created by ramanan_ramesh on 02-Nov-17.
 */

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.VersionViewHolder> {

    public List<Timetable> timetableList=new ArrayList<>();
    Context context;
    RecyclerView mRecyclerView;
    Activity activity;

    public void setHomeActivitiesList(Context context, List<Timetable> timetableList) {
        this.timetableList=timetableList;
    }

    public TimeTableAdapter(Context context, List<Timetable> timetableList,RecyclerView recyclerView, Activity activity) {
        this.context = context;
        setHomeActivitiesList(context,timetableList);
        this.mRecyclerView=recyclerView;
        this.activity=activity;
    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.timetable_card, viewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=mRecyclerView.getChildAdapterPosition(v);
                if(pos>=0&&pos<getItemCount()&&timetableList.get(pos).getSubCode().length()==5)
                {
                   // Toast.makeText(context,String.valueOf(pos), Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(context, ScrollingActivity.class);
                    intent.putExtra("subCode",timetableList.get(pos).getSubCode());
                    SharedPreferences sharedpreferences=activity.getPreferences(Context.MODE_PRIVATE);
                    intent.putExtra("sem",sharedpreferences.getString(semester,""));
                    intent.putExtra("depid",sharedpreferences.getString(departmentID,""));
                    context.startActivity(intent);
                }

            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final TextView textView=(TextView)view.findViewById(R.id.listitem_subname);
                SharedPreferences sharedpreferences=activity.getPreferences(Context.MODE_PRIVATE);
                String sem=sharedpreferences.getString(semester,"");
                String dep_id=sharedpreferences.getString(departmentID,"");
                if(!textView.getText().toString().isEmpty()) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("subject").child(dep_id).child(sem).child(textView.getText().toString());
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            FrameLayout frameLayout = (FrameLayout) activity.findViewById(R.id.main_coordinator);
                            Snackbar snackbar = Snackbar.make(frameLayout, dataSnapshot.child("subName").getValue(String.class), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                return true;
            }
        });
        return new VersionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VersionViewHolder versionViewHolder, int i) {

            versionViewHolder.title.setText(timetableList.get(i).getTimings());
            versionViewHolder.subTitle.setText(timetableList.get(i).getSubCode());
    }

    @Override
    public int getItemCount() {
        return timetableList.size();
    }

    class VersionViewHolder extends RecyclerView.ViewHolder {
        CardView cardItemLayout;
        TextView title;
        TextView subTitle;

        public VersionViewHolder(View itemView) {
            super(itemView);

            cardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);
            title = (TextView) itemView.findViewById(R.id.listitem_name);
            subTitle = (TextView) itemView.findViewById(R.id.listitem_subname);

        }
    }
}

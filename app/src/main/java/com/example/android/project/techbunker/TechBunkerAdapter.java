package com.example.android.project.techbunker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.project.MainActivity.USN;


/**
 * Created by ramanan_ramesh on 06-Nov-17.
 */

public class TechBunkerAdapter extends RecyclerView.Adapter<TechBunkerAdapter.VersionViewHolder> {

    public static List<TechBunker> techBunkerList=new ArrayList<>();
    Context context;
    static Activity activity;

    public void setHomeActivitiesList(Context context, List<TechBunker> techBunkers) {

        this.techBunkerList=techBunkers;
    }

    public TechBunkerAdapter(Context context,  List<TechBunker> techBunkers,Activity activity) {
        this.context = context;
        setHomeActivitiesList(context,techBunkers);
        this.activity=activity;
    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.techbunker_card, viewGroup, false);
        return new VersionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VersionViewHolder versionViewHolder, int i) {
        TechBunker techBunker=techBunkerList.get(i);
        versionViewHolder.textView1.setText(techBunker.getSubName());
        versionViewHolder.textView2.setText(String.valueOf(techBunker.getBunks()));
        versionViewHolder.textView3.setText(String.valueOf(techBunker.getRatio()));
        versionViewHolder.textView4.setText(String.valueOf(techBunker.getClasses()));
        versionViewHolder.textView5.setText(String.valueOf(techBunker.getPercentage()));
        versionViewHolder.textView6.setText(String.valueOf(techBunker.getMinPerscentage()));
    }

    @Override
    public int getItemCount() {
        return techBunkerList.size();
    }
    public static class VersionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public static CardView cardItemLayout;
        public static TextView textView1,textView2,textView3,textView4,textView5, textView6;


        public VersionViewHolder(View itemView) {
            super(itemView);

            cardItemLayout = (CardView) itemView.findViewById(R.id.techbunkercard);
            textView1=(TextView)itemView.findViewById(R.id.textView2);
            textView2=(TextView)itemView.findViewById(R.id.textView17);
            textView3=(TextView)itemView.findViewById(R.id.textView20);
            textView4=(TextView)itemView.findViewById(R.id.textView25);
            textView5=(TextView)itemView.findViewById(R.id.textView28);
            textView6=(TextView)itemView.findViewById(R.id.textView33);
            itemView.findViewById(R.id.imageButton3).setOnClickListener(this);
            itemView.findViewById(R.id.imageButton5).setOnClickListener(this);
            itemView.findViewById(R.id.imageButton7).setOnClickListener(this);
            itemView.findViewById(R.id.imageButton4).setOnClickListener(this);
            itemView.findViewById(R.id.imageButton6).setOnClickListener(this);
            itemView.findViewById(R.id.imageButton8).setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.imageButton3){
                SharedPreferences sharedpreferences =activity.getPreferences(Context.MODE_PRIVATE);
                String usn=sharedpreferences.getString(USN,"");
                final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("techbunker").child(usn).child(techBunkerList.get(getAdapterPosition()).getSubName());
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int bunks=dataSnapshot.child("bunks").getValue(Integer.class);
                        int minBunks=dataSnapshot.child("minPerscentage").getValue(Integer.class);
                        int classes=dataSnapshot.child("classes").getValue(Integer.class);
                        databaseReference.child("bunks").setValue(bunks+1);
                        float a=(float)((classes-bunks-1)*100.0/classes);
                        databaseReference.child("percentage").setValue(a);
                        float b=(float)((a-minBunks)*classes/100.0);
                        databaseReference.child("ratio").setValue(b);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
            if(view.getId()==R.id.imageButton4)
            {
                SharedPreferences sharedpreferences =activity.getPreferences(Context.MODE_PRIVATE);
                String usn=sharedpreferences.getString(USN,"");
                final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("techbunker").child(usn).child(techBunkerList.get(getAdapterPosition()).getSubName());
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int bunks=dataSnapshot.child("bunks").getValue(Integer.class);
                        int minBunks=dataSnapshot.child("minPerscentage").getValue(Integer.class);
                        int classes=dataSnapshot.child("classes").getValue(Integer.class);
                        databaseReference.child("bunks").setValue(bunks-1);
                        float a=(float)((classes-(bunks-1))*100.0/classes);
                        databaseReference.child("percentage").setValue(a);
                        float b=(float)((a-minBunks)*classes/100.0);
                        databaseReference.child("ratio").setValue(b);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            if(view.getId()==R.id.imageButton5)
            {
                SharedPreferences sharedpreferences =activity.getPreferences(Context.MODE_PRIVATE);
                String usn=sharedpreferences.getString(USN,"");
                final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("techbunker").child(usn).child(techBunkerList.get(getAdapterPosition()).getSubName());
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int classes=dataSnapshot.child("classes").getValue(Integer.class);
                        int bunks=dataSnapshot.child("bunks").getValue(Integer.class);
                        int minBunks=dataSnapshot.child("minPerscentage").getValue(Integer.class);
                        databaseReference.child("classes").setValue(classes+1);
                        float a=(float)((classes+1-bunks)*100.0/(classes+1));
                        databaseReference.child("percentage").setValue(a);
                        float b=(float)((a-minBunks)*(classes+1)/100.0);
                        databaseReference.child("ratio").setValue(b);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            if(view.getId()==R.id.imageButton6)
            {
                SharedPreferences sharedpreferences =activity.getPreferences(Context.MODE_PRIVATE);
                String usn=sharedpreferences.getString(USN,"");
                final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("techbunker").child(usn).child(techBunkerList.get(getAdapterPosition()).getSubName());
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int classes=dataSnapshot.child("classes").getValue(Integer.class);
                        int bunks=dataSnapshot.child("bunks").getValue(Integer.class);
                        int minBunks=dataSnapshot.child("minPerscentage").getValue(Integer.class);
                        databaseReference.child("classes").setValue(classes-1);
                        float a=(float)(((classes-1)-(bunks))*100.0/(classes-1));
                        databaseReference.child("percentage").setValue(a);
                        float b=(float)((a-minBunks)*(classes-1)/100.0);
                        databaseReference.child("ratio").setValue(b);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            if(view.getId()==R.id.imageButton7)
            {
                SharedPreferences sharedpreferences =activity.getPreferences(Context.MODE_PRIVATE);
                String usn=sharedpreferences.getString(USN,"");
                final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("techbunker").child(usn).child(techBunkerList.get(getAdapterPosition()).getSubName());
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        float percent=dataSnapshot.child("percentage").getValue(Float.class);
                        int minBunks=dataSnapshot.child("minPerscentage").getValue(Integer.class);
                        int classes=dataSnapshot.child("classes").getValue(Integer.class);
                        databaseReference.child("minPerscentage").setValue(minBunks+1);
                        float a=(float)(((percent)-(minBunks+1))*classes/100.0);
                        databaseReference.child("ratio").setValue(a);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            if(view.getId()==R.id.imageButton8)
            {
                SharedPreferences sharedpreferences =activity.getPreferences(Context.MODE_PRIVATE);
                String usn=sharedpreferences.getString(USN,"");
                final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("techbunker").child(usn).child(techBunkerList.get(getAdapterPosition()).getSubName());
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        float percent=dataSnapshot.child("percentage").getValue(Float.class);
                        int minBunks=dataSnapshot.child("minPerscentage").getValue(Integer.class);
                        int classes=dataSnapshot.child("classes").getValue(Integer.class);
                        databaseReference.child("minPerscentage").setValue(minBunks-1);
                        float a=(float)(((percent)-(minBunks-1))*classes/100.0);
                        databaseReference.child("ratio").setValue(a);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        }
    }
}


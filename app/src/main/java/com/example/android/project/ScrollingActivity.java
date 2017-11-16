package com.example.android.project;

import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by ramanan_ramesh on 07-Nov-17.
 */

public class ScrollingActivity extends AppCompatActivity {
    private ImageView image_scrolling_top;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_scrollingview);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        final TextView textView=(TextView)findViewById(R.id.text_subName);
        TextView textView1=(TextView) findViewById(R.id.text_subCode);
        final TextView textView2=(TextView)findViewById(R.id.text_credits);
        final TextView textView3=(TextView)findViewById(R.id.text_numberOfHours);

        String subCode=getIntent().getStringExtra("subCode");
        String sem=getIntent().getStringExtra("sem");
        String dep_id=getIntent().getStringExtra("depid");

        textView1.setText(subCode);

        final TextView textView4,textView5,textView6,textView7, textView8,textView9;
        final TextView textView10,textView11,textView12,textView13;

        textView4=(TextView)findViewById(R.id.content1);
        textView5=(TextView)findViewById(R.id.content2);
        textView6=(TextView)findViewById(R.id.content3);
        textView7=(TextView)findViewById(R.id.content4);
        textView8=(TextView)findViewById(R.id.content_5);
        textView9=(TextView)findViewById(R.id.content5);

        textView10=(TextView)findViewById(R.id.content_1);
        textView11=(TextView)findViewById(R.id.content_2);
        textView12=(TextView)findViewById(R.id.content_3);
        textView13=(TextView)findViewById(R.id.content_4);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("subject").child(dep_id).child(sem).child(subCode);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Subject subject=dataSnapshot.getValue(Subject.class);
                NestedScrollView nestedScrollView=(NestedScrollView)findViewById(R.id.nestedscrollview);
                nestedScrollView.setVisibility(View.VISIBLE);
                CollapsingToolbarLayout collapsingToolbar =
                        (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
                collapsingToolbar.setTitle(subject.getSubName());
                textView10.setText("UNIT 1");
                textView11.setText("UNIT 2");
                textView12.setText("UNIT 3");
                textView13.setText("UNIT 4");

                textView.setText(subject.getSubName());
                textView2.setText(String.valueOf(subject.getCredits()));
                textView3.setText(String.valueOf(subject.getNumberOfHours()));
                final List<String> list=subject.getContent();
                textView4.setText(list.get(0));
                textView5.setText(list.get(1));
                textView6.setText(list.get(2));
                textView7.setText(list.get(3));
                if(!list.get(4).isEmpty())
                {
                    textView8.setText("UNIT 5");
                    textView9.setText(list.get(4));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        image_scrolling_top = (ImageView) findViewById(R.id.image_scrolling_top);
        image_scrolling_top.setImageResource(R.drawable.material_design_3);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            CollapsingToolbarLayout collapsing_toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
            collapsing_toolbar_layout.setExpandedTitleTextColor(ColorStateList.valueOf(Color.TRANSPARENT));
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}

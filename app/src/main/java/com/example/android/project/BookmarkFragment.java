package com.example.android.project;

/**
 * Created by ramanan_ramesh on 04-Nov-17.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

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
 * Created by Jauhar xlr on 4/18/2016.
 * mycreativecodes.in
 */
public class BookmarkFragment extends Fragment {
    String sem,dep_id;
    RecyclerView recyclerView;
    List<String> stringList=new ArrayList<>();
    List<String> stringList1=new ArrayList<>();

    TextView textView4,textView5,textView6,textView7, textView8,textView9;
    TextView textView10,textView11,textView12,textView13;
    TextView textView,textView1,textView2,textView3;
    NestedScrollView nestedScrollView;
    LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bookmark_layout,null);
        SharedPreferences sharedpreferences =getActivity().getPreferences(Context.MODE_PRIVATE);
        sem=sharedpreferences.getString(semester,"");
        dep_id=sharedpreferences.getString(departmentID,"");

        textView=(TextView)rootView.findViewById(R.id.text_subName);
        textView1=(TextView)rootView.findViewById(R.id.text_subCode);
        textView2=(TextView)rootView.findViewById(R.id.text_credits);
        textView3=(TextView)rootView.findViewById(R.id.text_numberOfHours);

        final AppCompatSpinner appCompatSpinner=(AppCompatSpinner)rootView.findViewById(R.id.subjectlist_spinner);
        linearLayout=(LinearLayout)rootView.findViewById(R.id.subject_linearlayout);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.subject_recyclerview);
        nestedScrollView=(NestedScrollView)rootView.findViewById(R.id.nestedscrollview);

        textView4=(TextView)rootView.findViewById(R.id.content1);
        textView5=(TextView)rootView.findViewById(R.id.content2);
        textView6=(TextView)rootView.findViewById(R.id.content3);
        textView7=(TextView)rootView.findViewById(R.id.content4);
        textView8=(TextView)rootView.findViewById(R.id.content_5);
        textView9=(TextView)rootView.findViewById(R.id.content5);

        textView10=(TextView)rootView.findViewById(R.id.content_1);
        textView11=(TextView)rootView.findViewById(R.id.content_2);
        textView12=(TextView)rootView.findViewById(R.id.content_3);
        textView13=(TextView)rootView.findViewById(R.id.content_4);

        final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("subject").child(dep_id).child(sem);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stringList.clear();
                stringList1.clear();
                for (final DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    {
                        String b=dataSnapshot1.child("subName").getValue(String.class);
                        stringList.add(b);
                        stringList1.add(dataSnapshot1.getKey());
                        //ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,stringList);
                        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_2, android.R.id.text1,stringList) {
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                View view = super.getView(position, convertView, parent);
                                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                                text1.setText(stringList.get(position));

                                text2.setText("\n" );
                                return view;
                            }
                        };
                        appCompatSpinner.setAdapter(adapter);
                        appCompatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                //getShit(appCompatSpinner.getSelectedItem().toString());
                                getShit(stringList1.get(i));
                                linearLayout.setBackgroundColor(R.color.subject5);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return rootView;
    }
    void getShit(String s)
    {
        final String code=s;
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("subject").child(dep_id).child(sem).child(s);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Subject subject=dataSnapshot.getValue(Subject.class);
                nestedScrollView.setVisibility(View.VISIBLE);
                textView10.setText("UNIT 1");
                textView11.setText("UNIT 2");
                textView12.setText("UNIT 3");
                textView13.setText("UNIT 4");

                textView.setText(subject.getSubName());
                textView1.setText(code);
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
    }
    int getColor(int a)
    {
        int x=R.color.subject5;
        switch (a){
        case 0:x=R.color.subject1;break;
        case 1:x=R.color.subject2;break;
            case 2: x=R.color.subject3; break;
            case 3: x=R.color.subject4;break;
            case 4:x=R.color.subject5;break;
    }
    return x;
    }
}
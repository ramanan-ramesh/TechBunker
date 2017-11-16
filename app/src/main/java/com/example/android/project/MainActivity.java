package com.example.android.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.android.project.techbunker.TechBunker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jauhar xlr on 4/18/2016.
 * mycreativecodes.in
 */
public class MainActivity extends AppCompatActivity{
    public static final String studentName="studentName";
    public static final String USN="USN";
    public static final String section="section";
    public static final String semester="semester";
    public static final String departmentID="departmentID";
    public static final String flag1=new String();
    static SharedPreferences sharedpreferences;
    public static boolean isit;
    public static boolean calledAlready=false;
    public static final String PREF_USER_FIRST_TIME = "user_first_time";
    boolean isUserFirstTime;

    static DrawerLayout myDrawerLayout;
    static NavigationView myNavigationView;
    static FragmentManager myFragmentManager;
    static FragmentTransaction myFragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getPreferences(Context.MODE_PRIVATE);

        isit = sharedpreferences.getBoolean(flag1, false);

        if (!calledAlready) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            calledAlready = true;
        }


        if (isit) {
            addStuff();
        } else {
            setContentView(R.layout.check);
            final SharedPreferences.Editor editor = sharedpreferences.edit();
            final EditText editText = (EditText) findViewById(R.id.editText);
            final EditText editText10 = (EditText) findViewById(R.id.editText10);
            final Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
            final Spinner spinner4 = (Spinner) findViewById(R.id.spinner4);
            final Spinner spinner5 = (Spinner) findViewById(R.id.spinner5);
            Button button4 = (Button) findViewById(R.id.button4);
            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String usn, sname, sec, sem, depid;
                    usn = editText.getText().toString();
                    sname = editText10.getText().toString();
                    depid = String.valueOf(spinner3.getSelectedItemPosition() + 1);
                    sem = spinner4.getSelectedItem().toString();
                    sec = spinner5.getSelectedItem().toString();
                    editor.putString(USN, usn);
                    editor.putString(studentName, sname);
                    editor.putString(departmentID, depid);
                    editor.putString(semester, sem);
                    editor.putString(section, sec);
                    editor.putBoolean(flag1, true);
                    editor.commit();
                    final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("users").child(usn).setValue(new Student(usn,sname,sec,Integer.parseInt(depid),Integer.parseInt(sem)));
                    final List<String> stringList=new ArrayList<>();
                    databaseReference.child("subject").child(depid).child(sem).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            stringList.clear();
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                String a=dataSnapshot1.child("subName").getValue(String.class);
                                stringList.add(a);
                                databaseReference.child("techbunker").child(usn).child(a).setValue(new TechBunker(a,0,52,(float)100.0,85,(float)7.8));
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                   addStuff();
                }
            });

            Intent intent =new Intent(getApplicationContext(),PagerActivity.class);
            startActivity(intent);
        }
    }

     public void addStuff(){
        isit=sharedpreferences.getBoolean(flag1,true);
        setContentView(R.layout.activity_main);


        /**
         *Setup the DrawerLayout and NavigationView
         */
        myDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        myNavigationView = (NavigationView) findViewById(R.id.nav_drawer) ;
        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the HomeFragment as the first Fragment
         */
        myFragmentManager = getSupportFragmentManager();
        myFragmentTransaction = myFragmentManager.beginTransaction();
        myFragmentTransaction.replace(R.id.containerView, new HomeFragment()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */
        myNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem selectedMenuItem) {
                myDrawerLayout.closeDrawers();
                if (selectedMenuItem.getItemId() == R.id.nav_item_home) {
                    FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, new HomeFragment()).commit();
                }
                if (selectedMenuItem.getItemId() == R.id.nav_item_bookmark) {
                    FragmentTransaction xfragmentTransaction = myFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new BookmarkFragment()).commit();
                }
                return false;
            }
        });
        /**
         * Setup Drawer Toggle of the Toolbar
         */
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, myDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);
        myDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }
}

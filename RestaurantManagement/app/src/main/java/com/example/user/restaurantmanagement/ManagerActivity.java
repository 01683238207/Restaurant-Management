package com.example.user.restaurantmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManagerActivity extends AppCompatActivity {

    ArrayList<String > arraylist;
    ArrayList<String > arraylist2;
    ListView listView;
    BaseAdapter adapter;
    FirebaseDatabase database;
    DatabaseReference reference;
    ChildEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        arraylist=new ArrayList<>();
        arraylist2=new ArrayList<>();

        initAll();
        listView= (ListView) findViewById(R.id.list_manager);
        listView.setAdapter(adapter);

    }

    private void initAll() {
        adapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return arraylist.size();
            }

            @Override
            public String getItem(int position) {
                return arraylist.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v=getLayoutInflater().inflate(R.layout.manager_item_list,parent,false);
                ((TextView)v.findViewById(R.id.table_no)).setText(arraylist2.get(position));
                ((TextView)v.findViewById(R.id.item_list)).setText(getItem(position));
                v.findViewById(R.id.served).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reference.child(arraylist2.get(position)).setValue(null);
                        arraylist.remove(position);
                        arraylist2.remove(position);
                        notifyDataSetChanged();
                    }
                });
                return v;
            }
        };
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("orders");
        listener=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(arraylist.size()>0 && !dataSnapshot.getValue().toString().equals(arraylist.get(arraylist.size()-1))) {
                    arraylist.add(dataSnapshot.getValue().toString());
                    arraylist2.add(dataSnapshot.getKey());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        reference.addChildEventListener(listener);
    }


    @Override
    protected void onResume() {
        super.onResume();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot shot:dataSnapshot.getChildren()){
                    arraylist.add(shot.getValue().toString());
                    arraylist2.add(shot.getKey());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.example.user.restaurantmanagement;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity{
    ArrayList<ItemNamePrice > arraylist;
    ListView listView;
    FirebaseDatabase database;
    DatabaseReference reference;
    String items;
    int total=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        String []item={"Thai Soup","China Soup","Egg Chawmen","Chiken Chawmen","Kabab Ruthy",
                "Chiken Tacco","Chiken pizza", "B.B.Q Pizza","Mutton Pizza","Crispy Fried","Chiken France Fry",
                "Maharaga Mac","Kranchy chiken Burger","Sharma  Sandwich","Cabbage Salad","Tomato Salad",
                "7up","Speed","Pepsi","Mountain Dew","Chocolate coffee","Black Coffee","Valentine Coffee","Faluda"};
        int []price={100,200,50,70,100,150,90,120,200,60,100,200,200,120,50,50,50,50,50,50,80,80,200,200};

        arraylist=new ArrayList<>();
        for (int i=0;i<item.length;i++)
            arraylist.add(new ItemNamePrice(item[i],price[i]));
        database= FirebaseDatabase.getInstance();
        reference= database.getReference("orders/"+getIntent().getStringExtra("name"));

        listView= (ListView) findViewById(R.id.list);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return arraylist.size();
            }

            @Override
            public ItemNamePrice getItem(int position) {
                return arraylist.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v=getLayoutInflater().inflate(R.layout.list_item,parent,false);
                ((TextView)v.findViewById(R.id.item_name)).setText(getItem(position).getName());
                ((TextView)v.findViewById(R.id.item_rate)).setText(""+getItem(position).getPrice());
                v.findViewById(R.id.quantity).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       BlankFragment blankFragment= new BlankFragment();
                        blankFragment.setListener(new BlankFragment.OnFragmentInteractionListener() {
                            @Override
                            public void addQuantity(int i) {
                                getItem(position).setQuantity(i);
                                Toast.makeText(Main2Activity.this,""+getItem(position).getName()+" item is "+i,Toast.LENGTH_SHORT).show();
                            }
                        });
                        blankFragment.show(getSupportFragmentManager(),"Dialog");
                    }
                });
                return v;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        items="";
        for (int i=0;i<arraylist.size();i++){
            if(arraylist.get(i).getQuantity()>0){
                total+=arraylist.get(i).getPrice()*arraylist.get(i).getQuantity();
                items+=arraylist.get(i).getQuantity()+" x "+arraylist.get(i).getName()+"\n";
            }
        }
        AlertDialog alertDialog=new AlertDialog.Builder(Main2Activity.this).
                setTitle("Item Selected").
                setMessage(items+"\n Total price "+total).
                setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reference.setValue(items);
                        Toast.makeText(Main2Activity.this,"Your Confirmation Send to Manager, Wait for Serve",Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Main2Activity.this,"Cancel",Toast.LENGTH_SHORT).show();
            }
        }).create();
        alertDialog.show();
        return true;
    }
}

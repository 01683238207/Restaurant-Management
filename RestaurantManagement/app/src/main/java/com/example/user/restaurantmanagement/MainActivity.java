package com.example.user.restaurantmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etlogin,etMangLogin;
    Button btnLogin,btnManagLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVies();
        btnLogin.setOnClickListener(this);
    }

    private void initVies() {
        etlogin=(EditText) findViewById(R.id.et_login);
        etMangLogin=(EditText) findViewById(R.id.et_manag_Login);
        btnLogin=(Button) findViewById(R.id.btn_login);
        btnManagLogin=(Button) findViewById(R.id.btn_manageLogin);
        btnManagLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (etMangLogin.getText().toString()){
                    case "abcd":
                        Intent intent=new Intent(MainActivity.this,ManagerActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"Give Manager ID",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (etlogin.getText().toString()){
            case "table1":
            case "table2":
            case "table3":
            case "table4":
            case "table5":
            case "table6":
                Intent intent2=new Intent(MainActivity.this,Main2Activity.class);
                intent2.putExtra("name",etlogin.getText().toString());
                startActivity(intent2);
                break;
            default:
                Toast.makeText(getApplicationContext(),"Give Table Number",Toast.LENGTH_SHORT).show();
                break;
        }
    }

}

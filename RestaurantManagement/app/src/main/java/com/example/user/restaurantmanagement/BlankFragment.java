package com.example.user.restaurantmanagement;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class BlankFragment extends DialogFragment implements View.OnClickListener{

    Button b1,b2,b3,b4;
    OnFragmentInteractionListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_blank, container, false);
        initAll(v);
        return  v;
    }

    private void initAll(View v) {
        b1= (Button) v.findViewById(R.id.btn_qnt1);
        b2= (Button) v.findViewById(R.id.btn_qnt2);
        b3= (Button) v.findViewById(R.id.btn_qnt3);
        b4= (Button) v.findViewById(R.id.btn_qnt4);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setListener(OnFragmentInteractionListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.addQuantity(Integer.parseInt(((Button)v).getText().toString()));
            dismiss();
        }else {
            Toast.makeText(getContext(),"No Listener Added to fragment",Toast.LENGTH_SHORT).show();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void addQuantity(int i);
    }
}

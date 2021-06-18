package com.example.e_commerceapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Queue;


public class SignInFragment extends Fragment {



    public SignInFragment() {
        // Required empty public constructor
    }

    private TextView textView3;
    private FrameLayout parentFrameLayout;
    private Button SignIn;
    String url = "http://ec2-13-126-56-107.ap-south-1.compute.amazonaws.com:3001/Customer/Login";
    String data = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        textView3 = view.findViewById(R.id.textView3);
        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);

        //Volley request
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
       SignIn = view.findViewById(R.id.buttonsignIn);
       SignIn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               JsonArrayRequest arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                   @Override
                   public void onResponse(JSONArray response) {
                   try {
                       JSONObject jsonObject = response.getJSONObject(0);
                       JSONArray login = jsonObject.getJSONArray("login");
                       for (int i = 0; i< login.length();i++){
                           JSONObject jsonObject1 = login.getJSONObject(i);
                           String Username= jsonObject.getString("Username");
                           String password = jsonObject.getString("password");

                           data ="Username" + "password";
                           SignIn.setText(data);
                       }

                   }catch (Exception exception){
                         exception.printStackTrace();
                   }
                   }
               }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                      error.printStackTrace();
                   }
               });
               requestQueue.add(arrayRequest);
           }
       });

        return view;

    }


    // for register new user
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignUpFragment());
            }
        });
    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    //sign In click

/*
 StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                       Log.d("Tag",response);
                   }
               }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       error.printStackTrace();
                   }
               });
               requestQueue.add(stringRequest);
 */
}
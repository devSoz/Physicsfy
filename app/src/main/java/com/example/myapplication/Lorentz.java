package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Lorentz#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Lorentz extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int C = 300000000;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ViewGroup root;
    private Vibrator vibrator;


    public Lorentz() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Lorentz.
     */
    // TODO: Rename and change types and number of parameters
    public static Lorentz newInstance(String param1, String param2) {
        Lorentz fragment = new Lorentz();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        root = (ViewGroup) inflater.inflate(R.layout.fragment_lorentz, container,false);
        lorentzCalculate(); //Onclick events and key functionalities are added here
        //inflater.inflate(R.layout.fragment_viewroles, container, false);
        return root; // inflater.inflate(R.layout.fragment_lorentz, container, false);

    }

    private void lorentzCalculate()
    {
        // get the VIBRATOR_SERVICE system service
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        Button b1 = root.findViewById(R.id.btnSubmit1);

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                EditText speedl = (EditText) root.findViewById(R.id.ptxtSpeed);
                EditText lorentz = (EditText) root.findViewById(R.id.ptxtLorentz);
                //If speed is not entered, show validation error
                if (TextUtils.isEmpty(speedl.getText())) {
                    speedl.setError("Enter speed");
                }

                else
                {
                    //if entered speed is not valid input, inform user
                    if(!(isNumeric(speedl.getText().toString())) || Double.parseDouble(speedl.getText().toString()) >C)
                    {
                        //Log.d("test","4");
                        Toast.makeText(getActivity() ,"Invalid speed input", Toast.LENGTH_SHORT).show();
                        speedl.setText("");
                    }

                    else if (TextUtils.isEmpty(lorentz.getText()))
                    {
                        //If user does not enter lorentz value, calculate based on speed and show the result.
                        bgColorChange(R.color.bgColor);
                        Double numSpeed = Double.parseDouble(String.valueOf(speedl.getText()));
                        Double lorentzFactor = (1 / (1 + Math.pow((numSpeed / C), 2)));
                        StringBuilder alertText1 = new StringBuilder(50);
                        alertText1.append("Lorentz factor: ");
                        alertText1.append(String.format("%.6f",(lorentzFactor)));
                        alertShow(alertText1);
                    }

                    else
                    {
                        //If lorentz value entered is not valid, show erorr
                        if(!(isNumeric(lorentz.getText().toString())))
                        {
                            Toast.makeText( getActivity(),"Invalid lorentz factor input", Toast.LENGTH_SHORT).show();
                            lorentz.setText("");
                        }
                        else {
                            //if user enters lorentz value, compare the calculated value with user input
                            //if not same, change background, vibrate and show correct value
                            //if same, change background to green and show toast mesage
                            Double numSpeed = Double.parseDouble(String.valueOf(speedl.getText()));
                            Double lorentzFactor = (1 / (1 + Math.pow((numSpeed / C), 2)));
                            Double lorentzInput = Double.parseDouble(String.valueOf(lorentz.getText()));
                            if (Math.abs(lorentzInput - lorentzFactor) < 1e-9) {
                                bgColorChange(R.color.bgGreen);
                                Toast.makeText(getActivity(), "You are correct", Toast.LENGTH_SHORT).show();
                            } else {
                                //if not same, change background, vibrate and show correct value
                                final VibrationEffect vibrationEffect1;

                                // this is the only type of the vibration which requires system version Oreo (API 26)
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                                    // this effect creates the vibration of default amplitude for 1000ms(1 sec)
                                    vibrationEffect1 = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE);
                                    Log.d("Lorentz", "Vibrated");
                                    //Toast.makeText(MainActivity.this, "frst vibrate", Toast.LENGTH_SHORT).show();
                                    // it is safe to cancel other vibrations currently taking place
                                    vibrator.cancel();
                                    vibrator.vibrate(vibrationEffect1);
                                }

                                bgColorChange(R.color.bgRed);
                                StringBuilder alertText2 = new StringBuilder(50);
                                alertText2.append("The correct lorentz factor is: ");
                                alertText2.append(String.format("%.6f", (lorentzFactor)));
                                alertShow(alertText2);
                            }
                        }
                    }
                }
            }
        });

        Button b2 = (Button) root.findViewById(R.id.btnReset);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //on click of reset button, clear the text field
                EditText speedl2 = (EditText) root.findViewById(R.id.ptxtSpeed);
                EditText lorentz2 = (EditText) root.findViewById(R.id.ptxtLorentz);
                bgColorChange(R.color.bgColor);
                speedl2.setText("");
                lorentz2.setText("");
            }
        });
    }
    private void alertShow(StringBuilder alertText)
    {
        //FUnction takes alert message as input and show alert
        AlertDialog.Builder alertAnswer = new AlertDialog.Builder(getActivity());

        alertAnswer.setMessage(alertText)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                    }
                });
        AlertDialog createAlert = alertAnswer.create();
        createAlert.show();
    }
    private void bgColorChange(Integer clr)
    {
        //FUnction to change background color
        ScrollView layoutLorentz = root.findViewById(R.id.layoutLorentz);
        //ConstraintLayout lLorentz = root.findViewById(R.id.cLorentz);
        layoutLorentz.setBackgroundResource(clr);
        //lLorentz.setBackgroundResource(clr);
    }

    //To check if entered value is numeric
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }

    }
}
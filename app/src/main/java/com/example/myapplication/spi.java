package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link spi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class spi extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int interval = 1000;
    private ViewGroup root;

    public spi() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment spi.
     */
    // TODO: Rename and change types and number of parameters
    public static spi newInstance(String param1, String param2) {
        spi fragment = new spi();
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
        root = (ViewGroup) inflater.inflate(R.layout.fragment_spi, container,false);
        //Thread that calls every 1 second and calculate SPI value
        Handler handler = new Handler();
        Runnable runnable;
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, interval);
                spiCalculate();
            }
        };
        handler.postDelayed(runnable, interval);

        // Inflate the layout for this fragment
        return root; //inflater.inflate(R.layout.fragment_spi, container, false);
    }

    //called every 1 second
    private void spiCalculate()
    {
        //get current time
        Calendar cal = Calendar.getInstance();
        int hours = cal.get(Calendar.HOUR);
        int mins = cal.get(Calendar.MINUTE);
        int secs = cal.get(Calendar.SECOND);

        TextView tvHour = root.findViewById(R.id.txtHour);
        tvHour.setText(String.valueOf(hours)+"h :");
        TextView tvMin = root.findViewById(R.id.txtMin);
        tvMin.setText(String.valueOf(mins)+ "m :");
        TextView tvSec = root.findViewById(R.id.txtSec);
        tvSec.setText(String.valueOf(secs)+ "s");
        //Calculate SPI and show value
        Double doubleSPI = factorial(hours)/(Math.pow(mins,3)+secs);
        TextView tvSPI = root.findViewById(R.id.txtSPI);
        tvSPI.setText(String.format("%.6f",doubleSPI));
    }

    Integer factorial(int n)
    {
        if(n<=1)
        {
            return 1;
        }
        return n*factorial(n-1);
    }
}


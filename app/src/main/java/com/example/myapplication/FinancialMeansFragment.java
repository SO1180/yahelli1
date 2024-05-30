package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.logging.Level;

public class FinancialMeansFragment extends Fragment {

    private View view;

    ImageView iv0;
    ImageView iv1;
    ImageView iv2;
    ImageView iv3;

    ImageView selectednewPowerView;
    int selectedReloading = 0, selectedDamage = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_financial_means, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(5000);
                // Update UI on the UI thread
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Your UI update code here
                        openPowers();
                    }
                });
            }
        }).start();


        // relate to current powers
        iv0 = view.findViewById(R.id.ivPower0);
        iv1 = view.findViewById(R.id.ivPower1);
        iv2 = view.findViewById(R.id.ivPower2);
        iv3 = view.findViewById(R.id.ivPower3);

        setPowerListeners();


    }

    private void setPowerListeners() {
        iv0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Drawable d = iv0.getDrawable();
                iv0.setImageDrawable(selectednewPowerView.getDrawable());

                selectednewPowerView.setImageDrawable(d);

                // check powers and damage

                AppConstant.currPowers[0] = new Powers(selectedDamage, selectedReloading, ((BitmapDrawable) iv0.getDrawable()).getBitmap());
            }
        });

        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Drawable d = iv1.getDrawable();
                iv1.setImageDrawable(selectednewPowerView.getDrawable());

            }
        });

        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Drawable d = iv2.getDrawable();
                iv2.setImageDrawable(selectednewPowerView.getDrawable());

            }
        });


        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Drawable d = iv3.getDrawable();
                iv3.setImageDrawable(selectednewPowerView.getDrawable());

            }
        });


    }


    public void openPowers() // when ur razing a level
    {
        int level = 2;// get level
        int coins = 250; // get coins
        int numOfCoinsForThePower;
        if (level >= 2) {
            numOfCoinsForThePower = 700;
            TextView tvLock2 = view.findViewById(R.id.tvLock2);
            tvLock2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

            ImageView ivSelectLevel2 = view.findViewById(R.id.ivLevel2);
            ivSelectLevel2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Use the Builder class for convenient dialog construction.
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Do you want to buy this power?\n loading is: 164\n  damage is:8")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    if (coins < numOfCoinsForThePower) {
                                        Toast.makeText(getContext(), "You don't have enough coins for this power", Toast.LENGTH_SHORT).show();
                                    } else {
                                        coins = coins - numOfCoinsForThePower;
                                        tvLock2.setVisibility(View.INVISIBLE);

                                        AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                        builder2.setMessage("Do you want to replace this power?")
                                                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        selectednewPowerView = ivSelectLevel2;
                                                        selectedDamage = 164;
                                                        selectedReloading = 8;
                                                    }
                                                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        // User cancels the dialog.
                                                    }
                                                })
                                                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        // User cancels the dialog.

                                                    }
                                                });
                                        // Create the AlertDialog object and return it.

                                        AlertDialog adialog = builder.create();
                                        adialog.show();
                                    }

                                }
                            });
                }

            });


        }
    }
}
     //   Drawable lock = getResources().getDrawable(R.drawable.ic_baseline_lock_24);
       // lock.setImageDrawable(null);




package com.example.myapplication;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MyProfileFragment extends Fragment {

    private View view;
    //private ImageView imageView;
    private boolean fiveStars = false;

    public boolean isFiveStars() {
        return fiveStars;
    }

    private ImageView[] starViews = new ImageView[5];

    private void liseOfStarViews() {
        starViews[0] = view.findViewById(R.id.emptystar1);
        starViews[1] = view.findViewById(R.id.emptystar2);
        starViews[2] = view.findViewById(R.id.emptystar3);
        starViews[3] = view.findViewById(R.id.emptystar4);
        starViews[4] = view.findViewById(R.id.emptystar5);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        liseOfStarViews();
    }


    public void fillStar() // only when winning entrance here
    {
        Drawable emptyStar = getResources().getDrawable(R.drawable.emptystar);

        // check which are empty and which not.
        for (int i = 1; i <= 5; i++) {
            ImageView starView = starViews[i];
            if (starView.getDrawable().getConstantState().equals(emptyStar.getConstantState())) {
                // This star is empty, fill it
                starViews[i].setImageResource(R.drawable.filledstar);
                i = 6;
            }
            if(i == 5){
                fiveStars = true;
            }
        }
    }

    public void removeStar() // only when losing entrance here
    {
        Drawable filledstar = getResources().getDrawable(R.drawable.filledstar);

        // check which are empty and which not.
        for (int i = 5; i >= 1; i--) {
            ImageView starView = starViews[i];
            if (starView.getDrawable().getConstantState().equals(filledstar.getConstantState())) {
                // This star is full, remove it
                starViews[i].setImageResource(R.drawable.emptystar);
                i = 0;
            }
        }
    }

    public void changeLevel() // only when u have 5 full stars
    {
        TextView levelP = view.findViewById(R.id.myprofile_level);
        TextView levelF = view.findViewById(R.id.financialmeans_level);
        if(fiveStars)
        {
            for (int i = 1; i <= 5; i++) {
                if(levelP.getText().equals(i)){
                    levelP.setText(i + 1);
                    levelF.setText(i + 1);
                }
            }
        }
    }

    // TODO אולי יש פעולות שצריך לעשות שהם יהיו בהכי למעלה ואז הפעולות פה ירשו אותן
    //TODO בינתיים אני רק כותבת בכללי איזה פעולות צריכות להיות
    public void afterAGame(ImageView[] starViews) {

        TextView coinsP = view.findViewById(R.id.myprofile_coins);
        TextView coinsF = view.findViewById(R.id.financialmeans_coins);

        if(won)
            coinsP += 150;
            coinsF += 150;
            fillStar();
        if(draw)
            coinsP += 70;
            coinsF += 70;
        if(lost)
            removeStar();
    }



}
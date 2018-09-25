package com.j_productions.viewmodel;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.j_productions.viewmodel.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {

    // de viewmodel
    MainViewModel mainVM;

    //binding (vergeet databinding = true niet in gradle)
    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       mainVM = ViewModelProviders.of(this).get(MainViewModel.class);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setViewmodel(mainVM);

        //kleur navigatiebar
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    /**
     * score voor Team A met 1 verhogen.
     */
    public void addOneForTeamA(View v) {
        mainVM.addOneForTeamA();
        displayForTeamA(mainVM.scoreTeamA);
    }

    /**
     *  score voor Team B met 2 verhogen.
     */
    public void addTwoForTeamA(View v) {
        mainVM.addTwoForTeamA();
        displayForTeamA(mainVM.scoreTeamA);
    }

    /**
     *  score voor Team A met 3 verhogen.
     */
    public void addThreeForTeamA(View v) {
        mainVM.addThreeForTeamA();
        displayForTeamA(mainVM.scoreTeamA);
    }

    /**
     * score voor Team B met 1 verhogen.
     */
    public void addOneForTeamB(View v) {
        mainVM.addOneForTeamB();
        displayForTeamB(mainVM.scoreTeamA);
    }

    /**
     *  score voor Team B met 2 verhogen.
     */
    public void addTwoForTeamB(View v) {
        mainVM.addTwoForTeamB();
        displayForTeamB(mainVM.scoreTeamA);
    }

    /**
     *  score voor Team B met 3 verhogen.
     */
    public void addThreeForTeamB(View v) {
        mainVM.addThreeForTeamB();
        displayForTeamB(mainVM.scoreTeamA);
    }

    /**
     * Resets beide scores naar 0
     */
    public void resetScore(View v) {
        mainVM.resetScore();
        mainVM.resetScore();

        displayForTeamA(mainVM.scoreTeamA);
        displayForTeamB(mainVM.scoreTeamB);
    }

    /**
     * Toon score voor Team A.
     */
    public void displayForTeamA(int score) {
        TextView scoreView = findViewById(R.id.team_a_score);
        scoreView.setText(String.valueOf(mainVM.scoreTeamA));
    }

    /**
     * Toon score voor Team B.
     */
    public void displayForTeamB(int score) {
        TextView scoreView = findViewById(R.id.team_b_score);
        scoreView.setText(String.valueOf(mainVM.scoreTeamB));
    }
}


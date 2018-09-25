package com.j_productions.viewmodel;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    // score voor Team A
    public int scoreTeamA = 40;

    // score voor Team B
    public int scoreTeamB = 70;


    public void addOneForTeamA() {
        scoreTeamA = scoreTeamA + 1;
    }


    public void addTwoForTeamA() {
        scoreTeamA = scoreTeamA + 2;
    }


    public void addThreeForTeamA() {
        scoreTeamA = scoreTeamA + 3;
    }


    public void addOneForTeamB() {
        scoreTeamB = scoreTeamB + 1;
    }


    public void addTwoForTeamB() {
        scoreTeamB = scoreTeamB + 2;
    }


    public void addThreeForTeamB() {
        scoreTeamB = scoreTeamB + 3;
    }


    public void resetScore() {
        scoreTeamA = 0;
        scoreTeamB = 0;
    }
}

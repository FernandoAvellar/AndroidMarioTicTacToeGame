package com.example.connect3game;

import android.media.Image;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mp;

    //0:Bowser 1:Mario 2:Empty
    int activePlayer = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2}; //start all 9 slots with empty representation
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}}; //possibilities to win the game
    boolean gameActive = true;
    int numberOfAttempts = 0;

    public void newPlay(View view) {
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2) {
            numberOfAttempts++;
        }else if(gameState[tappedCounter] != 2 && numberOfAttempts == 9 && gameActive) {
            Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
            TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
            winnerTextView.setText("The game has no winner!");
            playAgainButton.setVisibility(View.VISIBLE);
            winnerTextView.setVisibility(View.VISIBLE);
        }

        if (gameState[tappedCounter] == 2 && gameActive) {
            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1500);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.mario);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.bowser);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1500).rotation(3600).setDuration(250);

            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                    // Someone has won!
                    gameActive = false;
                    String winner = activePlayer == 1 ? "Mario" : "Bowser";
                    Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
                    TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
                    winnerTextView.setText(winner + " has won!");
                    playAgainButton.setVisibility(View.VISIBLE);
                    winnerTextView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void playAgain(View view) {
        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
        playAgainButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for(int i=0; i<gridLayout.getChildCount(); i++) {
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }

        activePlayer = 0;
        gameState = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2};
        gameActive = true;
        numberOfAttempts = 0;
    }

    public void volumeControl(View view) {
        if(mp.isPlaying()) {
            mp.pause();
            ImageView volumeImageView = (ImageView) findViewById(R.id.volumeImageView);
            volumeImageView.setImageResource(R.drawable.outline_volume_off_black_36dp);
        }else {
            mp.start();
            ImageView volumeImageView = (ImageView) findViewById(R.id.volumeImageView);
            volumeImageView.setImageResource(R.drawable.outline_volume_up_black_36dp);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onStart(){
        super.onStart();
        mp = MediaPlayer.create(getApplicationContext(), R.raw.seu_tempo_ta_acabando);
        mp.setLooping(true);
        mp.start();
    }

    @Override
    public void onPause(){
        super.onPause();
        mp.stop();
    }

}

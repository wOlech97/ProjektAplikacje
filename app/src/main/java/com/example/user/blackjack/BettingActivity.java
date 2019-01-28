package com.example.user.blackjack;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class BettingActivity extends AppCompatActivity{
    Button mEnterButton;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.betting_main);

        mEnterButton = (Button)findViewById(R.id.deal);
        mEnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BettingActivity.this, MainActivity.class);
                if(GetterSetter.bet == 0){
                    Toast.makeText(BettingActivity.this, "Obstaw grę", Toast.LENGTH_SHORT).show();
                }
                else{
                    startActivity(intent);
                    playSound(R.raw.shuffle);
                }

            }
        });
    }

    private void playSound(int sound){
        if (mp != null){
            if (mp.isPlaying()||mp.isLooping()) {
                mp.stop();
            }
            mp.release();
            mp = null;
        }
        mp = MediaPlayer.create(this,sound);
        mp.start();
    }

    private void displayCashTotal(int cashTotal){
        TextView cashTotalTextView = (TextView)findViewById(R.id.cashTotal);
        cashTotalTextView.setText("Stan Konta:pln" + cashTotal);
    }

    private void displayBet(int betTotal){
        TextView betTotalTextView = (TextView)findViewById(R.id.betTotal);
        betTotalTextView.setText("w Grze:pln" + betTotal);
    }

    public void clearBet(View view){
        GetterSetter.cash = GetterSetter.cash + GetterSetter.bet;
        GetterSetter.bet = 0;
        displayCashTotal(GetterSetter.cash);
        displayBet(GetterSetter.bet);
    }

    public void plusOne(View view){
        if (GetterSetter.cash == 0){
            return;
        }
        GetterSetter.cash = GetterSetter.cash - 1;
        GetterSetter.bet = GetterSetter.bet + 1;
        displayCashTotal(GetterSetter.cash);
        displayBet(GetterSetter.bet);
        playSound(R.raw.chips_short);
    }

    public void plusFive(View view){
        if (GetterSetter.cash < 5){
            return;
        }
        GetterSetter.cash = GetterSetter.cash - 5;
        GetterSetter.bet = GetterSetter.bet + 5;
        displayCashTotal(GetterSetter.cash);
        displayBet(GetterSetter.bet);
        playSound(R.raw.chips_short);
    }

    public void plusTwentyFive(View view){
        if (GetterSetter.cash < 25){
            return;
        }
        GetterSetter.cash = GetterSetter.cash - 25;
        GetterSetter.bet = GetterSetter.bet + 25;
        displayCashTotal(GetterSetter.cash);
        displayBet(GetterSetter.bet);
        playSound(R.raw.chips_short);
    }

    public void plusFifty(View view){
        if (GetterSetter.cash < 50){
            return;
        }
        GetterSetter.cash = GetterSetter.cash - 50;
        GetterSetter.bet = GetterSetter.bet + 50;
        displayCashTotal(GetterSetter.cash);
        displayBet(GetterSetter.bet);
        playSound(R.raw.chips_short);
    }

    public void plusHundred(View view){
        if (GetterSetter.cash < 100){
            return;
        }
        GetterSetter.cash = GetterSetter.cash - 100;
        GetterSetter.bet = GetterSetter.bet + 100;
        displayCashTotal(GetterSetter.cash);
        displayBet(GetterSetter.bet);
        playSound(R.raw.chips_short);
    }


}

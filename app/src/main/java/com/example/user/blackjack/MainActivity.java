package com.example.user.blackjack;

import android.app.*;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    Button button;
    FragmentManager fragmentManager;
    MainActivityFragment fragment;
    TextView textView = null;
    MediaPlayer mp;
    AlertDialog.Builder sample;
    ImageView image;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.button2);
        fragmentManager = getSupportFragmentManager();
        fragment = (MainActivityFragment)fragmentManager.findFragmentById(R.id.fragment);
        textView = fragment.textviewPlayer;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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


    public void clickMethodHit(View view){ // Hit button
        if(GetterSetter.isStanding == false){
            GetterSetter.playerScore  = 0;
            GetterSetter.dealerScore = 0;
            GetterSetter.hit++;
            GetterSetter.bottunPressed = 1;
//            playSound(R.raw.dealing_card);
        }

    }
    public void clickMethodStand(View view){
        GetterSetter.playerScore = 0;
        GetterSetter.dealerScore = 0;
        GetterSetter.dealerHit = GetterSetter.hit;
        GetterSetter.bottunPressed = 1;
        GetterSetter.isStanding = true;
//        playSound(R.raw.dealing_card);
    }

    public void clickMethodRedeal(View view){ // redeal button
        if (GetterSetter.playerScore > 21){
            GetterSetter.cash = GetterSetter.cash - GetterSetter.bet;
        }
        else if(GetterSetter.dealerScore > 21){
            GetterSetter.cash = GetterSetter.cash + (GetterSetter.bet * 2);
        }
        else if(GetterSetter.playerScore > GetterSetter.dealerScore && GetterSetter.playerScore< 21){
            // Player win!!
            GetterSetter.cash = GetterSetter.cash + (GetterSetter.bet * 2);

        }
        else{
            // Dealer win! take betting amount
            GetterSetter.cash = GetterSetter.cash - GetterSetter.bet;

        }
        GetterSetter.playerScore = 0;
        GetterSetter.dealerScore = 0;
        GetterSetter.hit = 3;
        GetterSetter.dealerHit = 1;
        GetterSetter.bottunPressed = 1;
        GetterSetter.isStanding = false;
        GetterSetter.isDouble = 0;
        GetterSetter.playerBust = 0;
        GetterSetter.playerBlackjack = 0;
        fragment.shuffleDeck(GetterSetter.card);
        playSound(R.raw.dealing_card);
        GetterSetter.horizontalMove = 0;
        GetterSetter.verticalMove = 400;
        GetterSetter.iswin = 0;
        GetterSetter.islose = 0;
    }

    public void clickMethodDouble(View view){
        if(GetterSetter.isDouble == 1){
            Toast.makeText(MainActivity.this, "Nie możesz podwoić dwa razy", Toast.LENGTH_SHORT).show();

        }
        else{
            GetterSetter.playerScore  = 0;
            GetterSetter.dealerScore = 0;
            GetterSetter.hit++;
            GetterSetter.bottunPressed = 1;
            GetterSetter.isDouble = 1;
            GetterSetter.bet = GetterSetter.bet * 2;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}

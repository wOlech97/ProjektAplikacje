package com.example.user.blackjack;

import android.app.AlertDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import java.util.Random;

import static com.example.user.blackjack.R.layout.black_jack_dialog;
import static com.example.user.blackjack.R.layout.bust_dialog;



public class MainActivityFragment extends Fragment {
    Button button;
    Card[] deck;
    int n = 0;
    View rootView;
    TextView textviewPlayer;
    static TextView textviewDealer;
    TextView textviewCash;
    TextView textviewBet;
    Handler mHandler;
    MediaPlayer mp;
    AlertDialog.Builder bustMessage;
    AlertDialog.Builder blackJackMessage;
    ImageView image;

    public MainActivityFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.fragment_main,container, false); //??

        textviewPlayer = (TextView) rootView.findViewById(R.id.playerScore);
        textviewPlayer.setTextColor(Color.WHITE);
        textviewPlayer.setTextSize(20);

        textviewDealer = (TextView) rootView.findViewById(R.id.dealerScore);
        textviewDealer.setTextColor(Color.WHITE);
        textviewDealer.setTextSize(20);

        textviewCash = (TextView) rootView.findViewById(R.id.cash);
        textviewCash.setTextColor(Color.WHITE);
        textviewCash.setTextSize(20);

        textviewBet = (TextView) rootView.findViewById(R.id.bet);
        textviewBet.setTextColor(Color.WHITE);
        textviewBet.setTextSize(20);

//        rootView.setBackgroundResource(R.drawable.casino_table);
//
//        rootView.setBackgroundColor(Color.GREEN);

        deck = new Card[52];
        for(int suit = 0; suit < 4; suit++){
            for (int rank = 0; rank < 13; rank++ ){
                deck[n] = new Card(suit,rank);
                n++;
            }
        }
        GetterSetter.card = deck;
        deck = shuffleDeck(deck);

        mHandler = new Handler();
        mHandler.post(mUpdate); //??
        return rootView;
    }

    private void playSound(int sound){
        if (mp != null){
            if (mp.isPlaying()||mp.isLooping()) {
                mp.stop();
            }
            mp.release();
            mp = null;
        }
        mp = MediaPlayer.create(getContext(),sound);
        mp.start();
    }

    public void bustDialog(){
        bustMessage = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogLayout;
        dialogLayout = inflater.inflate(bust_dialog,null);
        bustMessage.setView(dialogLayout);
        bustMessage.setMessage("Spalony");
        playSound(R.raw.oh_loud);

        final AlertDialog alert = bustMessage.create();
        alert.show();
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                alert.dismiss();
            }
        }.start();
    }

    public void blackJackDialog(){
        blackJackMessage = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogLayout = inflater.inflate(black_jack_dialog,null);
        blackJackMessage.setView(dialogLayout);
        blackJackMessage.setMessage("Black Jack!");
        playSound(R.raw.congratulations);

        final AlertDialog alert = blackJackMessage.create();
        alert.show();
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                alert.dismiss();
            }
        }.start();
    }

    private Runnable mUpdate = new Runnable() {
        @Override
        public void run() {
            if (GetterSetter.playerScore == 21){
                /// Black Jack
                textviewPlayer.setText("Gracz: " + GetterSetter.playerScore + " ");
                textviewDealer.setText("Diler: " + GetterSetter.dealerScore + " ");
                textviewCash.setText("Kasa: " + (GetterSetter.cash) + " ");
                textviewBet.setText("Zakład: " + GetterSetter.bet +  " ");
                GetterSetter.isBlackJack = true;
                GetterSetter.isStanding = true;
                if(GetterSetter.playerBlackjack == 0){
                    GetterSetter.playerBlackjack = 1;
                }
                judgeWin();
            }

            else if(GetterSetter.playerScore < 21){
                textviewPlayer.setText("Gracz: " + GetterSetter.playerScore + " ");
                textviewDealer.setText("Diler: " + GetterSetter.dealerScore + " ");
                textviewCash.setText("Kasa: " + (GetterSetter.cash) + " ");
                textviewBet.setText("Zakład: " + GetterSetter.bet + " ");
            }
            else{
                textviewPlayer.setText("Spalony");
                if(GetterSetter.playerBust ==0){
                    GetterSetter.playerBust = 1;
                }
                judgeWin();
            }
            if(GetterSetter.bottunPressed == 0){
                if(GetterSetter.dealerHit > 1){
                    if(GetterSetter.dealerScore < 17 && GetterSetter.dealerScore != 0){
                        GetterSetter.playerScore = 0;
                        GetterSetter.dealerScore = 0;
                        GetterSetter.dealerHit++;
                        GetterSetter.bottunPressed = 1;
                    }
                    else{
                        judgeWin();
                    }
                }
            }

            if(GetterSetter.playerBlackjack == 1){
                blackJackDialog();
                GetterSetter.playerBlackjack = 2;
            }
            if(GetterSetter.playerBust == 1){
                bustDialog();
                judgeWin();
                GetterSetter.playerBust = 2;
            }
            if(GetterSetter.playerBust > 1){
                textviewDealer.setText("Diler: " + GetterSetter.dealerScore + " ");
            }

            mHandler.postDelayed(this,1);
        }
    };

    public void judgeWin(){
        if (GetterSetter.playerScore > 21){

        }
        else if(GetterSetter.dealerScore > 21){
            textviewDealer.setText("Spalony");
            if (GetterSetter.iswin == 0){
                GetterSetter.iswin = 1;
            }
        }
        else if(GetterSetter.playerScore > GetterSetter.dealerScore){
            if (GetterSetter.iswin == 0){
                GetterSetter.iswin = 1;
            }

        }
        else if(GetterSetter.playerScore < GetterSetter.dealerScore){
            if (GetterSetter.islose == 0){
                GetterSetter.islose = 1;
            }

        }
        else if (GetterSetter.playerScore == GetterSetter.dealerScore){
            textviewPlayer.setText("Push!");
            textviewDealer.setText("Push!");
        }
        else{

        }

        if(GetterSetter.iswin == 1){
            Context context = getContext();
            CharSequence text = "Wygrałeś!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            GetterSetter.iswin = 2;
        }
        if(GetterSetter.islose == 1){
            Context context = getContext();
            CharSequence text = "Przegrałeś";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            GetterSetter.islose = 2;
        }
    }

    public Card[] shuffleDeck(Card[] deck){
        Random random = new Random();
        Card cardHolder = new Card(0,0);
        for (int n = 0; n <52; n++){
            int randomIndex = random.nextInt(52);
            cardHolder = deck[randomIndex];
            deck[randomIndex] = deck[n];
            deck[n] = cardHolder;

        }
        return deck;
    }




}

package com.example.user.blackjack;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView; // view that handles animation


public class Panel extends SurfaceView implements SurfaceHolder.Callback {

    Paint paint;
    private CanvasThread canvasthread; // this holds bitmap object
    CardDraw cardDraw;
    int localScore;
    Bitmap mScaledBitmap;


    public Panel(Context context, AttributeSet attrs){
        super(context, attrs); //??
        getHolder().addCallback(this);
        canvasthread = new CanvasThread(getHolder(),this);
        setFocusable(true); // enable view's focus event
        paint = new Paint();
        cardDraw = new CardDraw(context);

    }

    public Panel(Context context){
        super(context);
        getHolder().addCallback(this);
        canvasthread = new CanvasThread(getHolder(), this);
        setFocusable(true);
    }

    protected  void init(){
        Resources res = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.casino_table_small);
        int h = 1200;
        int w = 1000;
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, w, h, true);
        mScaledBitmap = scaled;
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawBitmap(mScaledBitmap, 0, 0, paint);
//        canvas.drawColor(Color.GREEN);
            for (int x = 0; x <= 1; x++){
               if (x == 0 && GetterSetter.dealerHit < 3){
                   cardDraw.deal(canvas,501, (80 * x), -200);
               }
                else{
//                   cardDraw.deal(canvas,x, (80 * x), -200);
                   if(GetterSetter.horizontalMove < 81){
                       GetterSetter.horizontalMove = GetterSetter.horizontalMove + 4;
                       GetterSetter.verticalMove = GetterSetter.verticalMove - 20;
                   }
                   if(GetterSetter.verticalMove >= -200){
                       GetterSetter.verticalMove = -200;
                   }
                   cardDraw.deal(canvas,x, (GetterSetter.horizontalMove * x), GetterSetter.verticalMove);
               }
                if (GetterSetter.bottunPressed == 1){
                    addScore(x,false,true);
                }
            }
            for (int n = 2; n <= 3; n++){
//                cardDraw.deal(canvas,n, (80 * n), 250);
                if(GetterSetter.horizontalMove < 81){
                    GetterSetter.horizontalMove = GetterSetter.horizontalMove + 4;
                    GetterSetter.verticalMove = GetterSetter.verticalMove - 20;
                }
                if(GetterSetter.verticalMove <= 250){
                    GetterSetter.verticalMove = 250;
                }
                cardDraw.deal(canvas,n, (GetterSetter.horizontalMove * n), GetterSetter.verticalMove);

                if (GetterSetter.bottunPressed == 1){
                    addScore(n,true,false);
                }
            }

            for (int n = 4; n <=GetterSetter.hit; n++ ){
                cardDraw.deal(canvas,n, (80 * n), 250);

                if (GetterSetter.bottunPressed == 1){
                    addScore(n, true, false);
                }

            }

            for (int x = (GetterSetter.hit + 1); x <= GetterSetter.dealerHit; x++){ /// when hit stand button,
                cardDraw.deal(canvas,x, (80 * x), -200);

                if (GetterSetter.bottunPressed == 1){
                    addScore(x,false,true);
                }
            }

            if (GetterSetter.playerBust == 1) {
                for (int x = 0; x <= 1; x++) {
                    cardDraw.deal(canvas, x, (80 * x), -200);
                    addScore(x, false, true);
                }
            }
            GetterSetter.bottunPressed = 0;
    }

    public void addScore(int n, boolean player,boolean dealer){
        if(n == 0 && GetterSetter.dealerHit < 3) { // when player has not clicked stand
            localScore = 0;
        }else{
            if(GetterSetter.card[n].rank >= 8 && GetterSetter.card[n].rank < 12){ /// if rank is 10, jack, queen and king, then give 10 as score
                localScore = 10;
            }
            if(GetterSetter.card[n].rank == 12){
                if(player){
                    if(GetterSetter.playerScore > 10){
                        localScore = 1;
                    }
                    else{
                        localScore = 11;
                    }
                }
                if(dealer){
                    if(GetterSetter.dealerScore > 10){
                        localScore = 1;
                    }
                    else{
                        localScore = 11;
                    }
                }

            }
            if (GetterSetter.card[n].rank < 8){ // less than 10
                localScore = GetterSetter.card[n].rank + 2; // I think I can refactor this later
            }

            if (player){
                GetterSetter.playerScore  = GetterSetter.playerScore + localScore;
            }
            if (dealer){
                GetterSetter.dealerScore  = GetterSetter.dealerScore + localScore;
            }
        }

    }

    public void update(){

    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    public void surfaceCreated(SurfaceHolder holder){
        canvasthread.setRunning(true); // as long as true, this redraws canvasthread forever
        canvasthread.start();
    }

    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        canvasthread.setRunning(false); // this will stop redrawing
        while(retry){
            try { // try blocks to wrap things that may fail to execute properly
                canvasthread.join();//??
                retry = false;
            }
            catch(InterruptedException exception){

            }
        }

    }


}

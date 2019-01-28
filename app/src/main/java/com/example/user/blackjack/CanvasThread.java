package com.example.user.blackjack;

import android.graphics.Canvas;
import android.view.SurfaceHolder;


public class CanvasThread extends Thread {
    private SurfaceHolder _surfaceHolder;
    private Panel _panel;
    private boolean running = false;

    public CanvasThread(SurfaceHolder surfaceHolder, Panel panel){
        _surfaceHolder = surfaceHolder;
        _panel = panel;
    }

    public void setRunning(boolean run)
    {running = run;}

    @Override
    public void run(){
        Canvas canvas;
        _panel.init();
        while(running){
            canvas = null; //??
            try{
//                sleep(30);
                canvas = _surfaceHolder.lockCanvas(null);
                synchronized (_surfaceHolder){
                    _panel.onDraw(canvas);
                }
//            }
//            catch(InterruptedException ex){
//                ex.printStackTrace();
            }
            finally{// finally code will always run
                if (canvas != null){
                    _surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

        }
    }
}


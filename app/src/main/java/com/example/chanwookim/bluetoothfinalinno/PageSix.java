package com.example.chanwookim.bluetoothfinalinno;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import pl.droidsonroids.gif.GifImageButton;


public class PageSix extends AppCompatActivity {



    //UIs


    //Service stuff
    BluetoothService mService;
    boolean mBound = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.page_six);

        init();



    }

    private void init() {

        //UI Stuff
        final GifImageButton gib = (GifImageButton)findViewById(R.id.shareButton);


        final Animation fadeout = new AlphaAnimation(1.f, 1.f);
        fadeout.setDuration(1250); // You can modify the duration here
        fadeout.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation animation) {
                gib.setBackgroundResource(R.drawable.sharegif);//your gif file
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                gib.startAnimation(fadeout);

            }
        });
        gib.startAnimation(fadeout);

    }

    //service stuff
    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, BluetoothService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

    }

    //service stuff
    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    //service Stuff
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            BluetoothService.LocalBinder binder = (BluetoothService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }

    };


    public void shareClick (View view) {


        if(mBound){
            mService.sendShareSignal();
        }

        Intent myIntent5 = new Intent(this, screwAround.class);
        startActivity(myIntent5);



    }

    public void onBackPressed(){


        if(mBound){
            mService.notShare();
        }

        Intent myIntent5 = new Intent(this, PageFive.class);
        startActivity(myIntent5);
        setContentView(R.layout.page_five);

    }












}







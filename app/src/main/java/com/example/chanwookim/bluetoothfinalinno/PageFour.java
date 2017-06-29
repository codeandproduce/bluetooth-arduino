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
import android.widget.Button;

import pl.droidsonroids.gif.GifImageButton;


public class PageFour extends AppCompatActivity {



    //UIs
    Button tagButton;

    //Service stuff
    BluetoothService mService;
    boolean mBound = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.page_four);

        init();

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

    private void init() {

        //UI Stuff
        final GifImageButton gib = (GifImageButton)findViewById(R.id.tagButton);


        final Animation fadeout = new AlphaAnimation(1.f, 1.f);
        fadeout.setDuration(1250); // You can modify the duration here
        fadeout.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation animation) {
                gib.setBackgroundResource(R.drawable.taggif);//your gif file
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



    public void tagClick (View view) {

        if(mBound){
            mService.sendTagSignal();
        }


        Intent myIntent3 = new Intent(view.getContext(), PageFive.class);
        startActivity(myIntent3);

    }

    public void onBackPressed(){

        if(mBound){
            mService.notTag();
        }


        Intent myIntent3 = new Intent(this, PageThree.class);
        startActivity(myIntent3);
        setContentView(R.layout.page_three);

    }












}







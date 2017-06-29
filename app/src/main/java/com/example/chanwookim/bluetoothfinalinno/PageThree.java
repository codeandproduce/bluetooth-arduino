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

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageButton;


public class PageThree extends AppCompatActivity {



    //UIs
    GifImageButton gib;
    GifDrawable GD;




    //Service stuff
    BluetoothService mService;
    boolean mBound = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.page_three);

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
        gib = (GifImageButton)findViewById(R.id.commentButton);


        final Animation fadeout = new AlphaAnimation(1.f, 1.f);
        fadeout.setDuration(1250); // You can modify the duration here
        fadeout.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation animation) {
                gib.setBackgroundResource(R.drawable.ocommentgif);//your gif file
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





    public void commentClick (View view) {


        if(mBound){
            mService.sendCommentSignal();
        }

        Intent myIntent2 = new Intent(view.getContext(), PageFour.class);
        startActivity(myIntent2);

    }

    public void onBackPressed(){

        if(mBound){
            mService.notComment();
        }


        Intent myIntent2 = new Intent(this, PageTwo.class);
        startActivity(myIntent2);
        setContentView(R.layout.page_two);


    }











}







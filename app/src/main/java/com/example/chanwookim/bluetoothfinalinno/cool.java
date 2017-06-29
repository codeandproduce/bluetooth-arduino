package com.example.chanwookim.bluetoothfinalinno;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import pl.droidsonroids.gif.GifImageView;


public class cool extends AppCompatActivity {



    //UIs
    Button likeButton;

    //Service stuff
    BluetoothService mService;
    boolean mBound = false;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.cool_layout);

        init();
        setUpUI(true);



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
        final GifImageView gib = (GifImageView) findViewById(R.id.dancingAndroid);


    }



    public void leftClick(View view){


        if(mBound){
            mService.moveLeft();
        }



    }

    public void rightClick(View view){


        if(mBound){
            mService.moveRight();
        }




    }

    private void setUpUI(boolean bool){



    }









}







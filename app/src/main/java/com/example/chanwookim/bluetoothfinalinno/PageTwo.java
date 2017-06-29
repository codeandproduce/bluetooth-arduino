package com.example.chanwookim.bluetoothfinalinno;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class PageTwo extends AppCompatActivity {




    //Service stuff
    BluetoothService mService;
    boolean mBound = false;


    //UIs





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_two);

        init();

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, BluetoothService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }


    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }


    private void init() {

        //UI Stuff


    }


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


    public void follow (View view) {


        if(mBound){
            mService.sendFollowSignal();
        }


        Intent myIntent = new Intent(PageTwo.this, PageThree.class);
        startActivity(myIntent);



    }

    public void onBackPressed(){

        if(mBound){
            mService.notFollow();
        }


        Intent myIntent = new Intent(PageTwo.this, MainActivity.class);
        startActivity(myIntent);



    }











}







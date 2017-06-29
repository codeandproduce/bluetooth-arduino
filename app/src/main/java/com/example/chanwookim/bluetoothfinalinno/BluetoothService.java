package com.example.chanwookim.bluetoothfinalinno;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import static android.content.ContentValues.TAG;


public class BluetoothService extends Service {

    //BluetoothStuff
    BluetoothDevice mDevice;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;

    private final IBinder mBinder = new LocalBinder();




    @Override
    public void onCreate() {
        super.onCreate();

        //Bluetooth Stuff
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            Log.d(TAG, "mBluetoothAdapter == null ");
        }

        /*
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
            mBluetoothAdapter.enable();

        }
        */
        

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                mDevice = device;
            }
        }


        //running Bluetooth
        ConnectThread mConnectThread = new ConnectThread(mDevice);
        mConnectThread.start();
    }




    public class LocalBinder extends Binder {
        BluetoothService getService() {
            // Return this instance of LocalService so clients can call public methods
            return BluetoothService.this;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    public void sendLikeSignal() {
        ConnectedThread sendData = new ConnectedThread(mmSocket);
        sendData.write(like.getBytes());
    }

    public void sendFollowSignal() {
        ConnectedThread sendData = new ConnectedThread(mmSocket);
        sendData.write(follow.getBytes());
    }

    public void sendCommentSignal() {
        ConnectedThread sendData = new ConnectedThread(mmSocket);
        sendData.write(comment.getBytes());
    }
    public void sendTagSignal() {
        ConnectedThread sendData = new ConnectedThread(mmSocket);
        sendData.write(tag.getBytes());
    }
    public void sendPostSignal() {
        ConnectedThread sendData = new ConnectedThread(mmSocket);
        sendData.write(post.getBytes());
    }

    public void sendShareSignal() {
        ConnectedThread sendData = new ConnectedThread(mmSocket);
        sendData.write(share.getBytes());
    }

    public void sendGoBackSignal() {
        ConnectedThread sendData = new ConnectedThread(mmSocket);
        sendData.write(goBack.getBytes());
    }

    public void notLike() {
        ConnectedThread sendData = new ConnectedThread(mmSocket);
        sendData.write(notLike.getBytes());
    }

    public void sendScrewAroundSignal(){
        ConnectedThread sendData = new ConnectedThread(mmSocket);
        sendData.write(screwAround.getBytes());
    }

    public void notFollow() {
        ConnectedThread sendData = new ConnectedThread(mmSocket);
        sendData.write(notFollow.getBytes());
    }

    public void notComment() {
        ConnectedThread sendData = new ConnectedThread(mmSocket);
        sendData.write(notComment.getBytes());
    }

    public void notShare() {
        ConnectedThread sendData = new ConnectedThread(mmSocket);
        sendData.write(notShare.getBytes());
    }

    public void notScrewAround() {
        ConnectedThread sendData = new ConnectedThread(mmSocket);
        sendData.write(notScrewAround.getBytes());
    }

    public void notTag() {
        ConnectedThread sendData = new ConnectedThread(mmSocket);
        sendData.write(notTag.getBytes());
    }

    public void notPost() {
        ConnectedThread sendData = new ConnectedThread(mmSocket);
        sendData.write(notPost.getBytes());
    }

    public void moveLeft(){
        ConnectedThread sendData = new ConnectedThread(mmSocket);
        sendData.write(moveLeft.getBytes());
    }

    public void moveRight(){
        ConnectedThread sendData = new ConnectedThread(mmSocket);
        sendData.write(moveRight.getBytes());
    }
    //Signals
    String like = "l";
    String post = "p";
    String follow = "f";
    String comment = "c";
    String share = "s";
    String tag = "t";
    String goBack = "z";
    String screwAround = "k";

    String notLike = "a";
    String notFollow = "b";
    String notComment = "d";
    String notShare = "e";
    String notScrewAround = "y";
    String notTag = "g";
    String notPost = "h";

    String moveRight = "0";
    String moveLeft = "1";





    //BLUETOOTH STUFF

    private class ConnectThread extends Thread {

        private final BluetoothDevice mmDevice;
        private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        public ConnectThread(BluetoothDevice device) {
            BluetoothSocket tmp = null;
            mmDevice = device;
            try {
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mmSocket = tmp;
        }

        public void run() {
            mBluetoothAdapter.cancelDiscovery();
            try {
                mmSocket.connect();
            } catch (IOException connectException) {
                Log.d("A", "connect Thread did not work");
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                    closeException.printStackTrace();
                }
            }

            Log.d("A", "connect Thread worked");


            ConnectedThread mConnectedThread = new ConnectedThread(mmSocket);
            mConnectedThread.start();
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("A", "couldnt get stream");

            }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
                Log.d("A", "it wrote!");

            } catch (IOException e) {
                e.printStackTrace();
                Log.d("A", "it didnt send !");

            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.d(TAG, "cancel happened");
            }
        }




    }












}

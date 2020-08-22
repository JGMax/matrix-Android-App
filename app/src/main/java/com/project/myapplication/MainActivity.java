package com.project.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String PREF_IP = "PREF_IP_ADDRESS";
    public final static String PREF_PORT = "PREF_PORT_NUMBER";
    AnimationDrawable clockAnimation;

    private final static int TIME_REQUEST_CODE = 1;
    // объявление кнопок и текстовых полей ввода
    private Button timeButton,fillButton,bounceButton,burstButton;
    private Button lightsButton,cylonButton,madnessButton,plasmaButton;
    private Button fireButton, rainbowButton,snowButton,sparklesButton;
    private Button starfallButton, ballsButton,messageButton,snakeButton;

    public static String message = "C";
    public static String[] messageArguments;
    public final static int maxMessageSize = 23;
    public final static int sendDelay = 10;

    // получить ip адрес
    public final static String ipAddress = "192.168.1.129"; //= editTextIPAddress.getText().toString().trim();
    // получить номер порта
    public final static int portNumber = 10; //= editTextPortNumber.getText().toString().trim();

    public Button autoBrightButton;
    public SeekBar bright;
    public static int brightness;

    public static boolean isAutoBright = true;
    public static boolean isVisible = false;

    Context cont;

    // общие объекты параметров, используемые для сохранения IP адреса и порта, чтобы
    // пользователь не вводил их в следующий раз, когда он открывает приложение
    public static SharedPreferences.Editor editor;
    public static SharedPreferences sharedPreferences;

    final Handler messageHandler = new Handler();
    Runnable messageRunnable = new Runnable(){
        @Override
        public void run(){
            message = "C";
            // Текущее время
            Date currentDate = new Date();
            // Форматирование времени как "день+месяц+год"
            DateFormat dateFormat = new SimpleDateFormat("dd+MM+yyyy", Locale.getDefault());
            String dateText = dateFormat.format(currentDate);
            // Форматирование времени как "часы+минуты+секунды"
            DateFormat timeFormat = new SimpleDateFormat("HH+mm+ss", Locale.getDefault());
            String timeText = timeFormat.format(currentDate);
            messageArguments[2] = dateText;
            messageArguments[3] = timeText;
            for(int i = 0; i < maxMessageSize; i++)
                message += "+" + messageArguments[i];

            messageHandler.postDelayed(this, 1);
        }
    };

    final Handler sendHandler = new Handler();
    Runnable sendRunnable = new Runnable() {
        @Override
        public void run() {
            sendMessage(message);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cont = this;

        setContentView(R.layout.content_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        messageArguments = new String[maxMessageSize];

        messageHandler.postDelayed(messageRunnable, 0);

        sharedPreferences = getSharedPreferences("HTTP_HELPER_PREFS",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        autoBrightButton = findViewById(R.id.autoBrightButton);
        isAutoBright = sharedPreferences.getBoolean("isAutoBright", true);
        if(isAutoBright) {
            autoBrightButton.setBackground(this.getDrawable(R.drawable.auto_bright));
            messageArguments[0] = "1";
        }
        else {
            autoBrightButton.setBackground(this.getDrawable(R.drawable.sun_bright));
            messageArguments[0] = "0";
        }

        bright = findViewById(R.id.bright);
        brightness = sharedPreferences.getInt("brightness", 255);
        messageArguments[1] = Integer.toString(brightness);
        messageArguments[4] = "0";
        bright.setProgress(brightness);
        bright.setVisibility(View.GONE);
        bright.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                editor.putInt("brightness", i);
                editor.apply();
                brightness = i;

                messageArguments[1] = Integer.toString(i);
                sendHandler.post(sendRunnable);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sendHandler.postDelayed(sendRunnable, sendDelay);
            }
        });

        sendHandler.postDelayed(sendRunnable, sendDelay);

        timeButton = findViewById(R.id.timeButton);
        fillButton = findViewById(R.id.fillButton);
        bounceButton = findViewById(R.id.bounceButton);
        burstButton = findViewById(R.id.burstButton);

        lightsButton = findViewById(R.id.lightsButton);
        cylonButton = findViewById(R.id.cylonButton);
        madnessButton = findViewById(R.id.madnessButton);
        plasmaButton = findViewById(R.id.plasmaButton);

        fireButton = findViewById(R.id.fireButton);
        rainbowButton = findViewById(R.id.rainbowButton);
        snowButton = findViewById(R.id.snowButton);
        sparklesButton = findViewById(R.id.sparklesButton);

        starfallButton = findViewById(R.id.starfallButton);
        ballsButton = findViewById(R.id.ballsButton);
        messageButton = findViewById(R.id.messageButton);
        snakeButton = findViewById(R.id.snakeButton);

        // назначить слушателя кнопок (этот класс)
        autoBrightButton.setOnClickListener(this);

        autoBrightButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                isAutoBright = !isAutoBright;
                if(isAutoBright) {
                    autoBrightButton.setBackground(cont.getDrawable(R.drawable.auto_bright));
                    messageArguments[0] = "1";
                }
                else {
                    autoBrightButton.setBackground(cont.getDrawable(R.drawable.sun_bright));
                    messageArguments[0] = "0";
                }
                editor.putBoolean("isAutoBright", isAutoBright);
                editor.apply();

                //sendMessage(message);
                sendHandler.postDelayed(sendRunnable, sendDelay);

                return isVisible;
            }
        });

        timeButton.setOnClickListener(this);
        fillButton.setOnClickListener(this);
        bounceButton.setOnClickListener(this);
        burstButton.setOnClickListener(this);

        lightsButton.setOnClickListener(this);
        cylonButton.setOnClickListener(this);
        madnessButton.setOnClickListener(this);
        plasmaButton.setOnClickListener(this);

        fireButton.setOnClickListener(this);
        rainbowButton.setOnClickListener(this);
        snowButton.setOnClickListener(this);
        sparklesButton.setOnClickListener(this);

        starfallButton.setOnClickListener(this);
        ballsButton.setOnClickListener(this);
        messageButton.setOnClickListener(this);
        snakeButton.setOnClickListener(this);

        clockAnimation = (AnimationDrawable)timeButton.getBackground();
        clockAnimation.start();

        // получить IP адрес и номер порта из последнего раза, когда пользователь использовал
        // приложение, или поместить пустую строку "", если это первый раз
        /*editTextIPAddress.setText(sharedPreferences.getString(PREF_IP,""));
        editTextPortNumber.setText(sharedPreferences.getString(PREF_PORT,""));*/
    }


    @Override
    public void onClick(View view) {
        //new sendMessageTask().execute("Fuck");


        // сохранить IP адрес и номер порта для следующего использования приложения
        editor.putString(PREF_IP, ipAddress);    // установить значение ip адреса для сохранения
        editor.putInt(PREF_PORT, portNumber); // установить номер порта для сохранения
        editor.commit(); // сохранить IP и PORT

        // получить номер порта от кнопки, которая была нажата
        switch (view.getId()) {
            case R.id.timeButton:
                Intent intent = new Intent(MainActivity.this, ClockActivity.class);
                startActivityForResult(intent, TIME_REQUEST_CODE);
                break;
            case R.id.autoBrightButton:
                if (isVisible) {
                    bright.setVisibility(View.GONE);
                } else {
                    bright.setVisibility(View.VISIBLE);
                }
                isVisible = !isVisible;
                break;
            default:
                messageArguments[4] = "0";
        }

        //sendMessage(message);

        sendHandler.postDelayed(sendRunnable, sendDelay);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            bright.setProgress(brightness);
            messageArguments[4] = "0";
            if(isAutoBright) {
                autoBrightButton.setBackground(this.getDrawable(R.drawable.auto_bright));
                messageArguments[0] = "1";
            }
            else {
                autoBrightButton.setBackground(this.getDrawable(R.drawable.sun_bright));
                messageArguments[0] = "0";
            }

            sendHandler.postDelayed(sendRunnable, sendDelay);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void runUdpClient(String msg)  {

        try{
            byte[] msgBytes = (msg.getBytes());

            InetAddress ip = InetAddress.getByName(ipAddress);

            //SEND ON PORT 5000
            DatagramSocket socket = new DatagramSocket(5000);
            socket.setBroadcast(true);

            DatagramPacket packet =  new DatagramPacket(msgBytes,msgBytes.length, ip, portNumber);

            //packet.setAddress(ip);
            //packet.setPort(5000);

            socket.send(packet);
            socket.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sendMessage(final String message){

        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    runUdpClient(message);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
}



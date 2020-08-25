package com.project.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import static com.project.myapplication.MainActivity.isAutoBright;
import static com.project.myapplication.MainActivity.messageArguments;

public class ClockActivity extends AppCompatActivity {
    private Button autoBrightButton;
    private SeekBar bright;

    /*private SeekBar slideBar;
    private int slide = 1;

    private ImageView timeColor;
    private ImageView timeTarget;
    private TextView timeText;
    private int timeColorRGB;

    private ImageView secondsColor;
    private ImageView secondsTarget;
    private TextView secondsText;
    private int secondsColorRGB;

    private ImageView dateColor;
    private ImageView dateTarget;
    private TextView dateText;
    private int dateColorRGB;

    private float timeX = 0;
    private float timeY = 0;
    private float timeVector = 0;

    private float secondsX = 0;
    private float secondsY = 0;
    private float secondsVector = 0;

    private float dateX = 0;
    private float dateY = 0;
    private float dateVector = 0;

    private Button isEasterEgg;
    private boolean isEggActive;

    private Button alarmsButton;
    private boolean isAlarmShow = false;
    private LinearLayout topLayout;
    private LinearLayout middleLayout;
    private LinearLayout alarmsLayout;

    private int alarmsNumber = 0;
    private ListView alarmsView;

    private SeekBar sunriseBar;
    public static int sunriseTime = 30;
    private TextView sunriseText;

    private Button addAlarmButton;
    List<String> alarmsTime = new ArrayList<>();
    List<Boolean> alarmActive = new ArrayList<>();
    List<Integer> alarmMinutes = new ArrayList<>();
    public static List<Integer> alarmMinutesActivated = new ArrayList<>();

    private ImageView sunColor;
    private ImageView sunTarget;
    private TextView sunText;
    private int sunColorRGB;

    private float sunX = 0;
    private float sunY = 0;
    private float sunVector = 0;

    final ListAdapter myAdapter = new ListAdapter(this, alarmsTime, alarmActive);

    private int myHour;
    private int myMinute;
    private int alarmsNumberAll = 0;

    Context cont;

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
*/

    private TabAdapter tabAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button add_circle_but;
    private final int ALARMS_REQUEST_CODE = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //cont = this;

        setContentView(R.layout.content_clock);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new ClockAlarms(), "Будильник");
        tabAdapter.addFragment(new ClockSettings(), "Настройки");

        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);

        add_circle_but = findViewById(R.id.add_alarm_but);
        //add_circle_but.setBackground(getDrawable(R.drawable.anim_add_circle_selected));

        add_circle_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClockActivity.this, AlarmActivity.class);
                startActivityForResult(intent, ALARMS_REQUEST_CODE);
            }
        });

        add_circle_but.setOnTouchListener(new View.OnTouchListener() {
            boolean flag = true;
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                Drawable drawable;
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        flag = true;
                        drawable = view.getBackground();
                        if (drawable instanceof Animatable)
                            if (((Animatable) drawable).isRunning())
                                ((Animatable) drawable).stop();

                        view.setBackground(getDrawable(R.drawable.anim_add_circle_selected));
                        drawable = view.getBackground();
                        if (drawable instanceof Animatable)
                            ((Animatable) drawable).start();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (flag) {
                            if (Math.abs(event.getX() - view.getWidth()/2.0) > view.getWidth() ||
                                    Math.abs(event.getY() - view.getHeight()/2.0) > view.getHeight()) {
                                flag = false;
                                drawable = view.getBackground();
                                if (drawable instanceof Animatable)
                                    if (((Animatable) drawable).isRunning())
                                        ((Animatable) drawable).stop();

                                view.setBackground(getDrawable(R.drawable.anim_add_circle_unselected));
                                drawable = view.getBackground();

                                if (drawable instanceof Animatable)
                                    ((Animatable) drawable).start();
                            }
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        if (flag) {
                            drawable = view.getBackground();
                            if (drawable instanceof Animatable)
                                if (((Animatable) drawable).isRunning())
                                    ((Animatable) drawable).stop();

                            view.setBackground(getDrawable(R.drawable.anim_add_circle_unselected));
                            drawable = view.getBackground();

                            if (drawable instanceof Animatable)
                                ((Animatable) drawable).start();
                        }
                        break;
                }
                return false;
            }
        });


        tabLayout.getTabAt(0).setIcon(R.drawable.alarm_icon);
        tabLayout.getTabAt(0).getIcon().setTint(getColor(R.color.selectedTab));
        tabLayout.getTabAt(1).setIcon(R.drawable.settings_icon);
        tabLayout.getTabAt(1).getIcon().setTint(getColor(R.color.unselectedTab));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(positionOffset != 0) {
                    add_circle_but.setScaleX(Math.abs(positionOffset - 1));
                    add_circle_but.setScaleY(Math.abs(positionOffset - 1));
                    add_circle_but.setClickable(false);
                }
                else
                {
                    add_circle_but.setClickable(true);
                    add_circle_but.setScaleX(Math.abs(position - 1));
                    add_circle_but.setScaleY(Math.abs(position - 1));
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setTint(getColor(R.color.selectedTab));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setTint(getColor(R.color.unselectedTab));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /*RelativeLayout clock_alarms_layout = findViewById(R.id.clock_alarms);
        RelativeLayout clock_settings_layout = findViewById(R.id.clock_settings);*/

        /*Display display = getWindowManager().getDefaultDisplay();
        final Point display_size = new Point();
        display.getSize(display_size);
        clock_alarms_layout.setMinimumWidth(display_size.x);
        clock_settings_layout.setMinimumWidth(display_size.x);*/


        //messageHandler.postDelayed(messageRunnable, 0);

        autoBrightButton = findViewById(R.id.autoBrightButton);
        if(isAutoBright) {
            autoBrightButton.setBackground(this.getDrawable(R.drawable.auto_bright));
            messageArguments[0] = "1";
        }
        else {
            autoBrightButton.setBackground(this.getDrawable(R.drawable.sun_bright));
            messageArguments[0] = "0";
        }

        /*bright = findViewById(R.id.bright);
        messageArguments[1] = Integer.toString(brightness);
        bright.setProgress(brightness);
        bright.setVisibility(GONE);
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

                sendHandler.postDelayed(sendRunnable, sendDelay);

                return isVisible;
            }
        });

        autoBrightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isVisible) {
                    bright.setVisibility(GONE);
                } else {
                    bright.setVisibility(VISIBLE);
                }
                isVisible = !isVisible;
                sendHandler.postDelayed(sendRunnable, sendDelay);
            }
        });

        //------------------------------------------------------------------
        timeColor = findViewById(R.id.timeColor);
        timeTarget = findViewById(R.id.timeTarget);
        timeText = findViewById(R.id.timeText);

        if(sharedPreferences.getFloat("timeX", -1) != -1)
        {
            timeX = sharedPreferences.getFloat("timeX", 0);
        }
        else
        {
            timeX = (float)(timeColor.getX() + timeColor.getWidth()/2.0 - timeTarget.getWidth()/2.0);
        }

        if(sharedPreferences.getFloat("timeY", -1) != -1)
        {
            timeY = sharedPreferences.getFloat("timeY", 0);
        }
        else
        {
            timeY = (float)(timeColor.getY() + timeColor.getHeight()/2.0 - timeTarget.getHeight()/2.0);
        }

        timeTarget.setX(timeX);
        timeTarget.setY(timeY);

        timeColorRGB = sharedPreferences.getInt("timeColorRGB", 0);
        timeText.setTextColor(timeColorRGB);

        timeColor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                timeVector = (float)(Math.pow(Math.abs(event.getX() - (float)(timeColor.getX() + timeColor.getWidth()/2.0)), 2)
                        + Math.pow(Math.abs(event.getY() - (float)(timeColor.getY() + timeColor.getHeight()/2.0)), 2));

                if(timeVector <= Math.pow((timeColor.getX() + timeColor.getWidth()/2.0) - timeTarget.getWidth()/4.0, 2))
                {
                    timeX = event.getX() - (float)(timeTarget.getWidth()/2.0);
                    timeY = event.getY() - (float)(timeTarget.getHeight()/2.0);
                }
                else
                {
                    timeX = ((((event.getX() - (float)(timeColor.getX() + timeColor.getWidth()/2.0))
                            * (float)(timeColor.getWidth()/2.0))/((float)Math.sqrt(timeVector) + (float)(timeTarget.getWidth()/1.8)))
                            + (float)(timeColor.getWidth()/2.0) - (float)(timeTarget.getWidth()/2.0));
                    timeY = ((((event.getY() - (float)(timeColor.getY() + timeColor.getHeight()/2.0))
                            * (float)(timeColor.getHeight()/2.0))/((float)Math.sqrt(timeVector) + (float)(timeTarget.getHeight()/1.8)))
                            + (float)(timeColor.getHeight()/2.0) - (float)(timeTarget.getHeight()/2.0));
                }
                view.setDrawingCacheEnabled(true);

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                        timeTarget.setX(timeX);
                        timeTarget.setY(timeY);
                        timeColorRGB = view.getDrawingCache().getPixel((int) timeX + (int)(timeTarget.getHeight()/2.0),
                                (int) timeY + (int)(timeTarget.getHeight()/2.0));
                        timeText.setTextColor(timeColorRGB);
                        break;
                    case MotionEvent.ACTION_MOVE: // движение
                        timeTarget.setX(timeX);
                        timeTarget.setY(timeY);
                        timeColorRGB = view.getDrawingCache().getPixel((int) timeX + (int)(timeTarget.getHeight()/2.0),
                                (int) timeY + (int)(timeTarget.getHeight()/2.0));
                        timeText.setTextColor(timeColorRGB);
                        break;
                    case MotionEvent.ACTION_UP: // отпускание
                    case MotionEvent.ACTION_CANCEL:
                        editor.putFloat("timeX", timeX);
                        editor.putFloat("timeY", timeY);
                        editor.putInt("timeColorRGB", timeColorRGB);
                        editor.commit();
                        messageArguments[6] = Integer.toHexString(timeColorRGB);
                        sendHandler.postDelayed(sendRunnable, sendDelay);
                        break;
                }
                messageArguments[6] = Integer.toHexString(timeColorRGB);
                sendHandler.post(sendRunnable);
                return true;
            }
        });


        secondsColor = findViewById(R.id.secondsColor);
        secondsTarget = findViewById(R.id.secondsTarget);
        secondsText = findViewById(R.id.secondsText);

        if(sharedPreferences.getFloat("secondsX", -1) != -1)
        {
            secondsX = sharedPreferences.getFloat("secondsX", 0);
        }
        else
        {
            secondsX = (float)(secondsColor.getX() + secondsColor.getWidth()/2.0 - secondsTarget.getWidth()/2.0);
        }

        if(sharedPreferences.getFloat("secondsY", -1) != -1)
        {
            secondsY = sharedPreferences.getFloat("secondsY", 0);
        }
        else
        {
            secondsY = (float)(secondsColor.getY() + secondsColor.getHeight()/2.0 - secondsTarget.getHeight()/2.0);
        }

        secondsTarget.setX(secondsX);
        secondsTarget.setY(secondsY);

        secondsColorRGB = sharedPreferences.getInt("secondsColorRGB", 0);
        secondsText.setTextColor(secondsColorRGB);

        secondsColor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                secondsVector = (float)(Math.pow(Math.abs(event.getX() - (float)(secondsColor.getX() + secondsColor.getWidth()/2.0)), 2)
                        + Math.pow(Math.abs(event.getY() - (float)(secondsColor.getY() + secondsColor.getHeight()/2.0)), 2));

                if(secondsVector <= Math.pow((secondsColor.getX() + secondsColor.getWidth()/2.0) - secondsTarget.getWidth()/4.0, 2))
                {
                    secondsX = event.getX() - (float)(secondsTarget.getWidth()/2.0);
                    secondsY = event.getY() - (float)(secondsTarget.getHeight()/2.0);
                }
                else
                {
                    secondsX = ((((event.getX() - (float)(secondsColor.getX() + secondsColor.getWidth()/2.0))
                            * (float)(secondsColor.getWidth()/2.0))/((float)Math.sqrt(secondsVector) + (float)(secondsTarget.getWidth()/1.8)))
                            + (float)(secondsColor.getWidth()/2.0) - (float)(secondsTarget.getWidth()/2.0));
                    secondsY = ((((event.getY() - (float)(secondsColor.getY() + secondsColor.getHeight()/2.0))
                            * (float)(secondsColor.getHeight()/2.0))/((float)Math.sqrt(secondsVector) + (float)(secondsTarget.getHeight()/1.8)))
                            + (float)(secondsColor.getHeight()/2.0) - (float)(secondsTarget.getHeight()/2.0));
                }
                view.setDrawingCacheEnabled(true);

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                        secondsTarget.setX(secondsX);
                        secondsTarget.setY(secondsY);
                        secondsColorRGB = view.getDrawingCache().getPixel((int) secondsX + (int)(secondsTarget.getHeight()/2.0),
                                (int) secondsY + (int)(secondsTarget.getHeight()/2.0));
                        secondsText.setTextColor(secondsColorRGB);
                        break;
                    case MotionEvent.ACTION_MOVE: // движение
                        secondsTarget.setX(secondsX);
                        secondsTarget.setY(secondsY);
                        secondsColorRGB = view.getDrawingCache().getPixel((int) secondsX + (int)(secondsTarget.getHeight()/2.0),
                                (int) secondsY + (int)(secondsTarget.getHeight()/2.0));
                        secondsText.setTextColor(secondsColorRGB);
                        break;
                    case MotionEvent.ACTION_UP: // отпускание
                    case MotionEvent.ACTION_CANCEL:
                        editor.putFloat("secondsX", secondsX);
                        editor.putFloat("secondsY", secondsY);
                        editor.putInt("secondsColorRGB", secondsColorRGB);
                        editor.commit();
                        messageArguments[7] = Integer.toHexString(secondsColorRGB);
                        sendHandler.postDelayed(sendRunnable, sendDelay);
                        break;
                }
                messageArguments[7] = Integer.toHexString(secondsColorRGB);
                sendHandler.post(sendRunnable);
                return true;
            }
        });

        dateColor = findViewById(R.id.dateColor);
        dateTarget = findViewById(R.id.dateTarget);
        dateText = findViewById(R.id.dateText);

        if(sharedPreferences.getFloat("dateX", -1) != -1)
        {
            dateX = sharedPreferences.getFloat("dateX", 0);
        }
        else
        {
            dateX = (float)(dateColor.getX() + dateColor.getWidth()/2.0 - dateTarget.getWidth()/2.0);
        }

        if(sharedPreferences.getFloat("dateY", -1) != -1)
        {
            dateY = sharedPreferences.getFloat("dateY", 0);
        }
        else
        {
            dateY = (float)(dateColor.getY() + dateColor.getHeight()/2.0 - dateTarget.getHeight()/2.0);
        }

        dateTarget.setX(dateX);
        dateTarget.setY(dateY);

        dateColorRGB = sharedPreferences.getInt("dateColorRGB", 0);
        dateText.setTextColor(dateColorRGB);

        dateColor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                dateVector = (float)(Math.pow(Math.abs(event.getX() - (float)(dateColor.getX() + dateColor.getWidth()/2.0)), 2)
                        + Math.pow(Math.abs(event.getY() - (float)(dateColor.getY() + dateColor.getHeight()/2.0)), 2));

                if(dateVector <= Math.pow((dateColor.getX() + dateColor.getWidth()/2.0) - dateTarget.getWidth()/4.0, 2))
                {
                    dateX = event.getX() - (float)(dateTarget.getWidth()/2.0);
                    dateY = event.getY() - (float)(dateTarget.getHeight()/2.0);
                }
                else
                {
                    dateX = ((((event.getX() - (float)(dateColor.getX() + dateColor.getWidth()/2.0))
                            * (float)(dateColor.getWidth()/2.0))/((float)Math.sqrt(dateVector) + (float)(dateTarget.getWidth()/1.8)))
                            + (float)(dateColor.getWidth()/2.0) - (float)(dateTarget.getWidth()/2.0));
                    dateY = ((((event.getY() - (float)(dateColor.getY() + dateColor.getHeight()/2.0))
                            * (float)(dateColor.getHeight()/2.0))/((float)Math.sqrt(dateVector) + (float)(dateTarget.getHeight()/1.8)))
                            + (float)(dateColor.getHeight()/2.0) - (float)(dateTarget.getHeight()/2.0));
                }
                view.setDrawingCacheEnabled(true);

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                        dateTarget.setX(dateX);
                        dateTarget.setY(dateY);
                        dateColorRGB = view.getDrawingCache().getPixel((int) dateX + (int)(dateTarget.getHeight()/2.0),
                                (int) dateY + (int)(dateTarget.getHeight()/2.0));
                        dateText.setTextColor(dateColorRGB);
                        break;
                    case MotionEvent.ACTION_MOVE: // движение
                        dateTarget.setX(dateX);
                        dateTarget.setY(dateY);
                        dateColorRGB = view.getDrawingCache().getPixel((int) dateX + (int)(dateTarget.getHeight()/2.0),
                                (int) dateY + (int)(dateTarget.getHeight()/2.0));
                        dateText.setTextColor(dateColorRGB);
                        break;
                    case MotionEvent.ACTION_UP: // отпускание
                    case MotionEvent.ACTION_CANCEL:
                        editor.putFloat("dateX", dateX);
                        editor.putFloat("dateY", dateY);
                        editor.putInt("dateColorRGB", dateColorRGB);
                        editor.commit();
                        messageArguments[9] = Integer.toHexString(dateColorRGB);
                        sendHandler.postDelayed(sendRunnable, sendDelay);
                        break;
                }
                messageArguments[9] = Integer.toHexString(dateColorRGB);
                sendHandler.post(sendRunnable);
                return true;
            }
        });

        //------------------------------------------------------------------------

        slideBar = findViewById(R.id.slideBar);
        slide = sharedPreferences.getInt("slide", 1);
        slideBar.setProgress(slide);

        slideBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                slide = i;
                messageArguments[8] = Integer.toString(slide);
                sendHandler.post(sendRunnable);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                editor.putInt("slide", slide);
                editor.commit();
                sendHandler.postDelayed(sendRunnable, sendDelay);
            }
        });

        alarmsButton = findViewById(R.id.alarmsButton);
        topLayout = findViewById(R.id.topLayout);
        middleLayout = findViewById(R.id.middleLayout);
        alarmsLayout = findViewById(R.id.alarmsLayout);
        alarmsView = findViewById(R.id.alarmsView);
        alarmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAlarmShow = !isAlarmShow;
                if(!isAlarmShow)
                {
                    alarmsButton.setText(cont.getText(R.string.alarms));
                    topLayout.setVisibility(VISIBLE);
                    middleLayout.setVisibility(VISIBLE);
                    slideBar.setVisibility(VISIBLE);
                    alarmsLayout.setVisibility(GONE);
                    alarmsView.setVisibility(GONE);
                }
                else
                {
                    alarmsButton.setText(cont.getText(R.string.times));
                    alarmsLayout.setVisibility(VISIBLE);
                    alarmsView.setVisibility(VISIBLE);
                    topLayout.setVisibility(GONE);
                    middleLayout.setVisibility(GONE);
                    slideBar.setVisibility(GONE);
                }
            }
        });

        alarmsView = findViewById(R.id.alarmsView);
        GetAlarms getAlarms = new GetAlarms();
        getAlarms.execute();
        alarmsView.setAdapter(myAdapter);

        alarmsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View itemClick, int position, long id) {
                if(!alarmActive.get(position))
                {
                    if(alarmsNumber < 10) {
                        alarmsNumber++;
                        messageArguments[12] = Integer.toString(alarmsNumber);
                        messageArguments[12 + alarmsNumber] = alarmsTime.get(position);
                        alarmActive.set(position, true);

                        AddActiveAlarm addActiveAlarm = new AddActiveAlarm();
                        addActiveAlarm.execute(alarmMinutes.get(position));

                        myAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        Toast.makeText(cont, "Десять - максимальное число одновременно включенных будильников", Toast.LENGTH_SHORT).show();
                    }
                    sendHandler.postDelayed(sendRunnable, sendDelay);
                }
                else
                {
                    DeleteAlarm deleteAlarm = new DeleteAlarm();
                    deleteAlarm.execute(alarmsTime.get(position));

                    DeleteActiveAlarm deleteActiveAlarm = new DeleteActiveAlarm();
                    deleteActiveAlarm.execute(alarmMinutes.get(position));

                    alarmActive.set(position, false);
                    alarmsNumber--;
                    messageArguments[12] = Integer.toString(alarmsNumber);
                    myAdapter.notifyDataSetChanged();
                }

                startService(new Intent(cont, alarmService.class));
            }
        });

        alarmsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (alarmActive.get(position)) {
                    DeleteAlarm deleteAlarm = new DeleteAlarm();
                    deleteAlarm.execute(alarmsTime.get(position));

                    DeleteActiveAlarm deleteActiveAlarm = new DeleteActiveAlarm();
                    deleteActiveAlarm.execute(alarmMinutes.get(position));

                    alarmActive.set(position, false);
                    alarmsNumber--;
                    messageArguments[12] = Integer.toString(alarmsNumber);
                }
                view.setScaleX((float)(view.getWidth()/2.0));
                alarmsTime.remove(position);
                alarmActive.remove(position);
                alarmMinutes.remove(position);
                myAdapter.notifyDataSetChanged();
                alarmsNumberAll--;
                return true;
            }
        });

        alarmsNumberAll = sharedPreferences.getInt("alarmsNumberAll", 0);
        addAlarmButton = findViewById(R.id.addAlarmButton);
        addAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime();
            }
        });

        sunColor = findViewById(R.id.sunColor);
        sunTarget = findViewById(R.id.sunTarget);
        sunText = findViewById(R.id.sunText);

        if(sharedPreferences.getFloat("sunX", -1) != -1)
        {
            sunX = sharedPreferences.getFloat("sunX", 0);
        }
        else
        {
            sunX = (float)(sunColor.getX() + sunColor.getWidth()/2.0 - sunTarget.getWidth()/2.0);
        }

        if(sharedPreferences.getFloat("sunY", -1) != -1)
        {
            sunY = sharedPreferences.getFloat("sunY", 0);
        }
        else
        {
            sunY = (float)(sunColor.getY() + sunColor.getHeight()/2.0 - sunTarget.getHeight()/2.0);
        }

        sunTarget.setX(sunX);
        sunTarget.setY(sunY);

        sunColorRGB = sharedPreferences.getInt("sunColorRGB", 0);
        sunText.setTextColor(sunColorRGB);

        sunColor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                sunVector = (float)(Math.pow(Math.abs(event.getX() - (float)(sunColor.getX() + sunColor.getWidth()/2.0)), 2)
                        + Math.pow(Math.abs(event.getY() - (float)(sunColor.getY() + sunColor.getHeight()/2.0)), 2));

                if(sunVector <= Math.pow((sunColor.getX() + sunColor.getWidth()/2.0) - sunTarget.getWidth()/4.0, 2))
                {
                    sunX = event.getX() - (float)(sunTarget.getWidth()/2.0);
                    sunY = event.getY() - (float)(sunTarget.getHeight()/2.0);
                }
                else
                {
                    sunX = ((((event.getX() - (float)(sunColor.getX() + sunColor.getWidth()/2.0))
                            * (float)(sunColor.getWidth()/2.0))/((float)Math.sqrt(sunVector) + (float)(sunTarget.getWidth()/1.8)))
                            + (float)(sunColor.getWidth()/2.0) - (float)(sunTarget.getWidth()/2.0));
                    sunY = ((((event.getY() - (float)(sunColor.getY() + sunColor.getHeight()/2.0))
                            * (float)(sunColor.getHeight()/2.0))/((float)Math.sqrt(sunVector) + (float)(sunTarget.getHeight()/1.8)))
                            + (float)(sunColor.getHeight()/2.0) - (float)(sunTarget.getHeight()/2.0));
                }
                view.setDrawingCacheEnabled(true);

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                        sunTarget.setX(sunX);
                        sunTarget.setY(sunY);
                        sunColorRGB = view.getDrawingCache().getPixel((int) sunX + (int)(sunTarget.getHeight()/2.0),
                                (int) sunY + (int)(sunTarget.getHeight()/2.0));
                        sunText.setTextColor(sunColorRGB);
                        break;
                    case MotionEvent.ACTION_MOVE: // движение
                        sunTarget.setX(sunX);
                        sunTarget.setY(sunY);
                        sunColorRGB = view.getDrawingCache().getPixel((int) sunX + (int)(sunTarget.getHeight()/2.0),
                                (int) sunY + (int)(sunTarget.getHeight()/2.0));
                        sunText.setTextColor(sunColorRGB);
                        break;
                    case MotionEvent.ACTION_UP: // отпускание
                    case MotionEvent.ACTION_CANCEL:
                        editor.putFloat("sunX", sunX);
                        editor.putFloat("sunY", sunY);
                        editor.putInt("sunColorRGB", sunColorRGB);
                        editor.commit();
                        messageArguments[11] = Integer.toHexString(sunColorRGB);
                        sendHandler.postDelayed(sendRunnable, sendDelay);
                        break;
                }
                messageArguments[11] = Integer.toHexString(sunColorRGB);
                sendHandler.post(sendRunnable);
                return true;
            }
        });

        sunriseBar = findViewById(R.id.sunriseBar);
        sunriseTime = sharedPreferences.getInt("sunriseTime", 1);
        sunriseBar.setProgress(sunriseTime);

        sunriseText = findViewById(R.id.sunriseText);
        sunriseText.setText("Рассвет длится " + sunriseTime + " мин.");

        sunriseBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                sunriseTime = i;
                messageArguments[10] = Integer.toString(sunriseTime);
                sunriseText.setText("Рассвет длится " + sunriseTime + " мин.");
                sendHandler.post(sendRunnable);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sunriseText.setText("Рассвет длится " + sunriseTime + " мин.");
                editor.putInt("sunriseTime", sunriseTime);
                sendHandler.postDelayed(sendRunnable, sendDelay);
                editor.commit();
            }
        });

        //-------------------------------------------------------------------------

        isEasterEgg = findViewById(R.id.isEasterEgg);
        isEasterEgg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                isEggActive = !isEggActive;
                if(isEggActive) {
                    messageArguments[5] = "1";
                    Toast.makeText(cont, "Пасхалка активирована :-)", Toast.LENGTH_SHORT).show();
                }
                else {
                    messageArguments[5] = "0";
                    Toast.makeText(cont, "Пасхалка отключена :-(", Toast.LENGTH_SHORT).show();
                }

                editor.putBoolean("isEggActive", isEggActive);
                editor.commit();
                sendHandler.postDelayed(sendRunnable, sendDelay);
                return false;
            }
        });

        isEggActive = sharedPreferences.getBoolean("isEggActive", true);
        messageArguments[4] = "1";
        if(isEggActive)
            messageArguments[5] = "1";
        else
            messageArguments[5] = "0";
        messageArguments[6] = Integer.toHexString(timeColorRGB);
        messageArguments[7] = Integer.toHexString(secondsColorRGB);
        messageArguments[8] = Integer.toString(slide);
        messageArguments[9] = Integer.toHexString(dateColorRGB);
        messageArguments[10] = Integer.toString(sunriseTime);
        messageArguments[11] = Integer.toHexString(sunColorRGB);

        sendHandler.postDelayed(sendRunnable, sendDelay);*/
    }
/*
    @Override
    public void onBackPressed() {
        messageArguments[4] = "0";
        SaveAlarms saveAlarms = new SaveAlarms();
        saveAlarms.execute();
        Intent output = new Intent();
        setResult(RESULT_OK, output);
        this.finish();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        messageArguments[4] = "0";
        SaveAlarms saveAlarms = new SaveAlarms();
        saveAlarms.execute();
        super.onDestroy();
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

    public void setTime() {
        new TimePickerDialog(ClockActivity.this, TimePickerDialog.THEME_HOLO_DARK, timeSet,
                myHour,
                myMinute, true)
                .show();
    }

    TimePickerDialog.OnTimeSetListener timeSet=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour = hourOfDay;
            myMinute = minute;

            alarmsNumberAll++;
            editor.putInt("alarmsNumberAll", alarmsNumberAll);
            editor.commit();

            AddAlarm addAlarm = new AddAlarm();
            addAlarm.execute(myHour*60+myMinute);
        }
    };

    private class AddActiveAlarm extends AsyncTask<Integer, Integer, Void>
    {
        @Override
        protected Void doInBackground(Integer... target) {
            for(int i = 0; i <= alarmMinutesActivated.size(); i++)
            {
                if(i == alarmMinutesActivated.size())
                {
                    alarmMinutesActivated.add(target[0]);
                    break;
                }
                if(alarmMinutesActivated.get(i) > target[0])
                {
                    alarmMinutesActivated.add(i, target[0]);
                    break;
                }
            }
            return null;
        }
    }

    private class DeleteActiveAlarm extends AsyncTask<Integer, Integer, Void>
    {
        @Override
        protected Void doInBackground(Integer... target) {
            for(int i = 0; i < alarmMinutesActivated.size(); i++)
            {
                if (alarmMinutesActivated.get(i).equals(target[0])) {
                        //messageArguments[i] = "null";
                        alarmMinutesActivated.remove(i);
                        break;
                }
            }
            return null;
        }
    }

    private class AddAlarm extends AsyncTask<Integer, Integer, Void>
    {
        @Override
        protected Void doInBackground(Integer... target) {
            String sHour;
            String sMinute;
            if(myHour < 10) {
                sHour = "0" + myHour;
            }
            else {
                sHour = Integer.toString(myHour);
            }
            if(myMinute < 10) {
                sMinute = "0" + myMinute;
            }
            else {
                sMinute = Integer.toString(myMinute);
            }

            for(int i = 0; i <= alarmMinutes.size(); i++)
            {
                if(i == alarmMinutes.size())
                {
                    alarmMinutes.add(target[0]);
                    alarmsTime.add(sHour + ":" + sMinute);
                    alarmActive.add(false);
                    break;
                }
                if(alarmMinutes.get(i) > target[0])
                {
                    alarmMinutes.add(i, target[0]);
                    alarmsTime.add(i, sHour + ":" + sMinute);
                    alarmActive.add(i, false);
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            myAdapter.notifyDataSetChanged();
            super.onPostExecute(aVoid);
        }
    }

    private class DeleteAlarm extends AsyncTask<String, String, Void>
    {
        @Override
        protected Void doInBackground(String... target) {
            for(int i = 0; i < maxMessageSize; i++)
            {
                if(messageArguments[i] != null) {
                    if (messageArguments[i].equals(target[0])) {
                        messageArguments[i] = "null";
                        break;
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            sendHandler.postDelayed(sendRunnable, sendDelay);
            super.onPostExecute(aVoid);
        }
    }

    private class SaveAlarms extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            for(int i = 0; i < alarmsNumberAll; i++)
            {
                editor.putString("alarmsTime"+i, alarmsTime.get(i));
                editor.putBoolean("alarmActive"+i, alarmActive.get(i));
                editor.putInt("alarmMinutes"+i, alarmMinutes.get(i));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            editor.putInt("alarmsNumberAll", alarmsNumberAll);
            editor.commit();
            super.onPostExecute(aVoid);
        }
    }

    private class GetAlarms extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            for(int i = 0; i < sharedPreferences.getInt("alarmsNumberAll", 0); i++)
            {
                alarmsTime.add(sharedPreferences.getString("alarmsTime"+i, null));
                alarmActive.add(sharedPreferences.getBoolean("alarmActive"+i, false));
                alarmMinutes.add(sharedPreferences.getInt("alarmMinutes"+i, 0));
                if(alarmActive.get(i))
                {
                    alarmsNumber++;
                    alarmMinutesActivated.add(sharedPreferences.getInt("alarmMinutes"+i, 0));
                    messageArguments[12] = Integer.toString(alarmsNumber);
                    messageArguments[12+alarmsNumber] = alarmsTime.get(i);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            myAdapter.notifyDataSetChanged();
            sendHandler.postDelayed(sendRunnable, sendDelay);
            super.onPostExecute(aVoid);
        }
    }
    */
}

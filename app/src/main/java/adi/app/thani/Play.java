package adi.app.thani;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Play extends Activity {
    //@Override
    CountDownTimer hi;
    MediaPlayer player;
    int count;
    int i;
    String talam;
    int millisLeft = 0;
    HashMap<Integer, Integer> adi;
    HashMap<Integer, Integer> mishra;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);
        final FloatingActionButton play = findViewById(R.id.floatingActionButton2);
        talam = getIntent().getStringExtra(Intent.EXTRA_INDEX);
        count = 0;
        mishra = new HashMap<Integer, Integer>();
        mishra.put(0, R.mipmap.realhand_foreground);
        mishra.put(1, R.mipmap.hello);
        mishra.put(2, R.mipmap.hello);
        mishra.put(3, R.mipmap.realhand_foreground);
        mishra.put(4, R.mipmap.hello);
        mishra.put(5, R.mipmap.realhand_foreground);
        mishra.put(6, R.mipmap.hello);
        mishra.put(7, R.mipmap.seven_foreground);
        mishra.put(8, R.mipmap.six_foreground);
        mishra.put(9, R.mipmap.five_foreground);
        mishra.put(10, R.mipmap.four_foreground);
        mishra.put(11, R.mipmap.three_foreground);
        mishra.put(12, R.mipmap.two_foreground);
        mishra.put(13, R.mipmap.one_foreground);
        adi = new HashMap<Integer, Integer>();
        adi.put(0, R.mipmap.realhand_foreground);
        adi.put(1, R.mipmap.realpinky_foreground);
        adi.put(2, R.mipmap.realring_foreground);
        adi.put(3, R.mipmap.realmiddle_foreground);
        adi.put(4, R.mipmap.realhand_foreground);
        adi.put(5, R.mipmap.realturn_foreground);
        adi.put(6, R.mipmap.realhand_foreground);
        adi.put(7, R.mipmap.realturn_foreground);
        adi.put(8, R.mipmap.eight_foreground);
        adi.put(9, R.mipmap.seven_foreground);
        adi.put(10, R.mipmap.six_foreground);
        adi.put(11, R.mipmap.five_foreground);
        adi.put(12, R.mipmap.four_foreground);
        adi.put(13, R.mipmap.three_foreground);
        adi.put(14, R.mipmap.two_foreground);
        adi.put(15, R.mipmap.one_foreground);
        final ImageView image = findViewById(R.id.imageView4);
        final ImageView image2 = findViewById(R.id.imageView5);
        final ImageView image3 = findViewById(R.id.imageView6);
        String suffix = getIntent().getStringExtra(Intent.EXTRA_COMPONENT_NAME);
        final int bpm = getIntent().getIntExtra(Intent.EXTRA_TEXT,80);
        final String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath() + File.separator + suffix + ".mp4";
        image.setImageResource(R.mipmap.clap);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    player = new MediaPlayer();
                    try {
                        player.setDataSource(path);
                        player.prepare();
                        System.out.println("hi2");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (getIntent().getStringExtra(Intent.EXTRA_INDEX).equals("Adi Talam")) {
                        player.start();
                        hi = new CountDownTimer(1000000000, 60000 / bpm) {
                            public void onTick(long millisUntilFinished) {
                                System.out.println(millisUntilFinished);
                                image.setImageResource(adi.get(i));
                                image2.setImageResource(adi.get(i + 8));
                                i++;
                                if (i >= 8) {
                                    i = 0;
                                }
                            }

                            public void onFinish() {
                                Log.d("hi", "hello");
                            }
                        }.start();
                    } else if (getIntent().getStringExtra(Intent.EXTRA_INDEX).equals("Misra Chap")) {
                        player.start();
                        hi = new CountDownTimer(1000, 60000 / bpm) {
                            public void onTick(long millisUntilFinished) {
                                System.out.println(millisUntilFinished);
                                image.setImageResource(mishra.get(i));
                                image2.setImageResource(mishra.get(i + 8));
                                i++;
                                if (i >= 8) {
                                    i = 0;
                                }
                            }

                            public void onFinish() {
                                Log.d("hi", "hello");
                            }
                        }.start();
                    }
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();
                            hi.cancel();
                            mp = null;
                            finish();
                        }
                    });
                    count++;
                } else {
                    System.out.println("hi3");
                    hi.cancel();
                    finish();
                }
            }
        });
    }
    protected void onStop() {
        super.onStop();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}

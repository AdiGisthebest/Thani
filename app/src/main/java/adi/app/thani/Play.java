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

public class Play extends Activity {
    //@Override
    CountDownTimer hi;
    MediaPlayer player;
    int count;
    int i;
    String talam;
    int millisLeft = 0;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);
        final FloatingActionButton play = findViewById(R.id.floatingActionButton2);
        talam = getIntent().getStringExtra(Intent.EXTRA_INDEX);
        count = 0;
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
                        player.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    hi = new CountDownTimer(1000000000, 60000 / bpm) {
                        public void onTick(long millisUntilFinished) {
                            System.out.println(talam + " talam from play");
                            if (talam.trim().equals("Adi Talam")) {
                                switch (i) {
                                    case 0:
                                        image.setImageResource(R.mipmap.realhand_foreground);
                                        image2.setImageResource(R.mipmap.eight_foreground);
                                        break;
                                    case 1:
                                        image.setImageResource(R.mipmap.realpinky_foreground);
                                        image2.setImageResource(R.mipmap.seven_foreground);
                                        break;
                                    case 2:
                                        image.setImageResource(R.mipmap.realring_foreground);
                                        image2.setImageResource(R.mipmap.six_foreground);
                                        break;
                                    case 3:
                                        image.setImageResource(R.mipmap.realmiddle_foreground);
                                        image2.setImageResource(R.mipmap.five_foreground);
                                        break;
                                    case 4:
                                        image.setImageResource(R.mipmap.realhand_foreground);
                                        image2.setImageResource(R.mipmap.four_foreground);
                                        break;
                                    case 5:
                                        image.setImageResource(R.mipmap.realturn_foreground);
                                        image2.setImageResource(R.mipmap.three_foreground);
                                        break;
                                    case 6:
                                        image.setImageResource(R.mipmap.realhand_foreground);
                                        image2.setImageResource(R.mipmap.two_foreground);
                                        break;
                                    case 7:
                                        image.setImageResource(R.mipmap.realturn_foreground);
                                        image2.setImageResource(R.mipmap.one_foreground);
                                        break;
                                }
                                i++;
                                if (i >= 8) {
                                    i = 0;
                                }
                            } else if (talam.trim().equals("Misra Chap")) {
                                switch (i) {
                                    case 0:
                                        image.setImageResource(R.mipmap.realhand_foreground);
                                        image2.setImageResource(R.mipmap.seven_foreground);
                                        break;
                                    case 1:
                                        image.setImageResource(R.mipmap.hello_foreground);
                                        image2.setImageResource(R.mipmap.six_foreground);
                                        break;
                                    case 2:
                                        image2.setImageResource(R.mipmap.five_foreground);
                                        break;
                                    case 3:
                                        image.setImageResource(R.mipmap.realhand_foreground);
                                        image2.setImageResource(R.mipmap.four_foreground);
                                        break;
                                    case 4:
                                        image.setImageResource(R.mipmap.hello_foreground);
                                        image2.setImageResource(R.mipmap.three_foreground);
                                        break;
                                    case 5:
                                        image.setImageResource(R.mipmap.realhand_foreground);
                                        image2.setImageResource(R.mipmap.two_foreground);
                                        break;
                                    case 6:
                                        image.setImageResource(R.mipmap.hello_foreground);
                                        image2.setImageResource(R.mipmap.one_foreground);
                                        break;
                                }
                                i++;
                                if (i >= 7) {
                                    i = 0;
                                }
                            }
                        }

                        public void onFinish() {
                            Log.d("hi", "hello");
                        }
                    };
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();
                            hi.cancel();
                            mp = null;
                            finish();
                        }
                    });
                    hi.start();
                    count++;
                } else {
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

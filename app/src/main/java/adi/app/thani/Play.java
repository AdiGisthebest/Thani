package adi.app.thani;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
                }
                if (count == 0) {
                    if (count != 0) {
                        player.start();
                        play.setImageDrawable(getDrawable(android.R.drawable.ic_media_pause));
                    }
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();
                            mp = null;
                            finish();
                        }
                    });
                    hi = new CountDownTimer(1000000000, 60000 / (bpm * 2)) {
                        //@Override
                        public void onTick(long millisUntilFinished) {
                            if (talam.equals("Adi Talam")) {
                                switch (i) {
                                    case 0:
                                        image.setImageResource(R.mipmap.realhand_foreground);
                                        image2.setImageResource(R.mipmap.eight_foreground);
                                        break;
                                    case 2:
                                        image.setImageResource(R.mipmap.realpinky_foreground);
                                        image2.setImageResource(R.mipmap.seven_foreground);
                                        break;
                                    case 4:
                                        image.setImageResource(R.mipmap.realring_foreground);
                                        image2.setImageResource(R.mipmap.six_foreground);
                                        break;
                                    case 6:
                                        image.setImageResource(R.mipmap.realmiddle_foreground);
                                        image2.setImageResource(R.mipmap.five_foreground);
                                        break;
                                    case 8:
                                        image.setImageResource(R.mipmap.realhand_foreground);
                                        image2.setImageResource(R.mipmap.four_foreground);
                                        break;
                                    case 10:
                                        image.setImageResource(R.mipmap.realturn_foreground);
                                        image2.setImageResource(R.mipmap.three_foreground);
                                        break;
                                    case 12:
                                        image.setImageResource(R.mipmap.realhand_foreground);
                                        image2.setImageResource(R.mipmap.two_foreground);
                                        break;
                                    case 14:
                                        image.setImageResource(R.mipmap.realturn_foreground);
                                        image2.setImageResource(R.mipmap.one_foreground);
                                        break;
                                }
                                i++;
                                if (i >= 16) {
                                    i = 0;
                                }
                            } else if (talam.equals("Misra Chap")) {
                                switch (i) {
                                    case 0:
                                        image.setImageResource(R.mipmap.realhand_foreground);
                                        image2.setImageResource(R.mipmap.seven_foreground);
                                        break;
                                    case 2:
                                        image.setImageResource(R.mipmap.hello_foreground);
                                        image2.setImageResource(R.mipmap.six_foreground);
                                        break;
                                    case 4:
                                        image2.setImageResource(R.mipmap.five_foreground);
                                        break;
                                    case 6:
                                        image.setImageResource(R.mipmap.realhand_foreground);
                                        image2.setImageResource(R.mipmap.four_foreground);
                                        break;
                                    case 8:
                                        image.setImageResource(R.mipmap.hello_foreground);
                                        image2.setImageResource(R.mipmap.three_foreground);
                                        break;
                                    case 10:
                                        image.setImageResource(R.mipmap.realhand_foreground);
                                        image2.setImageResource(R.mipmap.two_foreground);
                                        break;
                                    case 12:
                                        image.setImageResource(R.mipmap.hello_foreground);
                                        image2.setImageResource(R.mipmap.one_foreground);
                                        break;
                                }
                                i++;
                                if (i >= 14) {
                                    i = 0;
                                }
                            }
                        }

                        public void onFinish() {
                            Log.d("hi", "hello");
                        }
                    };
                    hi.start();
                    count++;
                } else {
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

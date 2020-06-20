package adi.app.thani;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;

public class Play extends Activity {
    int count;
    MediaPlayer player;
    boolean paused = false;
    Runnable run;
    Handler handler = new Handler();

    public int[] dimens() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int Dispheight = metrics.heightPixels * 160 / metrics.densityDpi;
        int Dispwidth = metrics.heightPixels * 160 / metrics.densityDpi;
        int origHeight = 170;
        int origWidth = 125;
        int[] retval = {125, 170};
        for (int j = 0; j < 100; j++) {
            while (retval[0] + origWidth > Dispwidth) {
                origWidth = origWidth / 2;
            }
            retval[0] = retval[0] + origWidth;
            System.out.println(retval[0] + " ret");
            System.out.println(Dispwidth + " disp");
            System.out.println(origWidth);
        }
        for (int j = 0; j < 100; j++) {
            while (retval[1] + origHeight > Dispheight) {
                origHeight = origHeight / 2;
            }
            retval[1] = retval[1] + origHeight;
        }
        return retval;
    }
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);
        count = 0;
        final SeekBar prog = findViewById(R.id.seekBar2);
        final Button play = findViewById(R.id.button4);
        final VideoView vid = findViewById(R.id.videoView2);
        player = new MediaPlayer();
        String suffix = getIntent().getStringExtra(Intent.EXTRA_COMPONENT_NAME);
        if (getIntent().getBooleanExtra("Audio", false)) {
            final String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath() + File.separator + suffix + ".mp3";
            vid.setVisibility(View.INVISIBLE);
            try {
                player.setDataSource(path);
                player.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            prog.setMax(player.getDuration() / 1000);
            player.start();
            handler = new Handler();
            Play.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (player != null) {
                        int mCurrentPosition = (player.getCurrentPosition() / 1000) + 1;
                        System.out.println(mCurrentPosition);
                        prog.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                    run = this;
                }
            });
            prog.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (player != null && fromUser) {
                        player.seekTo(progress * 1000);
                    }
                }
            });
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    paused = !paused;
                    if (!paused) {
                        play.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_media_pause, 0, 0, 0);
                        handler.postDelayed(run, 1000);
                        player.start();
                    } else {
                        play.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_media_play, 0, 0, 0);
                        handler.removeCallbacks(run);
                        player.pause();
                    }
                }
            });
        } else {
            final String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath() + File.separator + suffix + ".mp4";
            Uri uri = Uri.parse(path);
            vid.setVideoURI(uri);
            ViewGroup.LayoutParams params = vid.getLayoutParams();
            int[] dimens = this.dimens();
            DisplayMetrics met = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(met);
            params.height = dimens[1] * met.densityDpi / 160;
            params.width = dimens[0] * met.densityDpi / 160;
            vid.setLayoutParams(params);
            vid.setMediaController(new MediaController(this));
            if (count == 0) {
                vid.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        vid.start();
                    }
                });
            }
            vid.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    finish();
                }
            });
        }


    }

    @Override
    public void onBackPressed() {
        if (player != null) {
            player.stop();
            handler.removeCallbacks(run);
            player.release();
        }
        super.onBackPressed();
    }

    protected void onStop() {
        super.onStop();
    }
}

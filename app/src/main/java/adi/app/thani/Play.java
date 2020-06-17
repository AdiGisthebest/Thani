package adi.app.thani;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.Nullable;

import java.io.File;
import java.util.HashMap;

public class Play extends Activity {
    MediaPlayer player;
    int count;
    String talam;
    HashMap<Integer, Integer> adi;
    HashMap<Integer, Integer> mishra;

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
        final VideoView vid = findViewById(R.id.videoView2);
        String suffix = getIntent().getStringExtra(Intent.EXTRA_COMPONENT_NAME);
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
    protected void onStop() {
        super.onStop();
    }
}

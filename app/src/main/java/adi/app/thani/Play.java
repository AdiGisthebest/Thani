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

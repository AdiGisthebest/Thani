package adi.app.thani;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Record extends Activity {
    int permissionsgranted;
    CountDownTimer hi;
    Boolean visibleChecked = true;
    ImageView image;
    ImageView image2;
    ImageView image3;
    EditText speed;
    ToggleButton toggle;
    int count;
    int i = 0;
    MediaRecorder record;
    String path;
    boolean buttonClick;
    Intent returnval;
    ConstraintLayout cl;
    HashMap<Integer, Integer> mishra;
    HashMap<Integer, Integer> adi;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recorder);
        returnval = new Intent();
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
        cl = findViewById(R.id.cl);
        count = getIntent().getIntExtra(Intent.EXTRA_INDEX,0);
        image = findViewById(R.id.imageView);
        image2 = findViewById(R.id.imageView2);
        image3 = findViewById(R.id.imageView3);
        speed = findViewById(R.id.editText);
        speed.setText("80");
        System.out.println(permissionsgranted + " permission at entry");
        image.setImageResource(R.mipmap.clap);
        record = new MediaRecorder();
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                run();
            } else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
            }
        } else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},0);
        }
    }
    public void run() {
        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath() + File.separator;
        count++;
        System.out.println(path);
        toggle = findViewById(R.id.toggleButton);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                final Intent intent = getIntent();
                if (isChecked) {
                    if (Integer.parseInt(speed.getText().toString()) >= 165) {
                        Toast.makeText(Record.this, "Please enter a lower speed", Toast.LENGTH_SHORT).show();
                        toggle.setChecked(false);
                        speed.setVisibility(View.VISIBLE);
                    } else {
                        record.setAudioSource(MediaRecorder.AudioSource.MIC);
                        record.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                        record.setOutputFile(path + "Thani" + (count - 1) + ".mp4");
                        record.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                        try {
                            record.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("hi1111111111111111111111111111111111111111");
                        System.out.println("hi2");
                        speed.setVisibility(View.INVISIBLE);
                        int speedNum;
                        if (speed.getText().toString().toCharArray().length >= 5) {
                            speedNum = 80;
                        } else {
                            speedNum = Integer.parseInt(speed.getText().toString());
                        }
                        returnval.putExtra(Intent.ACTION_PACKAGE_REMOVED, speedNum);
                        if (intent.getStringExtra(Intent.EXTRA_TEXT).equals("Adi Talam")) {
                            hi = new CountDownTimer(1000000000, 60000 / speedNum) {
                                //@Override
                                public void onTick(long millisUntilFinished) {
                                    System.out.println(millisUntilFinished);
                                    image.setImageResource(adi.get(i));
                                    image2.setImageResource(adi.get(i + 8));
                                    i++;
                                    if (i >= 8) {
                                        i = 0;
                                    }
                                    if (!visibleChecked) {
                                        if (hi != null) {
                                            hi.cancel();
                                            hi = null;
                                        }
                                    }
                                }

                                public void onFinish() {
                                    hi.cancel();
                                    Log.d("hi", "hello");
                                }
                            };
                        } else if (intent.getStringExtra(Intent.EXTRA_TEXT).equals("Misra Chap")) {
                            hi = new CountDownTimer(1000000000, 60000 / speedNum) {
                                //@Override
                                public void onTick(long millisUntilFinished) {
                                    System.out.println(millisUntilFinished);
                                    System.out.println(millisUntilFinished);
                                    image.setImageResource(mishra.get(i));
                                    image2.setImageResource(mishra.get(i + 8));
                                    i++;
                                    if (i >= 7) {
                                        i = 0;
                                    }
                                    if (!visibleChecked) {
                                        if (hi != null) {
                                            hi.cancel();
                                            hi = null;
                                        }
                                    }
                                }

                                public void onFinish() {
                                    hi.cancel();
                                    Log.d("hi", "hello");
                                }
                            };
                        }
                        record.start();
                        hi.start();
                    }
                } else {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popview = inflater.inflate(R.layout.popup, null);
                    final PopupWindow pop = new PopupWindow(popview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    pop.setOutsideTouchable(false);
                    pop.setFocusable(true);
                    pop.showAtLocation(buttonView, Gravity.CENTER, 0, 0);
                    setResult(RESULT_OK, returnval);
                    returnval.putExtra(Intent.EXTRA_TEXT, count);
                    returnval.putExtra(Intent.EXTRA_PACKAGE_NAME,intent.getStringExtra(Intent.EXTRA_TEXT));
                    FloatingActionButton del = popview.findViewById(R.id.floatingActionButton3);
                    FloatingActionButton save = popview.findViewById(R.id.floatingActionButton4);
                    final EditText name = popview.findViewById(R.id.editText2);
                    name.setText("Thani" + count);
                    pop.update();
                    buttonClick = false;
                    record.stop();
                    del.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            buttonClick = true;
                            pop.dismiss();
                            hi.cancel();
                            record.release();
                            File file1 = new File(path + "Thani" + (count - 1) + ".mp4");
                            file1.delete();
                            setResult(RESULT_CANCELED, returnval);
                            finish();
                        }
                    });
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hi.cancel();
                            buttonClick = true;
                            pop.dismiss();
                            record.release();
                            record = null;
                            File file1 = new File(path + "Thani" + (count - 1) + ".mp4");
                            File file = new File(path + name.getText().toString() + ".mp4");
                            file1.renameTo(file);
                            returnval.putExtra(Intent.EXTRA_STREAM, name.getText().toString());
                            finish();
                        }
                    });
                    pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            if (!buttonClick) {
                                hi.cancel();
                                record.release();
                                record = null;
                                File file1 = new File(path + "Thani" + (count - 1) + ".mp4");
                                File file = new File(path + name.getText().toString() + ".mp4");
                                file1.renameTo(file);
                                returnval.putExtra(Intent.EXTRA_STREAM, name.getText().toString());
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                setResult(RESULT_CANCELED);
                finish();
            } else {
                if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                } else {
                    run();
                }
            }
        } else if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                setResult(RESULT_CANCELED);
                finish();
            } else {
                run();
            }
        }
    }
}

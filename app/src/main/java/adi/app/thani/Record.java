package adi.app.thani;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wesley.camera2.util.Camera2Listener;
import com.wesley.camera2.util.CameraUtil;
import com.wesley.camera2.widget.AutoFitTextureView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Record extends Activity implements Camera2Listener {
    CountDownTimer hi;
    Boolean visibleChecked = true;
    ImageView image;
    ImageView image2;
    ImageView image3;
    //EditText speed;
    TextView speedText;
    Switch audio;
    boolean audioBool;
    SeekBar speed2;
    ToggleButton toggle;
    int count;
    public static final String TAG = "Camera2Fragment";
    int i = 0;
    TextView text;
    int soundid;
    MediaRecorder record;
    String path;
    boolean buttonClick;
    Intent returnval;
    ConstraintLayout cl;
    HashMap<Integer, Integer> mishra;
    HashMap<Integer, Integer> adi;
    HashMap<Integer, Integer> khanda;
    HashMap<Integer, Integer> rupa;
    SoundPool pool;
    View view;
    private AutoFitTextureView mCameraLayout;
    private CameraDevice mCameraDevice;
    private CameraCaptureSession mPreviewSession;
    private Size mPreviewSize;
    private Size mVideoSize;
    private CaptureRequest.Builder mPreviewBuilder;
    private MediaRecorder mMediaRecorder;
    private boolean isRecording;
    private HandlerThread mBackgroundThread;
    private Handler mBackgroundHandler;
    private Semaphore mCameraOpenCloseLock = new Semaphore(1);
    private boolean upsideDown;
    private int mCameraFacing = CameraCharacteristics.LENS_FACING_BACK;
    private File mCurrentFile;
    private String mRationaleMessage;
    private CameraCaptureSession.StateCallback mSessionCallback = new CameraCaptureSession.StateCallback() {

        @Override
        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
            mPreviewSession = cameraCaptureSession;
            updatePreview();
        }

        @Override
        public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
            Record.this.onConfigurationFailed();
            Record.this.finish();
        }
    };
    // Listeners
    private CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            mCameraDevice = camera;
            startPreview();
            mCameraOpenCloseLock.release();
            if (null != mCameraLayout) {
                configureTransform(mCameraLayout.getWidth(), mCameraLayout.getHeight());
            }
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            mCameraOpenCloseLock.release();
            camera.close();
            mCameraDevice = null;
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            mCameraOpenCloseLock.release();
            camera.close();
            mCameraDevice = null;
            Record.this.finish();
        }
    };
    private TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            openCamera(width, height);
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            configureTransform(width, height);
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

    public void startRecordingVideo() {
        try {
            // UI
            isRecording = true;

            // Start recording
            mMediaRecorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public void stopRecordingVideo() {
        closeCamera();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("CameraBackground");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    private void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            this.onInterruptedException(e);
        }
    }

    private void openCamera(int width, int height) {
        CameraManager cameraManager = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);
        try {
            if (!mCameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
                throw new RuntimeException("Time out waiting to lock camera opening.");
            }

            String cameraId = cameraManager.getCameraIdList()[1]; // Default to back camera
            /*for (String id : cameraManager.getCameraIdList()) {
                CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(id);
                int cameraFacing = characteristics.get(CameraCharacteristics.LENS_FACING);
                if (cameraFacing == mCameraFacing) {
                    cameraId = id;
                    break;
                }
            }*/

            CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics
                    .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            mVideoSize = CameraUtil.chooseVideoSize(map.getOutputSizes(MediaRecorder.class));
            mPreviewSize = CameraUtil.chooseVideoSize(map.getOutputSizes(SurfaceTexture.class));

            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mCameraLayout.setAspectRatio(mPreviewSize.getWidth(), mPreviewSize.getHeight());
            } else {
                mCameraLayout.setAspectRatio(mPreviewSize.getHeight(), mPreviewSize.getWidth());
            }

            int sensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
            if (sensorOrientation == 270) {
                // Camera is mounted the wrong way...
                upsideDown = true;
            }

            configureTransform(width, height);
            mMediaRecorder = new MediaRecorder();
            cameraManager.openCamera(cameraId, mStateCallback, null);

        } catch (CameraAccessException cae) {
            cae.printStackTrace();
            this.onCameraException(cae);
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            this.onNullPointerException(npe);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            this.onInterruptedException(ie);
            throw new RuntimeException("Interrupted while trying to lock camera opening.");
        } catch (SecurityException se) {
            se.printStackTrace();
        }
    }

    private void closeCamera() {
        try {
            mCameraOpenCloseLock.acquire();
            if (mPreviewSession != null) {
                mPreviewSession.close();
                mPreviewSession = null;
            }
            if (mCameraDevice != null) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
            if (mMediaRecorder != null) {
                mMediaRecorder.stop();
                mMediaRecorder.release();
                mMediaRecorder = null;
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            this.onInterruptedException(ie);
            throw new RuntimeException("Interrupted while trying to lock camera closing.");
        } finally {
            mCameraOpenCloseLock.release();
        }
    }

    private void startPreview() {
        if (mCameraDevice == null || !mCameraLayout.isAvailable() || mPreviewSize == null) {
            return;
        }
        try {
            setUpMediaRecorder();
            mCameraDevice.createCaptureSession(getSurfaces(), mSessionCallback, mBackgroundHandler);
        } catch (CameraAccessException cae) {
            cae.printStackTrace();
            this.onCameraException(cae);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            this.onIOException(ioe);
        }
    }

    private List<Surface> getSurfaces() {
        List<Surface> surfaces = new ArrayList<>();
        try {
            SurfaceTexture texture = mCameraLayout.getSurfaceTexture();
            assert texture != null;
            texture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
            mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_RECORD);

            Surface previewSurface = new Surface(texture);
            surfaces.add(previewSurface);
            mPreviewBuilder.addTarget(previewSurface);

            Surface recorderSurface = mMediaRecorder.getSurface();
            surfaces.add(recorderSurface);
            mPreviewBuilder.addTarget(recorderSurface);
        } catch (CameraAccessException cae) {
            cae.printStackTrace();
            this.onCameraException(cae);
        }

        return surfaces;
    }

    private void updatePreview() {
        if (mCameraDevice == null) {
            return;
        }
        try {
            setUpCaptureRequestBuilder(mPreviewBuilder);
            HandlerThread thread = new HandlerThread("CameraPreview");
            thread.start();
            mPreviewSession.setRepeatingRequest(mPreviewBuilder.build(), null, mBackgroundHandler);
        } catch (CameraAccessException cae) {
            cae.printStackTrace();
            this.onCameraException(cae);
        }
    }

    protected void setUpCaptureRequestBuilder(CaptureRequest.Builder builder) {
        builder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
    }

    private void configureTransform(int viewWidth, int viewHeight) {
        if (mCameraLayout == null || mPreviewSize == null) {
            return;
        }
        int rotation = this.getWindowManager().getDefaultDisplay().getRotation();
        Matrix matrix = new Matrix();
        RectF viewRect = new RectF(0, 0, viewWidth, viewHeight);
        RectF bufferRect = new RectF(0, 0, mPreviewSize.getHeight(), mPreviewSize.getWidth());
        float centerX = viewRect.centerX();
        float centerY = viewRect.centerY();
        if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
            bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY());
            matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL);
            float scale = Math.max(
                    (float) viewHeight / mPreviewSize.getHeight(),
                    (float) viewWidth / mPreviewSize.getWidth());
            matrix.postScale(scale, scale, centerX, centerY);
            matrix.postRotate(90 * (rotation - 2), centerX, centerY);
        }
        mCameraLayout.setTransform(matrix);
    }

    protected void setUpMediaRecorder() throws IOException {
        File file = new File(path + File.separator + "Thani" + count + ".mp4");
        mCurrentFile = file;
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mMediaRecorder.setOutputFile(file.getAbsolutePath());
        mMediaRecorder.setVideoEncodingBitRate(1600 * 1000);
        mMediaRecorder.setVideoFrameRate(30);
        mMediaRecorder.setVideoSize(mVideoSize.getWidth(), mVideoSize.getHeight());
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        int rotation = this.getWindowManager().getDefaultDisplay().getRotation();
        int orientation = CameraUtil.getOrientation(rotation, upsideDown);
        mMediaRecorder.setOrientationHint(orientation);
        mMediaRecorder.prepare();
    }

    // Default Cam2Listener events

    @Override
    public void onCameraException(CameraAccessException cae) {
        cae.printStackTrace();
    }

    @Override
    public void onNullPointerException(NullPointerException npe) {
        npe.printStackTrace();
    }

    @Override
    public void onInterruptedException(InterruptedException ie) {
        ie.printStackTrace();
    }

    @Override
    public void onIOException(IOException ioe) {
        ioe.printStackTrace();
    }

    @Override
    public void onConfigurationFailed() {
        Log.e(TAG, "Failed to configure camera");
    }
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
        khanda = new HashMap<Integer, Integer>();
        khanda.put(0, R.mipmap.realhand_foreground);
        khanda.put(1, R.mipmap.hello);
        khanda.put(2, R.mipmap.realhand_foreground);
        khanda.put(3, R.mipmap.hello);
        khanda.put(4, R.mipmap.hello);
        khanda.put(5, R.mipmap.five_foreground);
        khanda.put(6, R.mipmap.four_foreground);
        khanda.put(7, R.mipmap.three_foreground);
        khanda.put(8, R.mipmap.two_foreground);
        khanda.put(9, R.mipmap.one_foreground);
        rupa = new HashMap<Integer, Integer>();
        rupa.put(0, R.mipmap.realhand_foreground);
        rupa.put(1, R.mipmap.hello);
        rupa.put(2, R.mipmap.realhand_foreground);
        rupa.put(3, R.mipmap.hello);
        rupa.put(4, R.mipmap.realturn_foreground);
        rupa.put(5, R.mipmap.hello);
        rupa.put(6, R.mipmap.six_foreground);
        rupa.put(7, R.mipmap.five_foreground);
        rupa.put(8, R.mipmap.four_foreground);
        rupa.put(9, R.mipmap.three_foreground);
        rupa.put(10, R.mipmap.two_foreground);
        rupa.put(11, R.mipmap.one_foreground);
        cl = findViewById(R.id.cl);
        SoundPool.Builder builder = new SoundPool.Builder();
        pool = builder.build();
        text = findViewById(R.id.textView3);
        soundid = pool.load(this, R.raw.clap, 1);
        count = getIntent().getIntExtra(Intent.EXTRA_INDEX, 0);
        image = findViewById(R.id.imageView);
        image2 = findViewById(R.id.imageView2);
        image3 = findViewById(R.id.imageView3);
        speed2 = findViewById(R.id.seekBar);
        speedText = findViewById(R.id.textView4);
        audio = findViewById(R.id.switch2);
        //speed = findViewById(R.id.editText);
        //speed.setText("80");
        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath() + File.separator;
        record = new MediaRecorder();
        record.setAudioSource(MediaRecorder.AudioSource.MIC);
        record.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        record.setOutputFile(path + "Thani" + (count + 1) + ".mp4");
        record.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            record.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    run();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 2);
                }
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        } else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},0);
        }
    }
    public void run() {
        audio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                audioBool = isChecked;
                if (audioBool) {
                    mCameraLayout.setVisibility(View.INVISIBLE);
                } else {
                    mCameraLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        mCameraLayout = findViewById(R.id.camera_preview);
        mRationaleMessage = getString(com.wesley.camera2.R.string.camera2_permission_message);
        startBackgroundThread();
        if (mCameraLayout.isAvailable()) {
            openCamera(mCameraLayout.getWidth(), mCameraLayout.getHeight());
        } else {
            mCameraLayout.setSurfaceTextureListener(mSurfaceTextureListener);
        }
        count++;
        toggle = findViewById(R.id.toggleButton);
        speed2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Integer prog = new Integer(progress);
                speedText.setText(prog.toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                view = buttonView;
                final Intent intent = getIntent();
                //if ()
                if (isChecked) {
                    toggle.setClickable(false);
                    if (!audioBool) {
                        startRecordingVideo();
                    } else {
                        returnval.putExtra("Audio", true);
                        record.start();
                    }
                    text.setVisibility(View.INVISIBLE);
                    speed2.setVisibility(View.INVISIBLE);
                    speedText.setVisibility(View.INVISIBLE);
                    int speedNum;
                    speedNum = Integer.parseInt(speedText.getText().toString());
                    returnval.putExtra(Intent.ACTION_PACKAGE_REMOVED, speedNum);
                    if (intent.getStringExtra(Intent.EXTRA_TEXT).equals("Adi Talam")) {
                        hi = new CountDownTimer(1000000000, 60000 / speedNum) {
                            public void onTick(long millisUntilFinished) {
                                if (millisUntilFinished <= 999999800) {
                                    toggle.setClickable(true);
                                }
                                image.setImageResource(adi.get(i));
                                image2.setImageResource(adi.get(i + 8));
                                pool.play(soundid, 1, 1, 1, 0, 1);
                                //player.start();
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
                        hi = new CountDownTimer(1000000000, 60000 / (speedNum * 2)) {
                            public void onTick(long millisUntilFinished) {
                                if (millisUntilFinished <= 999999900) {
                                    toggle.setClickable(true);
                                }
                                if (i == 0 || i == 3 || i == 5) {
                                    pool.play(soundid, 1, 1, 1, 0, 1);
                                }
                                image.setImageResource(mishra.get(i));
                                image2.setImageResource(mishra.get(i + 7));
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
                    } else if (intent.getStringExtra(Intent.EXTRA_TEXT).equals("Khanda Chap")) {
                        hi = new CountDownTimer(1000000000, 60000 / (speedNum * 2)) {
                            public void onTick(long millisUntilFinished) {
                                if (millisUntilFinished <= 999999900) {
                                    toggle.setClickable(true);
                                }
                                if (i == 0 || i == 2) {
                                    pool.play(soundid, 1, 1, 1, 0, 1);
                                }
                                image.setImageResource(khanda.get(i));
                                image2.setImageResource(khanda.get(i + 5));
                                i++;
                                if (i >= 5) {
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
                    } else if (intent.getStringExtra(Intent.EXTRA_TEXT).equals("Rupaka Talam")) {
                        hi = new CountDownTimer(1000000000, 60000 / (speedNum * 2)) {
                            public void onTick(long millisUntilFinished) {
                                if (millisUntilFinished <= 999999900) {
                                    toggle.setClickable(true);
                                }
                                if (i == 0 || i == 2 || i == 4) {
                                    pool.play(soundid, 1, 1, 1, 0, 1);
                                }
                                image.setImageResource(rupa.get(i));
                                image2.setImageResource(rupa.get(i + 6));
                                i++;
                                if (i >= 6) {
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
                    hi.start();
                } else {
                    hi.cancel();
                    if (!audioBool) {
                        stopRecordingVideo();
                    } else {
                        record.stop();
                        record.release();
                    }
                    image.setImageResource(R.mipmap.hello);
                    image2.setImageResource(R.mipmap.hello);
                    image3.setImageResource(R.mipmap.hello);
                    toggle.setVisibility(View.INVISIBLE);
                    image.setVisibility(View.INVISIBLE);
                    image2.setVisibility(View.INVISIBLE);
                    image3.setVisibility(View.INVISIBLE);
                    mCameraLayout.setVisibility(View.INVISIBLE);
                    rename(intent);
                }

            }
        });
    }
    public void override(View view, final String name) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popview = inflater.inflate(R.layout.name, null);
        final PopupWindow popup = new PopupWindow(popview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setOutsideTouchable(false);
        popup.showAtLocation(getWindow().getDecorView().getRootView(), Gravity.CENTER, 0, 0);
        Button yes = popview.findViewById(R.id.button3);
        Button no = popview.findViewById(R.id.button2);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
                File file1 = new File(path + "Thani" + (count - 1) + ".mp4");
                File file = new File(path + name + ".mp4");
                file1.renameTo(file);
                returnval.putExtra(Intent.EXTRA_REFERRER_NAME, true);
                returnval.putExtra(Intent.EXTRA_STREAM, name);
                finish();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
                rename(getIntent());
            }
        });
    }

    public void rename(Intent intent) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popview = inflater.inflate(R.layout.popup, null);
        final PopupWindow pop = new PopupWindow(popview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setOutsideTouchable(false);
        pop.setFocusable(true);
        pop.showAtLocation(getWindow().getDecorView().getRootView(), Gravity.CENTER, 0, 0);
        setResult(RESULT_OK, returnval);
        returnval.putExtra(Intent.EXTRA_TEXT, count);
        returnval.putExtra(Intent.EXTRA_PACKAGE_NAME, intent.getStringExtra(Intent.EXTRA_TEXT));
        FloatingActionButton del = popview.findViewById(R.id.floatingActionButton3);
        FloatingActionButton save = popview.findViewById(R.id.floatingActionButton4);
        final EditText name = popview.findViewById(R.id.editText2);
        name.setText("Thani" + count);
        pop.update();
        buttonClick = false;
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick = true;
                pop.dismiss();
                File file1 = new File(path + "Thani" + count + ".mp4");
                file1.delete();
                setResult(RESULT_CANCELED, returnval);
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick = true;
                pop.dismiss();
                if (Datatofile.contains(getIntent().getStringArrayExtra(Intent.EXTRA_REFERRER), name.getText().toString())) {
                    Record.this.override(v, name.getText().toString());
                } else {
                    File file1 = new File(path + "Thani" + count + ".mp4");
                    File file = new File(path + name.getText().toString() + ".mp4");
                    file1.renameTo(file);
                    returnval.putExtra(Intent.EXTRA_STREAM, name.getText().toString());
                    finish();
                }
            }
        });
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (!buttonClick) {
                    File file1 = new File(path + "Thani" + count + ".mp4");
                    File file = new File(path + name.getText().toString() + ".mp4");
                    file1.renameTo(file);
                    returnval.putExtra(Intent.EXTRA_STREAM, name.getText().toString());
                    finish();
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
                } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 2);
                } else {
                    run();
                }
            }
        } else if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                setResult(RESULT_CANCELED);
                finish();
            } else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 2);
                } else {
                    run();
                }
            }
        } else if (requestCode == 2) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                setResult(RESULT_CANCELED);
                finish();
            } else {
                run();
            }
        }
    }

    @Override
    public void onBackPressed() {
        ConstraintLayout layout = findViewById(R.id.cl);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popview = inflater.inflate(R.layout.backpop, null);
        final PopupWindow pop = new PopupWindow(popview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setOutsideTouchable(false);
        pop.setFocusable(true);
        pop.showAtLocation(layout, Gravity.CENTER, 0, 0);
        Button yes = popview.findViewById(R.id.yes);
        Button no = popview.findViewById(R.id.no);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                setResult(RESULT_CANCELED);
                if (hi != null) {
                    hi.cancel();
                }
                finish();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });
    }

    @Override
    protected void onStop() {
        if (isRecording) {
            stopRecordingVideo();
        }
        if (hi != null) {
            hi.cancel();
        }
        finish();
        super.onStop();
    }
}

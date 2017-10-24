package com.frame.z.accumulate.media;

import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.z.accumulate.R;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 音频基础
 */
public class MediaActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    @BindView(R.id.surfaceview)
    SurfaceView surfaceview;
    @BindView(R.id.imageview)
    ImageView imageview;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.btnStartStop)
    Button btnStartStop;
    @BindView(R.id.btnPlayVideo)
    Button btnPlayVideo;

    private MediaRecorder recorder;
    private MediaPlayer player;
    private SurfaceHolder surfaceHolder;
    private Camera camera;

    private boolean mStartedFlg = false;//是否正在录像
    private boolean mIsPlay = false;//是否正在播放录像
    private String path;  //路径
    private int time = 0;

    //计时
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            time++;
            text.setText(time + "");
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        ButterKnife.bind(this);

        SurfaceHolder holder = surfaceview.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        btnStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsPlay) {
                    if (player != null) {
                        mIsPlay = false;
                        player.stop();
                        player.reset();
                        player.release();
                        player = null;
                    }
                }
                if (!mStartedFlg) {
                    handler.postDelayed(runnable, 1000);
                    imageview.setVisibility(View.GONE);
                    if (recorder == null) {
                        recorder = new MediaRecorder();
                    }

                    camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
                    if (camera != null) {
                        camera.setDisplayOrientation(90);
                        camera.unlock();
                        recorder.setCamera(camera);
                    }

                    try {

                        recorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
                        recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

                        //设置输出文件格式
                        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                        //设置编码格式
                        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                        recorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);

                        recorder.setVideoSize(640, 480); //设置视频的分辨率
                        recorder.setVideoFrameRate(30); //帧速率
                        recorder.setVideoEncodingBitRate(3 * 1024 * 1024); //设置的越大，视频越清晰
                        recorder.setOrientationHint(90); //方向
                        recorder.setMaxDuration(30 * 1000); //设置记录会话的最大持续时间（毫秒）
                        recorder.setPreviewDisplay(surfaceHolder.getSurface());

                        path = getSDPath();
                        if (path != null) {
                            File dir = new File(path + "/recordtest");
                            if (!dir.exists()) {
                                dir.mkdir();
                            }
                            path = dir + "/" + getDate() + ".mp4";

                            recorder.setOutputFile(path);
                            recorder.prepare();
                            recorder.start();
                            mStartedFlg = true;
                            btnStartStop.setText("stop");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //stop
                    try {
                        handler.removeCallbacks(runnable);
                        recorder.stop();
                        recorder.reset();
                        recorder.release();
                        recorder = null;
                        btnStartStop.setText("Start");
                        if (camera != null) {
                            camera.release();
                            camera = null;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mStartedFlg = false;
                }
            }
        });

        btnPlayVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsPlay = true;
                imageview.setVisibility(View.GONE);
                if (player == null) {
                    player = new MediaPlayer();
                }
                player.reset();
                Uri uri = Uri.parse(path);
                player = MediaPlayer.create(MediaActivity.this, uri);
                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                player.setDisplay(surfaceHolder);
                try {
                    player.prepare();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                player.start();
            }
        });

    }


   /* @OnClick({R.id.btnStartStop, R.id.btnPlayVideo})
    public void onViewClicked(View view) throws IOException {
        switch (view.getId()) {
            case R.id.btnStartStop:
                if (mIsPlay) {
                    if (player != null) {
                        mIsPlay = false;
                        player.stop();
                        player.reset();
                        player.release();
                        player = null;
                    }
                }
                if (!mStartedFlg) {
                    handler.postDelayed(runnable, 1000);
                    imageview.setVisibility(View.GONE);
                    if (recorder == null) {
                        recorder = new MediaRecorder();
                    }

                    camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
                    if (camera != null) {
                        camera.setDisplayOrientation(90);
                        camera.unlock();
                        recorder.setCamera(camera);
                    }

                    try {

                        recorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
                        recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

                        //设置输出文件格式
                        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                        //设置编码格式
                        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                        recorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);

                        recorder.setVideoSize(640, 480); //设置视频的分辨率
                        recorder.setVideoFrameRate(30); //帧速率
                        recorder.setVideoEncodingBitRate(3 * 1024 * 1024); //设置的越大，视频越清晰
                        recorder.setOrientationHint(90); //方向
                        recorder.setMaxDuration(30 * 1000); //设置记录会话的最大持续时间（毫秒）
                        recorder.setPreviewDisplay(surfaceHolder.getSurface());

                        path = getSDPath();
                        if (path != null) {
                            File dir = new File(path + "/recordtest");
                            if (!dir.exists()) {
                                dir.mkdir();
                            }
                            path = dir + "/" + getDate() + ".mp4";

                            recorder.setOutputFile(path);
                            recorder.prepare();
                            recorder.start();
                            mStartedFlg = true;
                            btnStartStop.setText("stop");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //stop
                    try {
                        handler.removeCallbacks(runnable);
                        recorder.stop();
                        recorder.reset();
                        recorder.release();
                        recorder = null;
                        btnStartStop.setText("Start");
                        if (camera != null) {
                            camera.release();
                            camera = null;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mStartedFlg = false;
                }
                break;
            case R.id.btnPlayVideo:
                mIsPlay = true;
                imageview.setVisibility(View.GONE);
                if (player != null) {
                    player = new MediaPlayer();
                }
                player.reset();
                Uri uri = Uri.parse(path);
                player = MediaPlayer.create(MediaActivity.this, uri);
                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                player.setDisplay(surfaceHolder);
                try {
                    player.prepare();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                player.start();

                break;
        }
    }*/

    /**
     * @return
     */
    private String getDate() {

        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);           // 获取年份
        int month = ca.get(Calendar.MONTH);         // 获取月份
        int day = ca.get(Calendar.DATE);            // 获取日
        int minute = ca.get(Calendar.MINUTE);       // 分
        int hour = ca.get(Calendar.HOUR);           // 小时
        int second = ca.get(Calendar.SECOND);       // 秒

        String date = "" + year + (month + 1) + day + hour + minute + second;

        return date;
    }

    /**
     * 获取存储文件路径
     */
    private String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
            return sdDir.toString();
        }
        return null;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.surfaceHolder = holder;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.surfaceHolder = holder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        surfaceview = null;
        surfaceHolder = null;
        handler.removeCallbacks(runnable);
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }
        if (player != null) {
            player.release();
            player = null;
        }

    }
}

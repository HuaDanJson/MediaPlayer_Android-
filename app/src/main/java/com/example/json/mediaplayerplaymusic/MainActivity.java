package com.example.json.mediaplayerplaymusic;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.IOException;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    @BindView(R.id.btnStart)
    Button btnStart;
    @BindView(R.id.btnPause)
    Button btnPause;
    @BindView(R.id.btnStop)
    Button btnStop;
    @BindView(R.id.btnIncreaseVoice)
    Button btnIncreaseVoice;
    @BindView(R.id.btnReduceVoice)
    Button btnReduceVoice;
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private int maxVolume; // 最大音量值
    private int currVolume; // 当前音量值
    private int stepVolume; // 每次调整的音量幅度


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mediaPlayer = MediaPlayer.create(this, R.raw.night);
        btnStart.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnIncreaseVoice.setOnClickListener(this);
        btnReduceVoice.setOnClickListener(this);
        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//获取最大音量
        stepVolume = maxVolume/6;// 每次调整的音量大概为最大音量的1/6
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStart:
                mediaPlayer.start();//开始播放
                break;
            case R.id.btnPause:
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();//暂停
                }
                break;
            case R.id.btnStop:
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();//停止
                    try {
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btnIncreaseVoice:
                currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);//获取当前音量
                //增加音量，但不超过最大音量值
                int tmpVolume = currVolume + stepVolume; //临时音量
                currVolume = tmpVolume<maxVolume?tmpVolume:maxVolume;
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,currVolume,AudioManager.FLAG_PLAY_SOUND);
                Toast.makeText(MainActivity.this, "增大音量", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnReduceVoice:
                currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);//获取当前音量
                //增加音量，但不超过最大音量值
                int tmpVolume2 = currVolume - stepVolume; //临时音量
                currVolume = tmpVolume2>0?tmpVolume2:0;
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,currVolume,AudioManager.FLAG_PLAY_SOUND);
                Toast.makeText(MainActivity.this, "减小音量", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}

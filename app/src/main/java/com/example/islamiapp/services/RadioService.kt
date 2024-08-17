package com.example.islamiapp.services

import android.Manifest
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.widget.RemoteViews
import android.widget.RemoteViews.RemoteView
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.islamiapp.R
import com.example.islamiapp.util.Constants
import com.example.islamiapp.util.Constants.CHANNEL_ID

class RadioService : Service() {
    enum class RadioState {ACTION,PLAY,PAUSE,STOP}
    val mMediaPlayer : MediaPlayer by lazy {MediaPlayer()}
    var mIsMpPrepared = false
    var mName = ""

    override fun onBind(intent: Intent): IBinder {
        return RadioBinder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val radioName = intent?.getStringExtra(Constants.RADIO_NAME)
        val radioURL = intent?.getStringExtra(Constants.RADIO_URL)

        if (radioName != null && radioURL != null) initMediaPlayer(radioName,radioURL)

        val action = intent?.getStringExtra(RadioState.ACTION.name)

        when(action) {
            RadioState.PLAY.name,RadioState.PAUSE.name -> {
                togglePlayMedia()
            }

            RadioState.STOP.name -> {
                stopMediaPlayer()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    fun initMediaPlayer(radioName: String, radioURL: String) {
        mName = radioName
        mMediaPlayer.apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(radioURL)
            prepareAsync()
            setOnPreparedListener { mp ->
                mIsMpPrepared = true
                createNotification(mName)
            }
        }
    }

    fun isMpPlaying() = mMediaPlayer.isPlaying

    fun stopMediaPlayer(){
        if(isMpPlaying()){
            mMediaPlayer.stop()
            mMediaPlayer.release()
        }
    }

    fun togglePlayMedia(){
        mMediaPlayer.apply {
            if(!isPlaying) startMediaPlayer()
            else pause()
        }
        updateNotification()
    }

    private fun startMediaPlayer() {
        if(mIsMpPrepared) mMediaPlayer.start()
    }

    private fun createNotification(name : String){
        val remoteView = RemoteViews(packageName,R.layout.notification_view)
        remoteView.apply {
            setTextViewText(R.id.title , getString(R.string.app_name))
            setTextViewText(R.id.description , name)
            setImageViewResource(R.id.play , if(mMediaPlayer.isPlaying) R.drawable.ic_pause else R.drawable.baseline_play_arrow_24)
            setOnClickPendingIntent(R.id.play,getPendingIntent())
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.quran_screen_logo)
            .setCustomContentView(remoteView)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        startForeground(1,notification)

    }

    private fun getPendingIntent(): PendingIntent? {
        val intent = Intent(this, RadioService::class.java)
        if(mMediaPlayer.isPlaying)
            intent.putExtra(RadioState.ACTION.name, RadioState.PAUSE)
        else
            intent.putExtra(RadioState.ACTION.name, RadioState.PLAY)

        return PendingIntent.getService(this, 12,intent,FLAG_IMMUTABLE)
    }

    private fun updateNotification(){
        val remoteView = RemoteViews(packageName,R.layout.notification_view)
        remoteView.apply {
            setTextViewText(R.id.title , getString(R.string.app_name))
            setTextViewText(R.id.description , mName)
            setImageViewResource(R.id.play , if(mMediaPlayer.isPlaying) R.drawable.ic_pause else R.drawable.baseline_play_arrow_24)
            setOnClickPendingIntent(R.id.play,getPendingIntent())
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.quran_screen_logo)
            .setCustomContentView(remoteView)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationManager =  getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(1,notification)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopMediaPlayer()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }
    inner class RadioBinder : Binder(){
        fun getService() : RadioService {
            return this@RadioService
        }

    }
}
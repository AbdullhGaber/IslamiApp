package com.example.islamiapp.ui.fragments.home.radio.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.islamiapp.R
import com.example.islamiapp.data.models.Radio
import com.example.islamiapp.databinding.FragmentRadioBinding
import com.example.islamiapp.services.RadioService


class RadioFragment : Fragment() {
    private lateinit var mBinding : FragmentRadioBinding
    private lateinit var mRadiosList : List<Radio>
    private var mCurrentIndex = 0
    private var mRadioService : RadioService? = null
    private var mBound = false
    val mServiceConnection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as RadioService.RadioBinder
            mRadioService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mRadioService = null
            mBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        bindService()
        startService()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = FragmentRadioBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getRadiosList()
        bindUI()
    }

    private fun onNextClick(){
        mBinding.nextIcIv.setOnClickListener {
            mRadioService?.stopMediaPlayer()
            mBinding.playIconIv.setImageResource(R.drawable.baseline_play_arrow_24)
            mCurrentIndex = if (mCurrentIndex
                == mRadiosList.size-1) 0 else ++mCurrentIndex
            mRadioService?.initMediaPlayer(mRadiosList[mCurrentIndex].name,mRadiosList[mCurrentIndex].url)
            mBinding.quranStation.text = mRadiosList[mCurrentIndex].name
        }
    }

    private fun onPreviousClick(){
        mBinding.previousIconIv.setOnClickListener {
            mRadioService?.stopMediaPlayer()
            mBinding.playIconIv.setImageResource(R.drawable.baseline_play_arrow_24)
            mCurrentIndex = if (mCurrentIndex == 0)  mRadiosList.size-1 else --mCurrentIndex
            mRadioService?.initMediaPlayer(mRadiosList[mCurrentIndex].name,mRadiosList[mCurrentIndex].url)
            mBinding.quranStation.text = mRadiosList[mCurrentIndex].name
        }
    }

    private fun onPlayClick(){
        mBinding.playIconIv.setOnClickListener {
            mRadioService?.let {
                it.togglePlayMedia()
                if(it.isMpPlaying()){
                    mBinding.playIconIv.setImageResource(R.drawable.ic_pause)
                }else{
                    mBinding.playIconIv.setImageResource(R.drawable.baseline_play_arrow_24)
                }
            }
        }
    }

    private fun bindUI(){
        val radioName = mRadiosList[mCurrentIndex].name
        val radioURL = mRadiosList[mCurrentIndex].url
        mBinding.quranStation.text = radioName
        mRadioService?.initMediaPlayer(radioName,radioURL)
        onPlayClick()
        onNextClick()
        onPreviousClick()
    }



    private fun startService(){
        val intent = Intent(requireContext(),RadioService::class.java)
        requireActivity().startService(intent)
    }

    private fun bindService(){
        val intent = Intent(requireContext(),RadioService::class.java)
        requireActivity().bindService(intent,mServiceConnection,Context.BIND_AUTO_CREATE)
    }

    private fun getRadiosList() {
        mRadiosList = listOf(
            Radio(
                "إذاعة إبراهيم الأخضر",
                "https://backup.qurango.net/radio/ibrahim_alakdar"
            ),
            Radio("إذاعة القارئ ياسين", "https://backup.qurango.net/radio/alqaria_yassen"),
            Radio("إذاعة أحمد الطرابلسي", "https://backup.qurango.net/radio/ahmed_altrabulsi")
        )
    }

    override fun onStop() {
        super.onStop()
        requireActivity().unbindService(mServiceConnection)
    }
}
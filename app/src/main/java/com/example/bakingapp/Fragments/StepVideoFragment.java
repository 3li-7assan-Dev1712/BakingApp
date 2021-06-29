package com.example.bakingapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;

public class StepVideoFragment extends Fragment {
    private String videoUrl;
    private PlayerView mPlayerView;
    private SimpleExoPlayer mSimpleExoPLayer;


    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public StepVideoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*Inflate the steps layout*/
        View view = inflater.inflate(R.layout.instruction_land_fragment, container, false);
        if (savedInstanceState != null){
            videoUrl = savedInstanceState.getString("ali");
        }
        mPlayerView = view.findViewById(R.id.playerView);
        mSimpleExoPLayer= new SimpleExoPlayer.Builder(getContext())
                .setTrackSelector(new DefaultTrackSelector(getContext()))
                .setLoadControl(new DefaultLoadControl()).build();
        if (videoUrl.equals("")){
            view.findViewById(R.id.noVideoText).setVisibility(View.VISIBLE);
            view.findViewById(R.id.noVideoImage).setVisibility(View.VISIBLE);
            mPlayerView.setVisibility(View.INVISIBLE);
        }else{
            view.findViewById(R.id.noVideoText).setVisibility(View.GONE);
            view.findViewById(R.id.noVideoImage).setVisibility(View.GONE);
            view.findViewById(R.id.playerView).setVisibility(View.VISIBLE);
            MediaItem mediaItem = MediaItem.fromUri(videoUrl);
            mSimpleExoPLayer.setMediaItem(mediaItem);
            mSimpleExoPLayer.prepare();
            mSimpleExoPLayer.setPlayWhenReady(true);
        }
        mPlayerView.setPlayer(mSimpleExoPLayer);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("ali", videoUrl);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSimpleExoPLayer.release();
    }
}

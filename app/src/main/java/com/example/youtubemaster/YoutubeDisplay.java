package com.example.youtubemaster;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.facebook.FacebookSdk;

public class YoutubeDisplay extends YouTubeBaseActivity implements
		OnInitializedListener {

	private YouTubePlayerView youTubeView;
	private String videoId;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.player);
		videoId = getIntent().getExtras().getString("id");
		youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);

		try {
			youTubeView.initialize(Config.DEVELOPER_KEY, this);
		}
		catch (Exception e)
		{
			System.out.println("Wuttt");
}

	}

	@Override
	public void onInitializationFailure(Provider arg0,
			YouTubeInitializationResult arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInitializationSuccess(Provider arg0, YouTubePlayer player,
			boolean wasRestored) {
		// TODO Auto-generated method stub

		player.loadVideo(videoId);
		 
        // Hiding player controls
        player.setPlayerStyle(PlayerStyle.CHROMELESS);

	}

}

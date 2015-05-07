package com.example.youtubemaster;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class YoutubeDisplay extends YouTubeBaseActivity implements
        OnInitializedListener, PopupMenu.OnMenuItemClickListener {

    private Activity ctxt;
    private YouTubePlayerView youTubeView;
    private Item holderItem;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.player);
        ctxt = this;
        holderItem = (Item) getIntent().getExtras().getSerializable("item");
        holderItem.getDescription();
        System.out.println("descslave" + holderItem.getCategory());
        System.out.println("onions & sour" + holderItem.getUrl());
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        try {
            youTubeView.initialize(Config.DEVELOPER_KEY, this);
        } catch (Exception e) {
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

        player.cueVideo(holderItem.getId());

        // Hiding player controls
        //player.setPlayerStyle(PlayerStyle.CHROMELESS);

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.facebook_selection:
                clickFacebook();
                return true;
            case R.id.twitter_selection:
                clickTwitter();
                return true;
        }
        return false;
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.main, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }

    private void clickFacebook() {

        System.out.println("pringles " + holderItem.getUrl());
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(holderItem.getUrl()))
                .setContentDescription(holderItem.getDescription())
                .setContentTitle(holderItem.getTitle())
                .setImageUrl(Uri.parse(holderItem.getThumbnail().getHqDefault()))
                .build();
        if (shareDialog.canShow(content));
        {
            System.out.println("canshowhohowhow");
            shareDialog.show(content);
        }
    }

    private void clickTwitter() {

        /*URL myURL;
        try {
            myURL = new URL(holderItem.getUrl());
        }
        catch (MalformedURLException e)
        {
            return;
        }

        TweetComposer.Builder builder = new TweetComposer.Builder(this)
                .text("just setting up my Fabric.")
                .image(Uri.parse(holderItem.getThumbnail().getHqDefault()));
                //.url(myURL);
        builder.show();*/
    }

    private class FacebookTask extends AsyncTask<ShareLinkContent, Void, Void> {

        @Override
        protected Void doInBackground(ShareLinkContent... s) {
            ShareDialog.show(ctxt, s[0]);
            return null;
        }
    }

    /*@Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }*/
}
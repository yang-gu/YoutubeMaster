package com.example.youtubemaster;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Android1 on 5/1/2015.
 */
public class ItemListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ImageView thumbnail;
    public TextView title;
    public TextView description;
    public TextView category;
    public TextView id;
    private Activity av;

    public ItemListViewHolder(Activity a, View v) {
        super(v);
        this.thumbnail = (ImageView)v.findViewById(R.id.thumb);
        this.title = (TextView)v.findViewById(R.id.title);
        this.description = (TextView)v.findViewById(R.id.description);
        this.category = (TextView)v.findViewById(R.id.category);
        this.id = (TextView)v.findViewById(R.id.id);
        av = a; 
    }

    @Override
    public void onClick(View view){
        Intent intent = new Intent(av,
                YoutubeDisplay.class);
        intent.putExtra("id", id.getText().toString());
        av.startActivity(intent);
    }
}

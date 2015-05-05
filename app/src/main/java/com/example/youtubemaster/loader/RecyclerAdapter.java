package com.example.youtubemaster.loader;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.youtubemaster.Item;
import com.example.youtubemaster.ItemListViewHolder;
import com.example.youtubemaster.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Android1 on 5/1/2015.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<ItemListViewHolder> {

    private List<Item> mItemList;
    private Activity mContext;

    public RecyclerAdapter(Activity ctxt, List<Item> list) {
        mContext = ctxt;
        mItemList = list;
    }

    @Override
    public ItemListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
        ItemListViewHolder mh = new ItemListViewHolder(mContext, v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemListViewHolder ilvh, int i) {
        Item item = mItemList.get(i);
        Picasso.with(mContext).load(item.getThumbnail().getSqDefault()).into(ilvh.thumbnail);
        ilvh.title.setText(item.getTitle());
        ilvh.description.setText(item.getDescription());
        ilvh.category.setText(item.getCategory());

    }

    @Override
    public int getItemCount() {
        return (null != mItemList ? mItemList.size() : 0);
    }

    public void clear() {
        mItemList.clear();
    }

    public void add(Item i) {
        mItemList.add(i);
    }
}

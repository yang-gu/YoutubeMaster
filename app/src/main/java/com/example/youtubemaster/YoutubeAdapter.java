package com.example.youtubemaster;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youtubemaster.lazyload.ImageManager;
import com.squareup.picasso.Picasso;

public class YoutubeAdapter extends ArrayAdapter<Item> {
	private List<Item> li;
	Activity ctxt;
	public ImageManager imageManager;

	class ViewHolder {

	}

	public YoutubeAdapter(Activity ctxt, int rId, List<Item> li) {
		super(ctxt, rId, li);
		this.li = li;
		this.ctxt = ctxt;
		imageManager = new ImageManager(ctxt.getApplicationContext(), 100);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return li.size();
	}

	@Override
	public Item getItem(int position) {
		// TODO Auto-generated method stub
		return li.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) ctxt
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_item, parent, false);
		ImageView thumb = (ImageView) rowView.findViewById(R.id.thumb);
		TextView title = (TextView) rowView.findViewById(R.id.title);
		TextView description = (TextView) rowView
				.findViewById(R.id.description);
		TextView category = (TextView) rowView.findViewById(R.id.category);

		Item item = li.get(position);

		Picasso.with(ctxt).load(item.getThumbnail().getSqDefault()).into(thumb);

		title.setText(item.getTitle());
		description.setText(item.getDescription());
		category.setText(item.getCategory());

		return rowView;
	}

	public void clear() {
		// TODO Auto-generated method stub
		li.clear();
	}

	public void add(Item i) {
		// TODO Auto-generated method stub
		li.add(i);
	}
}

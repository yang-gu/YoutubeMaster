package com.example.youtubemaster;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.youtubemaster.db.DBHelper;
import com.example.youtubemaster.db.ItemDataSource;
import com.example.youtubemaster.loader.SQLiteTestDataLoader;
import com.squareup.picasso.Picasso;

public class MainActivity extends ActionBarActivity implements
		LoaderManager.LoaderCallbacks<List<Item>> {
	private Activity ctxt;
	private static final int LOADER_ID = 1;
	private DBHelper mDbHelper;
	private static final boolean DEBUG = true;
	private static final String TAG = "MainActivity";
	private int i = 1;
	private YoutubeAdapter mYoutubeAdapter;
	private RecyclerAdapter mRecyclerAdapter;
	private ItemDataSource mDataSource;
	private RecyclerView mRecyclerView;
	private ListView lv;

	private SQLiteDatabase mDatabase;
	private SQLiteTestDataLoader mLoader;
	private boolean isLoading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ctxt = this;

		mDbHelper = new DBHelper(ctxt);
		mDatabase = mDbHelper.getWritableDatabase();
		mDataSource = new ItemDataSource(mDatabase);
		mYoutubeAdapter = new YoutubeAdapter(ctxt, R.layout.list_item,
				new ArrayList<Item>());

		mRecyclerAdapter = new RecyclerAdapter(this, new ArrayList<Item>());

		/* Initialize recycler view */
		mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mRecyclerView.setAdapter(mRecyclerAdapter);

		/*lv = (ListView) findViewById(R.id.listView);
		lv.setAdapter(mYoutubeAdapter);*/
/*		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Intent intent = new Intent(getApplicationContext(),
						YoutubeDisplay.class);
				intent.putExtra("id", mYoutubeAdapter.getItem(position).getId());
				startActivity(intent);
			}
		});*/
	}

	@Override
	protected void onResume() {
		super.onResume();

		mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

				LinearLayoutManager mLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
				int visibleItemCount = mLayoutManager.getChildCount();
				int totalItemsCount = mLayoutManager.getItemCount();
				int pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

				if (!isLoading && totalItemsCount > (visibleItemCount + pastVisiblesItems)) {
					i = totalItemsCount + 1;
					new ItemGetter().execute(i);
				}
			}
		});

		getLoaderManager().initLoader(LOADER_ID, null, this);

		new ItemGetter().execute(i);

		//new ItemIterator().execute(null, null, null);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		/*int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);*/
		return true;
	}

	@Override
	public Loader<List<Item>> onCreateLoader(int id, Bundle args) {
		SQLiteTestDataLoader loader = new SQLiteTestDataLoader(ctxt,
				mDataSource, null, null, null, null, null);
		mLoader = loader;
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<List<Item>> loader, List<Item> data) {
		isLoading = false;
		//mYoutubeAdapter.clear();
		mRecyclerAdapter.clear();

		for (Object o : data) {
			//mYoutubeAdapter.add((Item) o);
			mRecyclerAdapter.add((Item)o);
		}
		mRecyclerAdapter.notifyDataSetChanged();
//		mYoutubeAdapter.notifyDataSetChanged();
		/*
		 * lv.setOnScrollListener(new InfiniteScrollListener(35) {
		 * 
		 * @Override public void loadMore(int page, int totalItemsCount) {
		 * Log.e("loadmore", "LoadMoring " + new
		 * Integer(totalItemsCount).toString()); new
		 * ItemGetter().execute(totalItemsCount + 1); } });
		 */

	}

	@Override
	public void onLoaderReset(Loader<List<Item>> arg0) {
		mYoutubeAdapter.clear();
	}

	private class ItemGetter extends AsyncTask<Integer, Void, Void> {

		@Override
		protected Void doInBackground(Integer... ints) {
			isLoading = true;
			List<Item> li = YoutubeGetter.getItems(ints[0]);
			for (Item i : li) {
				mDataSource.insert(i);
			}
			mLoader.forceLoad();

			isLoading = false;

			return null;
		}
	}

	private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemListViewHolder> {

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
			ilvh.setItem(item);
			Picasso.with(mContext).load(item.getThumbnail().getSqDefault()).into(ilvh.thumbnail);
			ilvh.title.setText(item.getTitle());
			String testDesc;
			if (item.getDescription().length() < 50)
			{
				testDesc = item.getDescription();
			}
			else
			{
				testDesc = item.getDescription().substring(0, 47) + "...";
			}
			ilvh.description.setText(testDesc);
			ilvh.category.setText(item.getCategory());
			ilvh.id.setText(item.getId());

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

		public class ItemListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
			public ImageView thumbnail;
			public TextView title;
			public TextView description;
			public TextView category;
			public TextView id;
			private Context context;
			Item holderItem;

			public ItemListViewHolder(Context ctxt, View v) {
				super(v);
				this.thumbnail = (ImageView)v.findViewById(R.id.thumb);
				this.title = (TextView)v.findViewById(R.id.title);
				this.description = (TextView)v.findViewById(R.id.description);
				this.category = (TextView)v.findViewById(R.id.category);
				System.out.println("category is " + this.category.getText().toString());
				this.id = (TextView)v.findViewById(R.id.id);
				this.context = ctxt;
				v.setOnClickListener(this);
			}

			@Override
			public void onClick(View view){
				Intent intent = new Intent(ctxt,
						YoutubeDisplay.class);
				System.out.println("id is " + id.getText().toString());
				/*intent.putExtra("id", id.getText().toString());
				intent.putExtra("thumbnail", holderItem.getThumbnail().getSqDefault());
				intent.putExtra("url", holderItem.getUrl());
				intent.putExtra("description", holderItem.getDescription());
				intent.putExtra("title", holderItem.getTitle());*/

				holderItem.getDescription();

				intent.putExtra("item", holderItem);
				ctxt.startActivity(intent);
			}

			public void setItem(Item item) {
				holderItem = item;
			}
		}
	}
}

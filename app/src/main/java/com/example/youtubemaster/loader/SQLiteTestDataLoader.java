package com.example.youtubemaster.loader;

import java.util.List;
import android.content.Context;
import com.example.youtubemaster.db.DataSource;
import com.example.youtubemaster.Item;

public class SQLiteTestDataLoader extends AbstractDataLoader<List<Item>> {
	private DataSource mDataSource;
	private String mSelection;
	private String[] mSelectionArgs;
	private String mGroupBy;
	private String mHaving;
	private String mOrderBy;

	public SQLiteTestDataLoader(Context context, DataSource dataSource,
			String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy) {
		super(context);
		mDataSource = dataSource;
		mSelection = selection;
		mSelectionArgs = selectionArgs;
		mGroupBy = groupBy;
		mHaving = having;
		mOrderBy = orderBy;
	}

	@Override
	protected List buildList() {
		List testList = mDataSource.read(mSelection, mSelectionArgs, mGroupBy,
				mHaving, mOrderBy);
		return testList;
	}
}
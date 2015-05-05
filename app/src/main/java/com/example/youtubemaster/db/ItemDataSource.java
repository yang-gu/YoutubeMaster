package com.example.youtubemaster.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import junit.framework.Test;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.youtubemaster.Item;
import com.example.youtubemaster.Thumbnail;

public class ItemDataSource extends DataSource {

	public static final String TABLE_NAME = "item";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_CATEGORY = "category";
	public static final String COLUMN_SQ = "sq";
	public static final String COLUMN_HQ = "hq";

	// Database creation sql statement
	public static final String CREATE_COMMAND = "create table " + TABLE_NAME
			+ "(" + COLUMN_ID + " text primary key, " + COLUMN_TITLE
			+ " text not null, " + COLUMN_DESCRIPTION + " text not null, "
			+ COLUMN_CATEGORY + " text not null, " + COLUMN_SQ
			+ " text not null, " + COLUMN_HQ + " text not null);";

	public ItemDataSource(SQLiteDatabase database) {
		super(database);
	}

	@Override
	public boolean insert(Item item) {
		if (item == null) {
			return false;
		}

		String selectQuery = "SELECT * FROM item WHERE " + COLUMN_ID + " =\'"
				+ item.getId() + "\'";

		Cursor c = mDatabase.rawQuery(selectQuery, null);

		if (c.getCount() <= 0) {
			long result = mDatabase.insert(TABLE_NAME, null,
					generateContentValuesFromObject(item));

			return result != -1;
		}
		return true;
	}

	@Override
	public boolean delete(Item item) {
		if (item == null) {
			return false;
		}
		int result = mDatabase.delete(TABLE_NAME,
				COLUMN_ID + " = " + item.getId(), null);
		return result != 0;
	}

	@Override
	public boolean update(Item item) {
		if (item == null) {
			return false;
		}
		int result = mDatabase.update(TABLE_NAME,
				generateContentValuesFromObject(item),
				COLUMN_ID + " = " + item.getTitle(), null);
		return result != 0;
	}

	@Override
	public List<Test> read() {
		Cursor cursor = mDatabase.query(TABLE_NAME, getAllColumns(), null,
				null, null, null, null);
		List items = new ArrayList();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				items.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return items;
	}

	@Override
	public List<Item> read(String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy) {
		Cursor cursor = mDatabase.query(TABLE_NAME, getAllColumns(), selection,
				selectionArgs, groupBy, having, orderBy);
		List items = new ArrayList();
		if (cursor != null && cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				items.add(generateObjectFromCursor(cursor));
				cursor.moveToNext();
			}
			cursor.close();
		}
		return items;
	}

	public String[] getAllColumns() {
		return new String[] { COLUMN_ID, COLUMN_TITLE, COLUMN_DESCRIPTION,
				COLUMN_CATEGORY, COLUMN_SQ, COLUMN_HQ};
	}

	public Item generateObjectFromCursor(Cursor cursor) {
		if (cursor == null) {
			return null;
		}
		Random rand = new Random();
		Item item = new Item();
		item.setId(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
		System.out.println("db id is " + item.getId());
		item.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
		item.setDescription(cursor.getString(cursor
				.getColumnIndex(COLUMN_DESCRIPTION)));
		item.setCategory(cursor.getString(cursor
				.getColumnIndex(COLUMN_CATEGORY)));
		Thumbnail thumbnail = new Thumbnail();
		thumbnail.setSqDefault(cursor.getString(cursor
				.getColumnIndex(COLUMN_SQ)));
		thumbnail.setHqDefault(cursor.getString(cursor
				.getColumnIndex(COLUMN_HQ)));
		item.setThumbnail(thumbnail);

		return item;
	}

	public ContentValues generateContentValuesFromObject(Item item) {
		if (item == null) {
			return null;
		}

		Random rand = new Random();

		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, ((item.getId())));
		values.put(COLUMN_TITLE, item.getTitle());
		values.put(COLUMN_DESCRIPTION, item.getDescription());
		values.put(COLUMN_CATEGORY, item.getCategory());
		values.put(COLUMN_SQ, item.getThumbnail().getSqDefault());
		values.put(COLUMN_HQ, item.getThumbnail().getHqDefault());
		return values;
	}
	
	public int getCount(){
		String selectQuery = "SELECT count(*) from item";

		Cursor c = mDatabase.rawQuery(selectQuery, null);
		
		int count = c.getCount();
		c.close();
		return count;
	}

}

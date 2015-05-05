package com.example.youtubemaster.infiniteScrolling;

import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

public abstract class InfiniteScrollListener{
	private int bufferItemCount = 10;
	private int currentPage = 0;
	private int itemCount = 0;
	private boolean isLoading = false;

	public InfiniteScrollListener(int bufferItemCount) {
		this.bufferItemCount = bufferItemCount;
	}

	public abstract void loadMore(int page, int totalItemsCount);


	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// Do Nothing
	}

	public void onScroll(RecyclerView rv, int dx, int dy) {
		/*if (totalItemCount < itemCount) {
			System.out.println("firstBranch");
			this.itemCount = totalItemCount;
			if (totalItemCount == 0) {
				this.isLoading = true;
			}
		}

		if ((totalItemCount > itemCount)) {
			System.out.println("secondBranch");
			isLoading = false;
			itemCount = totalItemCount;
			currentPage++;
		}

		if (!isLoading
				&& (totalItemCount - visibleItemCount) <= (firstVisibleItem + bufferItemCount)) {
			System.out.println("thirdBranch");
			loadMore(currentPage + 1, totalItemCount);
			isLoading = true;
		}*/
		

		

	}
}

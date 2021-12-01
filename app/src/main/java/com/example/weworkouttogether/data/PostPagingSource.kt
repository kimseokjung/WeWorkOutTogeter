package com.example.weworkouttogether.data

import androidx.paging.PagingSource
import androidx.paging.PagingState

open class PostPagingSource(private val  postUrlDao: PostUrlDao): PagingSource<Int,PostSingleItem>(){

    companion object{
        const val INIT_PAGE_INDEX = 0
    }

    override fun getRefreshKey(state: PagingState<Int, PostSingleItem>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostSingleItem> {
        val position = params.key ?: INIT_PAGE_INDEX
        val loadData = postUrlDao.getPage(position,params.loadSize)

        return LoadResult.Page(
            data = loadData,
            prevKey = if(position == INIT_PAGE_INDEX) null else position -1,
            nextKey = if(loadData.isNullOrEmpty()) null else position +1
        )

    }


}
package com.example.coincapapp.feature.coinList.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.coincapapp.feature.coinList.data.CoinRepository
import com.example.coincapapp.feature.coinList.data.model.CoinResponse
import kotlinx.coroutines.delay

class CoinListPagingSource(
    private val repository: CoinRepository,
    private val searchQuery: String
) : PagingSource<Int, CoinResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CoinResponse> {
        return try {
            val nextPage = params.key ?: 1
            delay(3000)
            val response = repository.loadPagingCoins(
                searchQuery = searchQuery,
                limit = params.loadSize,
                offset = (nextPage - 1) * params.loadSize,
            ).data // TODO: Implement data fetching
            println("responseq3412 $nextPage ${(nextPage - 1) * params.loadSize}: $response")
            LoadResult.Page(
                data = response,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (response.isEmpty()) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CoinResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
package com.example.coincapapp.feature.coinList.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.coincapapp.feature.coinList.data.CoinRepository
import com.example.coincapapp.feature.coinList.data.model.toEntity
import com.example.coincapapp.feature.coinList.domain.entities.CoinEntity
import kotlinx.coroutines.delay

class CoinListPagingSource(
    private val repository: CoinRepository,
    private val searchQuery: String
) : PagingSource<Int, CoinEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CoinEntity> {
        return try {
            val nextPage = params.key ?: 1
            val response = repository.loadPagingCoins(
                searchQuery = searchQuery,
                limit = params.loadSize,
                offset = (nextPage - 1) * params.loadSize,
            ).data // TODO: Implement data fetching
            val entities = response.map {
                it.toEntity()
            }
            println("responseq3412 $nextPage ${(nextPage - 1) * params.loadSize}: $response")
            LoadResult.Page(
                data = entities,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (response.isEmpty()) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CoinEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
package com.example.coincapapp.feature.coinList.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.coincapapp.feature.coinList.data.CoinRepository
import com.example.coincapapp.feature.coinList.data.model.toEntity
import com.example.coincapapp.feature.coinList.domain.entities.CoinEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCoinListUseCaseImpl @Inject constructor(
    private val repository: CoinRepository,
) : GetCoinListUseCase {

    override suspend operator fun invoke(searchQuery: String): Flow<PagingData<CoinEntity>> {

        val config = PagingConfig(
            initialLoadSize = 20,
            pageSize = 20,
            enablePlaceholders = false,
            prefetchDistance = 10,
        )

        return Pager(config) {
            CoinListPagingSource(
                repository = repository,
                searchQuery = searchQuery,
            )
        }.flow.map { pagingData ->
            pagingData.map { response ->
                response.toEntity()
            }
        }
    }
}
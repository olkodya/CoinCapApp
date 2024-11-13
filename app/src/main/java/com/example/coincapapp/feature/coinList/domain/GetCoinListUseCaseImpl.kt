package com.example.coincapapp.feature.coinList.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.coincapapp.feature.coinList.data.CoinRepository
import com.example.coincapapp.feature.coinList.data.model.toEntity
import com.example.coincapapp.feature.coinList.domain.entities.CoinEntity
import com.example.coincapapp.feature.coinList.domain.paging.CoinListPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCoinListUseCaseImpl @Inject constructor(
    private val repository: CoinRepository,
) : GetCoinListUseCase {

override suspend operator fun invoke(): Flow<PagingData<CoinEntity>> {

        val config = PagingConfig(
            pageSize = 100,
            enablePlaceholders = false,
            prefetchDistance = 5,
        )

        return Pager(config) {
            CoinListPagingSource(repository = repository)
        }.flow.map { pagingData ->
            pagingData.map { response ->
                response.toEntity()
            }
        }
    }
}
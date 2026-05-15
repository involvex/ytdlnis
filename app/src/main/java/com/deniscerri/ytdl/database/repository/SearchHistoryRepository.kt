package com.involvex.ytmp3dlp.database.repository

import com.involvex.ytmp3dlp.database.dao.SearchHistoryDao
import com.involvex.ytmp3dlp.database.models.SearchHistoryItem

class SearchHistoryRepository(private val searchHistoryDao: SearchHistoryDao) {
    fun getAll() : List<SearchHistoryItem> {
        return searchHistoryDao.getAll()
    }

    fun getAllByKeyword(keyword: String) : List<SearchHistoryItem> {
        return searchHistoryDao.getAllByKeyword(keyword)
    }

    suspend fun insert(query: String){
        searchHistoryDao.insert(SearchHistoryItem(0, query))
    }

    suspend fun deleteAll(){
        searchHistoryDao.deleteAll()
    }

    suspend fun delete(query: String){
        searchHistoryDao.delete(query)
    }
}


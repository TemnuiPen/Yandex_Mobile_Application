package com.example.yandexmobileapplication.ForTinyDB

import android.content.Context
import com.example.yandexmobileapplication.ApiDataLists
import com.example.yandexmobileapplication.MainActivity
import com.example.yandexmobileapplication.TinyDB.TinyDB
import java.util.*

class TinyDBMethods : MainActivity<Objects>() {


    fun saveStartStockListLocal(context:Context){
        val tinyDB = TinyDB(context)
        val apiDataList: ApiDataLists? = null
        responseStockDTO =  apiDataList?.defaultList
        tinyDB.putObject(ApiDataLists.key, ResponseStockDTOWrapper(responseStockDTO))
    }

    fun loadStartStockListLocal(context:Context){
        val tinyDB = TinyDB(context)
        val loaded = tinyDB.getObject(ApiDataLists.key, ResponseStockDTOWrapper()::class.java)
        if(loaded == null){
            val apiDataList: ApiDataLists? = null
            responseStockDTO = apiDataList?.defaultList
        }
        else{
            responseStockDTO = loaded.responseStockDTO!!
        }
    }
}
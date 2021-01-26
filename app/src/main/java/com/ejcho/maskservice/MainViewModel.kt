package com.ejcho.maskservice

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ejcho.maskservice.model.Store
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel : ViewModel() {
    val itemLiveData = MutableLiveData<List<Store>>()
    val loadingLiveData = MutableLiveData<Boolean>()

    private val maskService: MaskService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(MaskService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        maskService = retrofit.create(MaskService::class.java)

        fetchStoreInfo()
    }

    private fun fetchStoreInfo() {
        // 로딩 시작
        loadingLiveData.value = true

        // 자바 thread 대체
        // viewModelScope : ViewModel Lifecycle 안에 있을 때 동작함 (코틀린에서만 사용 가능)
        viewModelScope.launch {
            val storeInfo = maskService.fetchStoreInfo(37.188078, 127.043002)
            // itemLiveData.value = storeInfo.stores

            itemLiveData.value = storeInfo.stores.filter {
                // 스크롤 시 죽는거 방지
                it.remain_stat != null
            }

            // 로딩 끝
            loadingLiveData.value = false
        }

    }
}
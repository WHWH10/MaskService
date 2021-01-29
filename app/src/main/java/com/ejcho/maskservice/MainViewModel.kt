package com.ejcho.maskservice

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ejcho.maskservice.model.Store
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val maskService: MaskService,
    private val fusedLocationClient: FusedLocationProviderClient
) : ViewModel() {
    val itemLiveData = MutableLiveData<List<Store>>()
    val loadingLiveData = MutableLiveData<Boolean>()


    init {
        fetchStoreInfo()
//        fetchLocationInfo()
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

//    @SuppressLint("MissingPermission")
    fun fetchLocationInfo() {
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            Log.d("MainLocation_lat", "${location.latitude}")
            Log.d("MainLocation_lng", "${location.longitude}")
        }.addOnFailureListener { exception ->
            Log.d("MainLocation_Fail", "$exception")
        }
    }
}
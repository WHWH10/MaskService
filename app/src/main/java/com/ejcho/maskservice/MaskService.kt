package com.ejcho.maskservice

import com.ejcho.maskservice.model.StoreInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MaskService {

    @GET("sample.json")
    // suspend : 비동기로 오랫동안 쓸 수 있는 코드 , 직접 StoreInfo Return 받을 수 있음
    //         : suspend 함수는 suspend 안에서만 사용 가능
    suspend fun fetchStoreInfo(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double
    ): StoreInfo
    
    companion object {
        const val BASE_URL: String =
            "https://gist.githubusercontent.com/junsuk5/bb7485d5f70974deee920b8f0cd1e2f0/raw/063f64d9b343120c2cb01a6555cf9b38761b1d94/"
    }

}
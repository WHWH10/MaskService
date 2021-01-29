package com.ejcho.maskservice

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()) {map ->
        if(map[Manifest.permission.ACCESS_FINE_LOCATION] !! or
                map[Manifest.permission.ACCESS_COARSE_LOCATION] !!) {
            viewModel.fetchLocationInfo()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initPermission()

        val storeAdapter = StoreAdapter(this@MainActivity)

        findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            adapter = storeAdapter
        }

//        val items = listOf(
//            Store("경기도 용인시 기흥구", "1234", "test", 32.00001, 127.0001, "기흥약국", "plenty",
//            "100", "33"),
//            Store("서울시 서초구", "1234", "test", 32.00001, 127.0001, "서초약국", "few",
//                "24", "33"),
//            Store("서울시 송파구", "1234", "test", 32.00001, 127.0001, "송파약국", "some",
//                "45", "33")
//        )
//
//        storeAdapter.updateItem(items)
        viewModel.apply {
            itemLiveData.observe(this@MainActivity, Observer {
                storeAdapter.updateItem(it)
            })

            loadingLiveData.observe(this@MainActivity, Observer { isLoading ->
//                if(isLoading) {
//                    findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
//                }
//                else {
//
//                    findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
//                }
                findViewById<ProgressBar>(R.id.progressBar).visibility =
                    if (isLoading) View.VISIBLE else View.GONE
            })
        }
    }

    private fun initPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 권한 요청 부분
            requestPermission.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
            return
        }
    }
}
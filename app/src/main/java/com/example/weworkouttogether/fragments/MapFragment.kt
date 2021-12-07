package com.example.weworkouttogether.fragments

import android.Manifest
import android.graphics.PointF
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.findFragment
import com.example.weworkouttogether.App
import com.example.weworkouttogether.R
import com.example.weworkouttogether.databinding.FragmentHomeBinding
import com.example.weworkouttogether.databinding.FragmentMapBinding
import com.example.weworkouttogether.utils.PlaceSearchService
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.Utmk
import com.naver.maps.map.*
import com.naver.maps.map.MapFragment
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.android.synthetic.main.fragment_map.*

class MapFragment : Fragment(), OnMapReadyCallback, NaverMap.OnMapClickListener {
    private lateinit var binding: FragmentMapBinding
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    private lateinit var mapSettings: UiSettings

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        private val PERMISSION: Array<out String> = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        this.binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.naverMapFragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.naverMapFragment, it).commit()
            }
        mapFragment.getMapAsync(this)


        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        var utmk = Utmk(953935.5, 1952044.1)
        var latLng = utmk.toLatLng()

        latLng = LatLng(latLng.latitude, latLng.longitude)
        utmk = Utmk.valueOf(latLng)

        Toast.makeText(
            this.context,
            "위도 : ${utmk.x}, 경도 : ${utmk.y}",
            Toast.LENGTH_SHORT
        ).show()

        binding.mapSearchBtn.setOnClickListener {
            var text = binding.mapSearch.text.toString().trim()
            PlaceSearchService().placeSearch(text)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) {//권한 거부됨
                Log.d("TAG", "permissions-Map 위치 권한 거부됨")
                naverMap.locationTrackingMode = LocationTrackingMode.None // 현위치 비활성
                return
            } else {
                Log.d("TAG", "permissions-Map 위치 권한 승인됨")
                naverMap.locationTrackingMode = LocationTrackingMode.Follow // 현위치 버튼 컨트롤 활성
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource

        ActivityCompat.requestPermissions(requireActivity(), PERMISSION, LOCATION_PERMISSION_REQUEST_CODE)

        naverMap.locationTrackingMode = LocationTrackingMode.Follow
        naverMap.onMapClickListener = this

        mapSettings = naverMap.uiSettings
        mapSettings.isLocationButtonEnabled = true





    }

    override fun onMapClick(point: PointF, latLng: LatLng) {
        Toast.makeText(this.context, "${point}클릭됨 ${latLng.latitude}, ${latLng.longitude}", Toast.LENGTH_SHORT).show()
    }
}
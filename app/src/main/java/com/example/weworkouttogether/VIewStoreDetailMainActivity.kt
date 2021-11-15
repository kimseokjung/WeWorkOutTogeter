package com.example.weworkouttogether

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.weworkouttogether.datas.Store
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import kotlinx.android.synthetic.main.activity_view_store_detail_main.*

class VIewStoreDetailMainActivity : BaseActivity() {

    private lateinit var mStoreData: Store

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_store_detail_main)
        setUpEvents()
        setValues()
    }

    override fun setUpEvents() {
        //전화 퍼미션
        detailCallStore.setOnClickListener {
            val permissionListener = object : PermissionListener {
                override fun onPermissionGranted() {
                    val myUri = Uri.parse("tel:${mStoreData.tel}")
                    val myIntent = Intent(Intent.ACTION_CALL, myUri)
                    startActivity(myIntent)
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(mContext, "전화 권한이 없습니다", Toast.LENGTH_SHORT).show()
                }

            }
            TedPermission.create()
                .setPermissionListener(permissionListener)
                .setDeniedMessage("설정 > 전화 권한을 켜주세요")
                .setPermissions(android.Manifest.permission.CALL_PHONE)
                .check()
        }
    }

    override fun setValues() {

        mStoreData = intent.getSerializableExtra("datas") as Store

        Glide.with(mContext).load(mStoreData.photoUrl).into(detailCimg)

        detailStoreName.text = mStoreData.name
        detailTelNum.text = mStoreData.tel

    }
}
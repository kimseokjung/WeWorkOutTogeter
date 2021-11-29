package com.example.weworkouttogether

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.weworkouttogether.utils.PreferenceUtil
import com.example.weworkouttogether.utils.RoomDataUtil
import com.google.firebase.auth.FirebaseAuth

class LogOutDialog(activity: Activity){
    private val mActivity = activity
    private val dlg = Dialog(mActivity)
    private lateinit var tvTitle : TextView
    private lateinit var btnOk : Button
    private lateinit var btnCancel : Button
    private lateinit var listener: MyDialogOKClickListener
    private lateinit var mFirebaseAuth : FirebaseAuth
    private lateinit var pref : PreferenceUtil


    fun start(content: String){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.sign_out_dialog_item)
        dlg.setCancelable(false)
        pref = PreferenceUtil(mActivity)

        tvTitle = dlg.findViewById(R.id.tvLogOutTitle)
        tvTitle.text = content

        btnOk = dlg.findViewById(R.id.btnLogOutDial)
        btnOk.setOnClickListener {
            logOut()
            Toast.makeText(mActivity, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()
            dlg.dismiss()
        }

        btnCancel = dlg.findViewById(R.id.btnLogOutCancelDial)
        btnCancel.setOnClickListener {


            dlg.dismiss()
        }
        dlg.show()

    }
    private fun logOut(){
//        sharedPref = mActivity.getSharedPreferences("login",Context.MODE_PRIVATE)
//        val prefEditor : SharedPreferences.Editor = sharedPref.edit()
//        prefEditor.clear()
//        prefEditor.apply()
        mFirebaseAuth = FirebaseAuth.getInstance()
        mFirebaseAuth.signOut()

        Log.e("TAG", "logOut: $mActivity", )
        val intent = Intent(mActivity, LogInActivity::class.java)
        startActivity(mActivity,intent,null)
        RoomDataUtil().clearAll()
        pref.setPref("autoLogin","no")
        pref.deletePref()
        mActivity.finish()
    }


    fun setOnOKClickListener(listener: (String) -> Unit){
        this.listener = object : MyDialogOKClickListener{
            override fun OnOKClicked(content: String) {
                listener(content)
            }

        }
    }


    interface MyDialogOKClickListener {
        fun OnOKClicked(content: String)
    }



}
package com.interview.activity


import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.interview.R
import com.interview.utils.App
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener


class ActSplash : AppCompatActivity() {

    var strTAG : String  = "ActSplash"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

        initialize()
        askPermission()


    }

    private fun initialize(){

    }

    private fun openHomeScreen(){

        var i1 = Intent(this, ActMain::class.java)
        startActivity(i1)

    }


    //-- Android Permission
    fun askPermission() {

        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        App.createFolder()

                        openHomeScreen()

                    } else if (report.isAnyPermissionPermanentlyDenied) {
                        alert()
                    }

                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).check()

    }

    fun alert() {

        val builder = AlertDialog.Builder(this)

        builder.setTitle("Permission Denied!")
        builder.setMessage("Storage Permission Required")
        builder.setPositiveButton("Open Settings",
            DialogInterface.OnClickListener { dialog, id ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            })
        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, id -> dialog.dismiss() })

        builder.create()
        builder.show()
    }





}

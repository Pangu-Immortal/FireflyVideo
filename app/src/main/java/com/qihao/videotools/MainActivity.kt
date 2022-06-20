package com.qihao.videotools

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.qihao.videotools.databinding.ActivityMainBinding
import tv.danmaku.ijk.media.example.activities.VideoActivity

private const val TAG = "MainActivity"
private const val REQUEST_CODE_PERMISSIONS = 10
private const val REQUEST_SELECT_FILE = 11

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mPlayerPath: AppCompatButton
    private lateinit var pathView: EditText
    private lateinit var picSelect: ImageButton

    /**
     * 请求存储权限
     */
    private fun requestWritePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // 先判断有没有权限
            if (Environment.isExternalStorageManager()) {
                selectFile()//去选择文件
            } else {
                try {
                    Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                        data = Uri.parse("package:$packageName")
                    }.let { startActivityForResult(it, REQUEST_CODE_PERMISSIONS) }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                selectFile()//去选择文件
            } else {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    REQUEST_CODE_PERMISSIONS
                )
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        mPlayerPath = findViewById<View>(R.id.player_path) as AppCompatButton
        pathView = findViewById<View>(R.id.path_view) as EditText
        picSelect = findViewById<View>(R.id.selected_video) as ImageButton
        picSelect.setOnClickListener {
            Log.d(TAG, "onClick: video")
            requestWritePermission()
        }
        mPlayerPath.setOnClickListener {
            VideoActivity.intentTo(
                this,
                pathView.text.toString(),
                "test"
            )
        }
    }

    //获取权限弹窗操作完成回调
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectFile() //去选择文件
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult: $requestCode")
        when (requestCode) {
            REQUEST_SELECT_FILE -> {
                if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {
                    toCrop(data.data!!)//选完文件以后跳转到裁剪界面
                }
            }
            REQUEST_CODE_PERMISSIONS -> {
                if (Environment.isExternalStorageManager()) {
                    selectFile()//选完文件以后跳转到裁剪界面
                }
            }
        }
    }

    private var someActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK
            && result.data != null && result.data!!.data != null
        ) {
            toCrop(result.data!!.data!!)//选完文件以后跳转到裁剪界面
        }
    }

    private fun selectFile() {
        val i = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            someActivityResultLauncher.launch(i) //Android 10以上的写法，targetAPI在31以上才这么写
        } else {
            startActivityForResult(i, REQUEST_SELECT_FILE)//Android 10以下的写法
        }
    }


    /**
     * 跳转裁剪页面
     */
    private fun toCrop(data: Uri) {
        val filePathColumn = arrayOf(MediaStore.Video.Media.DATA)
        try {
            val cursor: Cursor? = contentResolver.query(
                data,
                filePathColumn, null, null, null
            )
            if (cursor != null) {
                cursor.moveToFirst()
                val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
                val videoPath: String = cursor.getString(columnIndex)
                cursor.close()
                pathView.setText(videoPath)
            }
        } catch (e: Exception) {
            Log.e(TAG, "读取文件出错", e)
            Toast.makeText(this, "readVideoFileError", Toast.LENGTH_LONG).show()
        }
    }

}
package com.qihao.videotools

import ando.file.core.FileLogger
import ando.file.core.FileOperator
import ando.file.selector.*
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.qihao.videotools.databinding.ActivityMainBinding
import tv.danmaku.ijk.media.example.activities.VideoActivity

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mPlayerPath: AppCompatButton
    private lateinit var pathView: EditText
    private lateinit var picSelect: ImageButton
    private lateinit var fileSelector: FileSelector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FileOperator.init(application, true)
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
            fileSelector = FileSelector.with(this)
                .callback(object : FileSelectCallBack {
                    override fun onSuccess(results: List<FileSelectResult>?) {
                        results?.firstOrNull()?.filePath?.let {
                            VideoActivity.intentTo(this@MainActivity, it, "test")
                        }
                    }

                    override fun onError(e: Throwable?) {
                        FileLogger.e(e.toString())
                    }
                })
                .choose()
        }
        mPlayerPath.setOnClickListener { v ->
            VideoActivity.intentTo(
                this,
                pathView.text.toString(),
                "test"
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
       fileSelector.obtainResult(requestCode, resultCode, data)
    }

    private fun selectVideo(activity: Context) {
        val fileSelectOptions = FileSelectOptions()
        fileSelectOptions.fileType = FileType.VIDEO
        fileSelectOptions.minCount = 1
        fileSelectOptions.minCount = 1
        fileSelectOptions.fileCondition = object : FileSelectCondition {
            override fun accept(fileType: IFileType, uri: Uri?): Boolean {
                return uri != null
            }
        }

        fileSelector = FileSelector.with(activity)
            .setMimeTypes("video/*")
            .applyOptions(fileSelectOptions)
            .callback(object : FileSelectCallBack {
                override fun onSuccess(list: List<FileSelectResult>?) {
                    Log.d(TAG, "onSuccess: " + list!!.size)
                    for ((_, _, _, filePath) in list) {
                        VideoActivity.intentTo(activity, filePath, "test")
                    }
                }

                override fun onError(throwable: Throwable?) {
                    Log.e(TAG, "onError: 选取异常", throwable)
                    Toast.makeText(activity, throwable!!.message, Toast.LENGTH_SHORT).show()
                }
            })
            .choose()
    }
}
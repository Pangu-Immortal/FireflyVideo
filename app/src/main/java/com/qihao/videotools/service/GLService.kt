package com.qihao.videotools.service

import android.app.Service
import android.content.Intent
import android.opengl.GLSurfaceView
import android.os.IBinder
import android.service.wallpaper.WallpaperService

class GLService : WallpaperService() {

    override fun onCreateEngine(): Engine {
        return Engine()
    }
}
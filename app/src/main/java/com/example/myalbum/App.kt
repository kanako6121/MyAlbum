package com.example.myalbum

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
  override fun onCreate() {
    super.onCreate()
    setupTimber()
  }

  private fun setupTimber() {
    // 暫定でDebug利用のみ設定
    Timber.plant(Timber.DebugTree())
  }
}

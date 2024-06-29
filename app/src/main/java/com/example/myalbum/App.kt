package com.example.myalbum

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
  // TODO Delete when https://github.com/google/dagger/issues/3601 is resolved.
  @Inject @ApplicationContext lateinit var context: Context

  override fun onCreate() {
    super.onCreate()
    setupTimber()
  }

  private fun setupTimber() {
    // 暫定でDebug利用のみ設定
    Timber.plant(Timber.DebugTree())
  }
}


package tj.task.graphqlleadsapp

import android.app.Application
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val config = BundledEmojiCompatConfig(applicationContext)
        EmojiCompat.init(config)
    }
}
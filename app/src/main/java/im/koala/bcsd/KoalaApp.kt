package im.koala.bcsd

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KoalaApp : Application() {
    val appContext: Context = this
    val isApplicationDebug
        get() = isApplicationDebug(appContext)

    override fun onCreate() {
        super.onCreate()
        instance = this
        Hawk.init(appContext).build()
        KakaoSdk.init(this, this.resources.getString(R.string.KAKAO_KEY))
    }
    /**
     * 디버그모드인지 확인하는 함수
     */
    private fun isApplicationDebug(context: Context): Boolean {
        var debuggable = false
        val pm: PackageManager = context.packageManager
        try {
            val appinfo = pm.getApplicationInfo(context.packageName, 0)
            debuggable = 0 != appinfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
        } catch (e: PackageManager.NameNotFoundException) {
        }

        return debuggable
    }
    companion object {
        lateinit var instance: KoalaApp
            private set
    }
}
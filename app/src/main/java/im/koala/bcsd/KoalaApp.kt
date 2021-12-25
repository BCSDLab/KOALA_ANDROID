package im.koala.bcsd

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KoalaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, this.resources.getString(R.string.KAKAO_KEY))
        Log.e("key",this.resources.getString(R.string.KAKAO_KEY))
    }
}
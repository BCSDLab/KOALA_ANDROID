package im.koala.bcsd.ui.keyworddetail

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class KeywordDetailActivityContract : ActivityResultContract<String, Unit>() {
    override fun createIntent(context: Context, input: String): Intent {
        return Intent(context, KeywordDetailActivity::class.java).apply {
            putExtra(EXTRA_KEYWORD, input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?) {}

    companion object {
        const val EXTRA_KEYWORD = "EXTRA_KEYWORD"
    }
}
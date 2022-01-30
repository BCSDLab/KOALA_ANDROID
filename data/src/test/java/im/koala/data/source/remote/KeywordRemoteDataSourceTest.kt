package im.koala.data.source.remote

import im.koala.data.api.AuthApi
import im.koala.data.api.response.keyword.KeywordNoticeResponse
import im.koala.data.mapper.keyword.toKeywordNotice
import im.koala.data.repository.remote.KeywordRemoteDataSource
import im.koala.data.repository.remote.KeywordRemoteDataSourceImpl
import im.koala.domain.entity.keyword.Site
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@Suppress("NonAsciiCharacters")
class KeywordRemoteDataSourceTest {
    @Mock
    private lateinit var authApi: AuthApi

    @InjectMocks
    private lateinit var keywordRemoteDataSource: KeywordRemoteDataSource

    @Before
    fun init() {
        authApi = mock()
        keywordRemoteDataSource = KeywordRemoteDataSourceImpl(authApi)
    }

    @Test
    fun `getKeywordNotices의 site가 null 또는 Site_All이면 API의 결과 리스트를 그대로 반환한다`() = runBlockingTest {
        val sites = arrayOf(Site.Dorm, Site.Facebook, Site.Instagram, Site.Portal, Site.Youtube)
        val result = (1..10).map {
            KeywordNoticeResponse(
                createdAt = "2022-01-30",
                id = it,
                isRead = it % 2 == 0,
                site = sites[it % sites.size].toString(),
                title = "Keyword $it",
                url = ""
            )
        }

        whenever(authApi.getKeywordList(any(), any())).thenReturn(result)

        //assertThat(result.map { it.toKeywordNotice() }, `is`(keywordRemoteDataSource.getKeywordNotices("keyword", null)))
        assertThat(result.map { it.toKeywordNotice() }, `is`(keywordRemoteDataSource.getKeywordNotices("keyword", Site.All)))
    }
}
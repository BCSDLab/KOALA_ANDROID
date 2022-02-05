package im.koala.data.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import im.koala.data.source.RecentSearchDataSource
import im.koala.data.source.local.RecentKeywordSearchDataSource
import im.koala.data.source.local.RecentSiteSearchDataStore

@Module
@InstallIn(ActivityComponent::class)
abstract class RecentSearchDataStoreModule {

    @Binds
    @RecentKeywordSearch
    abstract fun bindRecentKeywordSearch(recentKeywordSearchDataSource: RecentKeywordSearchDataSource): RecentSearchDataSource

    @Binds
    @RecentSiteSearch
    abstract fun bindRecentSiteSearch(recentSiteSearchDataStore: RecentSiteSearchDataStore): RecentSearchDataSource

}
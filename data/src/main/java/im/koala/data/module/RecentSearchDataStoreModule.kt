package im.koala.data.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

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
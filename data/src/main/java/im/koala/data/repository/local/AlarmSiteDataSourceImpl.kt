package im.koala.data.repository.local

import javax.inject.Inject

class AlarmSiteDataSourceImpl @Inject constructor() : AlarmSiteDataSource {
    private val alarmSiteList = mutableListOf<Site>()
    private var id = 0

    override fun getAllList(callback: (List<Site>) -> Unit) {
        callback(alarmSiteList)
    }

    override fun addSite(site: String) {
        var isAdd = true
        alarmSiteList.forEach { siteData ->
            if (siteData.site == site) isAdd = false
        }
        if (isAdd) alarmSiteList.add(Site(id, site))
        id += 1
    }

    override fun deleteSite(site: String) {
        var removeSite = Site()
        alarmSiteList.forEach { siteData ->
            if (siteData.site == site) removeSite = siteData
        }
        alarmSiteList.remove(removeSite)
    }

    override fun deleteAllList() {
        alarmSiteList.clear()
        id = 0
    }
}

data class Site(
    val id: Int? = null,
    val site: String? = null
)
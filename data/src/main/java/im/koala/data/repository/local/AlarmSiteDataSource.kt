package im.koala.data.repository.local

interface AlarmSiteDataSource {
    fun getAllList(callback:(List<Site>)->Unit)
    fun addSite(site: String)
    fun deleteSite(site:String)
    fun deleteAllList()
}
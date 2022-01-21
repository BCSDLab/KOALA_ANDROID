package im.koala.domain.entity.keyword

sealed class Site(
    private val siteId: Int,
    private val name: String
) {
    override fun equals(other: Any?): Boolean {
        if(other !is Site) return false
        return this.siteId == other.siteId
    }

    override fun hashCode(): Int {
        return siteId.hashCode()
    }

    override fun toString(): String {
        return "Site(siteId=$siteId)"
    }

    object All: Site(0, "전체")
    object Portal: Site(1, "아우누리")
    object Dorm: Site(2, "아우미르")
    object Youtube: Site(3, "유튜브")
    object Facebook: Site(4, "페이스북")
    object Instagram: Site(5, "인스타그램")
}
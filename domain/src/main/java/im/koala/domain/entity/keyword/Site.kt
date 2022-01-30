package im.koala.domain.entity.keyword

sealed class Site(
    val siteId: Int,
    val name: String
) {
    override fun equals(other: Any?): Boolean {
        if (other !is Site) return false
        return this.siteId == other.siteId
    }

    override fun hashCode(): Int {
        return siteId.hashCode()
    }

    override fun toString(): String {
        return name
    }

    object All : Site(0, "ALL")
    object Portal : Site(1, "PORTAL")
    object Dorm : Site(2, "DORM")
    object Youtube : Site(3, "YOUTUBE")
    object Facebook : Site(4, "FACEBOOK")
    object Instagram : Site(5, "INSTAGRAM")
}
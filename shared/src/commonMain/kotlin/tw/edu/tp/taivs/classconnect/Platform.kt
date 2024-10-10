package tw.edu.tp.taivs.classconnect

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
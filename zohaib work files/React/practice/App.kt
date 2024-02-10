val getAllRatingData = db.appDao().getAllRating()

if (getAllRatingData != null && getAllRatingData.isNotEmpty()) {
    // Collect rating data and image resources efficiently
    val ratings = getAllRatingData.map { it.rate to getImageResource(it.platform) }

    // Find the highest-rated platform and its corresponding image
    val (highestRating, highestImage) = ratings.maxByOrNull { it.first } ?: return

    // Create the marker using the highest-rated platform's coordinates and image
    createMarker(latitude = ratings.filter { it.first == highestRating }.first().let { it.second.position.latitude },
                 longitude = ratings.filter { it.first == highestRating }.first().let { it.second.position.longitude },
                 title = highestRating.platform.displayName,
                 iconResID = highestImage.iconId)
} else {
    // Handle the case where there is no rating data
    Log.w("app_rate", "No rating data found. Cannot create marker.")
}

// Simplified `getImageResource` function (assumes `Platform` enum with `displayName` and `iconId` properties)
private fun getImageResource(platform: Platform): ImageResource {
    return when (platform) {
        Platform.WHATSAPP -> ImageResource(position = LatLng(lat, lng), iconId = R.drawable.your_whatsapp_icon)
        Platform.YOUTUBE -> ImageResource(position = LatLng(lat, lng), iconId = R.drawable.your_youtube_icon)
        // Similar cases for other platforms
        else -> throw IllegalArgumentException("Unknown platform: $platform")
    }
}

// Class representing an image resource (feel free to adjust based on your data structure)
data class ImageResource(val position: LatLng, val iconId: Int)

// Enum representing different platforms (or adapt if you have a different data structure)
enum class Platform {
    WHATSAPP, YOUTUBE, FACEBOOK, TWITTER, LINKEDIN, INSTAGRAM;

    val displayName: String
        get() = name.toLowerCase().replaceFirstChar({ it.titlecase() }) // Capitalize first letter

    // Add icon IDs using `ImageResource` or similar approach
}

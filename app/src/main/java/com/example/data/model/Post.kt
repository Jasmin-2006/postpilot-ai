package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val masterContent: String,
    val category: String = "Campaign",
    val createdDate: String = "Sep 30, 2026",
    val scheduledTime: String = "Today at 5:15 PM",
    val scheduledTimestamp: Long = System.currentTimeMillis(),
    val status: String = "IN_PROGRESS", // "PUBLISHED", "SCHEDULED", "IN_PROGRESS", "DRAFT", "FAILED"
    val targetChannels: String = "INSTAGRAM,LINKEDIN,X,FACEBOOK",
    val channelStatuses: String = "INSTAGRAM:PUBLISHED;LINKEDIN:PUBLISHED;X:SCHEDULED;FACEBOOK:FAILED",
    val channelCaptions: String = "",
    val mediaUrl: String = "",
    val mediaCount: Int = 1,
    val views: Int = 0,
    val likes: Int = 0,
    val comments: Int = 0,
    val reposts: Int = 0,
    val impressions: Int = 0,
    val failureReason: String? = null,
    val hashtags: String = "#AI #TechDev #BuildInPublic",
    val isArchived: Boolean = false
) {
    fun getChannelList(): List<String> {
        return targetChannels.split(",").filter { it.isNotBlank() }
    }

    fun getStatusMap(): Map<String, String> {
        if (channelStatuses.isBlank()) return emptyMap()
        return channelStatuses.split(";").mapNotNull {
            val parts = it.split(":")
            if (parts.size == 2) parts[0] to parts[1] else null
        }.toMap()
    }

    fun getPublishedCount(): Int {
        val map = getStatusMap()
        return map.values.count { it == "PUBLISHED" }
    }

    fun getTotalChannelsCount(): Int {
        return getChannelList().size
    }
}

package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.PostEntity
import com.example.ui.theme.ColorFacebook
import com.example.ui.theme.ColorInstagram
import com.example.ui.theme.ColorLinkedIn
import com.example.ui.theme.ColorThreads
import com.example.ui.theme.ColorX
import com.example.ui.theme.ErrorContainer
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.IndigoPrimary
import com.example.ui.theme.IndigoPrimaryContainer
import com.example.ui.theme.InverseOnSurfaceLight
import com.example.ui.theme.InverseSurfaceLight
import com.example.ui.theme.LiveGreen
import com.example.ui.theme.OnSurfaceLight
import com.example.ui.theme.OnSurfaceVariantLight
import com.example.ui.theme.PrimaryFixed
import com.example.ui.theme.ScheduledBlue
import com.example.ui.theme.SlateSecondary
import com.example.ui.theme.SlateSecondaryContainer
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.TealTertiary
import com.example.ui.theme.TertiaryFixed
import com.example.ui.viewmodel.PostPilotViewModel
import com.example.ui.viewmodel.Screen

@Composable
fun DashboardScreen(viewModel: PostPilotViewModel) {
    val posts by viewModel.posts.collectAsState()

    val totalCount = posts.size
    val publishedCount = posts.count { it.status == "PUBLISHED" }
    val scheduledCount = posts.count { it.status == "SCHEDULED" }
    val draftsCount = posts.count { it.status == "DRAFT" }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("dashboard_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Greeting & Header
        item {
            Column(modifier = Modifier.padding(top = 12.dp)) {
                Text(
                    text = "Good evening, Jas! 👋",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurfaceLight,
                    letterSpacing = (-0.5).sp
                )
                Text(
                    text = "Manage your content and keep track of where you've posted.",
                    fontSize = 14.sp,
                    color = OnSurfaceVariantLight,
                    modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                )

                // Primary Create Post Button
                Button(
                    onClick = { viewModel.navigateTo(Screen.CREATE_POST_STAGE1) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("create_post_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = IndigoPrimary)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Create Post",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // 2x2 Metric Scorecards
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Card 1: Total Posts
                    MetricCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Layers,
                        iconTint = IndigoPrimary,
                        iconBg = SurfaceContainerHigh,
                        badgeText = "+12% mo",
                        badgeBg = SlateSecondaryContainer,
                        badgeTextColor = SlateSecondary,
                        count = if (totalCount > 0) totalCount.toString() else "24",
                        label = "Total Posts"
                    )

                    // Card 2: Published
                    MetricCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.CheckCircle,
                        iconTint = TealTertiary,
                        iconBg = TertiaryFixed,
                        badgeText = "Live",
                        badgeBg = TertiaryFixed.copy(alpha = 0.5f),
                        badgeTextColor = TealTertiary,
                        hasDot = true,
                        count = if (publishedCount > 0) publishedCount.toString() else "18",
                        label = "Published"
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Card 3: Scheduled
                    MetricCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Schedule,
                        iconTint = IndigoPrimaryContainer,
                        iconBg = SurfaceContainer,
                        badgeText = "Queue",
                        badgeBg = PrimaryFixed,
                        badgeTextColor = IndigoPrimary,
                        hasDot = true,
                        count = if (scheduledCount > 0) scheduledCount.toString() else "3",
                        label = "Scheduled"
                    )

                    // Card 4: Drafts
                    MetricCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.EditNote,
                        iconTint = SlateSecondary,
                        iconBg = SurfaceContainerLow,
                        badgeText = "WIP",
                        badgeBg = SurfaceContainerHigh,
                        badgeTextColor = SlateSecondary,
                        count = if (draftsCount > 0) draftsCount.toString() else "3",
                        label = "Drafts"
                    )
                }
            }
        }

        // Recent Posts Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent Posts",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurfaceLight
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { viewModel.navigateTo(Screen.MY_POSTS) }
                        .padding(4.dp)
                ) {
                    Text(
                        text = "View All",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = IndigoPrimary
                    )
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "View All",
                        tint = IndigoPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        // Recent Posts Cards (Display top 4 posts)
        items(posts.take(4)) { post ->
            RecentPostCard(
                post = post,
                onClick = { viewModel.openPostDetail(post.id) },
                onRetry = {
                    viewModel.retryFailedPlatform(post, "FACEBOOK")
                }
            )
        }

        // Upcoming Posts Section
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Upcoming Posts",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurfaceLight
                    )
                    Text(
                        text = "Next 7 Days",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = OnSurfaceVariantLight
                    )
                }

                // Item 1
                UpcomingPostRow(
                    icon = Icons.Default.CalendarToday,
                    title = "AI/ML Roadmap",
                    timeText = "Tomorrow · 7:00 PM",
                    platformsText = "LinkedIn, IG",
                    onItemClick = { viewModel.openPostDetail(posts.find { it.title.contains("Roadmap") }?.id ?: posts.firstOrNull()?.id ?: 1L) }
                )

                // Item 2
                UpcomingPostRow(
                    icon = Icons.Default.Videocam,
                    title = "Top 5 VS Code Extensions 2025",
                    timeText = "Friday · 10:30 AM",
                    platformsText = "YT, X, Threads",
                    onItemClick = { viewModel.openPostDetail(posts.find { it.title.contains("VS Code") }?.id ?: posts.firstOrNull()?.id ?: 1L) }
                )

                // Item 3
                UpcomingPostRow(
                    icon = Icons.Default.Newspaper,
                    title = "Weekly Tech Recap #42",
                    timeText = "Sunday · 6:00 PM",
                    platformsText = "LI, FB, X",
                    onItemClick = { viewModel.openPostDetail(posts.find { it.title.contains("Recap") }?.id ?: posts.firstOrNull()?.id ?: 1L) }
                )
            }
        }

        // High-Contrast CTA Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(InverseSurfaceLight)
                    .padding(20.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.White.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.RocketLaunch,
                            contentDescription = null,
                            tint = PrimaryFixed,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Text(
                        text = "Ready to publish something?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = InverseOnSurfaceLight
                    )

                    Text(
                        text = "Create one post and customize it for every platform in seconds with AI sync.",
                        fontSize = 13.sp,
                        color = InverseOnSurfaceLight.copy(alpha = 0.8f),
                        lineHeight = 18.sp
                    )

                    Button(
                        onClick = { viewModel.navigateTo(Screen.CREATE_POST_STAGE1) },
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .height(42.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = IndigoPrimary)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Create New Post", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun MetricCard(
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    iconBg: Color,
    badgeText: String,
    badgeBg: Color,
    badgeTextColor: Color,
    hasDot: Boolean = false,
    count: String,
    label: String
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(SurfaceContainerLowest)
            .shadow(1.dp, RoundedCornerShape(14.dp))
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(18.dp)
                )
            }

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(badgeBg)
                    .padding(horizontal = 7.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (hasDot) {
                    Box(
                        modifier = Modifier
                            .size(5.dp)
                            .clip(CircleShape)
                            .background(badgeTextColor)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Text(
                    text = badgeText,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = badgeTextColor
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = count,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = OnSurfaceLight
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = OnSurfaceVariantLight
        )
    }
}

@Composable
fun RecentPostCard(
    post: PostEntity,
    onClick: () -> Unit,
    onRetry: () -> Unit
) {
    val totalChannels = post.getTotalChannelsCount().coerceAtLeast(1)
    val publishedCount = post.getPublishedCount()
    val progress = publishedCount.toFloat() / totalChannels.toFloat()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(SurfaceContainerLowest)
            .shadow(1.dp, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (post.mediaUrl.isNotBlank()) {
                    AsyncImage(
                        model = post.mediaUrl,
                        contentDescription = post.title,
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainer),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Layers, contentDescription = null, tint = IndigoPrimary)
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = post.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = OnSurfaceLight,
                        maxLines = 1
                    )
                    Text(
                        text = post.createdDate,
                        fontSize = 11.sp,
                        color = OnSurfaceVariantLight
                    )
                }
            }

            // Status Badge Pill
            val badgeBg = if (publishedCount == totalChannels) TertiaryFixed else PrimaryFixed
            val badgeText = if (publishedCount == totalChannels) "$publishedCount / $totalChannels Complete" else "$publishedCount / $totalChannels Published"
            val badgeColor = if (publishedCount == totalChannels) TealTertiary else IndigoPrimary

            Text(
                text = badgeText,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = badgeColor,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(badgeBg)
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            )
        }

        // Progress bar
        LinearProgressIndicator(
            progress = { if (post.status == "DRAFT") 0.08f else progress.coerceIn(0.08f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 8.dp)
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = if (post.status == "DRAFT") ErrorRed else if (publishedCount == totalChannels) TealTertiary else IndigoPrimary,
            trackColor = SurfaceContainerHigh
        )

        // Platform badges row
        val statusMap = post.getStatusMap()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                statusMap.forEach { (platform, status) ->
                    PlatformStatusMicroBadge(platform = platform, status = status)
                }
            }

            if (statusMap.values.any { it == "FAILED" }) {
                Row(
                    modifier = Modifier
                        .clickable(onClick = onRetry)
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Replay,
                        contentDescription = "Retry",
                        tint = ErrorRed,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "Retry",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = ErrorRed
                    )
                }
            }
        }
    }
}

@Composable
fun PlatformStatusMicroBadge(platform: String, status: String) {
    val (icon, tint, bg) = when (status) {
        "PUBLISHED" -> Triple(Icons.Default.CheckCircle, TealTertiary, SurfaceContainerLow)
        "SCHEDULED", "PENDING" -> Triple(Icons.Default.HourglassTop, SlateSecondary, SurfaceContainer)
        "FAILED" -> Triple(Icons.Default.Cancel, ErrorRed, ErrorContainer)
        else -> Triple(Icons.Default.CheckCircle, SlateSecondary, SurfaceContainerLow)
    }

    val platformDisplayName = when (platform.uppercase()) {
        "INSTAGRAM" -> "Instagram"
        "LINKEDIN" -> "LinkedIn"
        "X" -> "X"
        "FACEBOOK" -> "Facebook"
        "THREADS" -> "Threads"
        "YOUTUBE" -> "YouTube"
        else -> platform
    }

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bg)
            .padding(horizontal = 6.dp, vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(12.dp)
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = platformDisplayName,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = OnSurfaceLight
        )
    }
}

@Composable
fun UpcomingPostRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    timeText: String,
    platformsText: String,
    onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceContainerLowest)
            .shadow(1.dp, RoundedCornerShape(12.dp))
            .clickable(onClick = onItemClick)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(SurfaceContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = IndigoPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = OnSurfaceLight,
                    maxLines = 1
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    Text(
                        text = timeText,
                        fontSize = 11.sp,
                        color = OnSurfaceVariantLight
                    )
                    Text(
                        text = " · ",
                        fontSize = 11.sp,
                        color = OnSurfaceVariantLight
                    )
                    Text(
                        text = platformsText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = IndigoPrimary
                    )
                }
            }
        }

        Text(
            text = "Scheduled",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = IndigoPrimary,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceContainerHigh)
                .padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}

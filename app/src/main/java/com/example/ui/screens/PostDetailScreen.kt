package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.ColorFacebook
import com.example.ui.theme.ColorInstagram
import com.example.ui.theme.ColorLinkedIn
import com.example.ui.theme.ColorX
import com.example.ui.theme.ErrorContainer
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.IndigoPrimary
import com.example.ui.theme.IndigoPrimaryContainer
import com.example.ui.theme.LiveGreen
import com.example.ui.theme.OnSurfaceLight
import com.example.ui.theme.OnSurfaceVariantLight
import com.example.ui.theme.PrimaryFixed
import com.example.ui.theme.SlateSecondary
import com.example.ui.theme.SlateSecondaryContainer
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.TealTertiary
import com.example.ui.viewmodel.PostPilotViewModel
import com.example.ui.viewmodel.Screen

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PostDetailScreen(viewModel: PostPilotViewModel) {
    val posts by viewModel.posts.collectAsState()
    val selectedId by viewModel.selectedPostId.collectAsState()
    val post = posts.find { it.id == selectedId } ?: posts.firstOrNull()

    if (post == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Post not found")
        }
        return
    }

    val statusMap = post.getStatusMap()
    val publishedCount = statusMap.values.count { it == "PUBLISHED" }
    val scheduledCount = statusMap.values.count { it == "SCHEDULED" || it == "PENDING" }
    val actionNeededCount = statusMap.values.count { it == "FAILED" }
    val totalCount = statusMap.size.coerceAtLeast(1)
    val percent = ((publishedCount.toFloat() / totalCount.toFloat()) * 100).toInt()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("post_detail_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Sub-Navigation Bar
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .clickable { viewModel.navigateTo(Screen.MY_POSTS) }
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = OnSurfaceVariantLight, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Back to My Posts", fontSize = 13.sp, color = OnSurfaceVariantLight, fontWeight = FontWeight.Medium)
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceContainer)
                                .clickable {
                                    viewModel.composerTitle.value = post.title
                                    viewModel.composerContent.value = post.masterContent
                                    viewModel.navigateTo(Screen.CREATE_POST_STAGE1)
                                }
                                .padding(horizontal = 8.dp, vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Edit, contentDescription = null, tint = OnSurfaceLight, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "Edit", fontSize = 12.sp, color = OnSurfaceLight)
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceContainer)
                                .clickable {
                                    viewModel.showToast("Post duplicated to composer")
                                    viewModel.composerTitle.value = "Copy of " + post.title
                                    viewModel.composerContent.value = post.masterContent
                                    viewModel.navigateTo(Screen.CREATE_POST_STAGE1)
                                }
                                .padding(horizontal = 8.dp, vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, tint = OnSurfaceLight, modifier = Modifier.size(14.dp))
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceContainer)
                                .clickable { viewModel.showToast("More options menu") }
                                .padding(horizontal = 8.dp, vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.MoreHoriz, contentDescription = null, tint = OnSurfaceLight, modifier = Modifier.size(16.dp))
                        }
                    }
                }

                // Title & Subtitle
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = post.title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = post.category,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SlateSecondary,
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(SlateSecondaryContainer)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                    Text(
                        text = "Track where this post has been published and manage each platform version.",
                        fontSize = 13.sp,
                        color = OnSurfaceVariantLight
                    )
                }
            }
        }

        // Section 1: Post Overview Card
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceContainerLowest)
                    .shadow(1.dp, RoundedCornerShape(14.dp))
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Media preview frame
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceContainer)
                ) {
                    if (post.mediaUrl.isNotBlank()) {
                        AsyncImage(
                            model = post.mediaUrl,
                            contentDescription = post.title,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Row(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.Black.copy(alpha = 0.7f))
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Image, contentDescription = null, tint = Color.White, modifier = Modifier.size(13.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Media (${post.mediaCount})", fontSize = 11.sp, color = Color.White)
                    }
                }

                // Dates & Target Platform Count
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.CalendarToday, contentDescription = null, tint = OnSurfaceVariantLight, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Created ${post.createdDate}", fontSize = 12.sp, color = OnSurfaceVariantLight)
                    Text(text = "  •  ", fontSize = 12.sp, color = OnSurfaceVariantLight)
                    Icon(imageVector = Icons.Default.Share, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "${totalCount} Platforms Selected", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = IndigoPrimary)
                }

                // Master Copy Block
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceContainerLow)
                        .padding(12.dp)
                ) {
                    Text(text = "MASTER COPY", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = OnSurfaceVariantLight, letterSpacing = 0.5.sp)
                    Text(
                        text = "“${post.masterContent}”",
                        fontSize = 13.sp,
                        fontStyle = FontStyle.Italic,
                        color = OnSurfaceLight,
                        lineHeight = 18.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                // Hashtags
                FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    post.hashtags.split(" ").filter { it.isNotBlank() }.forEach { tag ->
                        Text(
                            text = tag,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = IndigoPrimary,
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(SurfaceContainer)
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }
        }

        // Section 2: Publishing Progress Card
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceContainerLowest)
                    .shadow(1.dp, RoundedCornerShape(14.dp))
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Publishing Progress", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                        Text(text = "Automated distribution status across channels", fontSize = 12.sp, color = OnSurfaceVariantLight)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = "$percent%", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = IndigoPrimary)
                        Text(text = "$publishedCount of $totalCount Live", fontSize = 11.sp, color = OnSurfaceVariantLight)
                    }
                }

                // Segmented Progress Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(SurfaceContainer)
                ) {
                    if (publishedCount > 0) {
                        Box(
                            modifier = Modifier
                                .weight(publishedCount.toFloat())
                                .fillMaxSize()
                                .background(IndigoPrimaryContainer)
                        )
                    }
                    if (scheduledCount > 0) {
                        Box(
                            modifier = Modifier
                                .weight(scheduledCount.toFloat())
                                .fillMaxSize()
                                .background(SlateSecondaryContainer)
                        )
                    }
                    if (actionNeededCount > 0) {
                        Box(
                            modifier = Modifier
                                .weight(actionNeededCount.toFloat())
                                .fillMaxSize()
                                .background(ErrorContainer)
                        )
                    }
                }

                // Breakdown Status Badges
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainerLow)
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(IndigoPrimary))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "$publishedCount Published", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
                    }

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainerLow)
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(SlateSecondary))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "$scheduledCount Scheduled", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
                    }

                    if (actionNeededCount > 0) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(SurfaceContainerLow)
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(ErrorRed))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "$actionNeededCount Action Needed", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
                        }
                    }
                }
            }
        }

        // Section 3: Platform Breakdown & Status Details
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Platform Variants", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                Text(text = "Synced via PostPilot Engine", fontSize = 12.sp, color = OnSurfaceVariantLight)
            }
        }

        // Card 1: Instagram
        item {
            val isPublished = statusMap["INSTAGRAM"] == "PUBLISHED"
            DetailPlatformCard(
                icon = Icons.Default.PhotoCamera,
                iconColor = ColorInstagram,
                platformName = "Instagram",
                handle = "@postpilot.app",
                timeText = if (isPublished) "Published Oct 24, 2:30 PM" else "Pending",
                isSuccess = isPublished,
                statusText = if (isPublished) "Published" else "Draft",
                captionText = "Packing bags just got an upgrade! ✈️ Meet the automated trip designer that turns your vibe into a real itinerary in 30 seconds. Link in bio! 📍 #TravelTech #NextGenVoyage",
                metric1 = "${post.views} views",
                metric2 = "${post.likes} likes",
                metric3 = "${post.comments} comments",
                onLivePostClick = { viewModel.showToast("Opening Instagram live post...") }
            )
        }

        // Card 2: LinkedIn
        item {
            val isPublished = statusMap["LINKEDIN"] == "PUBLISHED"
            DetailPlatformCard(
                icon = Icons.Default.Badge,
                iconColor = ColorLinkedIn,
                platformName = "LinkedIn",
                handle = "PostPilot Enterprise",
                timeText = if (isPublished) "Published Oct 24, 2:32 PM" else "Pending",
                isSuccess = isPublished,
                statusText = if (isPublished) "Published" else "Draft",
                captionText = "The future of business travel logistics lies at the intersection of agentic LLMs and live flight telematics. Here is how we orchestrated our travel engine architecture.",
                metric1 = "${post.impressions} impressions",
                metric2 = "${post.reposts} reposts",
                metric3 = "${post.likes + 28} reactions",
                onLivePostClick = { viewModel.showToast("Opening LinkedIn live post...") }
            )
        }

        // Card 3: X (Twitter)
        item {
            val isScheduled = statusMap["X"] == "SCHEDULED" || statusMap["X"] == "PENDING"
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceContainerLowest)
                    .shadow(1.dp, RoundedCornerShape(14.dp))
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.AlternateEmail, contentDescription = null, tint = OnSurfaceLight, modifier = Modifier.size(18.dp))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(text = "X (Twitter)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                            Text(text = "Tomorrow at 9:00 AM", fontSize = 11.sp, color = OnSurfaceVariantLight)
                        }
                    }

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(SlateSecondaryContainer)
                            .padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Schedule, contentDescription = null, tint = SlateSecondary, modifier = Modifier.size(13.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Scheduled", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SlateSecondary)
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceContainerLow)
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Tired of 24 open browser tabs for one weekend getaway? We taught AI to build the ultimate itinerary with real-time budget optimization. 🧵👇",
                        fontSize = 12.sp,
                        color = OnSurfaceLight,
                        lineHeight = 17.sp
                    )
                    Text(
                        text = "148/280 characters",
                        fontSize = 10.sp,
                        color = OnSurfaceVariantLight,
                        modifier = Modifier.align(Alignment.End).padding(top = 4.dp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Bolt, contentDescription = null, tint = OnSurfaceVariantLight, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(text = "Auto-queue ready", fontSize = 11.sp, color = OnSurfaceVariantLight)
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { viewModel.showToast("Rescheduled to Oct 2, 10:00 AM") },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainer, contentColor = OnSurfaceLight),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text(text = "Reschedule", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }

                        Button(
                            onClick = {
                                viewModel.retryFailedPlatform(post, "X")
                                viewModel.showToast("Posted to X immediately! 🚀")
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = IndigoPrimary, contentColor = Color.White),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text(text = "Post Now", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }

        // Card 4: Facebook (Failed)
        item {
            val isFailed = statusMap["FACEBOOK"] == "FAILED"
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceContainerLowest)
                    .shadow(1.dp, RoundedCornerShape(14.dp))
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.Public, contentDescription = null, tint = ColorFacebook, modifier = Modifier.size(18.dp))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(text = "Facebook", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                            Text(
                                text = if (isFailed) "Delivery Blocked • 2h ago" else "Published successfully",
                                fontSize = 11.sp,
                                color = if (isFailed) ErrorRed else LiveGreen,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isFailed) ErrorContainer else SurfaceContainer)
                            .padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (isFailed) Icons.Default.Error else Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = if (isFailed) ErrorRed else LiveGreen,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isFailed) "Failed" else "Published",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isFailed) ErrorRed else LiveGreen
                        )
                    }
                }

                if (isFailed) {
                    // Alert banner
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(ErrorContainer.copy(alpha = 0.5f))
                            .padding(10.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = ErrorRed, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "OAuth Token Expired: Page permissions must be refreshed before this post can be pushed.",
                            fontSize = 11.sp,
                            color = ErrorRed,
                            lineHeight = 16.sp
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerLow)
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Built an AI-powered travel planner that helps users create personalized trips based on budget, style, and real-time flight data. Compose once, deploy everywhere.",
                        fontSize = 12.sp,
                        color = OnSurfaceLight,
                        lineHeight = 17.sp
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            viewModel.composerTitle.value = post.title
                            viewModel.composerContent.value = post.masterContent
                            viewModel.navigateTo(Screen.CREATE_POST_STAGE1)
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainer, contentColor = OnSurfaceLight),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Text(text = "Edit Caption", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            viewModel.retryFailedPlatform(post, "FACEBOOK")
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isFailed) ErrorRed else IndigoPrimary,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = if (isFailed) "Retry Now" else "Re-dispatch", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        // Bottom Action & Telemetry Helper Bar
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceContainer)
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = OnSurfaceVariantLight, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Audit log updated 4 minutes ago", fontSize = 12.sp, color = OnSurfaceVariantLight)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { viewModel.showToast("Exported campaign report (CSV/PDF)") },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerLowest, contentColor = OnSurfaceLight),
                        modifier = Modifier.weight(1f).height(38.dp)
                    ) {
                        Icon(imageVector = Icons.Default.FileDownload, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Export Report", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }

                    Button(
                        onClick = { viewModel.showToast("Tracking link copied to clipboard!") },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = IndigoPrimary, contentColor = Color.White),
                        modifier = Modifier.weight(1f).height(38.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Link, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Share Tracking Link", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun DetailPlatformCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    platformName: String,
    handle: String,
    timeText: String,
    isSuccess: Boolean,
    statusText: String,
    captionText: String,
    metric1: String,
    metric2: String,
    metric3: String,
    onLivePostClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(SurfaceContainerLowest)
            .shadow(1.dp, RoundedCornerShape(14.dp))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(18.dp))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = platformName, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = handle, fontSize = 11.sp, color = OnSurfaceVariantLight)
                    }
                    Text(text = timeText, fontSize = 11.sp, color = OnSurfaceVariantLight)
                }
            }

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainer)
                    .padding(horizontal = 8.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(13.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = statusText, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = IndigoPrimary)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(SurfaceContainerLow)
                .padding(10.dp)
        ) {
            Text(text = captionText, fontSize = 12.sp, color = OnSurfaceLight, lineHeight = 17.sp, maxLines = 2)
        }

        // Metrics & View Live Post
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Visibility, contentDescription = null, tint = OnSurfaceVariantLight, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(text = metric1, fontSize = 11.sp, color = OnSurfaceVariantLight)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = null, tint = OnSurfaceVariantLight, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(text = metric2, fontSize = 11.sp, color = OnSurfaceVariantLight)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.ChatBubble, contentDescription = null, tint = OnSurfaceVariantLight, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(text = metric3, fontSize = 11.sp, color = OnSurfaceVariantLight)
                }
            }

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(SurfaceContainer)
                    .clickable(onClick = onLivePostClick)
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "View Live Post", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = IndigoPrimary)
                Spacer(modifier = Modifier.width(3.dp))
                Icon(imageVector = Icons.Default.OpenInNew, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(12.dp))
            }
        }
    }
}

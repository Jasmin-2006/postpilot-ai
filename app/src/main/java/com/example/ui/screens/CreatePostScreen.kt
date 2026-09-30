package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.PermMedia
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.ui.theme.ColorFacebook
import com.example.ui.theme.ColorInstagram
import com.example.ui.theme.ColorLinkedIn
import com.example.ui.theme.ColorThreads
import com.example.ui.theme.ColorX
import com.example.ui.theme.ColorYouTube
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.IndigoPrimary
import com.example.ui.theme.IndigoPrimaryContainer
import com.example.ui.theme.LiveGreen
import com.example.ui.theme.OnSurfaceLight
import com.example.ui.theme.OnSurfaceVariantLight
import com.example.ui.theme.PrimaryFixed
import com.example.ui.theme.SlateSecondary
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.SurfaceLight
import com.example.ui.viewmodel.PostPilotViewModel
import com.example.ui.viewmodel.Screen

@Composable
fun CreatePostScreen(
    viewModel: PostPilotViewModel,
    stage: Int = 1
) {
    if (stage == 1) {
        CreatePostStage1(viewModel)
    } else {
        CreatePostStage2(viewModel)
    }
}

@Composable
fun CreatePostStage1(viewModel: PostPilotViewModel) {
    val title by viewModel.composerTitle.collectAsState()
    val content by viewModel.composerContent.collectAsState()
    val selectedChannels by viewModel.composerTargetChannels.collectAsState()
    val attachedMedia by viewModel.composerAttachedMedia.collectAsState()
    val scheduledTime by viewModel.composerScheduledTime.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("create_post_stage1"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Nav Action Bar
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .clickable { viewModel.navigateTo(Screen.DASHBOARD) }
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = OnSurfaceVariantLight,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Dashboard",
                        fontSize = 13.sp,
                        color = OnSurfaceVariantLight,
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { viewModel.saveDraftPost() },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SurfaceContainerLow,
                            contentColor = SlateSecondary
                        ),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Bookmark, contentDescription = null, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Draft", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    }

                    Button(
                        onClick = { viewModel.navigateTo(Screen.CREATE_POST_STAGE2) },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = IndigoPrimaryContainer,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text(text = "Continue", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(15.dp))
                    }
                }
            }
        }

        // Header context
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(7.dp).clip(CircleShape).background(IndigoPrimary))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "UNIVERSAL COMPOSER",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = IndigoPrimary,
                        letterSpacing = 0.5.sp
                    )
                }
                Text(
                    text = "Create New Post",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurfaceLight,
                    letterSpacing = (-0.5).sp
                )
                Text(
                    text = "Compose your core message once. PostPilot effortlessly synchronizes tone and formats across every channel.",
                    fontSize = 13.sp,
                    color = OnSurfaceVariantLight,
                    lineHeight = 18.sp
                )
            }
        }

        // Target Channels Matrix
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Hub, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Target Channels", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
                    }

                    Text(
                        text = "${selectedChannels.size} active",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = IndigoPrimary,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainer)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Horizontal scroll of platform chips
                val allPlatforms = listOf("X", "LINKEDIN", "INSTAGRAM", "THREADS", "FACEBOOK", "YOUTUBE")
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(allPlatforms) { platform ->
                        val isSelected = selectedChannels.contains(platform)
                        val displayName = when (platform) {
                            "X" -> "X (Twitter)"
                            "LINKEDIN" -> "LinkedIn"
                            "INSTAGRAM" -> "Instagram"
                            "THREADS" -> "Threads"
                            "FACEBOOK" -> "Facebook"
                            "YOUTUBE" -> "YouTube Community"
                            else -> platform
                        }

                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (isSelected) SurfaceContainerHigh else SurfaceContainerLow)
                                .clickable { viewModel.toggleComposerChannel(platform) }
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) IndigoPrimary else SlateSecondary)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = displayName,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (isSelected) OnSurfaceLight else OnSurfaceVariantLight
                            )
                            if (isSelected) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = IndigoPrimary,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Master Content Composer Card
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
                // Post Title
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Post Title", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "(Internal Reference)", fontSize = 12.sp, color = OnSurfaceVariantLight)
                        }
                        Text(text = "Optional", fontSize = 11.sp, color = OnSurfaceVariantLight)
                    }

                    OutlinedTextField(
                        value = title,
                        onValueChange = { viewModel.composerTitle.value = it },
                        placeholder = { Text("e.g., Q3 Breakthrough Launch & Tech Teaser", fontSize = 13.sp) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp)
                            .testTag("post_title_input"),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = SurfaceContainerLow,
                            focusedContainerColor = SurfaceContainerLowest,
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = IndigoPrimary
                        )
                    )
                }

                // Master Content Field
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.EditNote, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Master Content", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
                        }

                        // AI Polish Button
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .background(PrimaryFixed)
                                .clickable { viewModel.polishMasterContentWithAI() }
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "AI Polish", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = IndigoPrimary)
                        }
                    }

                    // Content Box with integrated toolbar and char count
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainerLow)
                    ) {
                        Column {
                            OutlinedTextField(
                                value = content,
                                onValueChange = { viewModel.composerContent.value = it },
                                placeholder = {
                                    Text(
                                        "Share your breakthrough, product drop, or tactical insights... PostPilot adapts length, tags, and formatting per platform in the next step.",
                                        fontSize = 13.sp,
                                        lineHeight = 18.sp
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(130.dp)
                                    .testTag("master_content_input"),
                                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedBorderColor = Color.Transparent,
                                    focusedBorderColor = Color.Transparent
                                )
                            )

                            // Toolbar
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(SurfaceContainerLowest.copy(alpha = 0.85f))
                                    .padding(horizontal = 8.dp, vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Icon(
                                        imageVector = Icons.Default.SentimentSatisfied,
                                        contentDescription = "Emoji",
                                        tint = OnSurfaceVariantLight,
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clickable { viewModel.composerContent.value += " 🚀" }
                                    )
                                    Icon(
                                        imageVector = Icons.Default.Image,
                                        contentDescription = "Media",
                                        tint = OnSurfaceVariantLight,
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clickable { viewModel.addComposerMediaPreset() }
                                    )
                                    Icon(
                                        imageVector = Icons.Default.Tag,
                                        contentDescription = "Tag",
                                        tint = OnSurfaceVariantLight,
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clickable { viewModel.addHashtagToComposer("#TechNews") }
                                    )
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "${content.length}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (content.length > 280) ErrorRed else IndigoPrimary
                                    )
                                    Text(
                                        text = " / 280 (X)",
                                        fontSize = 11.sp,
                                        color = OnSurfaceVariantLight
                                    )
                                }
                            }
                        }
                    }

                    // Quick Trending Hashtags
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, tint = OnSurfaceVariantLight, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Trending:", fontSize = 11.sp, color = OnSurfaceVariantLight)
                        Spacer(modifier = Modifier.width(6.dp))

                        val trendingTags = listOf("#BuildInPublic", "#TechNews", "#AI", "#CreatorEconomy")
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(trendingTags) { tag ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(SurfaceContainer)
                                        .clickable { viewModel.addHashtagToComposer(tag) }
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Text(text = tag, fontSize = 11.sp, color = IndigoPrimary, fontWeight = FontWeight.Medium)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Media Attachments Upload
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.PermMedia, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Media Attachments", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
                    }
                    Text(text = "Max 4 files (50MB)", fontSize = 11.sp, color = OnSurfaceVariantLight)
                }

                // Drop zone box
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceContainerLow)
                        .clickable { viewModel.addComposerMediaPreset() }
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(IndigoPrimary.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.CloudUpload, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "Tap to select photos or video", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
                    Text(text = "JPG, PNG, WebP or MP4", fontSize = 11.sp, color = OnSurfaceVariantLight)
                }

                // Media Previews Strip
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    attachedMedia.forEachIndexed { index, mediaUrl ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(88.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceContainer)
                        ) {
                            AsyncImage(
                                model = mediaUrl,
                                contentDescription = "Media item $index",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                            // Remove button
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(4.dp)
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(Color.Black.copy(alpha = 0.6f))
                                    .clickable { viewModel.removeComposerMedia(index) },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = "Remove", tint = Color.White, modifier = Modifier.size(12.dp))
                            }
                            // File size badge
                            Text(
                                text = if (index == 0) "1.2 MB" else "2.8 MB",
                                fontSize = 9.sp,
                                color = Color.White,
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(4.dp)
                                    .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(3.dp))
                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                            )
                        }
                    }

                    if (attachedMedia.size < 4) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .height(88.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceContainer)
                                .clickable { viewModel.addComposerMediaPreset() },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(imageVector = Icons.Default.AddPhotoAlternate, contentDescription = null, tint = OnSurfaceVariantLight, modifier = Modifier.size(20.dp))
                            Text(text = "Add More", fontSize = 11.sp, color = OnSurfaceVariantLight, modifier = Modifier.padding(top = 4.dp))
                        }
                    }
                }
            }
        }

        // Post Scheduling Banner
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceContainerLowest)
                    .shadow(1.dp, RoundedCornerShape(14.dp))
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Schedule, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(text = "Post Scheduling", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
                        Text(text = "Recommended slot: $scheduledTime", fontSize = 11.sp, color = OnSurfaceVariantLight)
                    }
                }

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainer)
                        .clickable { viewModel.showToast("Slot auto-optimized to highest creator engagement window") }
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Auto-Optimize", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                    Spacer(modifier = Modifier.width(3.dp))
                    Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(14.dp))
                }
            }
        }

        // Continue Button & Footer
        item {
            Column(
                modifier = Modifier.padding(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        if (content.isBlank()) {
                            viewModel.showToast("Please draft your message first")
                        } else {
                            viewModel.navigateTo(Screen.CREATE_POST_STAGE2)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("continue_to_channel_tuning_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = IndigoPrimary)
                ) {
                    Text(text = "Continue to Channel Tuning", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .clickable { viewModel.saveDraftPost() }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Bookmark, contentDescription = null, tint = SlateSecondary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Save as Draft", fontSize = 12.sp, color = SlateSecondary)
                    }

                    Text(text = " • ", fontSize = 12.sp, color = OnSurfaceVariantLight)

                    Row(
                        modifier = Modifier
                            .clickable {
                                viewModel.composerTitle.value = ""
                                viewModel.composerContent.value = ""
                                viewModel.showToast("Form cleared")
                            }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.DeleteSweep, contentDescription = null, tint = SlateSecondary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Clear form", fontSize = 12.sp, color = SlateSecondary)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreatePostStage2(viewModel: PostPilotViewModel) {
    val title by viewModel.composerTitle.collectAsState()
    val content by viewModel.composerContent.collectAsState()
    val activePlatform by viewModel.stage2ActivePlatform.collectAsState()
    val captionsMap by viewModel.stage2Captions.collectAsState()
    val hashtags by viewModel.composerHashtags.collectAsState()
    val currentCaption = captionsMap[activePlatform] ?: content

    var newTagInput by remember { mutableStateOf("") }
    var isAddingTag by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("create_post_stage2"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Sticky Sub-Nav Header
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
                            .clickable { viewModel.navigateTo(Screen.CREATE_POST_STAGE1) }
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = OnSurfaceVariantLight, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Create Post", fontSize = 13.sp, color = OnSurfaceVariantLight, fontWeight = FontWeight.Medium)
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { viewModel.saveDraftPost() },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainer, contentColor = OnSurfaceLight),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text(text = "Save Draft", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }

                        Button(
                            onClick = { viewModel.finalizeAndPublishPost() },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = IndigoPrimaryContainer, contentColor = Color.White),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text(text = "Next", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(14.dp))
                        }
                    }
                }

                // Stepper Progress Tracker
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceContainerLowest)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Step 1: Checked
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(18.dp).clip(CircleShape).background(SurfaceContainerHigh),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(12.dp))
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "1. Compose", fontSize = 11.sp, color = OnSurfaceVariantLight)
                    }

                    Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = OnSurfaceVariantLight, modifier = Modifier.size(14.dp))

                    // Step 2: Active
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(IndigoPrimary.copy(alpha = 0.1f))
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(18.dp).clip(CircleShape).background(IndigoPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "2", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Captions", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = IndigoPrimary)
                    }

                    Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = OnSurfaceVariantLight, modifier = Modifier.size(14.dp))

                    // Step 3: Upcoming
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(18.dp).clip(CircleShape).background(SurfaceContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "3", fontSize = 10.sp, color = OnSurfaceVariantLight)
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Review", fontSize = 11.sp, color = OnSurfaceVariantLight)
                    }
                }
            }
        }

        // Context title
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "STAGE 02 OF 03",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = IndigoPrimary,
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(IndigoPrimary.copy(alpha = 0.1f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "•", fontSize = 10.sp, color = OnSurfaceVariantLight)
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(LiveGreen))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Live Multi-Sync", fontSize = 11.sp, color = OnSurfaceVariantLight)
                }
                Text(text = "Customize Captions", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                Text(text = "Fine-tune platform-specific voice, hashtags, and limits tailored for high organic engagement.", fontSize = 12.sp, color = OnSurfaceVariantLight)
            }
        }

        // Master Content Summary Card
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceContainerLowest)
                    .shadow(1.dp, RoundedCornerShape(14.dp))
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainer)
                    ) {
                        AsyncImage(
                            model = "https://lh3.googleusercontent.com/aida-public/AB6AXuAE13sxezVH1SPnm3iGev3qOrFpsag9e9dNLSvJ5xHu9oJasW6uaN38aqPkhSss2VFwruKlxORNHrfPD5F3CtprjdbqiaE6MoQy8tVlJjpEPkiyKe8mb3vxyWYlsFuAE4-qxKPMjrTuVMClE-psynTQyzOLVPBgIwYyhJRGYKqO6hKwHjilf9h4Zdkg2bFSDG7MRgpnSD6oUtz8EY4jnvnR94jMD03yBnxpxXKH4v81oJj9g3gaiGpG",
                            contentDescription = "Master Preview",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = "MASTER",
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .background(Color.Black.copy(alpha = 0.6f))
                                .padding(vertical = 1.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = title.ifBlank { "AI Travel Planner" },
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnSurfaceLight
                            )
                            Row(
                                modifier = Modifier.clickable { viewModel.navigateTo(Screen.CREATE_POST_STAGE1) },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "Edit Master", fontSize = 11.sp, color = IndigoPrimary, fontWeight = FontWeight.SemiBold)
                                Icon(imageVector = Icons.Default.OpenInNew, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(12.dp))
                            }
                        }
                        Text(
                            text = content.ifBlank { "Built an AI-powered travel planner that helps users create personalized trips with tailored itineraries in seconds..." },
                            fontSize = 12.sp,
                            color = OnSurfaceVariantLight,
                            maxLines = 2,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                // 4 Channels Connected Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerLow)
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Sync, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "4 Channels Connected", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        PlatformMiniIcon(color = ColorInstagram, text = "IG")
                        PlatformMiniIcon(color = ColorLinkedIn, text = "LI")
                        PlatformMiniIcon(color = ColorX, text = "X")
                        PlatformMiniIcon(color = ColorFacebook, text = "FB")
                    }
                }
            }
        }

        // Platform Variants Tabs
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Platform Variants", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                    Text(
                        text = "4 of 4 synchronized",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = IndigoPrimary,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainerHigh)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }

                // Horizontal Tab selection
                val tabs = listOf("INSTAGRAM", "LINKEDIN", "X", "FACEBOOK")
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(tabs) { tab ->
                        val isSelected = activePlatform == tab
                        val (label, tint) = when (tab) {
                            "INSTAGRAM" -> "Instagram" to ColorInstagram
                            "LINKEDIN" -> "LinkedIn" to ColorLinkedIn
                            "X" -> "X (Twitter)" to ColorX
                            "FACEBOOK" -> "Facebook" to ColorFacebook
                            else -> tab to IndigoPrimary
                        }

                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (isSelected) IndigoPrimary else SurfaceContainerLowest)
                                .shadow(if (isSelected) 2.dp else 1.dp, RoundedCornerShape(20.dp))
                                .clickable { viewModel.stage2ActivePlatform.value = tab }
                                .padding(horizontal = 12.dp, vertical = 7.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) Color.White else tint)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = label,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else OnSurfaceLight
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = if (isSelected) Color.White else IndigoPrimary,
                                modifier = Modifier.size(13.dp)
                            )
                        }
                    }
                }
            }
        }

        // Active Platform Editor Card (e.g. Instagram or selected platform)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceContainerLowest)
                    .shadow(2.dp, RoundedCornerShape(14.dp))
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    when (activePlatform) {
                                        "INSTAGRAM" -> ColorInstagram.copy(alpha = 0.15f)
                                        "LINKEDIN" -> ColorLinkedIn.copy(alpha = 0.15f)
                                        "X" -> ColorX.copy(alpha = 0.15f)
                                        else -> ColorFacebook.copy(alpha = 0.15f)
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = when (activePlatform) {
                                    "INSTAGRAM" -> "IG"
                                    "LINKEDIN" -> "LI"
                                    "X" -> "X"
                                    else -> "FB"
                                },
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = when (activePlatform) {
                                    "INSTAGRAM" -> ColorInstagram
                                    "LINKEDIN" -> ColorLinkedIn
                                    "X" -> ColorX
                                    else -> ColorFacebook
                                }
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(text = "$activePlatform Caption", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                            Text(text = "Feed Post & Carousel", fontSize = 11.sp, color = OnSurfaceVariantLight)
                        }
                    }

                    Text(
                        text = "Feed Post & Reel",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = IndigoPrimary,
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(SurfaceContainerHigh)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                // AI Copy Optimizers Bar
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(IndigoPrimary.copy(alpha = 0.05f))
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "AI Copy Optimizers", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = IndigoPrimary)
                        }
                        Text(
                            text = "✨ Generate New",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = IndigoPrimary,
                            modifier = Modifier.clickable {
                                viewModel.applyAICopyOptimizer(activePlatform, "Add Emojis ✨")
                            }
                        )
                    }

                    val optimizers = listOf("Improve Hook", "Shorten", "Add Emojis ✨", "Call to Action")
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(optimizers) { action ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SurfaceContainerLowest)
                                    .shadow(1.dp, RoundedCornerShape(8.dp))
                                    .clickable { viewModel.applyAICopyOptimizer(activePlatform, action) }
                                    .padding(horizontal = 8.dp, vertical = 5.dp)
                            ) {
                                Text(text = action, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = OnSurfaceLight)
                            }
                        }
                    }
                }

                // Caption Body Editor
                Column {
                    Text(text = "Caption Body", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceVariantLight)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainerLow)
                    ) {
                        Column {
                            OutlinedTextField(
                                value = currentCaption,
                                onValueChange = { viewModel.updatePlatformCaption(activePlatform, it) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(130.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedBorderColor = Color.Transparent,
                                    focusedBorderColor = Color.Transparent
                                )
                            )

                            // Editor Footer
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(SurfaceContainer.copy(alpha = 0.5f))
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${currentCaption.length} / 2,200 chars",
                                    fontSize = 11.sp,
                                    color = OnSurfaceVariantLight
                                )
                                Text(
                                    text = "${hashtags.size} / 30 hashtags",
                                    fontSize = 11.sp,
                                    color = OnSurfaceVariantLight
                                )
                            }
                        }
                    }
                }

                // Dedicated Platform Hashtags Manager
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Tag, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Platform Hashtags", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceVariantLight)
                        }
                        Text(
                            text = "Find Trending",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = IndigoPrimary,
                            modifier = Modifier.clickable {
                                viewModel.addPlatformHashtag("#TechTrends2026")
                            }
                        )
                    }

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        hashtags.forEach { tag ->
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(SurfaceContainer)
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = tag, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = OnSurfaceLight)
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Remove",
                                    tint = OnSurfaceVariantLight,
                                    modifier = Modifier
                                        .size(12.dp)
                                        .clickable { viewModel.removePlatformHashtag(tag) }
                                )
                            }
                        }

                        if (!isAddingTag) {
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(IndigoPrimary.copy(alpha = 0.1f))
                                    .clickable { isAddingTag = true }
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(text = "Add Tag", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = IndigoPrimary)
                            }
                        } else {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                OutlinedTextField(
                                    value = newTagInput,
                                    onValueChange = { newTagInput = it },
                                    placeholder = { Text("#tag", fontSize = 10.sp) },
                                    modifier = Modifier.width(90.dp).height(36.dp),
                                    singleLine = true
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Button(
                                    onClick = {
                                        if (newTagInput.isNotBlank()) {
                                            val formatted = if (newTagInput.startsWith("#")) newTagInput else "#$newTagInput"
                                            viewModel.addPlatformHashtag(formatted)
                                            newTagInput = ""
                                        }
                                        isAddingTag = false
                                    },
                                    modifier = Modifier.height(32.dp),
                                    contentPadding = androidx.compose.foundation.layout.PaddingValues(4.dp)
                                ) {
                                    Text("Add", fontSize = 10.sp)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Preview Card: LinkedIn
        item {
            PlatformPreviewVariantCard(
                title = "LinkedIn Caption",
                subtitle = "Article & Feed Post",
                badgeText = "Professional Format",
                color = ColorLinkedIn,
                badgeColor = IndigoPrimary,
                content = captionsMap["LINKEDIN"] ?: "Excited to share our AI travel project architecture with the developer community!",
                charCount = "${captionsMap["LINKEDIN"]?.length ?: 318} / 3,000 characters",
                onAction1 = { viewModel.applyAICopyOptimizer("LINKEDIN", "Improve Hook") },
                action1Label = "✨ Enhance with AI",
                onAction2 = { viewModel.applyAICopyOptimizer("LINKEDIN", "Shorten") },
                action2Label = "Shorten"
            )
        }

        // Preview Card: X (Twitter) with circular countdown
        item {
            val xCaption = captionsMap["X"] ?: "Just launched AI Travel Planner! ✈️🤖 Plan custom trips in seconds. Check it out!"
            val charLimit = 280
            val charProgress = (xCaption.length.toFloat() / charLimit).coerceIn(0f, 1f)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceContainerLowest)
                    .shadow(1.dp, RoundedCornerShape(14.dp))
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(ColorX.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "𝕏", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = ColorX)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(text = "X Post", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                            Text(text = "Single Tweet Format", fontSize = 11.sp, color = OnSurfaceVariantLight)
                        }
                    }

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainerLow)
                            .padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            progress = { charProgress },
                            modifier = Modifier.size(12.dp),
                            color = IndigoPrimary,
                            trackColor = SurfaceContainerHigh,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "${xCaption.length} / $charLimit", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerLow)
                        .padding(10.dp)
                ) {
                    Text(text = xCaption, fontSize = 12.sp, color = OnSurfaceLight, lineHeight = 17.sp)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "${100 - (charProgress * 100).toInt()}% remaining", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = IndigoPrimary)

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(SurfaceContainer)
                                .clickable { viewModel.applyAICopyOptimizer("X", "Shorten") }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(text = "Shorten", fontSize = 11.sp, color = OnSurfaceLight)
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(SurfaceContainer)
                                .clickable { viewModel.applyAICopyOptimizer("X", "Improve Hook") }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(text = "Make Punchy", fontSize = 11.sp, color = OnSurfaceLight)
                        }
                    }
                }
            }
        }

        // Preview Card: Facebook
        item {
            PlatformPreviewVariantCard(
                title = "Facebook Caption",
                subtitle = "Community Page Post",
                badgeText = "Casual Tone",
                color = ColorFacebook,
                badgeColor = IndigoPrimary,
                content = captionsMap["FACEBOOK"] ?: "Hey everyone! 👋 Really excited to share a project I've been working on: an AI Travel Planner designed to build custom itineraries based on your travel style. Would love your feedback!",
                charCount = "${captionsMap["FACEBOOK"]?.length ?: 174} characters",
                onAction1 = { viewModel.applyAICopyOptimizer("FACEBOOK", "Improve Hook") },
                action1Label = "Improve",
                onAction2 = { viewModel.applyAICopyOptimizer("FACEBOOK", "Call to Action") },
                action2Label = "Add Question"
            )
        }

        // Pro Creator Tip Card
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainerLow)
                    .padding(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(imageVector = Icons.Default.Lightbulb, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = "Pro Creator Tip", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                    Text(
                        text = "Instagram performs best with 3-5 hyper-relevant tags, while LinkedIn algorithmic reach prioritizes questions that encourage comment threads.",
                        fontSize = 12.sp,
                        color = OnSurfaceVariantLight,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        // Bottom CTAs
        item {
            Column(
                modifier = Modifier.padding(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { viewModel.finalizeAndPublishPost() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("next_review_and_schedule_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = IndigoPrimary)
                ) {
                    Text(text = "Next: Review & Schedule", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .clickable { viewModel.saveDraftPost() }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Bookmark, contentDescription = null, tint = SlateSecondary, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Save as Draft", fontSize = 12.sp, color = SlateSecondary)
                    }

                    Text(text = " • ", fontSize = 12.sp, color = OnSurfaceVariantLight)

                    Row(
                        modifier = Modifier
                            .clickable {
                                viewModel.stage2Captions.value = mutableMapOf(
                                    "INSTAGRAM" to content,
                                    "LINKEDIN" to content,
                                    "X" to content,
                                    "FACEBOOK" to content
                                )
                                viewModel.showToast("All variants re-synced from master copy")
                            }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.RestartAlt, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Sync All from Master", fontSize = 12.sp, color = IndigoPrimary, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
fun PlatformMiniIcon(color: Color, text: String) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(color.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, fontSize = 8.sp, fontWeight = FontWeight.Bold, color = color)
    }
}

@Composable
fun PlatformPreviewVariantCard(
    title: String,
    subtitle: String,
    badgeText: String,
    color: Color,
    badgeColor: Color,
    content: String,
    charCount: String,
    onAction1: () -> Unit,
    action1Label: String,
    onAction2: () -> Unit,
    action2Label: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(SurfaceContainerLowest)
            .shadow(1.dp, RoundedCornerShape(14.dp))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(color.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = title.take(2).uppercase(), fontSize = 11.sp, fontWeight = FontWeight.Bold, color = color)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                    Text(text = subtitle, fontSize = 11.sp, color = OnSurfaceVariantLight)
                }
            }

            Text(
                text = badgeText,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = badgeColor,
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(SurfaceContainerHigh)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(SurfaceContainerLow)
                .padding(10.dp)
        ) {
            Text(text = content, fontSize = 12.sp, color = OnSurfaceLight, lineHeight = 17.sp)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = charCount, fontSize = 11.sp, color = OnSurfaceVariantLight)

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(SurfaceContainer)
                        .clickable(onClick = onAction1)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(text = action1Label, fontSize = 11.sp, color = OnSurfaceLight)
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(SurfaceContainer)
                        .clickable(onClick = onAction2)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(text = action2Label, fontSize = 11.sp, color = OnSurfaceLight)
                }
            }
        }
    }
}

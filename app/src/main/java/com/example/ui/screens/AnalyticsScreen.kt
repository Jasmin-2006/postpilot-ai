package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.DynamicFeed
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ColorFacebook
import com.example.ui.theme.ColorInstagram
import com.example.ui.theme.ColorLinkedIn
import com.example.ui.theme.ColorThreads
import com.example.ui.theme.ColorX
import com.example.ui.theme.ColorYouTube
import com.example.ui.theme.ErrorContainer
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.IndigoPrimary
import com.example.ui.theme.IndigoPrimaryContainer
import com.example.ui.theme.LiveGreen
import com.example.ui.theme.OnSurfaceLight
import com.example.ui.theme.OnSurfaceVariantLight
import com.example.ui.theme.PrimaryFixed
import com.example.ui.theme.PrimaryFixedDim
import com.example.ui.theme.SlateSecondary
import com.example.ui.theme.SlateSecondaryContainer
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.TealTertiary
import com.example.ui.theme.TertiaryFixed
import com.example.ui.theme.TertiaryFixedDim
import com.example.ui.viewmodel.PostPilotViewModel
import com.example.ui.viewmodel.Screen

@Composable
fun AnalyticsScreen(viewModel: PostPilotViewModel) {
    val posts by viewModel.posts.collectAsState()
    var selectedRange by remember { mutableStateOf("Last 30 Days") }

    val totalCount = posts.size
    val publishedCount = posts.count { it.status == "PUBLISHED" }
    val scheduledCount = posts.count { it.status == "SCHEDULED" }
    val draftsCount = posts.count { it.status == "DRAFT" }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("analytics_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Page Header & Range Controls
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(IndigoPrimary))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "ANALYTICS & ACTIVITY",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = IndigoPrimary,
                        letterSpacing = 0.5.sp
                    )
                }

                Text(text = "Content Analytics", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                Text(
                    text = "Understand your publishing activity across your social platforms.",
                    fontSize = 13.sp,
                    color = OnSurfaceVariantLight
                )

                // Date selector & export report
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainerLowest)
                            .shadow(1.dp, RoundedCornerShape(10.dp))
                            .clickable {
                                selectedRange = if (selectedRange == "Last 30 Days") "Last 7 Days" else "Last 30 Days"
                                viewModel.showToast("Selected range: $selectedRange")
                            }
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.CalendarToday, contentDescription = null, tint = OnSurfaceVariantLight, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = selectedRange, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
                        Icon(imageVector = Icons.Default.ExpandMore, contentDescription = null, tint = OnSurfaceVariantLight, modifier = Modifier.size(16.dp))
                    }

                    Button(
                        onClick = { viewModel.showToast("Exporting Analytics summary report (PDF/CSV)") },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = IndigoPrimary),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Download, contentDescription = null, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Export Report", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        // 4 KPI Scorecards (2x2)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AnalyticsScorecard(
                        modifier = Modifier.weight(1f),
                        label = "Total Posts",
                        count = totalCount.toString(),
                        subtext = "Created in selected ...",
                        icon = Icons.Default.DynamicFeed,
                        iconColor = IndigoPrimary,
                        iconBg = SurfaceContainer
                    )
                    AnalyticsScorecard(
                        modifier = Modifier.weight(1f),
                        label = "Published",
                        count = publishedCount.toString(),
                        subtext = "Successfully complet...",
                        icon = Icons.Default.CheckCircle,
                        iconColor = TealTertiary,
                        iconBg = SurfaceContainerLow
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AnalyticsScorecard(
                        modifier = Modifier.weight(1f),
                        label = "Scheduled",
                        count = scheduledCount.toString(),
                        subtext = "Queued for automat...",
                        icon = Icons.Default.Schedule,
                        iconColor = IndigoPrimary,
                        iconBg = PrimaryFixed
                    )
                    AnalyticsScorecard(
                        modifier = Modifier.weight(1f),
                        label = "Drafts",
                        count = draftsCount.toString(),
                        subtext = "Work in progress in q...",
                        icon = Icons.Default.EditNote,
                        iconColor = SlateSecondary,
                        iconBg = SurfaceContainer
                    )
                }
            }
        }

        // Publishing Activity Bar Chart Card
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
                        Text(text = "Publishing Activity", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                        Text(text = "Posts published over time (Sep 1 – Sep 30)", fontSize = 11.sp, color = OnSurfaceVariantLight)
                    }

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainerLow)
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, tint = TealTertiary, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(text = "+28% vs previous period", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TealTertiary)
                    }
                }

                // Interactive Bar Chart Visual
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(top = 10.dp)
                ) {
                    // Guide lines and bars
                    Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
                        // Horizontal background grid lines
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            listOf("6", "4", "2", "0").forEach { mark ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = mark, fontSize = 10.sp, color = OnSurfaceVariantLight, modifier = Modifier.width(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Box(modifier = Modifier.weight(1f).height(1.dp).background(SurfaceContainer))
                                }
                            }
                        }

                        // 4 Interactive Bars
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 24.dp, bottom = 4.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            // Week 1 (3 posts: 50%)
                            ActivityBar(label = "Week 1", count = "3 posts", heightFraction = 0.5f, isPeak = false, onClick = { viewModel.showToast("Week 1: 3 posts published") })
                            // Week 2 (5 posts: 83%)
                            ActivityBar(label = "Week 2", count = "5 posts", heightFraction = 0.83f, isPeak = false, onClick = { viewModel.showToast("Week 2: 5 posts published") })
                            // Week 3 (4 posts: 66%)
                            ActivityBar(label = "Week 3", count = "4 posts", heightFraction = 0.66f, isPeak = false, onClick = { viewModel.showToast("Week 3: 4 posts published") })
                            // Week 4 (6 posts: 100% Peak)
                            ActivityBar(label = "Week 4", count = "6 posts (Peak)", heightFraction = 1f, isPeak = true, onClick = { viewModel.showToast("Week 4: 6 posts published (Peak!)") })
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Total 18 published items verified", fontSize = 11.sp, color = OnSurfaceVariantLight)
                    Text(text = "Daily average: 0.6 posts/day", fontSize = 11.sp, color = OnSurfaceVariantLight)
                }
            }
        }

        // Posts by Platform Breakdown Card
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
                        Text(text = "Posts by Platform", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                        Text(text = "Multi-channel cross-posting distributions", fontSize = 11.sp, color = OnSurfaceVariantLight)
                    }

                    Text(
                        text = "40 slots recorded",
                        fontSize = 11.sp,
                        color = OnSurfaceVariantLight,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainer)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                // Platform progress rows
                PlatformDistributionRow(name = "Instagram", dotColor = ColorInstagram, postsCount = "12 posts", percentage = "30%", progress = 0.30f, barColor = IndigoPrimary)
                PlatformDistributionRow(name = "LinkedIn", dotColor = ColorLinkedIn, postsCount = "10 posts", percentage = "25%", progress = 0.25f, barColor = IndigoPrimary)
                PlatformDistributionRow(name = "X (Twitter)", dotColor = ColorX, postsCount = "6 posts", percentage = "15%", progress = 0.15f, barColor = IndigoPrimaryContainer)
                PlatformDistributionRow(name = "Facebook", dotColor = ColorFacebook, postsCount = "5 posts", percentage = "12.5%", progress = 0.125f, barColor = PrimaryFixedDim)
                PlatformDistributionRow(name = "Threads", dotColor = ColorThreads, postsCount = "4 posts", percentage = "10%", progress = 0.10f, barColor = SlateSecondaryContainer)
                PlatformDistributionRow(name = "YouTube", dotColor = ColorYouTube, postsCount = "3 posts", percentage = "7.5%", progress = 0.075f, barColor = SurfaceContainerHigh)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Internal platform output counts only", fontSize = 11.sp, color = OnSurfaceVariantLight)
                    Text(text = "6 Connected", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = IndigoPrimary)
                }
            }
        }

        // Content Status Breakdown Card
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
                        Text(text = "Content Status Breakdown", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                        Text(text = "Lifecycle states of current calendar entries", fontSize = 11.sp, color = OnSurfaceVariantLight)
                    }

                    Text(
                        text = "24 Catalog items",
                        fontSize = 11.sp,
                        color = OnSurfaceVariantLight,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainer)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                // Stacked Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .clip(RoundedCornerShape(6.dp))
                ) {
                    Box(modifier = Modifier.weight(0.75f).fillMaxHeight().background(TealTertiary))
                    Box(modifier = Modifier.weight(0.125f).fillMaxHeight().background(IndigoPrimary))
                    Box(modifier = Modifier.weight(0.042f).fillMaxHeight().background(TertiaryFixedDim))
                    Box(modifier = Modifier.weight(0.083f).fillMaxHeight().background(SlateSecondaryContainer))
                }

                // 2x2 Legend
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    StatusLegendTile(modifier = Modifier.weight(1f), dotColor = TealTertiary, label = "Published", count = "18", percent = "75.0%", percentColor = TealTertiary)
                    StatusLegendTile(modifier = Modifier.weight(1f), dotColor = IndigoPrimary, label = "Scheduled", count = "3", percent = "12.5%", percentColor = IndigoPrimary)
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    StatusLegendTile(modifier = Modifier.weight(1f), dotColor = TertiaryFixedDim, label = "Pending", count = "1", percent = "4.2%", percentColor = OnSurfaceVariantLight)
                    StatusLegendTile(modifier = Modifier.weight(1f), dotColor = SlateSecondary, label = "Draft", count = "2", percent = "8.3%", percentColor = OnSurfaceVariantLight)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Publishing Completion Rate", fontSize = 12.sp, color = OnSurfaceVariantLight)
                    Text(text = "75%", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TealTertiary)
                }
            }
        }

        // Platform Activity (Mobile Items)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "Platform Activity", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                Text(text = "Breakdown of content lifecycle across channels", fontSize = 12.sp, color = OnSurfaceVariantLight)

                PlatformActivityMobileCard(tag = "IG", color = ColorInstagram, name = "Instagram", total = "12 total", breakdown = "9 published · 2 scheduled · 1 draft")
                PlatformActivityMobileCard(tag = "IN", color = ColorLinkedIn, name = "LinkedIn", total = "10 total", breakdown = "8 published · 1 scheduled · 1 draft")
                PlatformActivityMobileCard(tag = "X", color = ColorX, name = "X (Twitter)", total = "6 total", breakdown = "4 published · 1 scheduled · 1 draft")
                PlatformActivityMobileCard(tag = "FB", color = ColorFacebook, name = "Facebook", total = "5 total", breakdown = "4 published · 0 scheduled · 1 draft")
                PlatformActivityMobileCard(tag = "TH", color = ColorThreads, name = "Threads", total = "4 total", breakdown = "3 published · 1 scheduled · 0 draft")
                PlatformActivityMobileCard(tag = "YT", color = ColorYouTube, name = "YouTube", total = "3 total", breakdown = "2 published · 0 scheduled · 1 draft")
            }
        }

        // Recent Publishing Activity (Timeline)
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
                        Text(text = "Recent Publishing Activity", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                        Text(text = "Chronological audit of catalog events", fontSize = 11.sp, color = OnSurfaceVariantLight)
                    }

                    Row(
                        modifier = Modifier.clickable { viewModel.showToast("Audit history log view") },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "View full history", fontSize = 11.sp, color = IndigoPrimary, fontWeight = FontWeight.SemiBold)
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(13.dp))
                    }
                }

                // Timeline events
                TimelineItemRow(
                    dotColor = LiveGreen,
                    title = "‘AI Travel Planner’",
                    platformBadge = "Instagram",
                    badgeColor = ColorInstagram,
                    subtitle = "Post marked as published successfully",
                    time = "Today · 7:15 PM"
                )

                TimelineItemRow(
                    dotColor = LiveGreen,
                    title = "‘AI Travel Planner’",
                    platformBadge = "LinkedIn",
                    badgeColor = ColorLinkedIn,
                    subtitle = "Carousel payload delivered to feed",
                    time = "Today · 7:30 PM"
                )

                TimelineItemRow(
                    dotColor = IndigoPrimary,
                    title = "‘AI/ML Roadmap’",
                    platformBadge = "X",
                    badgeColor = ColorX,
                    subtitle = "Queued in automated publication buffer",
                    time = "Tomorrow · 6:00 PM"
                )

                TimelineItemRow(
                    dotColor = SlateSecondary,
                    title = "‘My New Portfolio’",
                    platformBadge = "Draft",
                    badgeColor = SlateSecondary,
                    subtitle = "Composition saved locally with 2 image assets",
                    time = "Yesterday"
                )
            }
        }

        // Your Content Overview (Factual summary)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceContainerLow)
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(IndigoPrimaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Lightbulb, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = "Your Content Overview", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                        Text(text = "Factual insights derived from your internal publishing cadence", fontSize = 11.sp, color = OnSurfaceVariantLight)
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OverviewFactoidBox(modifier = Modifier.weight(1f), label = "Most Used Platform", value = "Instagram", subtext = "(12 posts)", isValuePrimary = true)
                    OverviewFactoidBox(modifier = Modifier.weight(1f), label = "Most Active Day", value = "Wednesday", subtext = "Peak cadence", isValuePrimary = false)
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OverviewFactoidBox(modifier = Modifier.weight(1f), label = "Created This Month", value = "18 posts", subtext = "+28%", isValuePrimary = false)
                    OverviewFactoidBox(modifier = Modifier.weight(1f), label = "Completion Rate", value = "75%", subtext = "(18/24)", isValuePrimary = false)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Factual metrics derived exclusively from your local PostPilot content database. No synthetic social impressions or engagement counts included.",
                        fontSize = 11.sp,
                        color = OnSurfaceVariantLight,
                        lineHeight = 15.sp
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun AnalyticsScorecard(
    modifier: Modifier = Modifier,
    label: String,
    count: String,
    subtext: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    iconBg: Color
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceContainerLowest)
            .shadow(1.dp, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, fontSize = 11.sp, color = OnSurfaceVariantLight)
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(16.dp))
            }
        }

        Text(text = count, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
        Text(text = subtext, fontSize = 10.sp, color = OnSurfaceVariantLight, maxLines = 1)
    }
}

@Composable
fun ActivityBar(
    label: String,
    count: String,
    heightFraction: Float,
    isPeak: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(56.dp)
            .fillMaxHeight()
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(
            modifier = Modifier
                .width(44.dp)
                .fillMaxHeight(heightFraction)
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .background(if (isPeak) IndigoPrimary else PrimaryFixed)
        )
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isPeak) FontWeight.Bold else FontWeight.Medium,
            color = if (isPeak) IndigoPrimary else OnSurfaceVariantLight,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun PlatformDistributionRow(
    name: String,
    dotColor: Color,
    postsCount: String,
    percentage: String,
    progress: Float,
    barColor: Color
) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(dotColor))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = name, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = OnSurfaceLight)
            }
            Text(text = "$postsCount · $percentage", fontSize = 11.sp, color = OnSurfaceVariantLight)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(SurfaceContainer)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .fillMaxHeight()
                    .background(barColor)
            )
        }
    }
}

@Composable
fun StatusLegendTile(
    modifier: Modifier = Modifier,
    dotColor: Color,
    label: String,
    count: String,
    percent: String,
    percentColor: Color
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(SurfaceContainerLow)
            .padding(10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(dotColor))
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = label, fontSize = 11.sp, color = OnSurfaceLight)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = count, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
            Text(text = percent, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = percentColor)
        }
    }
}

@Composable
fun PlatformActivityMobileCard(
    tag: String,
    color: Color,
    name: String,
    total: String,
    breakdown: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(SurfaceContainerLow)
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(color.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = tag, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = color)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = name, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
            }
            Text(text = total, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
        }
        Text(text = breakdown, fontSize = 11.sp, color = OnSurfaceVariantLight)
    }
}

@Composable
fun TimelineItemRow(
    dotColor: Color,
    title: String,
    platformBadge: String,
    badgeColor: Color,
    subtitle: String,
    time: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .padding(top = 4.dp)
                .size(8.dp)
                .clip(CircleShape)
                .background(dotColor)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = platformBadge,
                    fontSize = 10.sp,
                    color = badgeColor,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(badgeColor.copy(alpha = 0.1f))
                        .padding(horizontal = 6.dp, vertical = 1.dp)
                )
            }
            Text(text = subtitle, fontSize = 11.sp, color = OnSurfaceVariantLight)
        }
        Text(text = time, fontSize = 10.sp, color = OnSurfaceVariantLight)
    }
}

@Composable
fun OverviewFactoidBox(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    subtext: String,
    isValuePrimary: Boolean
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(SurfaceContainerLowest)
            .padding(10.dp)
    ) {
        Text(text = label, fontSize = 10.sp, color = OnSurfaceVariantLight)
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 2.dp)) {
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (isValuePrimary) IndigoPrimary else OnSurfaceLight
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = subtext, fontSize = 10.sp, color = OnSurfaceVariantLight)
        }
    }
}

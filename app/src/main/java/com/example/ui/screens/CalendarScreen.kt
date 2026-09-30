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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.Web
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
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
import com.example.ui.theme.TealTertiary
import com.example.ui.theme.TertiaryFixed
import com.example.ui.viewmodel.PostPilotViewModel
import com.example.ui.viewmodel.Screen

@Composable
fun CalendarScreen(viewModel: PostPilotViewModel) {
    val posts by viewModel.posts.collectAsState()
    val selectedDate by viewModel.selectedCalendarDate.collectAsState()
    val calendarViewMode by viewModel.calendarViewMode.collectAsState()

    var activePlatformFilter by remember { mutableStateOf("ALL") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("calendar_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Calendar Hero Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Content Calendar", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                        Text(text = "Plan, schedule, and track content across all channels.", fontSize = 13.sp, color = OnSurfaceVariantLight)
                    }

                    Button(
                        onClick = { viewModel.navigateTo(Screen.CREATE_POST_STAGE1) },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = IndigoPrimary),
                        modifier = Modifier.height(38.dp)
                    ) {
                        Icon(imageVector = Icons.Default.AddCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "+ Post", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    }
                }

                // Month Navigator & View Mode Controls
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceContainerLow)
                        .padding(10.dp),
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
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SurfaceContainerLowest)
                                    .clickable { viewModel.showToast("September 2026") },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = "Prev", tint = OnSurfaceLight, modifier = Modifier.size(18.dp))
                            }

                            Text(
                                text = "October 2026",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnSurfaceLight,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )

                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SurfaceContainerLowest)
                                    .clickable { viewModel.showToast("November 2026") },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.ChevronRight, contentDescription = "Next", tint = OnSurfaceLight, modifier = Modifier.size(18.dp))
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceContainerLowest)
                                .clickable {
                                    viewModel.setSelectedCalendarDate("2026-10-01")
                                    viewModel.showToast("Set to Today: Oct 1, 2026")
                                }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(text = "Today", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = IndigoPrimary)
                        }
                    }

                    // Segmented Controls (Month / Week / Day)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainer)
                            .padding(2.dp)
                    ) {
                        val modes = listOf("MONTH" to "Month", "WEEK" to "Week", "DAY" to "Day")
                        modes.forEach { (mode, label) ->
                            val isSelected = calendarViewMode == mode
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSelected) SurfaceContainerLowest else Color.Transparent)
                                    .shadow(if (isSelected) 1.dp else 0.dp, RoundedCornerShape(6.dp))
                                    .clickable { viewModel.setCalendarViewMode(mode) }
                                    .padding(vertical = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = label,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) IndigoPrimary else OnSurfaceVariantLight
                                )
                            }
                        }
                    }
                }
            }
        }

        // Filter Strip (Platforms & Statuses)
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(SurfaceContainerLowest)
                            .shadow(1.dp, RoundedCornerShape(16.dp))
                            .clickable {
                                activePlatformFilter = "ALL"
                                viewModel.showToast("Filter: All Platforms")
                            }
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(IndigoPrimary))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "All Platforms", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
                        Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null, tint = OnSurfaceVariantLight, modifier = Modifier.size(14.dp))
                    }
                }

                item {
                    CalendarFilterPill(
                        label = "IG",
                        icon = Icons.Default.PhotoCamera,
                        isSelected = activePlatformFilter == "IG",
                        onClick = { activePlatformFilter = if (activePlatformFilter == "IG") "ALL" else "IG" }
                    )
                }

                item {
                    CalendarFilterPill(
                        label = "LI",
                        icon = Icons.Default.Work,
                        isSelected = activePlatformFilter == "LI",
                        onClick = { activePlatformFilter = if (activePlatformFilter == "LI") "ALL" else "LI" }
                    )
                }

                item {
                    CalendarFilterPill(
                        label = "X",
                        icon = Icons.Default.Tag,
                        isSelected = activePlatformFilter == "X",
                        onClick = { activePlatformFilter = if (activePlatformFilter == "X") "ALL" else "X" }
                    )
                }

                item {
                    CalendarFilterPill(
                        label = "Threads",
                        icon = Icons.Default.Forum,
                        isSelected = activePlatformFilter == "THREADS",
                        onClick = { activePlatformFilter = if (activePlatformFilter == "THREADS") "ALL" else "THREADS" }
                    )
                }

                item {
                    Box(modifier = Modifier.width(1.dp).height(16.dp).background(SurfaceContainerHigh))
                }

                item {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(SurfaceContainerLowest)
                            .shadow(1.dp, RoundedCornerShape(16.dp))
                            .clickable { viewModel.showToast("Filtered by Scheduled posts") }
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(5.dp).clip(CircleShape).background(IndigoPrimary))
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = "Scheduled", fontSize = 11.sp, color = OnSurfaceLight)
                    }
                }

                item {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(SurfaceContainerLowest)
                            .shadow(1.dp, RoundedCornerShape(16.dp))
                            .clickable { viewModel.showToast("Filtered by Published posts") }
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(5.dp).clip(CircleShape).background(LiveGreen))
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = "Published", fontSize = 11.sp, color = OnSurfaceLight)
                    }
                }

                item {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(SurfaceContainerLowest)
                            .shadow(1.dp, RoundedCornerShape(16.dp))
                            .clickable { viewModel.showToast("Filtered by Drafts") }
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(5.dp).clip(CircleShape).background(SlateSecondary))
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = "Drafts", fontSize = 11.sp, color = OnSurfaceLight)
                    }
                }
            }
        }

        // Monthly Interactive Grid
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceContainerLowest)
                    .shadow(1.dp, RoundedCornerShape(14.dp))
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Day of Week Header
                Row(modifier = Modifier.fillMaxWidth()) {
                    listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").forEach { day ->
                        Text(
                            text = day,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = OnSurfaceVariantLight,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Row 1: 28, 29, 30 (Published), 1 (Selected Oct 1), 2, 3 (Scheduled), 4
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                    CalendarDayCell(dayNumber = "28", isDimmed = true, modifier = Modifier.weight(1f), onClick = {})
                    CalendarDayCell(dayNumber = "29", isDimmed = true, modifier = Modifier.weight(1f), onClick = {})
                    CalendarDayCell(dayNumber = "30", dotColor = LiveGreen, modifier = Modifier.weight(1f), onClick = { viewModel.setSelectedCalendarDate("2026-09-30") })
                    CalendarDayCell(
                        dayNumber = "1",
                        isSelected = selectedDate == "2026-10-01",
                        dots = listOf(Color.White, PrimaryFixed),
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.setSelectedCalendarDate("2026-10-01") }
                    )
                    CalendarDayCell(dayNumber = "2", modifier = Modifier.weight(1f), onClick = { viewModel.setSelectedCalendarDate("2026-10-02") })
                    CalendarDayCell(dayNumber = "3", dotColor = IndigoPrimary, modifier = Modifier.weight(1f), onClick = { viewModel.setSelectedCalendarDate("2026-10-03") })
                    CalendarDayCell(dayNumber = "4", modifier = Modifier.weight(1f), onClick = { viewModel.setSelectedCalendarDate("2026-10-04") })
                }

                // Row 2: 5 (Draft), 6, 7, 8 (Scheduled), 9, 10, 11
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                    CalendarDayCell(dayNumber = "5", dotColor = SlateSecondary, modifier = Modifier.weight(1f), onClick = { viewModel.setSelectedCalendarDate("2026-10-05") })
                    CalendarDayCell(dayNumber = "6", modifier = Modifier.weight(1f), onClick = { viewModel.setSelectedCalendarDate("2026-10-06") })
                    CalendarDayCell(dayNumber = "7", modifier = Modifier.weight(1f), onClick = { viewModel.setSelectedCalendarDate("2026-10-07") })
                    CalendarDayCell(dayNumber = "8", dotColor = IndigoPrimary, modifier = Modifier.weight(1f), onClick = { viewModel.setSelectedCalendarDate("2026-10-08") })
                    CalendarDayCell(dayNumber = "9", modifier = Modifier.weight(1f), onClick = { viewModel.setSelectedCalendarDate("2026-10-09") })
                    CalendarDayCell(dayNumber = "10", modifier = Modifier.weight(1f), onClick = { viewModel.setSelectedCalendarDate("2026-10-10") })
                    CalendarDayCell(dayNumber = "11", modifier = Modifier.weight(1f), onClick = { viewModel.setSelectedCalendarDate("2026-10-11") })
                }

                // Row 3: 12, 13, 14 (dot), 15, 16, 17, 18
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                    CalendarDayCell(dayNumber = "12", modifier = Modifier.weight(1f), onClick = { viewModel.setSelectedCalendarDate("2026-10-12") })
                    CalendarDayCell(dayNumber = "13", modifier = Modifier.weight(1f), onClick = { viewModel.setSelectedCalendarDate("2026-10-13") })
                    CalendarDayCell(dayNumber = "14", dotColor = IndigoPrimary, modifier = Modifier.weight(1f), onClick = { viewModel.setSelectedCalendarDate("2026-10-14") })
                    CalendarDayCell(dayNumber = "15", modifier = Modifier.weight(1f), onClick = { viewModel.setSelectedCalendarDate("2026-10-15") })
                    CalendarDayCell(dayNumber = "16", modifier = Modifier.weight(1f), onClick = { viewModel.setSelectedCalendarDate("2026-10-16") })
                    CalendarDayCell(dayNumber = "17", modifier = Modifier.weight(1f), onClick = { viewModel.setSelectedCalendarDate("2026-10-17") })
                    CalendarDayCell(dayNumber = "18", modifier = Modifier.weight(1f), onClick = { viewModel.setSelectedCalendarDate("2026-10-18") })
                }

                // Legend Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .border(width = 0.5.dp, color = SurfaceContainer, shape = RoundedCornerShape(8.dp))
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    CalendarLegendItem(color = LiveGreen, label = "Published")
                    CalendarLegendItem(color = IndigoPrimary, label = "Scheduled")
                    CalendarLegendItem(color = SlateSecondary, label = "Pending")
                    CalendarLegendItem(color = OnSurfaceVariantLight, label = "Draft")
                }
            }
        }

        // Selected Date Detail Panel (Thursday, Oct 1, 2026)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (selectedDate == "2026-10-01") "Thursday, Oct 1, 2026" else "Selected: $selectedDate",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurfaceLight
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "2 Posts",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = IndigoPrimary,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(PrimaryFixed)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .clickable { viewModel.navigateTo(Screen.CREATE_POST_STAGE1) }
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(text = "Add Post", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = IndigoPrimary)
                    }
                }

                // Card 1: AI/ML Roadmap
                val roadmapPost = posts.find { it.title.contains("Roadmap") } ?: posts.firstOrNull()
                if (roadmapPost != null) {
                    CalendarPostDetailItem(
                        post = roadmapPost,
                        platform1 = "LinkedIn",
                        platform2 = "X",
                        timeText = "6:00 PM",
                        statusText = "Scheduled",
                        statusColor = IndigoPrimary,
                        statusBg = PrimaryFixed,
                        summaryText = "Queue #1 in evening slot",
                        onEdit = {
                            viewModel.composerTitle.value = roadmapPost.title
                            viewModel.composerContent.value = roadmapPost.masterContent
                            viewModel.navigateTo(Screen.CREATE_POST_STAGE1)
                        },
                        onViewDetails = { viewModel.openPostDetail(roadmapPost.id) }
                    )
                }

                // Card 2: Python Learning Journey
                val pythonPost = posts.find { it.title.contains("Python") } ?: posts.getOrNull(1)
                if (pythonPost != null) {
                    CalendarPostDetailItem(
                        post = pythonPost,
                        platform1 = "Instagram",
                        platform2 = null,
                        timeText = "8:00 PM",
                        statusText = "Draft",
                        statusColor = SlateSecondary,
                        statusBg = SurfaceContainerHigh,
                        summaryText = "Awaiting final image carousel",
                        onEdit = {
                            viewModel.composerTitle.value = pythonPost.title
                            viewModel.composerContent.value = pythonPost.masterContent
                            viewModel.navigateTo(Screen.CREATE_POST_STAGE1)
                        },
                        onViewDetails = { viewModel.openPostDetail(pythonPost.id) }
                    )
                }
            }
        }

        // Upcoming Posts Pipeline (3 items)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Upcoming Pipeline", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                        Text(text = "Next content queued for auto-dispatch", fontSize = 12.sp, color = OnSurfaceVariantLight)
                    }
                    Text(
                        text = "View All",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = IndigoPrimary,
                        modifier = Modifier.clickable { viewModel.navigateTo(Screen.MY_POSTS) }
                    )
                }

                // Pipeline item 1
                PipelineItemRow(
                    title = "AI Travel Planner",
                    statusText = "Published",
                    statusBg = TertiaryFixed,
                    statusColor = TealTertiary,
                    subtitle = "Sep 30 · 7:00 PM · IG, LinkedIn",
                    imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuD4-mu9-F93q4C9XzCSoTx6r1QSffk_BjIxan5YjFX9tCKVZOfpeu9vwTByx8iP9RFE1hFPBpCQytFgcTlriQHmCmEOzb091HGZZJGyZjDB9c73qxR14g9X08ytbs28ySxNPASj6-Lz4QDgDVjxNdVtQsoOzuikBdFfjBflFBzibS03iDEOy7UbjRz0ndwTSHHIuV5zhvFP8QJzsY4AmsyuDXD0029f19n17TDzDOHXEkrAs8QTtxkn",
                    onClick = { viewModel.openPostDetail(posts.find { it.title.contains("Travel") }?.id ?: 1L) }
                )

                // Pipeline item 2
                PipelineItemRow(
                    title = "30 Days of Coding",
                    statusText = "Scheduled",
                    statusBg = PrimaryFixed,
                    statusColor = IndigoPrimary,
                    subtitle = "Saturday, Oct 3 · 7:30 PM · IG, Threads",
                    imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBOcTxDagrWTvKLK63VZYD89H1RLSBf59ywPwygAiiRtKBq658t8gQkpyiU4M9LccvHKgcXbJnlg7pHGyP6-Q6aChoF-5qhN7ztOAd1-bQIIpyQiPvjmoD6z3KcfcCWDlVt0VQjx0MDGOVV6ATa_12pGVPI9CD6XKWMIgF_92uR6MlPHZucmI83dfqkjM-L2HtAgEJ34DxHvpc6Bk1xYRwpnqOfz6YuhyktxbCNYfcMU68PYeb7pk6i",
                    onClick = { viewModel.openPostDetail(posts.find { it.title.contains("30 Days") }?.id ?: 1L) }
                )

                // Pipeline item 3
                PipelineItemRow(
                    title = "My New Portfolio",
                    statusText = "Draft",
                    statusBg = SurfaceContainerHigh,
                    statusColor = SlateSecondary,
                    subtitle = "Monday, Oct 5 · 6:30 PM · LinkedIn",
                    imageUrl = null,
                    onClick = { viewModel.openPostDetail(posts.find { it.title.contains("Portfolio") }?.id ?: 1L) }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun CalendarFilterPill(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) SurfaceContainerHigh else SurfaceContainerLow)
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = if (isSelected) IndigoPrimary else OnSurfaceVariantLight, modifier = Modifier.size(13.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = label, fontSize = 11.sp, color = if (isSelected) IndigoPrimary else OnSurfaceVariantLight, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun CalendarDayCell(
    dayNumber: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    isDimmed: Boolean = false,
    dotColor: Color? = null,
    dots: List<Color>? = null,
    onClick: () -> Unit
) {
    val bg = when {
        isSelected -> IndigoPrimaryContainer
        dotColor != null -> SurfaceContainerLow
        else -> Color.Transparent
    }

    val textColor = when {
        isSelected -> Color.White
        isDimmed -> OnSurfaceVariantLight.copy(alpha = 0.35f)
        else -> OnSurfaceLight
    }

    Column(
        modifier = modifier
            .height(52.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(bg)
            .clickable(onClick = onClick)
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = dayNumber,
            fontSize = 12.sp,
            fontWeight = if (isSelected || dotColor != null) FontWeight.Bold else FontWeight.Normal,
            color = textColor
        )

        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            if (dots != null) {
                dots.forEach { dot ->
                    Box(modifier = Modifier.size(5.dp).clip(CircleShape).background(dot))
                }
            } else if (dotColor != null) {
                Box(modifier = Modifier.size(5.dp).clip(CircleShape).background(dotColor))
            } else {
                Spacer(modifier = Modifier.size(5.dp))
            }
        }
    }
}

@Composable
fun CalendarLegendItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(color))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = label, fontSize = 11.sp, color = OnSurfaceVariantLight)
    }
}

@Composable
fun CalendarPostDetailItem(
    post: com.example.data.model.PostEntity,
    platform1: String,
    platform2: String?,
    timeText: String,
    statusText: String,
    statusColor: Color,
    statusBg: Color,
    summaryText: String,
    onEdit: () -> Unit,
    onViewDetails: () -> Unit
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = platform1,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = IndigoPrimary,
                    modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(SurfaceContainerHigh).padding(horizontal = 6.dp, vertical = 2.dp)
                )

                if (platform2 != null) {
                    Text(
                        text = platform2,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = OnSurfaceLight,
                        modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(SurfaceContainer).padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Schedule, contentDescription = null, tint = OnSurfaceVariantLight, modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(text = timeText, fontSize = 11.sp, color = OnSurfaceVariantLight)
                }
            }

            Text(
                text = statusText,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = statusColor,
                modifier = Modifier.clip(RoundedCornerShape(10.dp)).background(statusBg).padding(horizontal = 8.dp, vertical = 3.dp)
            )
        }

        Row(verticalAlignment = Alignment.Top) {
            if (post.mediaUrl.isNotBlank()) {
                AsyncImage(
                    model = post.mediaUrl,
                    contentDescription = post.title,
                    modifier = Modifier.size(56.dp).clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier.size(56.dp).clip(RoundedCornerShape(8.dp)).background(SurfaceContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.Web, contentDescription = null, tint = OnSurfaceVariantLight)
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = post.title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                Text(
                    text = post.masterContent,
                    fontSize = 12.sp,
                    color = OnSurfaceVariantLight,
                    maxLines = 2,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = summaryText, fontSize = 11.sp, color = OnSurfaceVariantLight)

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(SurfaceContainer)
                        .clickable(onClick = onEdit)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(text = "Edit", fontSize = 11.sp, color = OnSurfaceLight)
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(SurfaceContainerLow)
                        .clickable(onClick = onViewDetails)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(text = "View Details", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = IndigoPrimary)
                }
            }
        }
    }
}

@Composable
fun PipelineItemRow(
    title: String,
    statusText: String,
    statusBg: Color,
    statusColor: Color,
    subtitle: String,
    imageUrl: String?,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceContainerLowest)
            .shadow(1.dp, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (imageUrl != null) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = title,
                    modifier = Modifier.size(46.dp).clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier.size(46.dp).clip(RoundedCornerShape(8.dp)).background(SurfaceContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.Web, contentDescription = null, tint = OnSurfaceVariantLight)
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = statusText,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = statusColor,
                        modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(statusBg).padding(horizontal = 6.dp, vertical = 1.dp)
                    )
                }
                Text(text = subtitle, fontSize = 11.sp, color = OnSurfaceVariantLight, modifier = Modifier.padding(top = 2.dp))
            }
        }

        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null, tint = OnSurfaceVariantLight, modifier = Modifier.size(18.dp))
    }
}

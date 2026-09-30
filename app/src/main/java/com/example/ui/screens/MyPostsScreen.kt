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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DynamicFeed
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ViewAgenda
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.ui.theme.ErrorContainer
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
import com.example.ui.theme.TealTertiary
import com.example.ui.theme.TertiaryFixed
import com.example.ui.viewmodel.PostPilotViewModel
import com.example.ui.viewmodel.Screen

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MyPostsScreen(viewModel: PostPilotViewModel) {
    val posts by viewModel.posts.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val statusFilter by viewModel.statusFilter.collectAsState()
    val viewMode by viewModel.viewMode.collectAsState()
    val selectedPostIds by viewModel.selectedPostIds.collectAsState()

    val filteredPosts = posts.filter { post ->
        val matchesQuery = searchQuery.isBlank() ||
            post.title.contains(searchQuery, ignoreCase = true) ||
            post.masterContent.contains(searchQuery, ignoreCase = true) ||
            post.targetChannels.contains(searchQuery, ignoreCase = true)

        val matchesStatus = when (statusFilter) {
            "PUBLISHED" -> post.status == "PUBLISHED"
            "SCHEDULED" -> post.status == "SCHEDULED"
            "DRAFT" -> post.status == "DRAFT"
            else -> true
        }

        matchesQuery && matchesStatus
    }

    val totalVolume = posts.size
    val publishedCount = posts.count { it.status == "PUBLISHED" }
    val scheduledCount = posts.count { it.status == "SCHEDULED" }
    val draftsCount = posts.count { it.status == "DRAFT" }

    Box(modifier = Modifier.fillMaxSize().testTag("my_posts_screen")) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Section
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "My Posts", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                        Text(text = "Manage all your content from one place.", fontSize = 13.sp, color = OnSurfaceVariantLight)
                    }

                    Button(
                        onClick = { viewModel.navigateTo(Screen.CREATE_POST_STAGE1) },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = IndigoPrimary),
                        modifier = Modifier.height(38.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Create Post", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            // 4 Metrics Cards
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Total Volume
                        PostMetricSmallCard(
                            modifier = Modifier.weight(1f),
                            label = "Total Volume",
                            count = totalVolume.toString(),
                            icon = Icons.Default.DynamicFeed,
                            iconColor = IndigoPrimary,
                            iconBg = SurfaceContainer
                        )

                        // Published
                        PostMetricSmallCard(
                            modifier = Modifier.weight(1f),
                            label = "Published",
                            count = publishedCount.toString(),
                            icon = Icons.Default.CheckCircle,
                            iconColor = TealTertiary,
                            iconBg = TertiaryFixed
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Scheduled
                        PostMetricSmallCard(
                            modifier = Modifier.weight(1f),
                            label = "Scheduled",
                            count = scheduledCount.toString(),
                            icon = Icons.Default.Schedule,
                            iconColor = IndigoPrimary,
                            iconBg = PrimaryFixed
                        )

                        // Drafts
                        PostMetricSmallCard(
                            modifier = Modifier.weight(1f),
                            label = "Drafts",
                            count = draftsCount.toString(),
                            icon = Icons.Default.EditNote,
                            iconColor = SlateSecondary,
                            iconBg = SurfaceContainerHigh
                        )
                    }
                }
            }

            // Search Bar & Filter Strip
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Search text field
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.setSearchQuery(it) },
                        placeholder = { Text("Search your posts by title, content, or platform...", fontSize = 13.sp) },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = OnSurfaceVariantLight, modifier = Modifier.size(20.dp))
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { viewModel.setSearchQuery("") }) {
                                    Icon(imageVector = Icons.Default.Close, contentDescription = "Clear", tint = OnSurfaceVariantLight, modifier = Modifier.size(18.dp))
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("post_search_bar"),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = SurfaceContainerLowest,
                            focusedContainerColor = SurfaceContainerLowest,
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = IndigoPrimary
                        ),
                        singleLine = true
                    )

                    // Horizontal filter dropdown triggers
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        item {
                            FilterDropdownPill(
                                label = "Status:",
                                value = if (statusFilter == "ALL") "All" else statusFilter,
                                onClick = {
                                    val next = when (statusFilter) {
                                        "ALL" -> "PUBLISHED"
                                        "PUBLISHED" -> "SCHEDULED"
                                        "SCHEDULED" -> "DRAFT"
                                        else -> "ALL"
                                    }
                                    viewModel.setStatusFilter(next)
                                    viewModel.showToast("Filter Status: $next")
                                }
                            )
                        }

                        item {
                            FilterDropdownPill(
                                label = "Platforms:",
                                value = "All (6)",
                                onClick = { viewModel.showToast("Showing posts from all 6 platforms") }
                            )
                        }

                        item {
                            FilterDropdownPill(
                                label = "Date:",
                                value = "All Time",
                                onClick = { viewModel.showToast("Date range: All Time") }
                            )
                        }

                        item {
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .clickable {
                                        viewModel.setSearchQuery("")
                                        viewModel.setStatusFilter("ALL")
                                        viewModel.showToast("Filters reset")
                                    }
                                    .padding(horizontal = 8.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(imageVector = Icons.Default.Refresh, contentDescription = null, tint = SlateSecondary, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(text = "Reset", fontSize = 11.sp, color = SlateSecondary)
                            }
                        }
                    }
                }
            }

            // View Switcher & Bulk Selection Controls
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable {
                                    viewModel.selectAllPosts(filteredPosts.map { it.id })
                                }
                                .padding(vertical = 4.dp)
                        ) {
                            Checkbox(
                                checked = selectedPostIds.isNotEmpty() && selectedPostIds.size == filteredPosts.size,
                                onCheckedChange = { viewModel.selectAllPosts(filteredPosts.map { it.id }) },
                                colors = CheckboxDefaults.colors(checkedColor = IndigoPrimary),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Select All", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
                        }

                        // Grid / List View Toggle
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceContainerLow)
                                .padding(2.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (viewMode == "GRID") SurfaceContainerLowest else Color.Transparent)
                                    .clickable { viewModel.setViewMode("GRID") }
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(imageVector = Icons.Default.GridView, contentDescription = "Grid", tint = if (viewMode == "GRID") IndigoPrimary else OnSurfaceVariantLight, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = "Grid", fontSize = 11.sp, color = if (viewMode == "GRID") IndigoPrimary else OnSurfaceVariantLight)
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (viewMode == "LIST") SurfaceContainerLowest else Color.Transparent)
                                    .clickable { viewModel.setViewMode("LIST") }
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(imageVector = Icons.Default.ViewAgenda, contentDescription = "List", tint = if (viewMode == "LIST") IndigoPrimary else OnSurfaceVariantLight, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = "List", fontSize = 11.sp, color = if (viewMode == "LIST") IndigoPrimary else OnSurfaceVariantLight)
                                }
                            }
                        }
                    }

                    // Active Bulk Actions Bar
                    if (selectedPostIds.isNotEmpty()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(SurfaceContainer)
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(IndigoPrimary))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "${selectedPostIds.size} posts selected",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurfaceLight
                                )
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    onClick = { viewModel.archiveSelectedPosts() },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerLowest, contentColor = SlateSecondary),
                                    modifier = Modifier.height(32.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.Archive, contentDescription = null, modifier = Modifier.size(13.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = "Archive", fontSize = 11.sp)
                                }

                                Button(
                                    onClick = { viewModel.deleteSelectedPosts() },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = ErrorContainer, contentColor = ErrorRed),
                                    modifier = Modifier.height(32.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(13.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = "Delete", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }

            // Post Cards Feed
            items(filteredPosts) { post ->
                val isSelected = selectedPostIds.contains(post.id)
                FeedPostItemCard(
                    post = post,
                    isSelected = isSelected,
                    onToggleSelect = { viewModel.togglePostSelection(post.id) },
                    onOpenDetail = { viewModel.openPostDetail(post.id) },
                    onEdit = {
                        viewModel.composerTitle.value = post.title
                        viewModel.composerContent.value = post.masterContent
                        viewModel.navigateTo(Screen.CREATE_POST_STAGE1)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }

        // Floating Action Button
        FloatingActionButton(
            onClick = { viewModel.navigateTo(Screen.CREATE_POST_STAGE1) },
            containerColor = IndigoPrimary,
            contentColor = Color.White,
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 75.dp, end = 16.dp)
                .testTag("floating_new_post_btn")
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "New Post", modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "New Post", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun PostMetricSmallCard(
    modifier: Modifier = Modifier,
    label: String,
    count: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    iconBg: Color
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceContainerLowest)
            .shadow(1.dp, RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = label, fontSize = 11.sp, color = OnSurfaceVariantLight)
            Text(text = count, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
        }

        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(18.dp))
        }
    }
}

@Composable
fun FilterDropdownPill(
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceContainerLowest)
            .shadow(1.dp, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 11.sp, color = OnSurfaceVariantLight)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = value, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = IndigoPrimary)
        Icon(imageVector = Icons.Default.ExpandMore, contentDescription = null, tint = OnSurfaceVariantLight, modifier = Modifier.size(14.dp))
    }
}

@Composable
fun FeedPostItemCard(
    post: PostEntity,
    isSelected: Boolean,
    onToggleSelect: () -> Unit,
    onOpenDetail: () -> Unit,
    onEdit: () -> Unit
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
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Thumbnail & Status Badge
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(SurfaceContainer)
                .clickable(onClick = onOpenDetail)
        ) {
            if (post.mediaUrl.isNotBlank()) {
                AsyncImage(
                    model = post.mediaUrl,
                    contentDescription = post.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // Top-left media count badge
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
                Text(text = "Media (${post.mediaCount})", fontSize = 10.sp, color = Color.White)
            }

            // Top-right status pill
            val (badgeBg, badgeText, badgeColor) = when (post.status) {
                "PUBLISHED" -> Triple(TertiaryFixed, "Published", TealTertiary)
                "SCHEDULED" -> Triple(PrimaryFixed, "Scheduled", IndigoPrimary)
                "DRAFT" -> Triple(SurfaceContainerHigh, "Draft", SlateSecondary)
                else -> Triple(SurfaceContainerHigh, "In Progress", IndigoPrimary)
            }

            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainerLowest.copy(alpha = 0.95f))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(badgeColor))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = badgeText, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = badgeColor)
            }
        }

        // Header info & selection checkbox
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.CalendarToday, contentDescription = null, tint = OnSurfaceVariantLight, modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = post.createdDate, fontSize = 11.sp, color = OnSurfaceVariantLight)
                }
                Text(
                    text = post.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurfaceLight,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            Checkbox(
                checked = isSelected,
                onCheckedChange = { onToggleSelect() },
                colors = CheckboxDefaults.colors(checkedColor = IndigoPrimary),
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = post.masterContent,
            fontSize = 12.sp,
            color = OnSurfaceVariantLight,
            maxLines = 2,
            lineHeight = 17.sp
        )

        // Platform Pills Bar
        val statusMap = post.getStatusMap()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            statusMap.forEach { (platform, status) ->
                PlatformStatusMicroBadge(platform = platform, status = status)
            }
        }

        // Multi-platform Progress Tracker
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Publishing Status", fontSize = 11.sp, color = OnSurfaceVariantLight)
                val statusLabel = if (post.status == "DRAFT") "Unpublished Draft" else "$publishedCount / $totalChannels published"
                Text(
                    text = statusLabel,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (publishedCount == totalChannels) TealTertiary else IndigoPrimary
                )
            }

            LinearProgressIndicator(
                progress = { if (post.status == "DRAFT") 0f else progress.coerceIn(0.08f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = if (post.status == "DRAFT") SurfaceContainerHigh else if (publishedCount == totalChannels) TealTertiary else IndigoPrimary,
                trackColor = SurfaceContainer
            )
        }

        // Actions Footer
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerLow)
                        .clickable(onClick = onOpenDetail)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(text = "View", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerLow)
                        .clickable(onClick = onEdit)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = if (post.status == "PUBLISHED") "Analytics" else "Edit",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = OnSurfaceLight
                    )
                }
            }

            IconButton(onClick = onOpenDetail) {
                Icon(imageVector = Icons.Default.MoreHoriz, contentDescription = "More", tint = OnSurfaceVariantLight)
            }
        }
    }
}

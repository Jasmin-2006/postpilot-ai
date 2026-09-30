package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PostEntity
import com.example.ui.theme.ErrorContainer
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.IndigoPrimary
import com.example.ui.theme.LiveGreen
import com.example.ui.theme.LiveGreenBg
import com.example.ui.theme.OnSurfaceLight
import com.example.ui.theme.OnSurfaceVariantLight
import com.example.ui.theme.ScheduledBlue
import com.example.ui.theme.ScheduledBlueBg
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostSearchDialog(
    posts: List<PostEntity>,
    onDismiss: () -> Unit,
    onSelectPost: (Long) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val filteredPosts = remember(query, posts) {
        if (query.isBlank()) posts.take(5)
        else posts.filter {
            it.title.contains(query, ignoreCase = true) ||
            it.masterContent.contains(query, ignoreCase = true)
        }
    }

    BasicAlertDialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(SurfaceContainerLowest)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Search Workspace",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurfaceLight
                )
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }

            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Search posts, campaigns, tags...") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = IndigoPrimary)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .testTag("search_text_input"),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Text(
                text = if (query.isBlank()) "Recent Posts" else "Results (${filteredPosts.size})",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = OnSurfaceVariantLight,
                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp)
            ) {
                items(filteredPosts) { post ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainerLow)
                            .clickable {
                                onSelectPost(post.id)
                                onDismiss()
                            }
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = post.title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = OnSurfaceLight
                            )
                            Text(
                                text = post.masterContent,
                                fontSize = 12.sp,
                                color = OnSurfaceVariantLight,
                                maxLines = 1
                            )
                        }
                        Text(
                            text = post.status,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = IndigoPrimary,
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(IndigoPrimary.copy(alpha = 0.1f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsDialog(
    onDismiss: () -> Unit
) {
    BasicAlertDialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(SurfaceContainerLowest)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = null,
                        tint = IndigoPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Notifications & Alerts",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurfaceLight
                    )
                }
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Alert 1: Facebook OAuth Error
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ErrorContainer.copy(alpha = 0.6f))
                    .padding(10.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = null,
                    tint = ErrorRed,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Facebook: OAuth Token Expired",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = ErrorRed
                    )
                    Text(
                        text = "Permissions need refresh before queued posts can be pushed. Tap Settings to reconnect.",
                        fontSize = 11.sp,
                        color = OnSurfaceLight
                    )
                    Text(
                        text = "2 hours ago",
                        fontSize = 10.sp,
                        color = OnSurfaceVariantLight,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            // Alert 2: Published successfully
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(LiveGreenBg)
                    .padding(10.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = LiveGreen,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "‘AI Travel Planner’ Published",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = LiveGreen
                    )
                    Text(
                        text = "Successfully delivered to Instagram and LinkedIn feeds.",
                        fontSize = 11.sp,
                        color = OnSurfaceLight
                    )
                    Text(
                        text = "Today · 7:15 PM",
                        fontSize = 10.sp,
                        color = OnSurfaceVariantLight,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            // Alert 3: Scheduled
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ScheduledBlueBg)
                    .padding(10.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    tint = ScheduledBlue,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "‘AI/ML Roadmap’ Queued",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = ScheduledBlue
                    )
                    Text(
                        text = "Scheduled for auto-dispatch tomorrow at 7:00 PM.",
                        fontSize = 11.sp,
                        color = OnSurfaceLight
                    )
                    Text(
                        text = "Tomorrow · 7:00 PM",
                        fontSize = 10.sp,
                        color = OnSurfaceVariantLight,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PostPilotToast(
    message: String?,
    onDismiss: () -> Unit
) {
    if (message != null) {
        LaunchedEffect(message) {
            delay(2500)
            onDismiss()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 75.dp, start = 16.dp, end = 16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFF213145))
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF4CD7F6))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = message,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }
    }
}

package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.IndigoPrimary
import com.example.ui.theme.OnSurfaceLight
import com.example.ui.theme.OnSurfaceVariantLight
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceLight

@Composable
fun PostPilotTopBar(
    onOpenDrawer: () -> Unit,
    onOpenSearch: () -> Unit,
    onOpenNotifications: () -> Unit,
    onOpenProfile: () -> Unit,
    currentSectionLabel: String? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(SurfaceLight.copy(alpha = 0.95f))
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onOpenDrawer,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .testTag("drawer_toggle")
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Open Navigation Menu",
                tint = OnSurfaceLight,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        // Brand Icon & Name
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onOpenDrawer() }
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(IndigoPrimary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "PostPilot Logo",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "PostPilot",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = OnSurfaceLight,
                letterSpacing = (-0.3).sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (currentSectionLabel != null) {
            Text(
                text = currentSectionLabel,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = OnSurfaceVariantLight,
                modifier = Modifier.padding(end = 8.dp)
            )
        }

        // Search Action
        IconButton(
            onClick = onOpenSearch,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .testTag("search_icon")
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Workspace",
                tint = OnSurfaceVariantLight,
                modifier = Modifier.size(22.dp)
            )
        }

        // Notifications Action with unread dot
        Box(contentAlignment = Alignment.Center) {
            IconButton(
                onClick = onOpenNotifications,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .testTag("notifications_icon")
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = OnSurfaceVariantLight,
                    modifier = Modifier.size(22.dp)
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 8.dp, end = 8.dp)
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(ErrorRed)
                    .border(1.5.dp, SurfaceLight, CircleShape)
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        // Jas Profile Avatar
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .border(1.5.dp, IndigoPrimary.copy(alpha = 0.3f), CircleShape)
                .clickable(onClick = onOpenProfile)
                .testTag("profile_avatar"),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBS19pltBUoG7tG9_G5wnY2YPIV57-grdp8sMV4HjTqByhrFSIX_YphiWBW60-9bQ0XdurcTsThJnnMo56yXgZg31PnPKctHuzdyQFsFC1EmVtC1QP_52xnmHBlXfuZyUeFAUONcYQLm1w86KciNKELJb_HOfGg-wFDi1mX3qOjjLIIHqsLGVeVsFV_1Rn13DUmjEgkNi3iX6FcXDco7tYT26aIn2ZOz4e_U-Qnd8kopl45LDKvAZR3",
                contentDescription = "Jas Profile",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

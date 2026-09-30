package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DynamicFeed
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.IndigoPrimary
import com.example.ui.theme.IndigoPrimaryContainer
import com.example.ui.theme.OnSurfaceLight
import com.example.ui.theme.OnSurfaceVariantLight
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.viewmodel.Screen

@Composable
fun PostPilotDrawerContent(
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(280.dp)
            .background(SurfaceContainerLowest)
            .padding(16.dp)
    ) {
        // Drawer Header with Close Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(IndigoPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "PostPilot Logo",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "PostPilot",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurfaceLight
                )
            }

            IconButton(onClick = onClose, modifier = Modifier.testTag("drawer_close_button")) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Menu",
                    tint = OnSurfaceVariantLight
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Profile Card
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceContainerLow)
                .clickable {
                    onNavigate(Screen.SETTINGS)
                    onClose()
                }
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBS19pltBUoG7tG9_G5wnY2YPIV57-grdp8sMV4HjTqByhrFSIX_YphiWBW60-9bQ0XdurcTsThJnnMo56yXgZg31PnPKctHuzdyQFsFC1EmVtC1QP_52xnmHBlXfuZyUeFAUONcYQLm1w86KciNKELJb_HOfGg-wFDi1mX3qOjjLIIHqsLGVeVsFV_1Rn13DUmjEgkNi3iX6FcXDco7tYT26aIn2ZOz4e_U-Qnd8kopl45LDKvAZR3",
                contentDescription = "Jas Profile",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = "Jas",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnSurfaceLight
                )
                Text(
                    text = "Growth Strategist",
                    fontSize = 12.sp,
                    color = OnSurfaceVariantLight
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Nav items list
        DrawerNavItem(
            icon = Icons.Default.GridView,
            label = "Dashboard",
            isSelected = currentScreen == Screen.DASHBOARD,
            onClick = {
                onNavigate(Screen.DASHBOARD)
                onClose()
            }
        )

        DrawerNavItem(
            icon = Icons.Default.AddCircle,
            label = "Create Post",
            isSelected = currentScreen == Screen.CREATE_POST_STAGE1 || currentScreen == Screen.CREATE_POST_STAGE2,
            onClick = {
                onNavigate(Screen.CREATE_POST_STAGE1)
                onClose()
            }
        )

        DrawerNavItem(
            icon = Icons.Default.CalendarMonth,
            label = "Calendar",
            isSelected = currentScreen == Screen.CALENDAR,
            onClick = {
                onNavigate(Screen.CALENDAR)
                onClose()
            }
        )

        DrawerNavItem(
            icon = Icons.Default.DynamicFeed,
            label = "My Posts",
            isSelected = currentScreen == Screen.MY_POSTS || currentScreen == Screen.POST_DETAIL,
            onClick = {
                onNavigate(Screen.MY_POSTS)
                onClose()
            }
        )

        DrawerNavItem(
            icon = Icons.Default.AutoAwesome,
            label = "AI Assistant",
            isSelected = currentScreen == Screen.AI_ASSISTANT,
            onClick = {
                onNavigate(Screen.AI_ASSISTANT)
                onClose()
            }
        )

        DrawerNavItem(
            icon = Icons.Default.Insights,
            label = "Analytics",
            isSelected = currentScreen == Screen.ANALYTICS,
            onClick = {
                onNavigate(Screen.ANALYTICS)
                onClose()
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        // Bottom Settings
        DrawerNavItem(
            icon = Icons.Default.Settings,
            label = "Settings",
            isSelected = currentScreen == Screen.SETTINGS,
            onClick = {
                onNavigate(Screen.SETTINGS)
                onClose()
            }
        )
    }
}

@Composable
private fun DrawerNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgColor = if (isSelected) IndigoPrimaryContainer else Color.Transparent
    val textColor = if (isSelected) Color.White else OnSurfaceVariantLight
    val iconColor = if (isSelected) Color.White else OnSurfaceVariantLight

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = iconColor,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
            color = textColor
        )
    }
}

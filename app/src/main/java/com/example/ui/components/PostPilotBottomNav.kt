package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DynamicFeed
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.IndigoPrimary
import com.example.ui.theme.OnSurfaceVariantLight
import com.example.ui.theme.SurfaceLight
import com.example.ui.viewmodel.Screen

@Composable
fun PostPilotBottomNav(
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 12.dp)
            .background(SurfaceLight.copy(alpha = 0.96f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 6.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Dashboard
            BottomNavItem(
                icon = Icons.Default.GridView,
                label = "Dashboard",
                isSelected = currentScreen == Screen.DASHBOARD,
                onClick = { onNavigate(Screen.DASHBOARD) },
                testTag = "nav_dashboard"
            )

            // Calendar
            BottomNavItem(
                icon = Icons.Default.CalendarMonth,
                label = "Calendar",
                isSelected = currentScreen == Screen.CALENDAR,
                onClick = { onNavigate(Screen.CALENDAR) },
                testTag = "nav_calendar"
            )

            // Center Floating Add Button
            Box(
                modifier = Modifier
                    .offset(y = (-6).dp)
                    .size(46.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(IndigoPrimary)
                    .shadow(4.dp, RoundedCornerShape(14.dp))
                    .clickable { onNavigate(Screen.CREATE_POST_STAGE1) }
                    .testTag("nav_create_post"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create Post",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Posts
            BottomNavItem(
                icon = Icons.Default.DynamicFeed,
                label = "Posts",
                isSelected = currentScreen == Screen.MY_POSTS || currentScreen == Screen.POST_DETAIL,
                onClick = { onNavigate(Screen.MY_POSTS) },
                testTag = "nav_posts"
            )

            // AI
            BottomNavItem(
                icon = Icons.Default.AutoAwesome,
                label = "AI",
                isSelected = currentScreen == Screen.AI_ASSISTANT,
                onClick = { onNavigate(Screen.AI_ASSISTANT) },
                testTag = "nav_ai"
            )

            // Insights (Analytics)
            BottomNavItem(
                icon = Icons.Default.Insights,
                label = "Insights",
                isSelected = currentScreen == Screen.ANALYTICS,
                onClick = { onNavigate(Screen.ANALYTICS) },
                testTag = "nav_insights"
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    val color = if (isSelected) IndigoPrimary else OnSurfaceVariantLight
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 4.dp, vertical = 6.dp)
            .testTag(testTag),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(22.dp)
        )
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = color,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CrisisAlert
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import com.example.ui.viewmodel.PostPilotViewModel

@Composable
fun SettingsScreen(viewModel: PostPilotViewModel) {
    val name by viewModel.profileName.collectAsState()
    val email by viewModel.profileEmail.collectAsState()
    val connectedPlatforms by viewModel.connectedPlatforms.collectAsState()
    val defaultTone by viewModel.defaultTone.collectAsState()
    val autoShortenLinks by viewModel.autoShortenLinks.collectAsState()
    val appendSignatureHashtags by viewModel.appendSignatureHashtags.collectAsState()
    val pushNotifications by viewModel.pushNotifications.collectAsState()
    val weeklyDigest by viewModel.weeklyDigest.collectAsState()
    val failedDeliveryAlerts by viewModel.failedDeliveryAlerts.collectAsState()

    var showEditProfile by remember { mutableStateOf(false) }
    var editName by remember { mutableStateOf(name) }
    var editEmail by remember { mutableStateOf(email) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("settings_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Settings", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainer)
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(IndigoPrimaryContainer))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Workspace Synced", fontSize = 11.sp, color = OnSurfaceVariantLight)
                    }
                }
                Text(
                    text = "Manage your profile, platforms, notifications, and preferences.",
                    fontSize = 13.sp,
                    color = OnSurfaceVariantLight
                )
            }
        }

        // Section 1: Profile Card
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
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(52.dp)) {
                            AsyncImage(
                                model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBS19pltBUoG7tG9_G5wnY2YPIV57-grdp8sMV4HjTqByhrFSIX_YphiWBW60-9bQ0XdurcTsThJnnMo56yXgZg31PnPKctHuzdyQFsFC1EmVtC1QP_52xnmHBlXfuZyUeFAUONcYQLm1w86KciNKELJb_HOfGg-wFDi1mX3qOjjLIIHqsLGVeVsFV_1Rn13DUmjEgkNi3iX6FcXDco7tYT26aIn2ZOz4e_U-Qnd8kopl45LDKvAZR3",
                                contentDescription = "Avatar",
                                modifier = Modifier.fillMaxSize().clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(IndigoPrimaryContainer)
                                    .border(1.5.dp, Color.White, CircleShape)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = name, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "PRO",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = IndigoPrimary,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(PrimaryFixed)
                                        .padding(horizontal = 5.dp, vertical = 1.dp)
                                )
                            }
                            Text(text = email, fontSize = 12.sp, color = OnSurfaceVariantLight)
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 2.dp)) {
                                Box(modifier = Modifier.size(5.dp).clip(CircleShape).background(IndigoPrimary))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "Creator Pro Plan · Active", fontSize = 11.sp, color = OnSurfaceVariantLight)
                            }
                        }
                    }

                    Button(
                        onClick = {
                            editName = name
                            editEmail = email
                            showEditProfile = !showEditProfile
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainer, contentColor = OnSurfaceLight),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Edit", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }

                // Inline quick edit drawer
                if (showEditProfile) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainerLow)
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Quick Profile Update", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                            IconButton(onClick = { showEditProfile = false }, modifier = Modifier.size(20.dp)) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = OnSurfaceVariantLight, modifier = Modifier.size(14.dp))
                            }
                        }

                        OutlinedTextField(
                            value = editName,
                            onValueChange = { editName = it },
                            label = { Text("Display Name", fontSize = 11.sp) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = SurfaceContainerLowest, focusedContainerColor = SurfaceContainerLowest)
                        )

                        OutlinedTextField(
                            value = editEmail,
                            onValueChange = { editEmail = it },
                            label = { Text("Primary Notification Email", fontSize = 11.sp) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = SurfaceContainerLowest, focusedContainerColor = SurfaceContainerLowest)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = { showEditProfile = false },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerLowest, contentColor = OnSurfaceLight),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Text("Cancel", fontSize = 11.sp)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = {
                                    viewModel.profileName.value = editName
                                    viewModel.profileEmail.value = editEmail
                                    showEditProfile = false
                                    viewModel.showToast("Profile changes saved!")
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = IndigoPrimary, contentColor = Color.White),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Text("Save Changes", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }
        }

        // Section 2: Connected Platforms
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Connected Platforms", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                        Text(text = "Authorize and manage where your high-impact campaigns disburse.", fontSize = 12.sp, color = OnSurfaceVariantLight)
                    }

                    val activeCount = connectedPlatforms.values.count { it }
                    Text(
                        text = "$activeCount / 6 Active",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = IndigoPrimary,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(PrimaryFixed)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }

                // Security Banner
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceContainerLow)
                        .padding(10.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(imageVector = Icons.Default.VerifiedUser, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "PostPilot relies exclusively on official OAuth 2.0 channels. We never access passwords or publish without explicit pipeline approvals.",
                        fontSize = 11.sp,
                        color = OnSurfaceVariantLight,
                        lineHeight = 15.sp
                    )
                }

                // Instagram
                PlatformAccountCard(
                    icon = Icons.Default.PhotoCamera,
                    iconColor = ColorInstagram,
                    name = "Instagram",
                    statusText = "Connected",
                    subtitle = "@jas_growth · Sync 10m ago",
                    isConnected = connectedPlatforms["INSTAGRAM"] == true,
                    actionLabel = if (connectedPlatforms["INSTAGRAM"] == true) "Disconnect" else "+ Connect",
                    onAction = {
                        if (connectedPlatforms["INSTAGRAM"] == true) viewModel.disconnectPlatform("INSTAGRAM")
                        else viewModel.reconnectPlatform("INSTAGRAM")
                    }
                )

                // LinkedIn
                PlatformAccountCard(
                    icon = Icons.Default.BusinessCenter,
                    iconColor = ColorLinkedIn,
                    name = "LinkedIn",
                    statusText = "Active",
                    subtitle = "Jas Creative (Company Page)",
                    isConnected = connectedPlatforms["LINKEDIN"] == true,
                    actionLabel = "Manage",
                    onAction = { viewModel.showToast("Opening LinkedIn organization settings") }
                )

                // X / Twitter
                PlatformAccountCard(
                    icon = Icons.Default.Tag,
                    iconColor = ColorX,
                    name = "X / Twitter",
                    statusText = "Connected",
                    subtitle = "@jasship",
                    isConnected = connectedPlatforms["X"] == true,
                    actionLabel = if (connectedPlatforms["X"] == true) "Disconnect" else "+ Connect",
                    onAction = {
                        if (connectedPlatforms["X"] == true) viewModel.disconnectPlatform("X")
                        else viewModel.reconnectPlatform("X")
                    }
                )

                // Facebook (With expired token alert)
                val fbConnected = connectedPlatforms["FACEBOOK"] == true
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceContainerLowest)
                        .shadow(1.dp, RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
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
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(ColorFacebook),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.Public, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = "Facebook", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (fbConnected) "Connected" else "Token Expired",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (fbConnected) LiveGreen else ErrorRed,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(if (fbConnected) SurfaceContainerHigh else ErrorContainer)
                                            .padding(horizontal = 6.dp, vertical = 1.dp)
                                    )
                                }
                                Text(text = "Jas Brand Hub (Page)", fontSize = 11.sp, color = OnSurfaceVariantLight)
                            }
                        }

                        Button(
                            onClick = {
                                viewModel.reconnectPlatform("FACEBOOK")
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = IndigoPrimary, contentColor = Color.White),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text(text = if (fbConnected) "Manage" else "Reconnect", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    if (!fbConnected) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(SurfaceContainerLow)
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = ErrorRed, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "OAuth token lapsed 2 days ago. Re-authorize to enable dispatch.",
                                fontSize = 11.sp,
                                color = OnSurfaceVariantLight
                            )
                        }
                    }
                }

                // YouTube
                PlatformAccountCard(
                    icon = Icons.Default.SmartDisplay,
                    iconColor = ColorYouTube,
                    name = "YouTube",
                    statusText = if (connectedPlatforms["YOUTUBE"] == true) "Connected" else "Not Connected",
                    subtitle = "Community posts & Shorts",
                    isConnected = connectedPlatforms["YOUTUBE"] == true,
                    actionLabel = if (connectedPlatforms["YOUTUBE"] == true) "Disconnect" else "+ Connect",
                    onAction = {
                        if (connectedPlatforms["YOUTUBE"] == true) viewModel.disconnectPlatform("YOUTUBE")
                        else viewModel.reconnectPlatform("YOUTUBE")
                    }
                )

                // Threads
                PlatformAccountCard(
                    icon = Icons.Default.Tag,
                    iconColor = ColorThreads,
                    name = "Threads",
                    statusText = if (connectedPlatforms["THREADS"] == true) "Connected" else "Not Connected",
                    subtitle = "Micro-narratives & replies",
                    isConnected = connectedPlatforms["THREADS"] == true,
                    actionLabel = if (connectedPlatforms["THREADS"] == true) "Disconnect" else "+ Connect",
                    onAction = {
                        if (connectedPlatforms["THREADS"] == true) viewModel.disconnectPlatform("THREADS")
                        else viewModel.reconnectPlatform("THREADS")
                    }
                )
            }
        }

        // Section 3: Publishing Defaults
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
                Column {
                    Text(text = "Publishing Defaults", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                    Text(text = "Configure automated rules for outbound copy and media schedules.", fontSize = 12.sp, color = OnSurfaceVariantLight)
                }

                // Default AI Tone
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Default AI Tone", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
                        Text(text = "Active: $defaultTone", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = IndigoPrimary)
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf("Professional", "Bold & Direct", "Playful Tech").forEach { t ->
                            val isSelected = defaultTone == t
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) IndigoPrimary else SurfaceContainer)
                                    .clickable { viewModel.defaultTone.value = t }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = t,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color.White else OnSurfaceVariantLight
                                )
                            }
                        }
                    }
                }

                // Auto-shorten links
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        Text(text = "Auto-shorten links", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
                        Text(text = "Wrap outbound URLs using post.pt branded link redirector", fontSize = 11.sp, color = OnSurfaceVariantLight)
                    }
                    Switch(
                        checked = autoShortenLinks,
                        onCheckedChange = { viewModel.autoShortenLinks.value = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = IndigoPrimary)
                    )
                }

                // Signature hashtags
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        Text(text = "Append signature hashtags", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
                        Text(text = "Automatically insert #BuildInPublic #SaaSLaunch to posts", fontSize = 11.sp, color = OnSurfaceVariantLight)
                    }
                    Switch(
                        checked = appendSignatureHashtags,
                        onCheckedChange = { viewModel.appendSignatureHashtags.value = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = IndigoPrimary)
                    )
                }

                // Dispatch check reminder
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        Text(text = "Dispatch check reminder", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
                        Text(text = "Notice before an automated publishing window triggers", fontSize = 11.sp, color = OnSurfaceVariantLight)
                    }
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainer)
                            .clickable { viewModel.showToast("Reminder: 15 mins prior") }
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "15 mins prior", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
                        Icon(imageVector = Icons.Default.ExpandMore, contentDescription = null, tint = OnSurfaceVariantLight, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }

        // Section 4: Notifications & Safeguards
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
                Column {
                    Text(text = "Notifications & Safeguards", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                    Text(text = "Control realtime alerting thresholds and pipeline escalation.", fontSize = 12.sp, color = OnSurfaceVariantLight)
                }

                // Push notifications
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(32.dp).clip(RoundedCornerShape(8.dp)).background(SurfaceContainerLow),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.NotificationsActive, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(18.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(text = "Push Notifications", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
                            Text(text = "Realtime dispatch alerts & comments", fontSize = 11.sp, color = OnSurfaceVariantLight)
                        }
                    }
                    Switch(
                        checked = pushNotifications,
                        onCheckedChange = { viewModel.pushNotifications.value = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = IndigoPrimary)
                    )
                }

                // Weekly digest
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(32.dp).clip(RoundedCornerShape(8.dp)).background(SurfaceContainerLow),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.MarkEmailRead, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(18.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(text = "Weekly Digest", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
                            Text(text = "Consolidated reach & engagement ROI", fontSize = 11.sp, color = OnSurfaceVariantLight)
                        }
                    }
                    Switch(
                        checked = weeklyDigest,
                        onCheckedChange = { viewModel.weeklyDigest.value = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = IndigoPrimary)
                    )
                }

                // Failed Delivery Alerts (URGENT)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(32.dp).clip(RoundedCornerShape(8.dp)).background(ErrorContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.CrisisAlert, contentDescription = null, tint = ErrorRed, modifier = Modifier.size(18.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "Failed Delivery Alerts", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "URGENT",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ErrorRed,
                                    modifier = Modifier.clip(RoundedCornerShape(3.dp)).background(ErrorContainer).padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                            Text(text = "Immediate SMS & push on API disconnect", fontSize = 11.sp, color = OnSurfaceVariantLight)
                        }
                    }
                    Switch(
                        checked = failedDeliveryAlerts,
                        onCheckedChange = { viewModel.failedDeliveryAlerts.value = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = IndigoPrimary)
                    )
                }

                // Developer API & Webhooks
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerLow)
                        .clickable { viewModel.showToast("Developer API keys active: prod-pk-92384") }
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Key, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(text = "Developer API & Webhooks", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
                            Text(text = "Manage 2 active production keys", fontSize = 11.sp, color = OnSurfaceVariantLight)
                        }
                    }
                    Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = OnSurfaceVariantLight, modifier = Modifier.size(16.dp))
                }
            }
        }

        // Section 5: Engine & Actions
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
                        Text(text = "PostPilot Engine", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                        Text(text = "Build 2026.10 · Release Candidate", fontSize = 11.sp, color = OnSurfaceVariantLight)
                    }
                    Text(
                        text = "v2.4.1",
                        fontSize = 11.sp,
                        color = OnSurfaceVariantLight,
                        modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(SurfaceContainer).padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { viewModel.showToast("Exporting PostPilot user data...") },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerLow, contentColor = OnSurfaceLight),
                        modifier = Modifier.weight(1f).height(36.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Download, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Export Data", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    }

                    Button(
                        onClick = { viewModel.showToast("Logged out of PostPilot session") },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerLow, contentColor = OnSurfaceLight),
                        modifier = Modifier.weight(1f).height(36.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Logout, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Log Out", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .clickable { viewModel.showToast("Warning: Deactivation requires administrative approval") }
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.DeleteForever, contentDescription = null, tint = ErrorRed, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Deactivate PostPilot Workspace", fontSize = 11.sp, color = ErrorRed)
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
fun PlatformAccountCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    name: String,
    statusText: String,
    subtitle: String,
    isConnected: Boolean,
    actionLabel: String,
    onAction: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceContainerLowest)
            .shadow(1.dp, RoundedCornerShape(12.dp))
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
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(iconColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = statusText,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isConnected) IndigoPrimary else OnSurfaceVariantLight,
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(if (isConnected) SurfaceContainerHigh else SurfaceContainer)
                            .padding(horizontal = 6.dp, vertical = 1.dp)
                    )
                }
                Text(text = subtitle, fontSize = 11.sp, color = OnSurfaceVariantLight)
            }
        }

        Button(
            onClick = onAction,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isConnected) SurfaceContainer else IndigoPrimary,
                contentColor = if (isConnected) OnSurfaceVariantLight else Color.White
            ),
            modifier = Modifier.height(32.dp)
        ) {
            Text(text = actionLabel, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

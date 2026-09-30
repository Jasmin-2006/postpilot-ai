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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.ui.theme.IndigoPrimary
import com.example.ui.theme.IndigoPrimaryContainer
import com.example.ui.theme.OnSurfaceLight
import com.example.ui.theme.OnSurfaceVariantLight
import com.example.ui.theme.PrimaryFixed
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.viewmodel.PostPilotViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AIAssistantScreen(viewModel: PostPilotViewModel) {
    val prompt by viewModel.aiPrompt.collectAsState()
    val tone by viewModel.aiTone.collectAsState()
    val length by viewModel.aiLength.collectAsState()
    val intent by viewModel.aiIntent.collectAsState()
    val includeHashtags by viewModel.aiIncludeHashtags.collectAsState()
    val useEmojis by viewModel.aiUseEmojis.collectAsState()
    val selectedPlatforms by viewModel.aiSelectedPlatforms.collectAsState()
    val isGenerating by viewModel.isAIGenerating.collectAsState()
    val variants by viewModel.aiGeneratedVariants.collectAsState()

    var showAttachedImage by remember { mutableStateOf(true) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("ai_assistant_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Utility Context Bar
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
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceContainerHigh)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(IndigoPrimaryContainer))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "AI STUDIO MODE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = IndigoPrimary)
                }

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(IndigoPrimary.copy(alpha = 0.08f))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Bolt, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Powered by Google AI Studio", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = IndigoPrimary)
                }
            }
        }

        // Editorial Hero Header
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "AI Content Assistant", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "✨", fontSize = 20.sp)
                }
                Text(
                    text = "Turn one core idea into platform-ready, high-converting content.",
                    fontSize = 13.sp,
                    color = OnSurfaceVariantLight
                )
            }
        }

        // Workstation Card 1: What do you want to post about?
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
                        Icon(imageVector = Icons.Default.Lightbulb, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "What do you want to post about?", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                    }
                    Text(text = "${prompt.length} / 2,000", fontSize = 11.sp, color = OnSurfaceVariantLight)
                }

                // Textarea container
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceContainerLow)
                        .padding(10.dp)
                ) {
                    Column {
                        OutlinedTextField(
                            value = prompt,
                            onValueChange = { viewModel.aiPrompt.value = it },
                            placeholder = { Text("Example: I just completed my first machine learning project and want to share my experience...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .testTag("ai_prompt_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                focusedBorderColor = Color.Transparent
                            )
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier
                                    .clickable {
                                        viewModel.aiPrompt.value = "⚡ " + prompt.trim() + " focusing on key architectural decisions and performance milestones."
                                        viewModel.showToast("Prompt refined!")
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(imageVector = Icons.Default.Tune, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "Refine Prompt", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = IndigoPrimary)
                            }
                            Text(text = "Natural Language input", fontSize = 11.sp, color = OnSurfaceVariantLight)
                        }
                    }
                }

                // Visual Context
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Visual Context (Optional)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
                        Text(text = "Multimodal vision enabled", fontSize = 11.sp, color = IndigoPrimary)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (showAttachedImage) {
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SurfaceContainerLow)
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    modifier = Modifier.weight(1f),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AsyncImage(
                                        model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBoKzvoVu-a04vu6c1yFUxMjMg-n3kpwXQtWnowlxVYYRjdE297mh9T2yapaNSGt61jwqLuOvcF_WsL7Mn7-_zYqt8MA1WBOsu3zbFqB7vw7mYhNmRFZRt5ZYo6BfmMXoEzqvOgajqYtGmIBBaq4zX1SR3uiwk4dpXaKIG0aKaixBB2X6STqQwE5G86TriVtwGtp77Wr4oc8qbQXrt-3V3u2H02De8InsIHACpbJIV7qvZaDnq5bh1_",
                                        contentDescription = "flowchart",
                                        modifier = Modifier.size(36.dp).clip(RoundedCornerShape(6.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(text = "agent-flowchart-v2.png", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight, maxLines = 1)
                                        Text(text = "840 KB • Image parsed", fontSize = 10.sp, color = OnSurfaceVariantLight)
                                    }
                                }
                                IconButton(onClick = { showAttachedImage = false }, modifier = Modifier.size(24.dp)) {
                                    Icon(imageVector = Icons.Default.Close, contentDescription = "Remove", tint = OnSurfaceVariantLight, modifier = Modifier.size(14.dp))
                                }
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceContainerLow)
                                .clickable {
                                    showAttachedImage = true
                                    viewModel.showToast("Visual context asset attached")
                                }
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(imageVector = Icons.Default.AddPhotoAlternate, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(18.dp))
                                Text(text = "+ Add", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = IndigoPrimary)
                            }
                        }
                    }
                }
            }
        }

        // Workstation Card 2: Target Platforms Selection Matrix (2x3)
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
                        Text(text = "Target Platforms", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                        Text(text = "Tuned specifically to channel limits & voice", fontSize = 11.sp, color = OnSurfaceVariantLight)
                    }

                    Text(
                        text = "Selected: ${selectedPlatforms.size} / 6",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = IndigoPrimary,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(IndigoPrimary.copy(alpha = 0.08f))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                // 2x3 Grid
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AITargetPlatformCard(
                            modifier = Modifier.weight(1f),
                            name = "Instagram",
                            handle = "@jas_growth",
                            icon = Icons.Default.PhotoCamera,
                            iconColor = ColorInstagram,
                            isSelected = selectedPlatforms.contains("INSTAGRAM"),
                            onToggle = {
                                val s = selectedPlatforms.toMutableSet()
                                if (s.contains("INSTAGRAM")) s.remove("INSTAGRAM") else s.add("INSTAGRAM")
                                viewModel.aiSelectedPlatforms.value = s
                            }
                        )
                        AITargetPlatformCard(
                            modifier = Modifier.weight(1f),
                            name = "LinkedIn",
                            handle = "Jas Creative",
                            icon = Icons.Default.Work,
                            iconColor = ColorLinkedIn,
                            isSelected = selectedPlatforms.contains("LINKEDIN"),
                            onToggle = {
                                val s = selectedPlatforms.toMutableSet()
                                if (s.contains("LINKEDIN")) s.remove("LINKEDIN") else s.add("LINKEDIN")
                                viewModel.aiSelectedPlatforms.value = s
                            }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AITargetPlatformCard(
                            modifier = Modifier.weight(1f),
                            name = "X (Twitter)",
                            handle = "@jasship",
                            iconLetter = "𝕏",
                            iconColor = ColorX,
                            isSelected = selectedPlatforms.contains("X"),
                            onToggle = {
                                val s = selectedPlatforms.toMutableSet()
                                if (s.contains("X")) s.remove("X") else s.add("X")
                                viewModel.aiSelectedPlatforms.value = s
                            }
                        )
                        AITargetPlatformCard(
                            modifier = Modifier.weight(1f),
                            name = "Facebook",
                            handle = "Page Feed",
                            iconLetter = "f",
                            iconColor = ColorFacebook,
                            isSelected = selectedPlatforms.contains("FACEBOOK"),
                            onToggle = {
                                val s = selectedPlatforms.toMutableSet()
                                if (s.contains("FACEBOOK")) s.remove("FACEBOOK") else s.add("FACEBOOK")
                                viewModel.aiSelectedPlatforms.value = s
                            }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AITargetPlatformCard(
                            modifier = Modifier.weight(1f),
                            name = "YouTube",
                            handle = "Community",
                            icon = Icons.Default.SmartDisplay,
                            iconColor = ColorYouTube,
                            isSelected = selectedPlatforms.contains("YOUTUBE"),
                            onToggle = {
                                val s = selectedPlatforms.toMutableSet()
                                if (s.contains("YOUTUBE")) s.remove("YOUTUBE") else s.add("YOUTUBE")
                                viewModel.aiSelectedPlatforms.value = s
                            }
                        )
                        AITargetPlatformCard(
                            modifier = Modifier.weight(1f),
                            name = "Threads",
                            handle = "@jas_threads",
                            iconLetter = "@",
                            iconColor = ColorThreads,
                            isSelected = selectedPlatforms.contains("THREADS"),
                            onToggle = {
                                val s = selectedPlatforms.toMutableSet()
                                if (s.contains("THREADS")) s.remove("THREADS") else s.add("THREADS")
                                viewModel.aiSelectedPlatforms.value = s
                            }
                        )
                    }
                }
            }
        }

        // Workstation Card 3: Customize Output Engine
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Customize Output Engine", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                    Icon(imageVector = Icons.Default.Psychology, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(20.dp))
                }

                // Tone of Voice
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = "TONE OF VOICE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = OnSurfaceVariantLight, letterSpacing = 0.5.sp)
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf("Professional", "Casual", "Friendly", "Creative", "Exciting").forEach { t ->
                            val isSelected = tone == t
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(if (isSelected) IndigoPrimary else SurfaceContainerLow)
                                    .clickable { viewModel.aiTone.value = t }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
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

                // Content Length
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = "CONTENT LENGTH", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = OnSurfaceVariantLight, letterSpacing = 0.5.sp)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainerLow)
                            .padding(2.dp)
                    ) {
                        listOf("Short", "Medium", "Long").forEach { l ->
                            val isSelected = length == l
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSelected) SurfaceContainerLowest else Color.Transparent)
                                    .shadow(if (isSelected) 1.dp else 0.dp, RoundedCornerShape(6.dp))
                                    .clickable { viewModel.aiLength.value = l }
                                    .padding(vertical = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = l,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) OnSurfaceLight else OnSurfaceVariantLight
                                )
                            }
                        }
                    }
                }

                // Intent & Format
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = "INTENT & FORMAT", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = OnSurfaceVariantLight, letterSpacing = 0.5.sp)
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf("Caption", "Post", "Announcement", "Project Update", "Educational", "Promotional").forEach { i ->
                            val isSelected = intent == i
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(if (isSelected) IndigoPrimary else SurfaceContainerLow)
                                    .clickable { viewModel.aiIntent.value = i }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = i,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color.White else OnSurfaceVariantLight
                                )
                            }
                        }
                    }
                }

                // Functional AI Toggles
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Switch(
                            checked = includeHashtags,
                            onCheckedChange = { viewModel.aiIncludeHashtags.value = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = IndigoPrimary)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Include Hashtags", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = OnSurfaceLight)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Switch(
                            checked = useEmojis,
                            onCheckedChange = { viewModel.aiUseEmojis.value = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = IndigoPrimary)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Use Emojis", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = OnSurfaceLight)
                    }
                }
            }
        }

        // Primary Action Hero Button
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Button(
                    onClick = { viewModel.generateAIContent() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("generate_formats_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = IndigoPrimaryContainer)
                ) {
                    if (isGenerating) {
                        CircularProgressIndicator(modifier = Modifier.size(18.dp), color = Color.White, strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Synthesizing Platforms...", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    } else {
                        Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Generate 4 Formats Now", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Calculates per-platform limits & algorithms auto...", fontSize = 11.sp, color = OnSurfaceVariantLight)
                    Text(
                        text = "Clear Input",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = IndigoPrimary,
                        modifier = Modifier.clickable {
                            viewModel.aiPrompt.value = ""
                            viewModel.showToast("Cleared input prompt")
                        }
                    )
                }
            }
        }

        // Generated Results Section
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Generated Content", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${variants.size} variants",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = IndigoPrimary,
                            modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(PrimaryFixed).padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceContainer)
                                .clickable {
                                    viewModel.showToast("All variants copied to clipboard!")
                                }
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, tint = OnSurfaceLight, modifier = Modifier.size(13.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Copy All", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight)
                        }

                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(IndigoPrimary.copy(alpha = 0.1f))
                                .clickable {
                                    viewModel.exportAIVariantsToComposer()
                                }
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.Send, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(13.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Export", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = IndigoPrimary)
                        }
                    }
                }

                // Result 1: Instagram
                variants["INSTAGRAM"]?.let { igText ->
                    AIResultVariantCard(
                        icon = Icons.Default.PhotoCamera,
                        iconColor = ColorInstagram,
                        platform = "Instagram",
                        handle = "@jas_growth",
                        styleBadge = "Visual Carousel Style",
                        text = igText,
                        meta = "${igText.length} / 2,200 chars • 4 tags",
                        onCopy = { viewModel.showToast("Instagram copy copied!") },
                        onEdit = { viewModel.exportAIVariantsToComposer() },
                        onRegen = { viewModel.generateAIContent() }
                    )
                }

                // Result 2: LinkedIn
                variants["LINKEDIN"]?.let { liText ->
                    AIResultVariantCard(
                        icon = Icons.Default.Work,
                        iconColor = ColorLinkedIn,
                        platform = "LinkedIn",
                        handle = "Jas Creative Profile",
                        styleBadge = "Professional Voice",
                        text = liText,
                        meta = "${liText.length} / 3,000 chars",
                        onCopy = { viewModel.showToast("LinkedIn copy copied!") },
                        onEdit = { viewModel.exportAIVariantsToComposer() },
                        onRegen = { viewModel.generateAIContent() }
                    )
                }

                // Result 3: X (Twitter)
                variants["X"]?.let { xText ->
                    AIResultVariantCard(
                        iconLetter = "𝕏",
                        iconColor = ColorX,
                        platform = "X (Twitter)",
                        handle = "@jasship",
                        styleBadge = "Punchy Format",
                        text = xText,
                        meta = "${xText.length} / 280 chars",
                        onCopy = { viewModel.showToast("X post copied!") },
                        onEdit = { viewModel.exportAIVariantsToComposer() },
                        onRegen = { viewModel.generateAIContent() }
                    )
                }

                // Result 4: Facebook
                variants["FACEBOOK"]?.let { fbText ->
                    AIResultVariantCard(
                        iconLetter = "f",
                        iconColor = ColorFacebook,
                        platform = "Facebook",
                        handle = "Page Update",
                        styleBadge = "Community Style",
                        text = fbText,
                        meta = "${fbText.length} chars",
                        onCopy = { viewModel.showToast("Facebook update copied!") },
                        onEdit = { viewModel.exportAIVariantsToComposer() },
                        onRegen = { viewModel.generateAIContent() }
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
fun AITargetPlatformCard(
    modifier: Modifier = Modifier,
    name: String,
    handle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    iconLetter: String? = null,
    iconColor: Color,
    isSelected: Boolean,
    onToggle: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) SurfaceContainerHigh else SurfaceContainerLow)
            .clickable(onClick = onToggle)
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(iconColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                if (icon != null) {
                    Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(16.dp))
                } else if (iconLetter != null) {
                    Text(text = iconLetter, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = iconColor)
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(text = name, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = OnSurfaceLight, maxLines = 1)
                Text(text = handle, fontSize = 10.sp, color = OnSurfaceVariantLight, maxLines = 1)
            }
        }

        if (isSelected) {
            Box(
                modifier = Modifier.size(18.dp).clip(CircleShape).background(IndigoPrimary),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
            }
        } else {
            Box(
                modifier = Modifier.size(18.dp).clip(CircleShape).background(SurfaceContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = OnSurfaceVariantLight, modifier = Modifier.size(12.dp))
            }
        }
    }
}

@Composable
fun AIResultVariantCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    iconLetter: String? = null,
    iconColor: Color,
    platform: String,
    handle: String,
    styleBadge: String,
    text: String,
    meta: String,
    onCopy: () -> Unit,
    onEdit: () -> Unit,
    onRegen: () -> Unit
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
                        .background(iconColor.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    if (icon != null) {
                        Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(16.dp))
                    } else if (iconLetter != null) {
                        Text(text = iconLetter, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = iconColor)
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = platform, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = OnSurfaceLight)
                    Text(text = handle, fontSize = 10.sp, color = OnSurfaceVariantLight)
                }
            }

            Text(
                text = styleBadge,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = OnSurfaceVariantLight,
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(SurfaceContainerLow)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }

        Text(text = text, fontSize = 12.sp, color = OnSurfaceLight, lineHeight = 17.sp)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = meta, fontSize = 10.sp, color = OnSurfaceVariantLight)

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                IconButton(onClick = onCopy, modifier = Modifier.size(28.dp)) {
                    Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Copy", tint = OnSurfaceVariantLight, modifier = Modifier.size(15.dp))
                }
                IconButton(onClick = onEdit, modifier = Modifier.size(28.dp)) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", tint = OnSurfaceVariantLight, modifier = Modifier.size(15.dp))
                }
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .clickable(onClick = onRegen)
                        .padding(horizontal = 6.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Sync, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(text = "Regen", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = IndigoPrimary)
                }
            }
        }
    }
}

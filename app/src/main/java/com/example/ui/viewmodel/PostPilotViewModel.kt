package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.AppDatabase
import com.example.data.model.PostEntity
import com.example.data.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class Screen {
    DASHBOARD,
    CREATE_POST_STAGE1,
    CREATE_POST_STAGE2,
    CALENDAR,
    MY_POSTS,
    AI_ASSISTANT,
    ANALYTICS,
    SETTINGS,
    POST_DETAIL
}

class PostPilotViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PostRepository

    val posts: StateFlow<List<PostEntity>>

    // Navigation state
    private val _currentScreen = MutableStateFlow(Screen.DASHBOARD)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val screenStack = mutableListOf<Screen>()

    // Selected Post for Detail View
    private val _selectedPostId = MutableStateFlow<Long?>(null)
    val selectedPostId: StateFlow<Long?> = _selectedPostId.asStateFlow()

    // Notification toast message
    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    // Search & Filter state
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _statusFilter = MutableStateFlow("ALL")
    val statusFilter: StateFlow<String> = _statusFilter.asStateFlow()

    private val _viewMode = MutableStateFlow("LIST") // "LIST" or "GRID"
    val viewMode: StateFlow<String> = _viewMode.asStateFlow()

    private val _selectedPostIds = MutableStateFlow<Set<Long>>(emptySet())
    val selectedPostIds: StateFlow<Set<Long>> = _selectedPostIds.asStateFlow()

    // Calendar selection
    private val _selectedCalendarDate = MutableStateFlow("2026-10-01")
    val selectedCalendarDate: StateFlow<String> = _selectedCalendarDate.asStateFlow()

    private val _calendarViewMode = MutableStateFlow("MONTH") // "MONTH", "WEEK", "DAY"
    val calendarViewMode: StateFlow<String> = _calendarViewMode.asStateFlow()

    // Composer Form State (Stage 1 & 2)
    val composerTitle = MutableStateFlow("")
    val composerContent = MutableStateFlow("")
    val composerTargetChannels = MutableStateFlow(setOf("X", "LINKEDIN", "INSTAGRAM", "THREADS", "FACEBOOK", "YOUTUBE"))
    val composerAttachedMedia = MutableStateFlow(listOf(
        "https://lh3.googleusercontent.com/aida-public/AB6AXuCnZm31iAQ5Zfw65EeaNDVwt6TDI7BeaD17zwAmD2OZ_6KvJ5FglQlwhXlhZQ_kuKwpOCOJmXRDncihF6TfuipKbTdf6E_VpRg1ypKxZwU-uoUMkc2oxYtkuFHEQJQKZzNb3kK10JZJgCB0CdFHiVxFLz-YHNMdJXGbaWKFyNUqYBD9jJzDa6IEZkiabZyUvwKw14pLBQcWuCiW2fk8tdEURH8ucO5Y6fqUy6G61MWJyvg1Uck1wjBe",
        "https://lh3.googleusercontent.com/aida-public/AB6AXuDlH4WndRhn9NPjTvK7hPg5HoTSTTFpMZ5CiWaQfUOlsoCvPoEdOGes43OcHAzt-zJLkl6_A0Lj_du40MN6fzqWXQ3z0XzRim5mCOrTM9jCcsGqRBV3p31k17zGs4X_bVxni7nf1-eHPlCSWLBEmwBapWJ9hwiO2GZQxsPmAU4ZUebL15Ss1B3oUvArC2ZppxN0MaqrDo5rQX-bKiDdakYYcwIu6tJzoMvBpF0kLcvdn-HEe6mhXgec"
    ))
    val composerScheduledTime = MutableStateFlow("Today at 5:15 PM")
    val composerHashtags = MutableStateFlow(listOf("#AI", "#MachineLearning", "#TravelTech", "#AIML"))
    val stage2ActivePlatform = MutableStateFlow("INSTAGRAM")
    val stage2Captions = MutableStateFlow(mutableMapOf(
        "INSTAGRAM" to "Just built my AI Travel Planner ✈️🤖\n\nPlanning trips just got smarter. This project helps users create personalized travel plans using AI.\n\nWhat do you think? 👀\n\n#AI #MachineLearning #TravelTech #AIML",
        "LINKEDIN" to "Excited to share my latest AI project — an AI-powered Travel Planner. 🚀\n\nThe application helps users create personalized travel plans using AI while simplifying the trip-planning process.\n\nThis project gave me an opportunity to explore AI integration, user experience design, and modern web development.",
        "X" to "Just launched AI Travel Planner! ✈️🤖 Plan custom trips with intelligent AI itineraries in seconds. Check it out & let me know your thoughts!",
        "FACEBOOK" to "Hey everyone! 👋 Really excited to share a project I've been working on: an AI Travel Planner designed to build custom itineraries based on your travel style. Would love your feedback!"
    ))

    // AI Assistant State
    val aiPrompt = MutableStateFlow("Just wrapped up building an autonomous agent that generates high-converting social copy across 6 networks. Learned so much about prompt engineering and token efficiency!")
    val aiTone = MutableStateFlow("Professional")
    val aiLength = MutableStateFlow("Medium")
    val aiIntent = MutableStateFlow("Post")
    val aiIncludeHashtags = MutableStateFlow(true)
    val aiUseEmojis = MutableStateFlow(true)
    val aiSelectedPlatforms = MutableStateFlow(setOf("INSTAGRAM", "LINKEDIN", "X", "FACEBOOK"))
    val isAIGenerating = MutableStateFlow(false)
    val aiGeneratedVariants = MutableStateFlow<Map<String, String>>(mapOf(
        "INSTAGRAM" to "Just wrapped up building an autonomous agent that generates high-converting social copy across 6 networks! 🚀 Built with Google AI Studio. 3 key lessons on token efficiency in the carousel below ⬇️✨\n\n#AI #MachineLearning #BuildInPublic #TechDev",
        "LINKEDIN" to "Excited to share a milestone from my recent engineering sprint: we built an autonomous social content pipeline using Google AI Studio. The architecture focuses on prompt optimization and multi-format token efficiency, reducing workflow bottlenecks by 70%.",
        "X" to "Built an autonomous agent that turns 1 prompt into platform-ready copy across 6 networks using Google AI Studio ⚡\n\nPrompt engineering + token efficiency = 10x faster shipping.",
        "FACEBOOK" to "Hey everyone! 👋 Really excited to share what I've been building: an AI agent that automatically formats your updates for each platform. How do you all approach cross-posting without losing your authentic voice?"
    ))

    // Settings Profile & Config
    val profileName = MutableStateFlow("Jas Creative")
    val profileEmail = MutableStateFlow("jas@example.com")
    val connectedPlatforms = MutableStateFlow(mutableMapOf(
        "INSTAGRAM" to true,
        "LINKEDIN" to true,
        "X" to true,
        "FACEBOOK" to false, // token expired
        "YOUTUBE" to false,
        "THREADS" to false
    ))
    val defaultTone = MutableStateFlow("Professional")
    val autoShortenLinks = MutableStateFlow(true)
    val appendSignatureHashtags = MutableStateFlow(true)
    val pushNotifications = MutableStateFlow(true)
    val weeklyDigest = MutableStateFlow(true)
    val failedDeliveryAlerts = MutableStateFlow(true)

    init {
        val database = AppDatabase.getDatabase(application)
        repository = PostRepository(database.postDao())

        posts = repository.allPosts.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        viewModelScope.launch {
            repository.initializeSeedDataIfNeeded()
        }
    }

    // Navigation functions
    fun navigateTo(screen: Screen) {
        if (_currentScreen.value != screen) {
            screenStack.add(_currentScreen.value)
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        if (screenStack.isNotEmpty()) {
            _currentScreen.value = screenStack.removeAt(screenStack.size - 1)
            return true
        }
        if (_currentScreen.value != Screen.DASHBOARD) {
            _currentScreen.value = Screen.DASHBOARD
            return true
        }
        return false
    }

    fun openPostDetail(postId: Long) {
        _selectedPostId.value = postId
        navigateTo(Screen.POST_DETAIL)
    }

    fun showToast(message: String) {
        _toastMessage.value = message
    }

    fun clearToast() {
        _toastMessage.value = null
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setStatusFilter(status: String) {
        _statusFilter.value = status
    }

    fun setViewMode(mode: String) {
        _viewMode.value = mode
    }

    fun togglePostSelection(postId: Long) {
        val current = _selectedPostIds.value.toMutableSet()
        if (current.contains(postId)) {
            current.remove(postId)
        } else {
            current.add(postId)
        }
        _selectedPostIds.value = current
    }

    fun selectAllPosts(allIds: List<Long>) {
        if (_selectedPostIds.value.size == allIds.size) {
            _selectedPostIds.value = emptySet()
        } else {
            _selectedPostIds.value = allIds.toSet()
        }
    }

    fun archiveSelectedPosts() {
        viewModelScope.launch {
            val ids = _selectedPostIds.value.toList()
            if (ids.isNotEmpty()) {
                repository.archivePosts(ids)
                showToast("Archived ${ids.size} posts")
                _selectedPostIds.value = emptySet()
            }
        }
    }

    fun deleteSelectedPosts() {
        viewModelScope.launch {
            val ids = _selectedPostIds.value.toList()
            if (ids.isNotEmpty()) {
                repository.deletePosts(ids)
                showToast("Deleted ${ids.size} posts")
                _selectedPostIds.value = emptySet()
            }
        }
    }

    fun setSelectedCalendarDate(date: String) {
        _selectedCalendarDate.value = date
    }

    fun setCalendarViewMode(mode: String) {
        _calendarViewMode.value = mode
    }

    fun toggleComposerChannel(channel: String) {
        val current = composerTargetChannels.value.toMutableSet()
        if (current.contains(channel)) {
            if (current.size > 1) {
                current.remove(channel)
            } else {
                showToast("Keep at least 1 destination platform")
            }
        } else {
            current.add(channel)
        }
        composerTargetChannels.value = current
    }

    fun polishMasterContentWithAI() {
        val content = composerContent.value.trim()
        if (content.isEmpty()) {
            composerContent.value = "🚀 Excited to unveil our next-gen creator intelligence engine. Purpose-built for hyper-scaling digital agencies and power creators."
            if (composerTitle.value.isEmpty()) {
                composerTitle.value = "Next-Gen Creator Engine Announcement"
            }
        } else {
            composerContent.value = "⚡ $content — optimized for high organic viral engagement across all platforms!"
        }
        showToast("✨ AI polished and optimized master copy!")
    }

    fun addHashtagToComposer(tag: String) {
        val current = composerContent.value
        composerContent.value = if (current.isEmpty()) tag else "$current $tag"
        showToast("Added $tag")
    }

    fun removeComposerMedia(index: Int) {
        val list = composerAttachedMedia.value.toMutableList()
        if (index in list.indices) {
            list.removeAt(index)
            composerAttachedMedia.value = list
            showToast("Media removed")
        }
    }

    fun addComposerMediaPreset() {
        val list = composerAttachedMedia.value.toMutableList()
        if (list.size < 4) {
            list.add("https://lh3.googleusercontent.com/aida-public/AB6AXuCFby87p2Q3XMq5BmI2K-lwEa6IufR8NuD8tHR440jlNoXUPDKFf5Q0WfYI-j3a6iszBG1Q7PW3qdJxs4gKqWueK-Uhs-JBR0iCeDrf_dHfhxEqVGljTeRdbQYViqk-co3m5M848sFDTKOGqZhAPbSg_yhwH5uLMNTh2afBec46fXI0qvj8QwHjHR_bOb_G6_qYW6Y30z_lJyfjdZHJ6K9sA6xHMWAVuQ8n7mNWdiK_0Nf5hDXg4dFi")
            composerAttachedMedia.value = list
            showToast("Added media attachment")
        } else {
            showToast("Max 4 media files allowed")
        }
    }

    fun updatePlatformCaption(platform: String, newCaption: String) {
        val map = stage2Captions.value.toMutableMap()
        map[platform] = newCaption
        stage2Captions.value = map
    }

    fun applyAICopyOptimizer(platform: String, action: String) {
        val current = stage2Captions.value[platform] ?: ""
        val updated = when (action) {
            "Improve Hook" -> "⚡ BREAKING: " + current.replace("Just built", "Here is what happened when I built")
            "Shorten" -> current.take((current.length * 0.65).toInt()) + "..."
            "Add Emojis ✨" -> "✨🚀 $current 📈🔥"
            "Call to Action" -> "$current\n\n👉 What are your thoughts? Drop a comment below!"
            else -> current
        }
        updatePlatformCaption(platform, updated)
        showToast("✨ Applied $action to $platform")
    }

    fun addPlatformHashtag(tag: String) {
        val current = composerHashtags.value.toMutableList()
        if (!current.contains(tag)) {
            current.add(tag)
            composerHashtags.value = current
            showToast("Tag added")
        }
    }

    fun removePlatformHashtag(tag: String) {
        val current = composerHashtags.value.toMutableList()
        current.remove(tag)
        composerHashtags.value = current
    }

    fun saveDraftPost() {
        val title = composerTitle.value.ifBlank { "Untitled Draft Post" }
        val content = composerContent.value.ifBlank { "Draft message ready for distribution" }
        viewModelScope.launch {
            repository.insertPost(
                PostEntity(
                    title = title,
                    masterContent = content,
                    status = "DRAFT",
                    targetChannels = composerTargetChannels.value.joinToString(","),
                    channelStatuses = composerTargetChannels.value.joinToString(";") { "$it:DRAFT" },
                    channelCaptions = stage2Captions.value["INSTAGRAM"] ?: content,
                    mediaUrl = composerAttachedMedia.value.firstOrNull() ?: "",
                    mediaCount = composerAttachedMedia.value.size
                )
            )
            showToast("Draft saved successfully to workspace")
            navigateTo(Screen.MY_POSTS)
        }
    }

    fun finalizeAndPublishPost() {
        val title = composerTitle.value.ifBlank { "New Campaign Post" }
        val content = composerContent.value.ifBlank { "Excited to share our latest project updates!" }
        viewModelScope.launch {
            repository.insertPost(
                PostEntity(
                    title = title,
                    masterContent = content,
                    status = "SCHEDULED",
                    targetChannels = composerTargetChannels.value.joinToString(","),
                    channelStatuses = composerTargetChannels.value.joinToString(";") { "$it:SCHEDULED" },
                    channelCaptions = stage2Captions.value["INSTAGRAM"] ?: content,
                    mediaUrl = composerAttachedMedia.value.firstOrNull() ?: "",
                    mediaCount = composerAttachedMedia.value.size,
                    scheduledTime = composerScheduledTime.value
                )
            )
            showToast("🚀 Post scheduled & synchronized across channels!")
            navigateTo(Screen.MY_POSTS)
        }
    }

    fun retryFailedPlatform(post: PostEntity, platform: String) {
        viewModelScope.launch {
            val updatedMap = post.getStatusMap().toMutableMap()
            updatedMap[platform] = "PUBLISHED"
            val updatedStatuses = updatedMap.entries.joinToString(";") { "${it.key}:${it.value}" }
            val newStatus = if (updatedMap.values.all { it == "PUBLISHED" }) "PUBLISHED" else "IN_PROGRESS"
            val updatedPost = post.copy(
                channelStatuses = updatedStatuses,
                status = newStatus,
                failureReason = null
            )
            repository.updatePost(updatedPost)
            showToast("Retrying $platform dispatch... Connected & Published! ✅")
        }
    }

    fun generateAIContent() {
        val prompt = aiPrompt.value.trim()
        if (prompt.isEmpty()) {
            showToast("Please enter a prompt first")
            return
        }
        isAIGenerating.value = true
        viewModelScope.launch {
            kotlinx.coroutines.delay(1000)
            val tone = aiTone.value
            val isShort = aiLength.value == "Short"

            val igText = if (isShort) {
                "🚀 $prompt\n\n#AI #TechDev #BuildInPublic"
            } else {
                "Just wrapped up an incredible milestone! 🚀 $prompt\n\nKey takeaways:\n1. Prompt precision matters\n2. Token optimization saves 70% cost\n3. Consistent delivery wins\n\nSwipe to see the full breakdown ➡️\n\n#AI #MachineLearning #BuildInPublic #TechDev"
            }

            val liText = "Excited to share insights from our latest sprint: $prompt\n\nAs we scale multi-channel distribution, maintaining high signal-to-noise ratio in modern workflows requires thoughtful architecture and iterative refinement.\n\nWhat strategies are working best for your team? Let's connect below."

            val xText = "Built an autonomous pipeline that turns 1 prompt into platform-ready copy across 6 networks ⚡\n\n$prompt\n\nPrompt efficiency = 10x shipping velocity."

            val fbText = "Hey everyone! 👋 Really excited to share what we've been building: $prompt. How do you approach cross-posting without losing your authentic brand voice? Would love your feedback!"

            aiGeneratedVariants.value = mapOf(
                "INSTAGRAM" to igText,
                "LINKEDIN" to liText,
                "X" to xText,
                "FACEBOOK" to fbText
            )
            isAIGenerating.value = false
            showToast("✨ Generated 4 platform-tailored formats!")
        }
    }

    fun exportAIVariantsToComposer() {
        val variants = aiGeneratedVariants.value
        composerTitle.value = "AI Generated: " + aiPrompt.value.take(30) + "..."
        composerContent.value = variants["LINKEDIN"] ?: aiPrompt.value
        stage2Captions.value = variants.toMutableMap()
        showToast("Exported to Universal Composer!")
        navigateTo(Screen.CREATE_POST_STAGE1)
    }

    fun reconnectPlatform(platform: String) {
        val map = connectedPlatforms.value.toMutableMap()
        map[platform] = true
        connectedPlatforms.value = map
        showToast("Connected to $platform successfully via OAuth 2.0")
    }

    fun disconnectPlatform(platform: String) {
        val map = connectedPlatforms.value.toMutableMap()
        map[platform] = false
        connectedPlatforms.value = map
        showToast("Disconnected from $platform")
    }
}

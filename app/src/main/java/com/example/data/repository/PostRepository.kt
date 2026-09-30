package com.example.data.repository

import com.example.data.db.PostDao
import com.example.data.model.PostEntity
import kotlinx.coroutines.flow.Flow

class PostRepository(private val postDao: PostDao) {

    val allPosts: Flow<List<PostEntity>> = postDao.getAllPosts()

    fun getPostById(id: Long): Flow<PostEntity?> = postDao.getPostById(id)

    suspend fun insertPost(post: PostEntity): Long = postDao.insertPost(post)

    suspend fun updatePost(post: PostEntity) = postDao.updatePost(post)

    suspend fun deletePost(post: PostEntity) = postDao.deletePost(post)

    suspend fun deletePosts(ids: List<Long>) = postDao.deletePostsByIds(ids)

    suspend fun archivePosts(ids: List<Long>) = postDao.archivePostsByIds(ids)

    suspend fun initializeSeedDataIfNeeded() {
        if (postDao.getPostCount() == 0) {
            val seedPosts = listOf(
                PostEntity(
                    title = "AI Travel Planner",
                    masterContent = "Built an AI-powered travel planner that helps users create personalized trips based on budget, style, and real-time flight data. Compose once, deploy everywhere.",
                    category = "Campaign",
                    createdDate = "Sep 30, 2026",
                    scheduledTime = "Oct 24, 2:30 PM",
                    scheduledTimestamp = System.currentTimeMillis() - 86400000L * 2,
                    status = "IN_PROGRESS",
                    targetChannels = "INSTAGRAM,LINKEDIN,X,FACEBOOK",
                    channelStatuses = "INSTAGRAM:PUBLISHED;LINKEDIN:PUBLISHED;X:SCHEDULED;FACEBOOK:FAILED",
                    channelCaptions = "Just built my AI Travel Planner ✈️🤖\n\nPlanning trips just got smarter. This project helps users create personalized travel plans using AI.\n\nWhat do you think? 👀\n\n#AI #MachineLearning #TravelTech",
                    mediaUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAv8qiAw2PuXC1u57DuEfOuTBOSeZe7nOUuuJjEnC0IgA7nr3QaXxFkgsyvqFmHT2205f7DeHUI671Me69KERcaGUb3syGCIhX-YTXULjANI8C2vSF7k0aEaXaA0WobD836NHKYKPJfbeSdIrXb1nNXHAYYAMISJf61V1Bjdqp4ss_AZEIGQnv4d9USUjMzK4RuzWmstHsGAxeUd10aF0zmTBT7nec8mWAJXNQUuQY4BDgxI8WRIYPi",
                    mediaCount = 1,
                    views = 1420,
                    likes = 88,
                    comments = 14,
                    reposts = 42,
                    impressions = 2150,
                    failureReason = "OAuth Token Expired: Page permissions must be refreshed before this post can be pushed.",
                    hashtags = "#AI #TravelTech #ProductLaunch"
                ),
                PostEntity(
                    title = "Built My First ML Project",
                    masterContent = "Lessons learned training custom vision models with edge deployment. How reducing model weights boosted mobile inference speed by 340%...",
                    category = "Case Study",
                    createdDate = "Sep 22, 2026",
                    scheduledTime = "Sep 22, 4:00 PM",
                    scheduledTimestamp = System.currentTimeMillis() - 86400000L * 8,
                    status = "PUBLISHED",
                    targetChannels = "LINKEDIN,INSTAGRAM",
                    channelStatuses = "LINKEDIN:PUBLISHED;INSTAGRAM:PUBLISHED",
                    channelCaptions = "Excited to share our technical writeup on optimizing neural networks for edge execution. Check out the GitHub repo in comments!",
                    mediaUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCHyEdRBVHztaSzvXbXfsZPqKoYZ9C8tOe6i4jP74ZPiHEuPmKhxBfCYfLZ88ZNpcUx-jnls78WkG8X_HtxiWS1eMKHDak0tpCMZ5NJQ_woIiVWsCwpMDMJ3WHjlnmpzHN1yXQricJYkSiq8tpp_-f-udeeSeMtMq6jP9HzQjyOm330MEQSxVs7rDUk4o4DP1pqCpuTK27DXHRufzmaIyDWFPsxE8B26EWJIoF5fjWlZmNdO39vFzqM",
                    mediaCount = 2,
                    views = 3280,
                    likes = 245,
                    comments = 38,
                    reposts = 54,
                    impressions = 4800,
                    hashtags = "#MachineLearning #ComputerVision #EdgeAI"
                ),
                PostEntity(
                    title = "30 Days of Coding",
                    masterContent = "Reflecting on daily building, shipping small features, and developer habits. Here is the framework I used to ship 4 production apps while working full-time...",
                    category = "Engineering",
                    createdDate = "Sep 20, 2026",
                    scheduledTime = "Oct 3, 7:30 PM",
                    scheduledTimestamp = System.currentTimeMillis() + 86400000L * 3,
                    status = "IN_PROGRESS",
                    targetChannels = "INSTAGRAM,THREADS",
                    channelStatuses = "INSTAGRAM:PUBLISHED;THREADS:PENDING",
                    channelCaptions = "Consistency beats intensity every single time. Here are 3 daily habits that helped me stay locked in for 30 consecutive days of coding.",
                    mediaUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBWbUIWITBp_EzYRIfR_drGwFZFvJattiIXCeNgKBGpodfFL1kViWwqbwCRsvyVU7sT0GR2jiu2URQwbwGmsZl9APjmX5BOvmOjKb5UhQ2VQ_DXb8JtyT9Kom2x4iOJ6K3yns6VyZeD7Rp8CB4Pl0Pde_qIlb7XxC_MO990e0kgAjHleghM0wRg0CaupI793s_5BdaVN10DZ6uLTahMeRbcEk4mamxZhW1KAL_iJTm49P2B9avPrTLn",
                    mediaCount = 1,
                    views = 850,
                    likes = 72,
                    comments = 9,
                    hashtags = "#BuildInPublic #100DaysOfCode #DevLife"
                ),
                PostEntity(
                    title = "AI/ML Roadmap",
                    masterContent = "A comprehensive guide to learning generative AI and transformer architectures in 2026. Includes free high-quality resources, math fundamentals, and practice repo links...",
                    category = "Educational",
                    createdDate = "Oct 1, 2026",
                    scheduledTime = "Tomorrow · 7:00 PM",
                    scheduledTimestamp = System.currentTimeMillis() + 86400000L * 1,
                    status = "SCHEDULED",
                    targetChannels = "LINKEDIN,X",
                    channelStatuses = "LINKEDIN:SCHEDULED;X:SCHEDULED",
                    channelCaptions = "Breaking down the 6-month roadmap from Python fundamentals to LLM orchestration and enterprise fine-tuning. Swipe through the slides!",
                    mediaUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCFby87p2Q3XMq5BmI2K-lwEa6IufR8NuD8tHR440jlNoXUPDKFf5Q0WfYI-j3a6iszBG1Q7PW3qdJxs4gKqWueK-Uhs-JBR0iCeDrf_dHfhxEqVGljTeRdbQYViqk-co3m5M848sFDTKOGqZhAPbSg_yhwH5uLMNTh2afBec46fXI0qvj8QwHjHR_bOb_G6_qYW6Y30z_lJyfjdZHJ6K9sA6xHMWAVuQ8n7mNWdiK_0Nf5hDXg4dFi",
                    mediaCount = 5,
                    hashtags = "#AI #DataScience #DeepLearning"
                ),
                PostEntity(
                    title = "My New Portfolio",
                    masterContent = "Minimalist portfolio showcase redesign with case studies and interactive prototypes. Focused on micro-interactions and high performance...",
                    category = "Design",
                    createdDate = "Oct 18, 2026",
                    scheduledTime = "Monday, Oct 5 · 6:30 PM",
                    scheduledTimestamp = System.currentTimeMillis() + 86400000L * 5,
                    status = "DRAFT",
                    targetChannels = "LINKEDIN,X",
                    channelStatuses = "LINKEDIN:PENDING;X:FAILED",
                    channelCaptions = "Finally revamped my personal portfolio website! Check out my latest design systems and production projects.",
                    mediaUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuB3mxZsx4qW0Zk6OTxUyxyedb6NfgG3OIPvLfb4O5h7DUV8IuijD3VI9AFyrREYzFSM1X2RN4SPkZOtl5ab3tHfg0PC7QXSgOJ1JZMxAiI8I5IpoGtLX0ijRpYtYH71LQxKPyC9zyCbmSkXhSpy2H6xQ0pIaH_sBYkw0V3FmPy9CGF8rF7tBSXEVa_u-LttMNZ6jj0IZMBWELu2O21t-g2lID7VlbPwc6ZMPruoBRy_ufZ2_tdYAYaE",
                    mediaCount = 2,
                    failureReason = "Delivery blocked: Please review authorization.",
                    hashtags = "#Portfolio #UIDesign #WebDev"
                ),
                PostEntity(
                    title = "Python Learning Journey",
                    masterContent = "5 key concepts every beginner must grasp when starting with Python data structures and asynchronous workers...",
                    category = "Tutorial",
                    createdDate = "Oct 1, 2026",
                    scheduledTime = "Thursday, Oct 1 · 8:00 PM",
                    scheduledTimestamp = System.currentTimeMillis() + 86400000L * 2,
                    status = "DRAFT",
                    targetChannels = "INSTAGRAM",
                    channelStatuses = "INSTAGRAM:DRAFT",
                    channelCaptions = "5 key Python concepts you cannot afford to skip when transitioning from script writing to building backend engines.",
                    mediaUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDST-PELK-rf3S_dt_Dxh6mapF_9noMCVfzS6k_6GMqbCzT-3-WE2BQs_KQuT3GHy6o02b56ViP62L6VPu_BVAAyxIwt4OM6vAeVVJ5inABb3_niFwEknT6zsq8Lt4n9_w7M2gwSx5uYgDAL8lb-Mf3YI3jtm8201hokC6zExaO2eKW4iTE3rE8FHA9MICZdGYix5oBsM95SB_2UiNcjvaazcucIgRkyuiCkXhgryOwznMVVAH-Hbm9",
                    mediaCount = 1,
                    hashtags = "#Python #Backend #DevTips"
                ),
                PostEntity(
                    title = "Top 5 VS Code Extensions 2025",
                    masterContent = "My top daily development extensions for VS Code in 2025: AI syntax autocompleters, intelligent git visualizers, and ergonomic themes.",
                    category = "Video Script",
                    createdDate = "Oct 2, 2026",
                    scheduledTime = "Friday · 10:30 AM",
                    scheduledTimestamp = System.currentTimeMillis() + 86400000L * 2,
                    status = "SCHEDULED",
                    targetChannels = "YOUTUBE,X,THREADS",
                    channelStatuses = "YOUTUBE:SCHEDULED;X:SCHEDULED;THREADS:SCHEDULED",
                    channelCaptions = "Want to double your coding efficiency? Here are my 5 non-negotiable VS Code extensions for 2025.",
                    mediaUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBOcTxDagrWTvKLK63VZYD89H1RLSBf59ywPwygAiiRtKBq658t8gQkpyiU4M9LccvHKgcXbJnlg7pHGyP6-Q6aChoF-5qhN7ztOAd1-bQIIpyQiPvjmoD6z3KcfcCWDlVt0VQjx0MDGOVV6ATa_12pGVPI9CD6XKWMIgF_92uR6MlPHZucmI83dfqkjM-L2HtAgEJ34DxHvpc6Bk1xYRwpnqOfz6YuhyktxbCNYfcMU68PYeb7pk6i",
                    mediaCount = 1,
                    hashtags = "#VSCode #DeveloperTools #Tech"
                ),
                PostEntity(
                    title = "Weekly Tech Recap #42",
                    masterContent = "Highlights of this week's open source AI releases, web standard updates, and cloud infrastructure benchmarks.",
                    category = "Newsletter",
                    createdDate = "Oct 3, 2026",
                    scheduledTime = "Sunday · 6:00 PM",
                    scheduledTimestamp = System.currentTimeMillis() + 86400000L * 4,
                    status = "SCHEDULED",
                    targetChannels = "LINKEDIN,FACEBOOK,X",
                    channelStatuses = "LINKEDIN:SCHEDULED;FACEBOOK:SCHEDULED;X:SCHEDULED",
                    channelCaptions = "Weekly Tech Recap #42 is out! Covering model distillation breakthroughs, edge computing benchmarks, and the new web component standards.",
                    mediaUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBBqjb8hIO3ZAjZM2yOhyBL7xgOli7ZNg9WAE9Xa1UUcuzLv6ZvXbV5yFNvcCbK_R_l38ke1BdOX9eye65orAWIwkhk5Z7gIKxLZK0dVhMnwv82EgM2LAZS8QpThOiMkD9YlqPvDpqnPW4-ixGKLiM4kOlcuT-KSt3zDMOnS_F-kmEkpJEr2_bxHhV_QugVBw7_8i7qq6k24P6ocZB1lQT3h4J-WyC_oPINd-HpuGQg9RFb50QT3GTy",
                    mediaCount = 3,
                    hashtags = "#TechRecap #WeeklyUpdate #OpenSource"
                )
            )
            postDao.insertPosts(seedPosts)
        }
    }
}

package com.example.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query("SELECT * FROM posts WHERE isArchived = 0 ORDER BY id DESC")
    fun getAllPosts(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE id = :id")
    fun getPostById(id: Long): Flow<PostEntity?>

    @Query("SELECT * FROM posts WHERE status = :status AND isArchived = 0 ORDER BY id DESC")
    fun getPostsByStatus(status: String): Flow<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: PostEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>): List<Long>

    @Update
    suspend fun updatePost(post: PostEntity)

    @Delete
    suspend fun deletePost(post: PostEntity)

    @Query("DELETE FROM posts WHERE id IN (:ids)")
    suspend fun deletePostsByIds(ids: List<Long>)

    @Query("UPDATE posts SET isArchived = 1 WHERE id IN (:ids)")
    suspend fun archivePostsByIds(ids: List<Long>)

    @Query("SELECT COUNT(*) FROM posts")
    suspend fun getPostCount(): Int
}

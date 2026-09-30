package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.PostEntity
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("PostPilot", appName)
    }

    @Test
    fun `test post entity channel and status parsing`() {
        val post = PostEntity(
            title = "Test Post",
            masterContent = "Hello World",
            targetChannels = "INSTAGRAM,LINKEDIN,X",
            channelStatuses = "INSTAGRAM:PUBLISHED;LINKEDIN:PUBLISHED;X:SCHEDULED"
        )

        assertEquals(3, post.getTotalChannelsCount())
        assertEquals(2, post.getPublishedCount())
        assertEquals("PUBLISHED", post.getStatusMap()["INSTAGRAM"])
        assertEquals("SCHEDULED", post.getStatusMap()["X"])
    }
}

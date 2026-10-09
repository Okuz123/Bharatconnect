package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.BharatRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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
        assertEquals("BharatSeva", appName)
    }

    @Test
    fun `admin authentication succeeds with specified credentials`() {
        val repo = BharatRepository()
        val success = repo.verifyAdminLogin("admin", "1234Mnbv")
        assertTrue(success)
        assertTrue(repo.isAdminLoggedIn.value)
    }

    @Test
    fun `wallet recharge increases balance`() {
        val repo = BharatRepository()
        val initial = repo.walletBalance.value
        repo.addMoneyToWallet(500)
        assertEquals(initial + 500, repo.walletBalance.value)
    }
}

package com.example.unittestproject.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    /**
     * we want to run all the function in the main thread because if we run it in different thread we can't predict which thread is running and may case different scenario
     * so we use runBlockingTest and define rule for juint4 with Instant Task Executor Rule
     */

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ShoppingDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            ShoppingDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.getDao()
    }

    @After
    fun turnOff() {
        database.close()
    }

    @Test
    fun insertShoppingItemTest() = runBlockingTest {
        val shoppingItem = ShoppingItem(
            name = "Autum",
            amount = 1911,
            imageUrl = "Layton",
            price = .558f,
            id = 1
        )
        dao.insertShoppingItem(shoppingItem)
        val allItemInDB = dao.observeAllShoppingItem().getOrAwaitValue()

        Truth.assertThat(allItemInDB).contains(
            shoppingItem
        )
    }

    @Test
    fun deleteShoppingItemTest() = runBlockingTest {
        val shoppingItem = ShoppingItem(
            name = "Autum",
            amount = 1911,
            imageUrl = "Layton",
            price = .558f,
            id = 1
        )
        dao.insertShoppingItem(shoppingItem)

        dao.deleteShoppingItem(shoppingItem)

        val allItemInDB = dao.observeAllShoppingItem().getOrAwaitValue()
        assertThat(allItemInDB).doesNotContain(shoppingItem)
    }

    @Test
    fun observeAllPriceTest() = runBlockingTest {
        val shoppingItem1 = ShoppingItem(
            name = "Champagne",
            amount = 1,
            imageUrl = "Yanira",
            price = 17.2f,
            id = 1
        )
        val shoppingItem2 = ShoppingItem(
            name = "Candida",
            amount = 2,
            imageUrl = "Thomasina",
            price = 10f,
            id = 2
        )

        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)

        val totalPrice = dao.observeTotalPrice().getOrAwaitValue()

        assertThat(totalPrice).isEqualTo(2 * 10f + 1 * 17.2f)
    }
}
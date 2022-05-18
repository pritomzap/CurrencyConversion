package com.currencyconversion.app.tests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.currencyconversion.app.ui.viewModels.MainViewModel
import com.google.ar.core.Config
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
/*import kotlinx.coroutines.test.runBlockingTest*/

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@SmallTest
class ViewModelTest {
    companion object{
        private const val TAG = "ViewModelTest"
    }

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

   /* @get:Rule
    var mainCoroutineRule = MainCoroutineRule()*/


    @Inject lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2)
    }

}
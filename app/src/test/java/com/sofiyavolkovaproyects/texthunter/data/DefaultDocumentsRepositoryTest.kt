/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sofiyavolkovaproyects.texthunter.data

import com.sofiyavolkovaproyects.texthunter.data.local.database.LandingItemType
import com.sofiyavolkovaproyects.texthunter.data.local.database.savedDocsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for [DefaultDocumentsRepository].
 */
class DefaultDocumentsRepositoryTest {

    @Test
    fun landingItemTypes_newItemSaved_itemIsReturned() = runTest {
        val repository = DefaultDocumentsRepository(FakeLandingItemTypeDao())

        repository.add("Repository")

        assertEquals(repository.savedDocuments.first().size, 1)
    }

}

private class FakeLandingItemTypeDao : savedDocsDao {

    private val data = mutableListOf<LandingItemType>()

    override fun getLandingItemTypes(): Flow<List<LandingItemType>> = flow {
        emit(data)
    }

    override suspend fun insertLandingItemType(item: LandingItemType) {
        data.add(0, item)
    }
}
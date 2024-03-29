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

package com.sofiyavolkovaprojects.texthunter.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SaveDocDao {
    @Query("SELECT * FROM documentItem ORDER BY uid DESC")
    fun getDocumentItems(): Flow<List<DocumentItem>>

    @Query("SELECT * FROM documentItem WHERE uid = :id")
    fun getDocumentItemById(id: Int): Flow<DocumentItem>

    @Insert
    suspend fun insertDocItem(item: DocumentItem)

    @Delete
    suspend fun deleteDocItem(item: DocumentItem)

    @Update
    suspend fun updateDocItem(item: DocumentItem)

}

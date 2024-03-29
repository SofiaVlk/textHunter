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

package com.sofiyavolkovaprojects.texthunter.data.di

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.sofiyavolkovaprojects.texthunter.data.DefaultDocumentsRepository
import com.sofiyavolkovaprojects.texthunter.data.DefaultImagesRepository
import com.sofiyavolkovaprojects.texthunter.data.DocumentsRepository
import com.sofiyavolkovaprojects.texthunter.data.ImagesRepository
import com.sofiyavolkovaprojects.texthunter.data.local.database.DocumentItem
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsDocumentsRepository(
        documentsRepository: DefaultDocumentsRepository
    ): DocumentsRepository

    @Singleton
    @Binds
    fun bindsImagesRepository(
        documentsRepository: DefaultImagesRepository
    ): ImagesRepository

}

class FakeDocumentsRepository @Inject constructor() : DocumentsRepository {
    override suspend fun getSavedDocuments(): Flow<List<DocumentItem>> = flowOf(
        fakeDocumentsItemList
    )
    override suspend fun getDocumentById(id: Int): Flow<DocumentItem> = flowOf(fakeDocumentsItemList.first())

    override suspend fun updateDocument(document: DocumentItem) {
        TODO("Not yet implemented")
    }

    override suspend fun add(document: DocumentItem) {
        throw NotImplementedError()
    }

    override suspend fun remove(document: DocumentItem) {
        TODO("Not yet implemented")
    }
}

val fakeDocumentsItemList = (1..4).map { order ->
    DocumentItem("Documento $order", LoremIpsum(50).values.first())
}


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

package com.sofiyavolkovaprojects.texthunter.ui.savedDocs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sofiyavolkovaprojects.texthunter.data.DocumentsRepository
import com.sofiyavolkovaprojects.texthunter.data.local.database.DocumentItem
import com.sofiyavolkovaprojects.texthunter.ui.savedDocs.SavedDocsUiState.Empty
import com.sofiyavolkovaprojects.texthunter.ui.savedDocs.SavedDocsUiState.Error
import com.sofiyavolkovaprojects.texthunter.ui.savedDocs.SavedDocsUiState.Loading
import com.sofiyavolkovaprojects.texthunter.ui.savedDocs.SavedDocsUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SavedDocsViewModel @Inject constructor(
    private val savedDocsRepository: DocumentsRepository
) : ViewModel() {

    private var _uiState: MutableStateFlow<SavedDocsUiState> = MutableStateFlow(Loading)
    internal val uiState: StateFlow<SavedDocsUiState> get() = _uiState

    init {
        getDocumentList()
    }
//elimina una lista de documentos
    fun removeDocument(document: DocumentItem) {
        viewModelScope.launch {
            savedDocsRepository.remove(document)
        }
        getDocumentList()
    }
//recupera una lista de documentos
    private fun getDocumentList() {
        viewModelScope.launch {
            savedDocsRepository.getSavedDocuments()
                .catch { _uiState.update { Error } }
                .collect { docItemList ->
                    _uiState.update {
                        if (docItemList.isNotEmpty()) {
                            Success(docItemList)
                        } else {
                            Empty
                        }
                    }
                }
        }
    }
}
//estados de SavedDocs
sealed interface SavedDocsUiState {
    data object Loading : SavedDocsUiState
    data object Empty : SavedDocsUiState
    data object Error: SavedDocsUiState
    data class Success(val data: List<DocumentItem>) : SavedDocsUiState
}
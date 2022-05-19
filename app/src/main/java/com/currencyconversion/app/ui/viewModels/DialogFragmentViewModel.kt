package com.currencyconversion.app.ui.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.currencyconversion.app.data.models.common.CommonDialogBuilder
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@ViewModelScoped
class DialogFragmentViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    private val _commonDialogBuilder: MutableLiveData<CommonDialogBuilder> = MutableLiveData()
    fun setData(titleText: CommonDialogBuilder) = viewModelScope.launch { _commonDialogBuilder.postValue(titleText) }
    fun fetchData(): LiveData<CommonDialogBuilder> = _commonDialogBuilder.switchMap { titleData -> liveData(Dispatchers.IO) { emit(titleData) } }
}
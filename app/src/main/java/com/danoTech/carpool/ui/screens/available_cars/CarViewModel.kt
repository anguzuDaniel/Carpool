package com.danoTech.carpool.ui.screens.available_cars


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danoTech.carpool.model.Car
import com.danoTech.carpool.model.service.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarViewModel @Inject constructor(
    private val storageService: StorageService
) : ViewModel() {

    private val _cars = MutableStateFlow<List<Car>>(emptyList())
    val cars = _cars.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _cars.value = storageService.getCars()
        }
    }
}
package com.example.androiddevchallenge.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val liveHour = MutableLiveData("0")
    val liveMinute = MutableLiveData("0")
    val liveSecond = MutableLiveData("0")
    val isCountingDown = MutableLiveData(false)

    fun startCounting() {
        if (isCountingDown.value == true) return
        isCountingDown.value = true
        val totalSecond =
            ((liveHour.value ?: "0").toInt() * 3600) +
                    ((liveMinute.value ?: "0").toInt() * 60) +
                    ((liveSecond.value ?: "0").toInt())
        viewModelScope.launch(Dispatchers.IO) {
            (1..totalSecond)
                .forEach {
                    update(totalSecond - it)
                    delay(1000)
                }
            isCountingDown.postValue(false)
        }
    }

    private fun update(leftSecond: Int) {
        val h = leftSecond / 3600
        val min = (leftSecond - (h * 3600)) / 60
        val s = (leftSecond - (h * 3600)) % 60
        liveHour.postValue(h.toString())
        liveMinute.postValue(min.toString())
        liveSecond.postValue(s.toString())
    }
}
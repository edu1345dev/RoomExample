package com.example.roomexample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    lateinit var database: RoomExampleDatabase
    val studentsLiveData by lazy { MutableLiveData<List<Student>>() }

    fun getStudents() {
        viewModelScope.launch {
            val students = database.studentsDao().getAll()
            studentsLiveData.postValue(students)
        }
    }

    fun addStudent(student: Student) {
        viewModelScope.launch {
            database.studentsDao().insertAll(student)
        }.invokeOnCompletion {
            getStudents()
        }
    }

    fun delete() {
        val student = studentsLiveData.value?.last() ?: return

        viewModelScope.launch {
            database.studentsDao().delete(student)
            getStudents()
        }
    }
}
package com.example.roomexample

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room

class MainActivity : AppCompatActivity() {
    private val dataBase by lazy {
        Room.databaseBuilder(
            applicationContext,
            RoomExampleDatabase::class.java,
            "database"
        ).build()
    }

    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    val list by lazy { findViewById<TextView>(R.id.list) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.database = dataBase
        viewModel.getStudents()

        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.studentsLiveData.observe(this) {
            var text = ""
            it.forEach { text += (it.toString() + "\n") }
            list.text = text
        }
    }

    fun addStudent(view: View) {
        val student = Student(name = "Jose", age = 32)
        viewModel.addStudent(student)
    }

    fun delete(view: View) {
        viewModel.delete()
    }
}
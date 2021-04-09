package com.example.roomexample

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StudentDao {
    @Query("SELECT * FROM student")
    suspend fun getAll(): List<Student>

    @Query("SELECT * FROM student WHERE name LIKE :name LIMIT 1")
    suspend fun findByName(name: String): Student

    @Insert
    suspend fun insertAll(vararg students: Student)

    @Delete
    suspend fun delete(students: Student)
}
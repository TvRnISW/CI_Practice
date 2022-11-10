package com.wuilvluxkh.kotlinpracticecoroutine

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val TAG: String = "MainActivity"
    lateinit var btnClick: Button
    lateinit var txtCount: TextView

    val channel = Channel<Int>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnClick = findViewById(R.id.btnClick)
        txtCount = findViewById(R.id.txtCount)

        CoroutineScope(Dispatchers.Main).launch {
            getUsers().forEach { it ->
                Log.d(TAG, "onCreate: $it")
                // new comment add
                Toast.makeText(applicationContext, "Hello world", Toast.LENGTH_LONG).show()
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            producer().onStart {
                Log.d(TAG, "onCreate: On Start")
            }.onCompletion {
                Log.d(TAG, "onCreate: Complete")
            }.onEach {
                Log.d(TAG, "onCreate: OnEach: ${it.toString()}")
            }.onEmpty {
                Log.d(TAG, "onCreate: On Empty")
            }.collect {
                Log.d(TAG, "onCreate: $it")
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            getNotes().map {
                 FormattedNote(it.isActive,it.id, it.name.uppercase(), it.address.uppercase())
            }.filter {
                it.isActive
            }.collect{
                Log.d(TAG, "onCreate: ${it.toString()}")
            }
        }

        consumer()

        val job = CoroutineScope(Dispatchers.Main).launch {
            val data: Flow<Int> = flowProducer()
            data.collect {
                Log.d(TAG, "onCreate:Flow data collected $it")
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            delay(5000)
            job.cancel()
        }
    }

    private suspend fun getUsers(): List<String> {
        val list = mutableListOf<String>()
        list.add(getUserId(1))
        list.add(getUserId(2))
        list.add(getUserId(3))
        list.add(getUserId(4))
        list.add(getUserId(5))
        list.add(getUserId(6))
        list.add(getUserId(7))

        return list
    }

    private suspend fun getUserId(id: Int): String {
        delay(2000)
        return "User$id"
    }

    fun producer(): Flow<Int> {
        return flow<Int> {
            val list = listOf(1, 2, 3, 4, 5, 6)
            list.forEach {
                delay(2000)
                emit(it)
            }
        }
    }

    fun flowProducer() = flow<Int> {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        list.forEach {
            delay(1000)
            emit(it)
        }
    }

    fun consumer() {
        CoroutineScope(Dispatchers.Main).launch {
            Log.d(TAG, "consumer: ${channel.receive().toString()}")
            Log.d(TAG, "consumer: ${channel.receive().toString()}")
            Log.d(TAG, "consumer: ${channel.receive().toString()}")
            Log.d(TAG, "consumer: ${channel.receive().toString()}")
            Log.d(TAG, "consumer: ${channel.receive().toString()}")
            Log.d(TAG, "consumer: ${channel.receive().toString()}")
        }
    }

    private fun getNotes(): Flow<Note> {

        val list = listOf<Note>(
            Note(true, 112, "A", "Mirpur"),
            Note(false, 212, "B", "Mirpur"),
            Note(false, 122, "C", "Mirpur"),
            Note(true, 132, "D", "Mirpur"),
            Note(false, 32, "E", "Mirpur"),
        )

        return list.asFlow()
    }
}
package com.wuilvluxkh.kotlinpracticecoroutine

import android.annotation.SuppressLint
import android.content.Intent
import android.net.StaticIpConfiguration
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Display
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.io.File

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
            getUsers().forEach{ it ->
                Log.d(TAG, "onCreate: $it")
                // new comment add
                Toast.makeText(applicationContext,"Hello world",Toast.LENGTH_LONG).show()
            }
        }

        producer()
        consumer()

        val job = CoroutineScope(Dispatchers.Main).launch {
            val data: Flow<Int> = flowProducer()
            data.collect{
                Log.d(TAG, "onCreate:Flow data collected $it")
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            delay(5000)
            job.cancel()
        }
    }

    private suspend fun getUsers(): List<String>{
        val list = mutableListOf<String>()
        list.add(getUserId(1))
        list.add(getUserId(2))
        list.add(getUserId(3))
        list.add(getUserId(4))

        return list
    }

    private suspend fun getUserId(id: Int): String{
        delay(2000)
        return "User$id"
    }

    fun producer(){
        CoroutineScope(Dispatchers.Main).launch {
            channel.send(1)
            delay(2000)
            channel.send(2)
            delay(1000)
            channel.send(3)
        }
    }

    fun flowProducer() = flow<Int> {
        val list = listOf(1,2,3,4,5,6,7,8,9)
        list.forEach {
            delay(1000)
            emit(it)
        }
    }

    fun consumer(){
        CoroutineScope(Dispatchers.Main).launch {
            Log.d(TAG, "consumer: ${channel.receive().toString()}")
            Log.d(TAG, "consumer: ${channel.receive().toString()}")
            Log.d(TAG, "consumer: ${channel.receive().toString()}")
        }
    }
}
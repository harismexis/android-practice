package com.harismexis.practice.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.harismexis.practice.R
import com.harismexis.practice.notification.createNotificationChannel
import com.harismexis.practice.service.WorkService

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    private var service: WorkService? = null
    private var outMessenger: Messenger? = null
    private var isServiceBound: Boolean = false

    private var inHandler: Handler = Handler(Looper.getMainLooper()) { msg ->
        when (msg.what) {
            WorkService.MSG_REGISTER_CLIENT -> Toast.makeText(
                this@MainActivity,
                "$TAG: I registered with the service",
                Toast.LENGTH_SHORT
            ).show()
            else -> {
            }
        }
        true
    }

    private val inMessenger: Messenger = Messenger(inHandler)

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(
            componentName: ComponentName,
            iBinder: IBinder
        ) {
            val binder = iBinder as WorkService.LocalBinder
            service = binder.getService()
            outMessenger = Messenger(binder.getInMessengerBinder())
            isServiceBound = true
            try {
                val msg = Message.obtain(null, WorkService.MSG_REGISTER_CLIENT)
                msg.replyTo = inMessenger
                outMessenger?.send(msg)
            } catch (e: RemoteException) {
                Log.d(TAG, e.message ?: "Error registering with the Service")
            }
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            service = null
            isServiceBound = false
            Toast.makeText(this@MainActivity,
                "Service disconnected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel(this)
    }

    override fun onStart() {
        super.onStart()
        bindWorkService()
    }

    override fun onStop() {
        super.onStop()
        unbindService(serviceConnection)
        isServiceBound = false
    }

    private fun bindWorkService() {
        Intent(this, WorkService::class.java).also { intent ->
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    fun speakToService(v: View) {
        if (!isServiceBound) return
        val msg: Message = Message.obtain(null, WorkService.MSG_SAY_HELLO, 0, 0)
        try {
            outMessenger?.send(msg)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }

    }

}
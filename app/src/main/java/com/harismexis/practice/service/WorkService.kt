package com.harismexis.practice.service

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat
import com.harismexis.practice.R

class WorkService : JobIntentService() {

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var inMessenger: Messenger
    private val binder = LocalBinder()
    private var clients = ArrayList<Messenger>()

    companion object {
        const val MSG_SAY_HELLO = 0
        const val MSG_REGISTER_CLIENT = 1
        const val MSG_UNREGISTER_CLIENT = 2

        val TAG: String = WorkService::class.java.simpleName

        private const val JOB_ID = 1000

        private const val NOTIFICATION_CHANNEL_ID = "10"
        private const val NOTIFY_ID = 100

        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(
                context,
                WorkService::class.java, JOB_ID, intent
            )
        }
    }

    override fun onCreate() {
        super.onCreate()
        inMessenger = Messenger(inHandler)
    }

    override fun onHandleWork(intent: Intent) {}

    private var inHandler: Handler = Handler(Looper.getMainLooper()) { msg ->
        when (msg.what) {
            MSG_SAY_HELLO -> {
            }
            MSG_REGISTER_CLIENT -> {
                val outMessenger = msg.replyTo
                clients.add(outMessenger)
                outMessenger.send(Message.obtain(null, MSG_REGISTER_CLIENT, 0, 0))
                // showToast("$TAG: Client registered")
                showNotification()
            }
            MSG_UNREGISTER_CLIENT -> clients.remove(msg.replyTo)
            else -> {
            }
        }
        true
    }

    inner class LocalBinder : Binder() {
        fun getService(): WorkService = this@WorkService
        fun getInMessengerBinder(): IBinder = inMessenger.binder
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy called")
        showToast("$TAG: onDestroy called")
    }

    private fun showToast(text: CharSequence?) {
        handler.post {
            Toast.makeText(this@WorkService, text, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showNotification() {
        var builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_content))
//            .setStyle(
//                NotificationCompat.BigTextStyle()
//                    .bigText("Much longer text that cannot fit one line...")
//            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        // notificationManager.createNotificationChannel(mChannel)
        notificationManager.notify(NOTIFY_ID, builder.build())
    }

}
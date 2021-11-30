package com.dteti.animapp

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dteti.animapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.util.logging.Logger

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("Lifecycle", "onCreate() called.")

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotifyEightChannel()
        setTimeNotifyEight()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_favourite, R.id.navigation_about
            )
        )
        navView.setupWithNavController(navController)
    }

    private fun setTimeNotifyEight() {
        calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 8
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotifyEightReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,pendingIntent
        )
    }

    private fun createNotifyEightChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val name : CharSequence = "NotifyEightReceiverChannel"
            val description = "Channel to Notify User at 8 AM"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel("NotifyEightAM",name,importance)
            channel.description = description
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )

            notificationManager.createNotificationChannel(channel)

        }

    }

    override fun onPause() {
        Log.i("Lifecycle", "onPause() called.")

        super.onPause()
    }

    override fun onResume() {
        Log.i("Lifecycle", "onResume() called.")

        super.onResume()
    }

    override fun onDestroy() {
        Log.i("Lifecycle", "onDestroy() called.")

        super.onDestroy()
    }
}
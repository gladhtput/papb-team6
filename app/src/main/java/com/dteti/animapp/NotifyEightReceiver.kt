package com.dteti.animapp

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import com.dteti.animapp.dto.AnimeDetails
import com.dteti.animapp.usecases.AnimeUseCases
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class NotifyEightReceiver : BroadcastReceiver() {
    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface BroadcastReceiverEntryPoint {
        fun animeUseCases(): AnimeUseCases
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val entryPoint = EntryPointAccessors.fromApplication(context, BroadcastReceiverEntryPoint::class.java)

        Log.d("BCR", "fired")

        GlobalScope.launch {
            val useCases = entryPoint.animeUseCases()

            var anime : AnimeDetails? = null
            do {
                anime = useCases.readAnimeById(Random.nextInt(0, 10000))

                if (anime != null && (anime.ageRating.contains("R+") || anime.ageRating.contains("Rx"))) {
                    Log.d("BCR", "Unsafe for work anime found. retrying...")
                    anime = null
                }
            } while(anime == null)

            val openDetailsIntent = Intent(context, DetailActivity::class.java).apply {
                putExtra("animeId", anime.id.toString())
            }

            if (context != null) {
                val pendingIntent : PendingIntent? = TaskStackBuilder.create(context).run {
                    addNextIntentWithParentStack(openDetailsIntent)
                    getPendingIntent(42, PendingIntent.FLAG_UPDATE_CURRENT)
                }

                /*val i = Intent(context,MainActivity::class.java)
                intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val pendingIntent = PendingIntent.getActivity(context, 0, openDetailsIntent, 0)*/

                val builder = NotificationCompat.Builder(context!!, "NotifyEightAM")
                    .setSmallIcon(R.drawable.ic_animapp)
                    .setContentTitle("AnimApp")
                    .setContentText("This morning's anime suggestion: ${anime.title}")
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)

                val notificationManager = NotificationManagerCompat.from(context)
                notificationManager.notify(123, builder.build())

                Log.d("BCR", anime?.title ?: "none")
            }
        }
    }
}
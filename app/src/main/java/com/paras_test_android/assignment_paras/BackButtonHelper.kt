package com.paras_test_android.assignment_paras


import android.app.Activity
import android.content.Intent
import android.widget.Button
import android.widget.Toast

/**
 * Clase auxiliar para configurar el botón de retroceso en las actividades para volver al menú principal y evitar la duplicación de código.
 */
object BackButtonHelper {
    fun setupBackButton(activity: Activity, buttonId: Int, destinationActivity: Class<out Activity>) {
        val backButton = activity.findViewById<Button>(buttonId)
        backButton.setOnClickListener {
            Toast.makeText(activity, activity.getString(R.string.toast_back_to_main), Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, destinationActivity)
            activity.startActivity(intent)
            activity.finish()
        }
    }
}

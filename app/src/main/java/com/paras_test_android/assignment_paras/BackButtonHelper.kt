package com.paras_test_android.assignment_paras


import android.app.Activity
import android.content.Intent
import android.widget.Button
import android.widget.Toast

/**
 * Helper class to set up back button in activities to navigate back to main menu
 and avoid code duplication
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

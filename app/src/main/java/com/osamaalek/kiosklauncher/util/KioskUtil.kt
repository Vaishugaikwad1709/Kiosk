package com.osamaalek.kiosklauncher.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.osamaalek.kiosklauncher.ui.AuthActivity

object KioskUtil {

    fun startKioskMode(activity: AppCompatActivity) {
        // Ensure that kiosk mode is started only if it's not already enabled
        if (!isKioskModeEnabled(activity)) {
            lockDevice(activity)
            navigateToAuthActivity(activity, false)
        }
    }

    fun stopKioskMode(activity: AppCompatActivity) {
        // Ensure that kiosk mode is stopped only if it's currently enabled
        if (isKioskModeEnabled(activity)) {
            unlockDevice(activity)
            navigateToAuthActivity(activity, true)
        }
    }

    private fun isKioskModeEnabled(activity: Activity): Boolean {
        val dpm = activity.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        return dpm.isLockTaskPermitted(activity.packageName)
    }

    @SuppressLint("NewApi")
    private fun lockDevice(activity: Activity) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.startLockTask()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("NewApi")
    private fun unlockDevice(activity: Activity) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.stopLockTask()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun navigateToAuthActivity(activity: AppCompatActivity, exitKioskMode: Boolean) {
        val intent = Intent(activity, AuthActivity::class.java).apply {
            putExtra("EXIT_KIOSK_MODE", exitKioskMode)
        }
        // Add flags to prevent back stack issues and ensure a clean transition
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        activity.startActivity(intent)
    }
}

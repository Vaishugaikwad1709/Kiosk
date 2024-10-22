package com.osamaalek.kiosklauncher.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.osamaalek.kiosklauncher.R
import com.osamaalek.kiosklauncher.model.AppInfo

class AppsAdapter(private val appList: List<AppInfo>, private val context: Context) :
    RecyclerView.Adapter<AppsAdapter.AppViewHolder>() {

    inner class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val appIcon: ImageView = itemView.findViewById(R.id.app_icon)
        private val appName: TextView = itemView.findViewById(R.id.app_name)

        fun bind(app: AppInfo) {
            appIcon.setImageDrawable(app.icon) // Change to setImageDrawable if icon is a Drawable
            appName.text = app.label // Ensure name is of type String

            itemView.setOnClickListener {
                // Show a toast message instead of requiring authentication
                Toast.makeText(context, "You are in kiosk mode, you can't access this application now.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_app, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(appList[position])
    }

    override fun getItemCount(): Int = appList.size
}

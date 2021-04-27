package com.revengeos.uidemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.revengeos.uidemo.R

class DummyAdapter() : RecyclerView.Adapter<DummyAdapter.ViewHolder>() {

    private val dummyData: IntArray = IntArray(100) { it }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.findViewById(R.id.dummy_text)

        fun bind(item: Int) {
            textView.text = "Dummy item ${item}"
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.dummy_adapter_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val item = dummyData[position]
        viewHolder.bind(item)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dummyData.size

}

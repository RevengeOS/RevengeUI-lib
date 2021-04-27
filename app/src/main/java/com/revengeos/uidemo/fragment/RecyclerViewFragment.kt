package com.revengeos.uidemo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.revengeos.revengeui.recyclerview.BounceEdgeEffectFactory
import com.revengeos.uidemo.R
import com.revengeos.uidemo.adapter.DummyAdapter

class RecyclerViewFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_recycler_view, container, false)
        val dummyRecyclerView = fragmentView.findViewById<RecyclerView>(R.id.dummy_recycler_view)
        dummyRecyclerView.layoutManager = LinearLayoutManager(fragmentView.context)
        dummyRecyclerView.adapter = DummyAdapter()
        dummyRecyclerView.edgeEffectFactory = BounceEdgeEffectFactory()

        val scrollDataText = fragmentView.findViewById<TextView>(R.id.scroll_data)
        val scrollStatusText = fragmentView.findViewById<TextView>(R.id.scroll_status)
        dummyRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                scrollDataText.text = "dx: $dx dy: $dy"
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val status = when (newState) {
                    RecyclerView.SCROLL_STATE_DRAGGING -> "dragging"
                    RecyclerView.SCROLL_STATE_IDLE -> "idle"
                    RecyclerView.SCROLL_STATE_SETTLING -> "settling"
                    else -> "unknown"
                }
                scrollStatusText.text = status
            }
        })
        // Inflate the layout for this fragment
        return fragmentView
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RecyclerViewFragment().apply {
                }
            }
}
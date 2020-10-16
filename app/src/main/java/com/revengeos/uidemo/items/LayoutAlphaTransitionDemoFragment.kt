package com.revengeos.uidemo.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.revengeos.revengeui.effect.LayoutAlphaTransitionHelper

import com.revengeos.uidemo.R

class LayoutTransitionDemoFragment : Fragment() {

    // False shows front layout
    // True shows back layout
    private var toggleState = false
    private lateinit var frontView : View
    private lateinit var backView : View
    private lateinit var switchButton : View
    private lateinit var alphaTransition : LayoutAlphaTransitionHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val inflatedLayout = inflater.inflate(R.layout.layout_transition_demo, container, false)
        frontView = inflatedLayout.findViewById(R.id.front_view)
        backView = inflatedLayout.findViewById(R.id.back_view)

        alphaTransition = LayoutAlphaTransitionHelper(frontView, backView, toggleState)

        switchButton = inflatedLayout.findViewById(R.id.switch_button)
        switchButton.setOnClickListener { view ->
            toggleState = !toggleState
            alphaTransition.setState(toggleState)

        }
        return inflatedLayout
    }
}

package com.example.final_project.domain.transaction.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.final_project.R
import kotlinx.android.synthetic.main.fragment_montir_on_the_way.*

class MontirOnTheWayFragment : Fragment() {
 override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_montir_on_the_way, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back_button_fragment_otw.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_montirOnTheWayFragment_pop)
            Navigation.findNavController(it).navigate(R.id.action_to_maps)
        }
    }
}
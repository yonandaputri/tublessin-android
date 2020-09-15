package com.example.final_project.domain.montir.fragment

import android.content.ContextWrapper
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.R
import com.example.final_project.domain.montir.MontirViewModel
import com.example.final_project.domain.montir.recycleview.NearbyMontirRecycleAdapter
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_nearby_montir.*

class NearbyMontirFragment : Fragment() {
    val montirViewModel by activityViewModels<MontirViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nearby_montir, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferenceBuilder()

        // Seharusnya pake User Location yang asli
        montirViewModel.requestNearbyMontir(
            Prefs.getDouble("userLocationLatitude", 0.0).toString(),
            Prefs.getDouble("userLocationLongitude", 0.0).toString()
        )

        list_nearby_recycle_view.layoutManager = LinearLayoutManager(activity)
        montirViewModel.getNearbyMontir().observe(viewLifecycleOwner, Observer {
            list_nearby_recycle_view.adapter = NearbyMontirRecycleAdapter(it.list, this)
        })
    }

    private fun preferenceBuilder() {
        Prefs.Builder()
            .setContext(this.activity)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(this.activity?.packageName)
            .setUseDefaultSharedPreference(true)
            .build()
    }
}
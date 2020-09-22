package com.example.final_project.domain.montir.fragment

import android.content.ContextWrapper
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.final_project.R
import com.example.final_project.config.defaultHost
import com.example.final_project.domain.montir.MontirViewModel
import com.example.final_project.domain.transaction.TransactionViewModel
import com.example.final_project.domain.transaction.model.Transaction
import com.example.final_project.domain.transaction.model.TransactionLocation
import com.example.final_project.domain.user.UserViewModel
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_montir_detail.*

class MontirDetailFragment : Fragment() {
    val montirViewModel by activityViewModels<MontirViewModel>()
    val transactionViewModel by activityViewModels<TransactionViewModel>()
    val userViewModel by activityViewModels<UserViewModel>()
    var transactionId:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_montir_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferenceBuilder()

        val userId = Prefs.getString("id", "0")
        var userFirstname = ""
        var userLatitude = 0.0
        var userLongitude = 0.0
        userViewModel.requestGetUserDetail(userId)
        userViewModel.getUserAccountInfo().observe(viewLifecycleOwner, Observer {
            userFirstname = it.result.profile.firstname
            userLatitude = it.result.profile.location.latitude
            userLongitude = it.result.profile.location.longitude
        })

        val montirId = Prefs.getString("selectedMontirId", "0")
        var montirFirstname = ""
        montirViewModel.requestMontirDetailById(montirId)
        montirViewModel.getMontirDetail().observe(viewLifecycleOwner, Observer {

            val url = "${defaultHost()}montir/file/image/${it.result.profile.imageURL}"
            val glideUrl = GlideUrl(
                url,
                LazyHeaders.Builder()
                    .addHeader("Authorization", "Bearer ${Prefs.getString("token", "0")}")
                    .build()
            )
            Glide.with(this)
                .load(glideUrl)
                .circleCrop()
                .into(photo_montir_detail)

            firstname_montir_detail.text = it.result.profile.firstname
            lastname_montir_detail.text = it.result.profile.lastname
            phone_montir_detail.text = it.result.profile.phone_number
            rating_montir_detail.text = Prefs.getString("montirAverageRating", "5.0")

            montirFirstname = it.result.profile.firstname
        })

        // Button untuk post new transaction ke backend
        order_button_montir_detail.setOnClickListener {
            Prefs.putBoolean("statusOrderan", true) // status orderan true
            order_button_montir_detail.isVisible = false
            cancel_button_montir_detail.isVisible = true
            chat_button_montir_detail.isVisible = true
//            Navigation.findNavController(it).navigate(R.id.action_montirDetailFragment_pop)
//            Navigation.findNavController(it).navigate(R.id.action_to_montir_on_the_way)
            transactionViewModel.PostNewTransaction(
                Transaction(
                    id_montir = montirId.toString(),
                    id_user = userId,
                    montir_firstname = montirFirstname,
                    user_firstname = userFirstname,
                    location = TransactionLocation(
                        latitude = userLatitude, longitude = userLongitude
                    )
                )
            )
        }

        cancel_button_montir_detail.setOnClickListener {
            transactionViewModel.UpdateStatusTransaction(
                Transaction(
                    status = "3",
                    location = TransactionLocation()
                ), transactionId?:"0"
            )
            Prefs.putBoolean("statusOrderan", false) // status orderan true
            order_button_montir_detail.isVisible = true
            cancel_button_montir_detail.isVisible = false
            chat_button_montir_detail.isVisible = false
            Navigation.findNavController(it).navigate(R.id.action_montirDetailFragment_pop)
            Navigation.findNavController(it).navigate(R.id.action_to_maps)
        }

        chat_button_montir_detail.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_montirDetailFragment_to_chatFragment)
        }

        transactionViewModel.transactionList().observe(viewLifecycleOwner, Observer {
            transactionId = it.Results?.result?.id
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

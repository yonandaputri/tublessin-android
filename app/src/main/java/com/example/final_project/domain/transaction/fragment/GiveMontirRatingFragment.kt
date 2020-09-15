package com.example.final_project.domain.transaction.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.final_project.R
import com.example.final_project.config.defaultHost
import com.example.final_project.domain.montir.MontirViewModel
import com.example.final_project.domain.montir.model.MontirRating
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_give_montir_rating.*
import kotlinx.android.synthetic.main.fragment_montir_detail.*

class GiveMontirRatingFragment : Fragment() {
    var totalStar = 0
    private val montirViewModel = MontirViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_give_montir_rating, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fiveStarButton()

        val montirId = Prefs.getString("finishedOrderMontirId", "0")
        montirViewModel.requestMontirDetailById(montirId)
        montirViewModel.getMontirDetail().observe(viewLifecycleOwner, Observer {
            firstname_review_fragment.text = it.result.profile.firstname
            lastname_review_fragment.text = it.result.profile.lastname
            loadMontirProfilePicture(it.result.profile.imageURL)
        })

        button_submit_review_fragment.setOnClickListener {
            montirViewModel.postNewMontirRating(
                montirId.toString(), MontirRating(
                    rating = totalStar,
                    rater_id = Prefs.getString("id", "0"),
                    review = "bagus Sekaliiiiii" // jangan di hardcode, bikin box ulasan
                )
            )
            Navigation.findNavController(it).navigate(R.id.action_giveMontirRatingFragment_pop)
        }
    }

    private fun loadMontirProfilePicture(imageName: String) {
        val url = "${defaultHost()}montir/file/image/${imageName}"
        val glideUrl = GlideUrl(
            url,
            LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer ${Prefs.getString("token", "0")}")
                .build()
        )

        Glide.with(this)
            .load(glideUrl)
            .circleCrop()
            .into(photo_review_fragment)
    }

    private fun fiveStarButton() {
        oneStar_review_fragment.setOnClickListener {
            totalStar = 1
            rating_message_review_fragment.text = "Aku tuh gabisa diginiin"
            fill_oneStar_review_fragment.isVisible = true
            fill_twoStar_review_fragment.isVisible = false
            fill_threeStar_review_fragment.isVisible = false
            fill_fourStar_review_fragment.isVisible = false
            fill_fiveStar_review_fragment.isVisible = false
        }
        twoStar_review_fragment.setOnClickListener {
            totalStar = 2
            rating_message_review_fragment.text = "Yaa lumayan dari pada lu manyun"
            fill_oneStar_review_fragment.isVisible = true
            fill_twoStar_review_fragment.isVisible = true
            fill_threeStar_review_fragment.isVisible = false
            fill_fourStar_review_fragment.isVisible = false
            fill_fiveStar_review_fragment.isVisible = false
        }
        threeStar_review_fragment.setOnClickListener {
            totalStar = 3
            rating_message_review_fragment.text = "Mantabeee!"
            fill_oneStar_review_fragment.isVisible = true
            fill_twoStar_review_fragment.isVisible = true
            fill_threeStar_review_fragment.isVisible = true
            fill_fourStar_review_fragment.isVisible = false
            fill_fiveStar_review_fragment.isVisible = false
        }
        fourStar_review_fragment.setOnClickListener {
            totalStar = 4
            rating_message_review_fragment.text = "GGWP Sangat memuaskannn"
            fill_oneStar_review_fragment.isVisible = true
            fill_twoStar_review_fragment.isVisible = true
            fill_threeStar_review_fragment.isVisible = true
            fill_fourStar_review_fragment.isVisible = true
            fill_fiveStar_review_fragment.isVisible = false
        }
        fiveStar_review_fragment.setOnClickListener {
            totalStar = 5
            rating_message_review_fragment.text = "Pelayanan memuaskan!! Jadi pengen bocor lagi"
            fill_oneStar_review_fragment.isVisible = true
            fill_twoStar_review_fragment.isVisible = true
            fill_threeStar_review_fragment.isVisible = true
            fill_fourStar_review_fragment.isVisible = true
            fill_fiveStar_review_fragment.isVisible = true
        }
    }
}
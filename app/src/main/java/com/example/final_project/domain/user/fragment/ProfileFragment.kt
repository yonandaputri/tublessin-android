package com.example.final_project.domain.user.fragment

import android.app.Activity
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.final_project.R
import com.example.final_project.config.defaultHost
import com.example.final_project.domain.user.UserViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.email_profile_view
import kotlinx.android.synthetic.main.fragment_profile.logoutClicked
import kotlinx.android.synthetic.main.fragment_profile.profile_picture_view
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pub.devrel.easypermissions.EasyPermissions

class ProfileFragment : Fragment(), View.OnClickListener {

    private val userViewModel = UserViewModel()
    lateinit var imageFileChoosed: MultipartBody.Part
    private lateinit var userId: String
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logoutClicked.setOnClickListener(this)
        updateClicked.setOnClickListener(this)
        navController = Navigation.findNavController(view)

        Prefs.Builder()
            .setContext(this.activity)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(this.activity?.packageName)
            .setUseDefaultSharedPreference(true)
            .build()

        userId = Prefs.getString("id", "0")
        userViewModel.requestGetUserDetail(userId)
        userViewModel.getUserAccountInfo().observe(viewLifecycleOwner, Observer {
            first_name.text = "${it.result.profile.firstname} "
            last_name.text = it.result.profile.lastname
            phone_number.text = it.result.profile.phone_number
            email_profile_view.text = it.result.profile.email

            val url = "${defaultHost()}user/file/image/${it.result.profile.imageURL}"
            val glideUrl = GlideUrl(
                url,
                LazyHeaders.Builder()
                    .addHeader("Authorization", "Bearer ${Prefs.getString("token", "0")}")
                    .build()
            )
            Glide.with(this)
                .load(glideUrl)
                .circleCrop().into(profile_picture_view)
        })

        camera_upload_button_view.setOnClickListener {
            if (EasyPermissions.hasPermissions(
                    requireActivity(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                ImagePicker.with(this) // buat ngambil gambar, pake library implementation 'com.github.dhaval2404:imagepicker:1.7.4'
                    .compress(1024)                     //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(
                        1080,
                        1080
                    )     //Final image resolution will be less than 1080 x 1080(Optional)
                    .cropSquare()
                    .start(300)

            } else {
                EasyPermissions.requestPermissions(
                    this,
                    "This application need your permission to access photo gallery.",
                    991,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 300 && resultCode == Activity.RESULT_OK) {
            val getFile = ImagePicker.getFile(data)!! // dapetin data gambar yang barusan dipilih
            Glide.with(this).load(getFile).circleCrop()
                .into(profile_picture_view) // Buat nampilin image yang dipilih, pake library glide

            val requestBody = RequestBody.create("multipart".toMediaTypeOrNull(), getFile)
            imageFileChoosed = MultipartBody.Part.createFormData("file", getFile.name, requestBody)
            userViewModel.uploadUserProfilePicture(userId, imageFileChoosed)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            logoutClicked -> {
                Prefs.remove("id")
                Prefs.remove("token")
                activity?.finish()
            }
            updateClicked -> {
                navController.navigate(R.id.action_profileFragment_to_updateUserProfileFragment)
            }
        }
    }
}

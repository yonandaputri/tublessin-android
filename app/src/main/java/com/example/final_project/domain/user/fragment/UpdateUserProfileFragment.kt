package com.example.final_project.domain.user.fragment

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.final_project.R
import com.example.final_project.domain.user.UserProfileUpdated
import com.example.final_project.domain.user.UserViewModel
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_update_user_profile.*

class UpdateUserProfileFragment : Fragment(), View.OnClickListener {

    private val userViewModel = UserViewModel()
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cancelUpdateProfileButton.setOnClickListener(this)
        updateProfileButton.setOnClickListener(this)

        userId = Prefs.getString("id", "0")

        userViewModel.requestGetUserDetail(userId)
        userViewModel.getUserAccountInfo().observe(viewLifecycleOwner, Observer { it ->
            editFirstNameUpdate.text = Editable.Factory.getInstance().newEditable(it.result.profile.firstname)
            editLastNameUpdate.text = Editable.Factory.getInstance().newEditable(it.result.profile.lastname)
            if (it.result.profile.gender == "L") {
                editGenderUpdate.setSelection(0)
            } else {
                editGenderUpdate.setSelection(1)
            }
            editEmailUpdate.text = Editable.Factory.getInstance().newEditable(it.result.profile.email)
            editMobileUpdate.text = Editable.Factory.getInstance().newEditable(it.result.profile.phone_number)
        })
    }

    override fun onClick(v: View?) {
        when (v) {
            cancelUpdateProfileButton -> {
                this.activity?.onBackPressed()
            }
            updateProfileButton -> {
                userViewModel.updateUserProfile(
                    userId,
                    UserProfileUpdated(
                        firstname = editFirstNameUpdate.text.toString(),
                        lastname = editLastNameUpdate.text.toString(),
                        gender = editGenderUpdate.selectedItem.toString(),
                        email = editEmailUpdate.text.toString(),
                        phone_number = editMobileUpdate.text.toString()
                    )
                )
                this.activity?.onBackPressed()
            }
        }
    }
}
package com.example.final_project.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.example.final_project.R
import com.example.final_project.domain.user.UserAccount
import com.example.final_project.domain.user.UserLocation
import com.example.final_project.domain.user.UserProfile
import com.example.final_project.domain.user.UserViewModel
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController
    val userViewModel by activityViewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        cirRegisterButton.setOnClickListener(this)
        back_button_register.setOnClickListener(this)
        haveAccount.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            cirRegisterButton -> {
                userViewModel.registerUser(
                    UserAccount(
                        username = editTextUsernameRegister.text.toString(),
                        password = editTextPasswordRegister.text.toString(),
                        profile = UserProfile(
                            firstname = editTextFirstName.text.toString(),
                            lastname = editTextLastName.text.toString(),
                            gender = editGender.selectedItem.toString(),
                            phone_number = editTextMobile.text.toString(),
                            email = editTextEmail.text.toString(),
                            location = UserLocation()
                        )
                    )
                )
                userViewModel.getUserAccountInfo().observe(this, Observer {
                    if (it.code == "200") {
                        navController.navigate(R.id.action_registerFragment2_to_successRegisterFragment)
                    } else if (it.code == "900") {
                        Toast.makeText(
                            v?.context, "Register Gagal. Username sudah digunakan",
                            Toast.LENGTH_LONG
                        ).show()
                    } else if (it.code == "800") {
                        Toast.makeText(
                            v?.context, "Register Gagal. Nomor Telefon Sudah Digunakan",
                            Toast.LENGTH_LONG
                        ).show()
                    } else if (it.code == "700") {
                        Toast.makeText(
                            v?.context, "Register Gagal. Email Sudah Digunakan",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }
            back_button_register -> {
                activity?.finish()
            }
            haveAccount -> {
                activity?.finish()
            }
        }
    }
}

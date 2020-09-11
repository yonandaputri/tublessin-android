package com.example.final_project.domain.login

class Login(
    val message: String = "",
    val token: String = "",
    val account: AccountInfo
)

class AccountInfo(
    val id: Int = 0,
    val username: String = "",
    val password: String = "",
    val status_account: String = ""
)
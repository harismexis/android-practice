package com.harismexis.breakingbad.wcoroutines.androiddevs.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harismexis.breakingbad.wcoroutines.androiddevs.overview.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository
): ViewModel() {

    // blocking the UI thread when making the network request
    fun loginBlockingUiThread(username: String, token: String) {
        val jsonBody = "{ username: \"$username\", token: \"$token\"}"
        loginRepository.makeLoginRequestBlocking(jsonBody)
    }

    fun loginMoveOffUiThread(username: String, token: String) {
        // Create a new coroutine to move the execution off the UI thread
        viewModelScope.launch(Dispatchers.IO) {
            val jsonBody = "{ username: \"$username\", token: \"$token\"}"
            loginRepository.makeLoginRequest(jsonBody)
        }
    }

    fun loginNoErrorHandling(username: String, token: String) {
        // Create a new coroutine on the UI thread
        viewModelScope.launch {
            val jsonBody = "{ username: \"$username\", token: \"$token\"}"
            // Make the network call and suspend execution until it finishes
            val result = loginRepository.makeLoginRequest(jsonBody)
            // Display result of the network request to the user
            when (result) {
                is Result.Success<LoginResponse> -> {} // Happy path
                else -> {} // Show error in UI
            }
        }
    }

    fun login(username: String, token: String) {
        viewModelScope.launch {
            val jsonBody = "{ username: \"$username\", token: \"$token\"}"
            val result = try {
                loginRepository.makeLoginRequest(jsonBody)
            } catch(e: Exception) {
                Result.Error(Exception("Network request failed"))
            }
            when (result) {
                is Result.Success<LoginResponse> -> {} // Happy path
                else -> {} // Show error in UI
            }
        }
    }

}
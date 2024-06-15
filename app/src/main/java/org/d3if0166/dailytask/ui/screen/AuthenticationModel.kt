package org.d3if0166.dailytask.ui.screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.d3if0166.dailytask.model.User
import org.d3if0166.dailytask.network.ApiStatus
import org.d3if0166.dailytask.network.UserApi

class AuthenticationModel: ViewModel() {
    var data = mutableStateOf(emptyList<User>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set

    fun saveData(name: String, email: String, photoUrl: String) {
        Log.d("Sign up...", "...")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = UserApi.service.addUser(
//                    userId.toRequestBody("text/plain".toMediaTypeOrNull()),
                    name.toRequestBody("text/plain".toMediaTypeOrNull()),
                    email.toRequestBody("text/plain".toMediaTypeOrNull()),
                    photoUrl.toRequestBody("text/plain".toMediaTypeOrNull()),
                )
                Log.d("Sign up...", "...")
                if (result.status == "success")
                    Log.d("Sign up Success", "horee")
                else
                    throw Exception(result.message)
            } catch (e: Exception) {
                Log.d("AuthenticationModel", "Failure: ${e.message}")
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }
}
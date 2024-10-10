package tw.edu.tp.taivs.classconnect.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(signedUpUsername: String?) : ViewModel() {
    private val _uiStateFlow = MutableStateFlow(
        LoginUiState(
            role = if (signedUpUsername != null) LoginRole.Teacher else LoginRole.Student,
            username = signedUpUsername ?: ""
        )
    )
    val uiStateFlow = _uiStateFlow.asStateFlow()

    init {
        if (signedUpUsername != null) {
            viewModelScope.launch {
                uiStateFlow.value.snackbarHostState.showSnackbar("Signed up successfully.")
            }
        }
    }

    fun changeRole(value: LoginRole) {
        _uiStateFlow.update { it.copy(role = value) }
    }

    fun changeUsername(value: String) {
        _uiStateFlow.update { it.copy(username = value, usernameError = null) }
    }

    fun changePassword(value: String) {
        _uiStateFlow.update { it.copy(password = value, passwordError = null) }
    }

    fun logIn(onSuccess: () -> Unit) {
        _uiStateFlow.update {
            it.copy(
                usernameError = if (it.username.isEmpty()) "Required." else null,
                passwordError = if (it.password.isEmpty()) "Required." else null
            )
        }
        if (uiStateFlow.value
                .run { listOf(usernameError, passwordError) }
                .any { it != null }
        ) return
        onSuccess()
    }
}
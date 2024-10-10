package tw.edu.tp.taivs.classconnect.ui.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {
    private val _uiStateFlow = MutableStateFlow(LoginUiState())
    val uiStateFlow = _uiStateFlow.asStateFlow()

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
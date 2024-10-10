package tw.edu.tp.taivs.classconnect.ui.signup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignupViewModel : ViewModel() {
    private val _uiStateFlow = MutableStateFlow(SignupUiState())
    val uiStateFlow = _uiStateFlow.asStateFlow()

    fun changeFirstName(value: String) {
        _uiStateFlow.update { it.copy(firstName = value, firstNameError = null) }
    }

    fun changeLastName(value: String) {
        _uiStateFlow.update { it.copy(lastName = value, lastNameError = null) }
    }

    fun changeUsername(value: String) {
        _uiStateFlow.update { it.copy(username = value, usernameError = null) }
    }

    fun changePassword(value: String) {
        _uiStateFlow.update { it.copy(password = value, passwordError = null) }
    }

    fun changeConfirmation(value: String) {
        _uiStateFlow.update { it.copy(confirmation = value, confirmationError = null) }
    }

    fun signUp(onSuccess: (username: String) -> Unit) {
        _uiStateFlow.update {
            it.copy(
                firstNameError = if (it.firstName.isEmpty()) "Required." else null,
                lastNameError = if (it.lastName.isEmpty()) "Required." else null,
                usernameError = if (it.username.isEmpty()) "Required." else null,
                passwordError = if (it.password.isEmpty()) "Required." else null,
                confirmationError = when {
                    it.confirmation.isEmpty() -> "Required."
                    it.confirmation != it.password -> "Passwords don't match."
                    else -> null
                }
            )
        }
        uiStateFlow.value.run {
            if (listOf(
                    firstNameError,
                    lastNameError,
                    usernameError,
                    passwordError,
                    confirmationError
                ).any { it != null }
            ) return
            onSuccess(username)
        }
    }
}
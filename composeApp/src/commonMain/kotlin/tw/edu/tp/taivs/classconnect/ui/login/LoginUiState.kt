package tw.edu.tp.taivs.classconnect.ui.login

data class LoginUiState(
    val role: LoginRole = LoginRole.Student,
    val username: String = "",
    val usernameError: String? = null,
    val password: String = "",
    val passwordError: String? = null
)
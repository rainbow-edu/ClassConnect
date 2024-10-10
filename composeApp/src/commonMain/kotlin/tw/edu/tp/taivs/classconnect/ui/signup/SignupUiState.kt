package tw.edu.tp.taivs.classconnect.ui.signup

data class SignupUiState(
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastName: String = "",
    val lastNameError: String? = null,
    val username: String = "",
    val usernameError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val confirmation: String = "",
    val confirmationError: String? = null
)
package tw.edu.tp.taivs.classconnect.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import classconnect.composeapp.generated.resources.Res
import classconnect.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun SignupScreen(
    onSignUp: (username: String) -> Unit,
    onLogInClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = viewModel<SignupViewModel>()
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    Scaffold(modifier) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Image(painterResource(Res.drawable.logo), null)
            Text(text = "ClassConnect", style = MaterialTheme.typography.displayMedium)
            Text(text = "Sign Up", style = MaterialTheme.typography.displaySmall)
            Column(Modifier.width(TextFieldDefaults.MinWidth)) {
                Row {
                    TextField(
                        value = uiState.firstName,
                        onValueChange = viewModel::changeFirstName,
                        isError = uiState.firstNameError != null,
                        supportingText = { Text(uiState.firstNameError ?: "") },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        singleLine = true,
                        label = { Text("First name") },
                        modifier = Modifier.weight(1F)
                    )
                    TextField(
                        value = uiState.lastName,
                        onValueChange = viewModel::changeLastName,
                        isError = uiState.lastNameError != null,
                        supportingText = { Text(uiState.lastNameError ?: "") },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        singleLine = true,
                        label = { Text("Last name") },
                        modifier = Modifier.weight(1F)
                    )
                }
                TextField(
                    value = uiState.username,
                    onValueChange = viewModel::changeUsername,
                    isError = uiState.usernameError != null,
                    supportingText = { Text(uiState.usernameError ?: "") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Ascii,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    label = { Text("Username") }
                )
                TextField(
                    value = uiState.password,
                    onValueChange = viewModel::changePassword,
                    isError = uiState.passwordError != null,
                    supportingText = { Text(uiState.passwordError ?: "") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    label = { Text("Password") }
                )
                TextField(
                    value = uiState.confirmation,
                    onValueChange = viewModel::changeConfirmation,
                    isError = uiState.confirmationError != null,
                    supportingText = { Text(uiState.confirmationError ?: "") },
                    keyboardActions = KeyboardActions(onDone = { viewModel.signUp(onSignUp) }),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    label = { Text("Retype password") }
                )
            }
            Button({ viewModel.signUp(onSignUp) }) {
                Text("Sign up")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Already have an account?")
                TextButton(onLogInClick) {
                    Text("Log in")
                }
            }
        }
    }
}
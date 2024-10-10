package tw.edu.tp.taivs.classconnect.ui.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SnackbarHost
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLogIn: () -> Unit,
    onSignUpClick: () -> Unit,
    signedUpUsername: String?,
    modifier: Modifier = Modifier
) {
    val viewModel = viewModel { LoginViewModel(signedUpUsername) }
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    Scaffold(
        snackbarHost = { SnackbarHost(uiState.snackbarHostState) },
        modifier = modifier
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Text(text = "ClassConnect", style = MaterialTheme.typography.displayLarge)
            Text(text = "Log In", style = MaterialTheme.typography.displayMedium)
            SingleChoiceSegmentedButtonRow {
                LoginRole.entries.forEachIndexed { index, role ->
                    SegmentedButton(
                        selected = uiState.role == role,
                        onClick = { viewModel.changeRole(role) },
                        shape = SegmentedButtonDefaults.itemShape(index, LoginRole.entries.size)
                    ) {
                        Text(role.name)
                    }
                }
            }
            Column(Modifier.width(TextFieldDefaults.MinWidth)) {
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
                    keyboardActions = KeyboardActions(onDone = { viewModel.logIn(onLogIn) }),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    label = { Text("Password") }
                )
            }
            Button({ viewModel.logIn(onLogIn) }) {
                Text("Log in")
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(ButtonDefaults.MinHeight + 8.dp)
            ) {
                Text("Don't have an account?")
                AnimatedVisibility(uiState.role == LoginRole.Teacher) {
                    TextButton(onSignUpClick) {
                        Text("Sign up")
                    }
                }
            }
            AnimatedVisibility(uiState.role == LoginRole.Student) {
                Text("Contact your teacher for support.")
            }
        }
    }
}
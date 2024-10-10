package tw.edu.tp.taivs.classconnect.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import tw.edu.tp.taivs.classconnect.ui.login.LoginScreen
import tw.edu.tp.taivs.classconnect.ui.signup.SignupScreen

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController, LoginRoute::class, modifier) {
        composable<LoginRoute> {
            val route = it.toRoute<LoginRoute>()
            LoginScreen({}, { navController.push(SignupRoute) }, route.signedUpUsername)
        }
        composable<SignupRoute> {
            SignupScreen(
                onSignUp = {
                    navController.pop<LoginRoute>(true)
                    navController.push(LoginRoute(it))
                },
                onLogInClick = { navController.pop<LoginRoute>() }
            )
        }
    }
}

private fun <T : Any> NavController.push(route: T) {
    navigate(route) { launchSingleTop = true }
}

private inline fun <reified T : Any> NavController.pop(inclusive: Boolean = false) {
    popBackStack<T>(inclusive)
}

@Serializable
private data class LoginRoute(val signedUpUsername: String? = null)

@Serializable
private data object SignupRoute
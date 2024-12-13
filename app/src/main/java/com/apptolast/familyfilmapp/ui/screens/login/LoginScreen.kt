package com.apptolast.familyfilmapp.ui.screens.login

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.apptolast.familyfilmapp.R
import com.apptolast.familyfilmapp.navigation.Routes
import com.apptolast.familyfilmapp.ui.screens.login.components.AlertRecoverPassDialog
import com.apptolast.familyfilmapp.ui.screens.login.components.LoginMainContent
import com.apptolast.familyfilmapp.ui.screens.login.uistates.LoginUiState
import com.apptolast.familyfilmapp.ui.screens.login.uistates.RecoverPassState
import com.apptolast.familyfilmapp.ui.theme.FamilyFilmAppTheme
import com.apptolast.familyfilmapp.utils.Constants
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import timber.log.Timber
import kotlin.random.Random

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    val snackBarHostState = remember { SnackbarHostState() }
    val loginUiState by viewModel.loginState.collectAsStateWithLifecycle()
    val recoverPassUIState by viewModel.recoverPassState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = loginUiState) {
        if (loginUiState.isLogged) {
            navController.navigate(Routes.Home.routes) {
                popUpTo(Routes.Login.routes) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    if (!loginUiState.errorMessage?.error.isNullOrBlank()) {
        LaunchedEffect(loginUiState.errorMessage) {
            snackBarHostState.showSnackbar(
                loginUiState.errorMessage!!.error,
                "Close",
                true,
                SnackbarDuration.Long,
            )
        }
    }

    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (intent != null) {
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(intent)
//                    viewModel.handleGoogleSignInResult(task.result as GoogleSignInAccount)
                }
            } else {
                Timber.d("$result")
            }
        }

    Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            MovieAppLoginScreen()
//            LoginContent(
//                loginUiState = loginUiState,
//                recoverPassState = recoverPassUIState,
//                onClickLogin = { email, pass ->
//                    when (loginUiState.screenState) {
//                        is LoginRegisterState.Login -> viewModel.login(email, pass)
//                        is LoginRegisterState.Register -> viewModel.register(email, pass)
//                    }
//                },
//                onClickScreenState = viewModel::changeScreenState,
//                onClickGoogleButton = {
//                    startForResult.launch(
//                        viewModel.googleSignInClient.signInIntent,
//                    )
//                },
//                onCLickRecoverPassword = viewModel::recoverPassword,
//                onRecoveryPassUpdate = viewModel::updateRecoveryPasswordState,
//            )
        }
    }
}

@Composable
fun MovieAppLoginScreen() {
    val backgroundImage = painterResource(id = R.drawable.movie_background) // Cambia a tu imagen de fondo
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Lista de imágenes en recursos drawable
    val imageList = listOf(
        R.drawable.movie_background,
        R.drawable.movie_background_2,
        R.drawable.movie_background_3,
        R.drawable.movie_background_4,
        R.drawable.movie_background_5,
        R.drawable.movie_background_6,
        R.drawable.movie_background_7,
        R.drawable.movie_background_8,
        R.drawable.movie_background_9,
        R.drawable.movie_background_10,
        R.drawable.movie_background_11,
        R.drawable.movie_background_12,
    )

    // Seleccionar una imagen aleatoria al iniciar la composición
    val randomImageId = remember { imageList[Random.nextInt(imageList.size)] }

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        // Fondo con imagen temática de películas
        Image(
            painter = painterResource(id = randomImageId),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )

        // Capa con gradiente para mayor legibilidad
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0x7C000000), Color(0xDC000000)),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY,
                    ),
                ),
        )

        // Contenido del Login
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {

            // Logo
            Image(
                painter = painterResource(R.drawable.logo_film_family),
                contentDescription = stringResource(R.string.login_snail_logo),
                modifier = Modifier
                    .width(130.dp)
                    .padding(12.dp),
            )

            // Título
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier.padding(bottom = 8.dp),
            )

            // Eslogan o mensaje adicional
            Text(
                text = stringResource(R.string.login_text_app_subtitle),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.LightGray,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier.padding(bottom = 24.dp),
            )

            // Campo de Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                placeholder = { Text("Ingrese su correo electrónico") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
//                colors = TextFieldDefaults.outlinedTextFieldColors(
//                    focusedBorderColor = Color.White,
//                    unfocusedBorderColor = Color.LightGray,
//                    textColor = Color.White,
//                    placeholderColor = Color.LightGray,
//                    cursorColor = Color.White
//                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Campo de Contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                placeholder = { Text("Ingrese su contraseña") },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = icon,
                            contentDescription = "Toggle Password Visibility",
                            tint = Color.White,
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
//                colors = TextFieldDefaults.outlinedTextFieldColors(
//                    focusedBorderColor = Color.White,
//                    unfocusedBorderColor = Color.LightGray,
//                    textColor = Color.White,
//                    placeholderColor = Color.LightGray,
//                    cursorColor = Color.White
//                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de Login
            Button(
                onClick = { /* Acción del botón */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF50057)),
            ) {
                Text(
                    text = "Iniciar Sesión",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Texto de registro
            Text(
                text = "¿No tienes cuenta? Regístrate",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                modifier = Modifier.clickable { /* Navega a la pantalla de registro */ },
            )
        }
    }
}

@Composable
fun LoginContent(
    loginUiState: LoginUiState,
    recoverPassState: RecoverPassState,
    onClickLogin: (String, String) -> Unit,
    onClickGoogleButton: () -> Unit,
    onClickScreenState: () -> Unit,
    onCLickRecoverPassword: (String) -> Unit,
    onRecoveryPassUpdate: (RecoverPassState) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .alpha(
                when (loginUiState.isLoading) {
                    true -> 0.4f
                    false -> 1f
                },
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LoginMainContent(
            loginUiState = loginUiState,
            onClick = onClickLogin,
        )

        Row(
            modifier = Modifier
                .padding(6.dp)
                .clickable { onClickScreenState() },
        ) {
            Text(
                text = stringResource(loginUiState.screenState.accountText),
                modifier = Modifier.padding(end = 4.dp),
            )

            // TODO: Create Typography for this text.
            Text(
                text = stringResource(loginUiState.screenState.signText),
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.Bold,
            )
        }

        Text(
            modifier = Modifier.clickable {
                onRecoveryPassUpdate(
                    recoverPassState.copy(
                        isDialogVisible = true,
                        emailErrorMessage = null,
                        errorMessage = null,
                    ),
                )
            },
            text = stringResource(R.string.login_text_forgot_your_password),
            color = MaterialTheme.colorScheme.outline,
        )

//        Button(
//            onClick = onClickGoogleButton,
//            modifier = Modifier.padding(vertical = 10.dp),
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically,
//            ) {
//                Image(
//                    painter = painterResource(R.drawable.logo_google),
//                    contentDescription = stringResource(R.string.login_icon_google),
//                    modifier = Modifier
//                        .size(30.dp)
//                        .padding(end = 6.dp),
//                )
//                Text(stringResource(R.string.login_text_sign_in_with_google))
//            }
//        }
    }

    if (loginUiState.isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.testTag(Constants.CIRCULAR_PROGRESS_INDICATOR),
        )
    }

    if (recoverPassState.isDialogVisible) {
        AlertRecoverPassDialog(
            onCLickSend = onCLickRecoverPassword,
            recoverPassState = recoverPassState,
            dismissDialog = {
                onRecoveryPassUpdate(
                    recoverPassState.copy(
                        isDialogVisible = false,
                    ),
                )
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    FamilyFilmAppTheme {
        LoginContent(
            loginUiState = LoginUiState(),
            recoverPassState = RecoverPassState(),
            onClickLogin = { _, _ -> },
            onCLickRecoverPassword = {},
            onClickGoogleButton = {},
            onClickScreenState = {},
            onRecoveryPassUpdate = {},
        )
    }
}

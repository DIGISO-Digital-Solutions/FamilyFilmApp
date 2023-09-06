package com.digitalsolution.familyfilmapp.ui.screens.login.usecases

import com.digitalsolution.familyfilmapp.BaseUseCase
import com.digitalsolution.familyfilmapp.model.local.UserData
import com.digitalsolution.familyfilmapp.repositories.LoginRepository
import com.digitalsolution.familyfilmapp.ui.screens.login.LoginScreenState
import com.digitalsolution.familyfilmapp.ui.screens.login.LoginUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class LoginWithGoogleUseCase @Inject constructor(
    private val repository: LoginRepository,
) : BaseUseCase<String, Flow<LoginUiState>>() {

    override suspend fun execute(parameters: String): Flow<LoginUiState> = channelFlow {
        send(
            LoginUiState().copy(
                screenState = LoginScreenState.Login(),
                isLoading = true
            )
        )

        repository.loginWithGoogle(parameters)
            .catch { exception ->
                send(
                    LoginUiState().copy(
                        screenState = LoginScreenState.Login(),
                        isLoading = false,
                        errorMessage = exception.message
                    )
                )
            }
            .collect { result ->
                result.fold(
                    onSuccess = { authResult ->
                        send(
                            LoginUiState().copy(
                                screenState = LoginScreenState.Login(),
                                userData = UserData(
                                    email = authResult.user?.email ?: "",
                                    pass = ""
                                ),
                                isLogged = authResult.user != null,
                                isLoading = false
                            )
                        )
                    },
                    onFailure = {
                        send(
                            LoginUiState().copy(
                                screenState = LoginScreenState.Login(),
                                isLoading = false,
                                errorMessage = it.message ?: "Login Error"
                            )
                        )
                    }
                )
            }
    }
}

package com.digitalsolution.familyfilmapp.ui.screens.login

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun TrailingIconPassword(isPasswordVisible: Boolean, passwordToVisible: () -> Unit) {
    val image =
        if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
    val description = if (isPasswordVisible) "Hide password" else "Show password"

    IconButton(onClick = passwordToVisible) {
        Icon(
            imageVector = image,
            contentDescription = description
        )
    }
}

package com.digitalsolution.familyfilmapp.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun CustomSpacer(size: Dp) {
    Spacer(modifier = Modifier.size(size))
}

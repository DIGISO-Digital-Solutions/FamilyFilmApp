package com.apptolast.familyfilmapp.ui.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.outlined.CheckBoxOutlineBlank
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.apptolast.familyfilmapp.R
import com.apptolast.familyfilmapp.model.local.Group
import com.apptolast.familyfilmapp.ui.theme.FamilyFilmAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailDialog(onDismissRequest: () -> Unit, group: List<Group>, checked: Boolean, onCheck: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = stringResource(id = R.string.dialog_add_movies_description))
                group.forEach { group ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Icon(imageVector = Icons.Filled.Groups, contentDescription = "")
                        Text(text = group.name)
                        IconToggleButton(checked = checked, onCheckedChange = { onCheck() }) {
                            if (checked) {
                                Icon(Icons.Filled.CheckBox, contentDescription = "checked")
                            } else {
                                Icon(Icons.Outlined.CheckBoxOutlineBlank, contentDescription = "uncheked")
                            }
                        }
                    }
                    // Row para el switch "vista-para ver"
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(text = if (checked) "vista" else "para ver", color = Color.White)

                        // Switch personalizado
                        Switch(
                            checked = checked,
                            onCheckedChange = { onCheck() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color(0xFFF5B300),
                                uncheckedThumbColor = Color(0xFFF5B300),
                                checkedTrackColor = Color.White,
                                uncheckedTrackColor = Color.White,
                            ),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun BasicDialogPreview() {
    FamilyFilmAppTheme {
        BasicDialog(
            title = "title",
            description = "description",
            confirmButtonText = "ok",
            cancelButtonText = "cancel",
            onConfirm = {},
            onDismiss = {},
        )
    }
}

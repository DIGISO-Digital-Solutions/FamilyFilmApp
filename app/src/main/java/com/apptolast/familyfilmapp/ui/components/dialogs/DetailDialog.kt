package com.apptolast.familyfilmapp.ui.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.apptolast.familyfilmapp.model.local.Group
import com.apptolast.familyfilmapp.model.local.User
import com.apptolast.familyfilmapp.ui.screens.detail.MovieDialogType
import com.apptolast.familyfilmapp.ui.theme.FamilyFilmAppTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailDialog(
    group: List<Group>,
    me: User,
    selectedMovieId: Int,
    dialogType: MovieDialogType,
    onConfirm: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {

    Dialog(
        onDismissRequest = { onDismissRequest() },
    ) {
        // Draw a rectangle shape with rounded corners inside the dialog
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            shape = MaterialTheme.shapes.large,
//        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.large,
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = when (dialogType) {
                    MovieDialogType.Watched -> "Add a movie you have already watched"
                    MovieDialogType.Unwatched -> "Add a movie you want to watched"
                    MovieDialogType.None -> throw IllegalStateException()
                },
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Start,
            )

            Spacer(modifier = Modifier.height(12.dp))

            FlowColumn {
                group.forEach { group ->
//                    val isSelected = selectedGroups.value.contains(group)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
//                        Icon(imageVector = Icons.Filled.Groups, contentDescription = "")
//                        Text(text = group.name)

                        Icon(
                            imageVector = Icons.Filled.Groups,
                            contentDescription = "",
                            modifier = Modifier.size(38.dp),
                        )
                        Text(
                            text = group.name,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp),
                        )
                        Checkbox(
                            checked = when (dialogType) {
                                MovieDialogType.Watched -> {

                                    false
                                }

                                MovieDialogType.Unwatched -> {

                                    false
                                }

                                MovieDialogType.None -> {
                                    /* no-op */
                                    false
                                }
                            },
                            onCheckedChange = {
//                                val newSelectedGroups = selectedGroups.value.toMutableSet()
//                                if (isSelected) {
//                                    newSelectedGroups.remove(group)
//                                } else {
//                                    newSelectedGroups.add(group)
//                                }
//                                selectedGroups.value = newSelectedGroups
                            },
                        )

                    }

                    // Row para el switch "vista-para ver"
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                        ) {
//                            Text(text = if (checked) "vista" else "para ver", color = Color.White)
//
//                            // Custom Switch
//                            Switch(
//                                checked = checked,
//                                onCheckedChange = { onCheck() },
//                                colors = SwitchDefaults.colors(
//                                    checkedThumbColor = Color(0xFFF5B300),
//                                    uncheckedThumbColor = Color(0xFFF5B300),
//                                    checkedTrackColor = Color.White,
//                                    uncheckedTrackColor = Color.White,
//                                ),
//                            )
//                        }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BasicDialogPreview() {
    FamilyFilmAppTheme {
        DetailDialog(
            group = listOf(
                Group().copy(name = "Group 1"),
                Group().copy(name = "Group 2"),
            ),
            me = User(),
            dialogType = MovieDialogType.Watched,
            selectedMovieId = 1,
        )
    }
}

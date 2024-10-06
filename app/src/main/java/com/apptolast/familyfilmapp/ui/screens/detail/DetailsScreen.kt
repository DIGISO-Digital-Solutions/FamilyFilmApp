package com.apptolast.familyfilmapp.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.apptolast.familyfilmapp.model.local.MovieCatalogue
import com.apptolast.familyfilmapp.ui.components.dialogs.DetailDialog
import com.apptolast.familyfilmapp.ui.screens.home.BASE_URL
import com.apptolast.familyfilmapp.ui.theme.FamilyFilmAppTheme
import java.util.Calendar
import java.util.Locale

@Composable
fun DetailsScreen(
    navController: NavController,
    movie: MovieCatalogue,
    viewModel: DetailScreenViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

//    val lazyListState = rememberLazyListState()
//    val isVisible = rememberSaveable { mutableStateOf(false) }
//    val checkedDetailDialog = rememberSaveable { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }
//    var scrolledY = 0f
//    var previousOffset = 0

//    LaunchedEffect(
//        key1 = detailScreenUIState.successMovieToViewList,
//    ) {
//        if (detailScreenUIState.successMovieToViewList.isNotBlank()) {
//            snackBarHostState.showSnackbar(
//                detailScreenUIState.successMovieToViewList,
//                "Close",
//                true,
//                SnackbarDuration.Long,
//            )
//        }
//    }

//    LaunchedEffect(key1 = detailScreenUIState.successMovieToWatchList) {
//        if (detailScreenUIState.successMovieToWatchList.isNotBlank()) {
//            snackBarHostState.showSnackbar(
//                detailScreenUIState.successMovieToWatchList,
//                "Close",
//                true,
//                SnackbarDuration.Long,
//            )
//        }
//    }

    LaunchedEffect(key1 = uiState.errorMessage?.error) {
        uiState.errorMessage?.let {
            if (it.error.isNotBlank()) {
                snackBarHostState.showSnackbar(
                    it.error,
                    "Close",
                    true,
                    SnackbarDuration.Long,
                )
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) { paddingValues ->
        DetailsContent(
            modifier = Modifier.padding(paddingValues),
            movie = movie,
            watchedClicked = {
                viewModel.showDialogGroups(MovieDialogType.Watched)
            },
            unwatchedClicked = {
                viewModel.showDialogGroups(MovieDialogType.Unwatched)
            },
        )
    }

    if (uiState.showDialogGroups != MovieDialogType.None) {
        DetailDialog(
            group = uiState.groups,
            me = uiState.me,
            selectedMovieId = movie.id,
            dialogType = uiState.showDialogGroups,
//            onCheck = {
//                uiState.checkForSeenToSee != uiState.checkForSeenToSee
//            },
            onDismissRequest = {
                viewModel.showDialogGroups(MovieDialogType.None)
            },
        )
    }
}

@Composable
private fun DetailsContent(
    movie: MovieCatalogue,
    modifier: Modifier = Modifier,
    watchedClicked: () -> Unit = {},
    unwatchedClicked: () -> Unit = {},
//    scrolledY: Float,
//    previousOffset: Int,
//    uiState: DetailScreenUIState,
) {
//    var scrolledY1 = scrolledY
//    var previousOffset1 = previousOffset
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        item {
            AsyncImage(
                model = "${BASE_URL}${movie.image}",
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            topStart = CornerSize(0.dp),
                            topEnd = CornerSize(0.dp),
                            bottomStart = MaterialTheme.shapes.large.bottomStart,
                            bottomEnd = MaterialTheme.shapes.large.bottomEnd,
                        ),
                    )
                    .height(420.dp),
//                    .graphicsLayer {
//                        scrolledY1 += lazyListState.firstVisibleItemScrollOffset - previousOffset1
//                        translationY = scrolledY1 * 0.5f
//                        previousOffset1 = lazyListState.firstVisibleItemScrollOffset
//                    },
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier.padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Text(
                        text = "Release Date: ${
                            Calendar.Builder().setInstant(movie.releaseDate).build().get(Calendar.YEAR).toString()
                        }",
                    )
                    Text(text = String.format(Locale.getDefault(), "Rating: %.1f", movie.voteAverage))
                    Text(
                        text = when (movie.adult) {
                            true -> "+18"
                            false -> ""
                        },
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    // Watched button
                    Button(
                        onClick = watchedClicked,
                        modifier = Modifier.weight(1f),
                    ) {
                        DetailsButtonContent(
                            icon = Icons.Default.Visibility,
                            text = "Watched",
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    // Unwatched button
                    OutlinedButton(
                        onClick = unwatchedClicked,
                        modifier = Modifier.weight(1f),
                    ) {
                        DetailsButtonContent(
                            icon = Icons.Default.VisibilityOff,
                            text = "Add to watchlist",
                        )
                    }
                }

                Text(
                    text = "Description",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(top = 15.dp),
                )

                Text(
                    text = movie.synopsis,
                    modifier = Modifier.padding(vertical = 15.dp),
                )
            }
        }
    }
}

@Composable
private fun DetailsButtonContent(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(vertical = 4.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.padding(end = 6.dp),
        )
        Text(
            text = text,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailsContentPreview() {
    FamilyFilmAppTheme {
        DetailsContent(
            movie = MovieCatalogue(),
        )
    }
}

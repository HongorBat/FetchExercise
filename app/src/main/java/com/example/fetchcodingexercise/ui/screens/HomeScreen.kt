package com.example.fetchcodingexercise.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fetchcodingexercise.R
import com.example.fetchcodingexercise.network.SingleItem
import com.example.fetchcodingexercise.ui.theme.FetchCodingExerciseTheme

@Composable
fun HomeScreen(
    uiState: AppUiState,
    retryAction : () -> Unit,
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues = PaddingValues(0.dp)
) {
    when(uiState){
        is AppUiState.Loading -> {
            LoadingScreen(modifier = Modifier.fillMaxSize())
        }
        is AppUiState.Success -> {

            //Show empty screen if response is empty
            if (uiState.response.isEmpty()){
                EmptyScreen(modifier = Modifier.fillMaxSize())
            } else {
                var expanded by remember { mutableStateOf(false) }

                var selectedListId by remember { mutableStateOf<Int?>(null) }

                ListItemScreen(
                    listItems = uiState.response,
                    selectedListId = selectedListId,
                    expanded = expanded,
                    onExpandedChanged = { expanded = !expanded },
                    onListIdChanged = { selectedListId = it; expanded = !expanded },
                    modifier = modifier.fillMaxSize(),
                    contentPadding = contentPaddingValues)
            }

        }
        is AppUiState.Error -> {
            ErrorScreen(retryAction = retryAction, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun ListItemScreen(
    listItems : Map<Int, List<SingleItem>>,
    selectedListId : Int?,
    expanded : Boolean,
    onExpandedChanged : () -> Unit,
    onListIdChanged : (Int?) -> Unit,
    modifier : Modifier = Modifier,
    contentPadding : PaddingValues = PaddingValues(0.dp)
){
    Column(modifier = modifier.padding(contentPadding)) {
        Box(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Button(onClick = onExpandedChanged) {
                Text(text = selectedListId?.let { stringResource(R.string.list_id_label, it) } ?: stringResource(R.string.select_list_id))
            }
            DropdownMenu(expanded, onDismissRequest = onExpandedChanged, modifier = Modifier) {
                listItems.keys.sorted().forEach { listId ->
                    DropdownMenuItem(
                        text = {
                            Text( text = stringResource(R.string.list_id_label, listId))
                               },
                        onClick = {onListIdChanged(listId)}
                    )
                }
                //To reset the selected list id
                DropdownMenuItem(
                    text = {
                        Text(text = "Reset")
                    },
                    onClick = { onListIdChanged(null) }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Display all items if no selection, or filter by selected listId
        val itemsToDisplay = selectedListId?.let { listItems[it] } ?: listItems.values.flatten()

        LazyColumn(
            modifier = Modifier.padding(horizontal = 4.dp),
        ) {
            items(itemsToDisplay){ item ->
                ListItemCard(item, modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth())
            }
        }

    }
}

@Composable
fun ListItemCard(singleItem: SingleItem, modifier: Modifier = Modifier){
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(R.string.list_id_label, singleItem.listId)
            )
            Text(
                text = stringResource(R.string.id_label, singleItem.id)
            )
            Text(
                text = stringResource(R.string.name_label, singleItem.name!!)
            )
        }
    }
}

@Composable
fun EmptyScreen(modifier: Modifier = Modifier){
    Column(modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Empty Response",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayLarge
        )
    }
}

// show loading screen when network request is not finished
@Composable
fun LoadingScreen(modifier: Modifier = Modifier){
    Image(
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading),
        modifier = modifier.size(200.dp)
    )
}

// show error screen if there and issue e.g no internet connection
@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.no_connection), contentDescription = null, modifier = Modifier.size(200.dp)
        )
        Text( text =  stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun ListItemScreenPreview(){
    FetchCodingExerciseTheme {
        EmptyScreen(modifier =  Modifier.fillMaxSize())
    }
}


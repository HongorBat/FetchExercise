package com.example.fetchcodingexercise.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fetchcodingexercise.R
import com.example.fetchcodingexercise.network.SingleItem
import com.example.fetchcodingexercise.ui.theme.FetchCodingExerciseTheme

@Composable
fun HomeScreen(
    uiState: AppUiState,
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues = PaddingValues(0.dp)
) {
    when(uiState){
        is AppUiState.Loading -> {
            LoadingScreen(modifier = Modifier.fillMaxSize())
        }
        is AppUiState.Success -> {
            ListItemScreen(uiState.response, modifier = modifier.fillMaxSize(), contentPadding = contentPaddingValues)
        }
        is AppUiState.Error -> {
            ErrorScreen(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun ListItemScreen(listItems : List<SingleItem>, modifier : Modifier = Modifier, contentPadding : PaddingValues = PaddingValues(0.dp)){
    LazyColumn(
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding
    ) {
        items(listItems){
            ListItemCard(it, modifier = Modifier.padding(4.dp).fillMaxWidth())
        }
    }
}

@Composable
fun ListItemCard(singleItem: SingleItem, modifier: Modifier = Modifier){
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "ID : ${singleItem.id}"
            )
            Text(
                text = "listId : ${singleItem.listId}"
            )
        }
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
fun ErrorScreen(modifier: Modifier = Modifier){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.no_connection), contentDescription = null, modifier = Modifier.size(200.dp)
        )
        Text( text =  stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}

@Composable
@Preview(showSystemUi = true)
fun ListItemScreenPreview(){
    FetchCodingExerciseTheme {
        val mockData = List(10) { SingleItem(id = 3, listId = 12, null)}
        ListItemScreen(mockData)
    }
}


package com.vikram.jokeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vikram.jokeapp.model.Joke
import com.vikram.jokeapp.network.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun JokeScreen() {
    val coroutineScope = rememberCoroutineScope()
    var joke by remember { mutableStateOf<Joke?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isLoading = true
        joke = RetrofitClient.api.getJoke()
        isLoading = false
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Funny image on top
            Image(
                painter = painterResource(id = R.drawable.laugh),
                contentDescription = "Laugh Image",
                modifier = Modifier
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                joke?.let {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = it.setup,
                                style = MaterialTheme.typography.titleLarge,
                                fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = it.punchline,
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 18.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            isLoading = true
                            joke = RetrofitClient.api.getJoke()
                            isLoading = false
                        }
                    },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("😂 Tell me another one")
                }
            }
        }
    }
}

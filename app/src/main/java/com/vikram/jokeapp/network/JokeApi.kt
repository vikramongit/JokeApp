package com.vikram.jokeapp.network

import com.vikram.jokeapp.model.Joke
import retrofit2.http.GET

interface JokeApi {
    @GET("random_joke")
    suspend fun getJoke(): Joke
}

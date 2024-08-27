package com.payback.data.images.api

import com.payback.data.images.models.SearchResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ImagesApi {

    @GET("/api")
    suspend fun search(
        @Query("key") key: String,
        @Query("q") query: String
    ): Response<SearchResults>
}

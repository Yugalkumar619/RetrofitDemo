package com.yugal.retofitdemo

import retrofit2.Response
import retrofit2.http.GET

interface AlbumService {

    @GET("/albums")
    suspend fun getAlbums() : Response<Album>
}
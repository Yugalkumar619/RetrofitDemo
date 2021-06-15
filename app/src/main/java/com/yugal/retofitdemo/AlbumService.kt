package com.yugal.retofitdemo

import retrofit2.Response
import retrofit2.http.*

interface AlbumService {

    @GET("/albums")
    suspend fun getAlbums() : Response<Album>

    // Query Parameters
    @GET("/albums")
    suspend fun getSortedAlbums(@Query("userId") userId:Int) : Response<Album>

    // Path Parameters
    @GET("/albums/{id}")
    suspend fun getAlbum(@Path(value = "id")albumId: Int) : Response<AlbumItem>

    // Post Request
    @POST("/albums")
    suspend fun uploadAlbum(@Body album: AlbumItem) : Response<AlbumItem>
}
package com.yugal.retofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var retService: AlbumService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        retService = RetrofitInstance
            .getRetofitInstance()
            .create(AlbumService::class.java)

        //getRequestWithQueryParameters()
        uploadAlbums()
}


private fun getRequestWithQueryParameters(){

    //path parameter example
    val pathResponse : LiveData<Response<AlbumItem>> = liveData {
        val response  = retService.getAlbum(3)
        emit(response)
    }

    pathResponse.observe(this, Observer {
        val title = it.body()?.title
        Toast.makeText(applicationContext, title, Toast.LENGTH_LONG).show()
    })



    val responseLiveData:LiveData<Response<Album>> = liveData {
        val response = retService.getSortedAlbums(1)
        emit(response)
    }
    responseLiveData.observe(this, Observer {
        val albumsList = it.body()?.listIterator()
        if(albumsList != null){
            while(albumsList.hasNext()){
                val albumsItem = albumsList.next()
                val result : String = " "+ "Album Title: ${albumsItem.title}"+"\n"+
                        " "+ "Album id: ${albumsItem.id}"+"\n"+
                        " "+ "user id: ${albumsItem.userId}"+"\n\n\n"
                text_view.append(result)
            }
        }
    })
}
    private fun uploadAlbums(){
        val album = AlbumItem(0,"My titlr",3)
        val postResponse : LiveData<Response<AlbumItem>> = liveData{
            val response:Response<AlbumItem> = retService.uploadAlbum(album)
            emit(response)
        }
        postResponse.observe(this, Observer {
            val receivedAlbumsItem = it.body()
            val result : String = " "+ "Album Title: ${receivedAlbumsItem?.title}"+"\n"+
                    " "+ "Album id: ${receivedAlbumsItem?.id}"+"\n"+
                    " "+ "user id: ${receivedAlbumsItem?.userId}"+"\n\n\n"
            text_view.text = result
        })
    }
}

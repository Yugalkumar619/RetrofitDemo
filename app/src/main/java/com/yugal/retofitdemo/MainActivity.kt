package com.yugal.retofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val retService = RetrofitInstance
            .getRetofitInstance()
            .create(AlbumService::class.java)

        val responseLiveData:LiveData<Response<Album>> = liveData {
            val response = retService.getAlbums()
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
}
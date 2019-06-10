package com.edulara.android.dynamiclinkexample

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ShortDynamicLink

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun LargeDynamicLinks(view : android.view.View){
        val dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://edularalev.com/"))
                .setDomainUriPrefix("https://edulara.page.link")
                // Open links with this app on Android
                .setAndroidParameters(DynamicLink.AndroidParameters.Builder().build())
                // Open links with com.example.ios on iOS
                .setIosParameters(DynamicLink.IosParameters.Builder("com.example.ios").build())
                .buildDynamicLink()

        shareDynamicLink(dynamicLink.uri)
    }

    fun ShortDynamicLinks(view : android.view.View){
        FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://edularalev.com/"))
                .setDomainUriPrefix("https://edulara.page.link")
                .setAndroidParameters(DynamicLink.AndroidParameters.Builder().build())
                .setIosParameters(DynamicLink.IosParameters.Builder("com.example.ios").build())
                // Set parameters
                // ...
                .buildShortDynamicLink(ShortDynamicLink.Suffix.SHORT)
                .addOnSuccessListener { result ->
                    // Short link created
                    val shortLink = result.shortLink
                    val flowchartLink = result.previewLink

                    shareDynamicLink(shortLink)
                }.addOnFailureListener {exception ->
            Log.i("Error", exception.message)
            // Error
        }
    }

    fun shareDynamicLink(uri : Uri){
        val intent = Intent(Intent.ACTION_SEND)
        val msg = "Visita mi pagina: "  + uri
        intent.putExtra(Intent.EXTRA_TEXT, msg)
        intent.type = "text/plain"
        startActivity(intent)

    }

}


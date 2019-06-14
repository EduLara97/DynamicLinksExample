package com.edulara.android.dynamiclinkexample

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.google.firebase.dynamiclinks.ShortDynamicLink
import java.net.URI

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseDynamicLinks.getInstance().getDynamicLink(intent).addOnSuccessListener(this) {pendingDynamicLinkData ->
            var deepLink : Uri? =null
            if (pendingDynamicLinkData != null){
                deepLink = pendingDynamicLinkData.link
                Toast.makeText(this, deepLink.toString(), Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener(this) {e ->
            Log.w("ErrorDynamicLink", e)
        }

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


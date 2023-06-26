package com.miempresa.googlemapv4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val values = arrayOf(
            "PLAZA DE ARMAS",
            "CHARACATO",
            "COLCA",
            "YURA",
            "MIRADOR SACHACA"
        )

        val imgs = arrayOf(
            "https://th.bing.com/th/id/R.6a759c618627b3c8db41e127e78b1164?rik=IYCqf7DtNRKGng&pid=ImgRaw&r=0",
            "https://1.bp.blogspot.com/-YqbEpJfP99Q/XZQB6SPhFJI/AAAAAAAAHx0/WKtqPcT9pSErk2GY4YlX4R5FwO9AtOMBACLcBGAsYHQ/w1200-h630-p-k-no-nu/b4.jpg",
            "https://mimapadelibros.files.wordpress.com/2015/02/colca-082.jpg?w=1024",
            "https://th.bing.com/th/id/R.18ba47661634c26b310c2d21cdaeaed2?rik=ROooY0isBtaViA&riu=http%3a%2f%2fwww.muniyura.gob.pe%2fimages%2f2017%2fInstitucionales%2fPlaza+de+Yura+Viejo-+ENERO+-+Historia.JPG&ehk=uEgHp90lYWDxVbPGKiBKXpsk2LyVFVURF365CsA6Nm8%3d&risl=&pid=ImgRaw&r=0",
            "https://th.bing.com/th/id/R.5640adcef87b4d33641f0d0b99866f85?rik=71V3G%2bUQdVj5Sw&riu=http%3a%2f%2fmedia-cdn.tripadvisor.com%2fmedia%2fphoto-s%2f01%2f16%2f98%2f94%2fel-mirador-de-sachaca.jpg&ehk=CmzCN%2fB%2fBtyf%2fluje8aYI3LyAL7fmaa9PvmJssjDxv4%3d&risl=&pid=ImgRaw&r=0"
        )
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, android.R.id.text1,
            values
        )
        lstDestinos.adapter = adapter
        lstDestinos.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val itemValue =
                    lstDestinos.getItemAtPosition(i) as String
                val img = imgs.get(i) as String
                val intent = Intent(this@MainActivity, MapsActivity::class.java)
                intent.putExtra("destino", itemValue.toLowerCase())
                intent.putExtra("img", img)
                startActivity(intent)
            }

    }
}
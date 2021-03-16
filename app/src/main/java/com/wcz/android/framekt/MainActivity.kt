package com.wcz.android.framekt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wcz.android.R
import com.wcz.corekt.anko.px2dp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("有输出吗》》》" + px2dp(2.0))

    }


}


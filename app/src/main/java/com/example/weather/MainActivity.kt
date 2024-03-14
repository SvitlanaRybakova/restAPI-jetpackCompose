package com.example.weather


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.example.weather.ui.theme.WeatherTheme
import org.json.JSONObject



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Layout("Stockholm", this)
                }
            }
        }
    }
}

@Composable
fun Layout(city: String, context: Context) {
    val state = remember {
        mutableStateOf("Unknown")
    }
    Column(modifier = Modifier.fillMaxSize()){
        Box(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
            ){
            Text(
                text = "The temp in $city is ${state.value}"
            )
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ){
            Button(
                onClick = { getResult(city, state, context) },
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                ){
                Text(text = "Refresh")
            }

        }
    }



}


private fun getResult(city: String, state: MutableState<String>, context:Context) {
    val lat = 44.34
        val lon = 10.99
val url = "https://api.openweathermap.org/data/2.5/weather" +
        "?lat=$lat&" +
        "lon=$lon" +
        "&appid=$API_KEY"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        {

            response ->
            val obj = JSONObject(response)
            state.value =  obj.getJSONObject("main").getString("temp")

            Log.d("Response", response)
        },
        {
            error ->
            Log.d("Error", error.toString())
        }
    )
    queue.add(stringRequest)

}
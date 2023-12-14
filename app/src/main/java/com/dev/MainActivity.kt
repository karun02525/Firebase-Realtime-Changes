package com.dev

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dev.ui.theme.FirebaseRealTimeTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // https://fir-realtime-233ad-default-rtdb.firebaseio.com/
        val database = Firebase.database
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")




        setContent {
            FirebaseRealTimeTheme {
                var data by remember {
                    mutableStateOf("")
                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    myRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            val value = dataSnapshot.getValue<String>()
                            if (value != null) {
                                data=value
                            }
                            Log.d("TAG", "Value is: $value")
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Failed to read value
                            Log.w("TAG", "Failed to read value.", error.toException())
                        }
                    })
                    Greeting(" $data")

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


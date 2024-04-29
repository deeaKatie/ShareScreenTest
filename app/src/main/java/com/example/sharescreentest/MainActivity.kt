package com.example.sharescreentest

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
import com.example.sharescreentest.ui.theme.ShareScreenTestTheme
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class MainActivity : ComponentActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        setContent {
            ShareScreenTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {

    val database = Firebase.database
    val myRef = database.getReference("message")

    var message : String? by remember {
        mutableStateOf("")
    }

    // Add a ValueEventListener to listen for changes
    val messageListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // This method will be called whenever data at the specified database reference changes.
            // You can retrieve the new data from the dataSnapshot parameter.
            for (snapshot in dataSnapshot.children) {
                message = snapshot.getValue(String::class.java)
                // Process the message here
                Log.e("FIREBASE", "data retrived:" + message)
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // This method will be called if the listener is unable to access the data or encounters an error
            Log.e("FIREBASE", "Error reading message", databaseError.toException())
        }
    }

    // Attach the listener to the database reference
    myRef.addValueEventListener(messageListener)

    Text("yoo" + message)
}

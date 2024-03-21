package com.example.shoppieadmin

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun HomeScreen() {
    var productName by rememberSaveable {
        mutableStateOf("")
    }

    var productCategory by rememberSaveable {
        mutableStateOf("")
    }

    var productDescription by rememberSaveable {
        mutableStateOf("")
    }

    var productPrice by rememberSaveable {
        mutableStateOf("")
    }

    var selectedImages by rememberSaveable { mutableStateOf<List<Uri>>(emptyList()) }
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uri ->
            selectedImages = uri
        }
    )
    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppToolBar(toolBarTitle = "Products Adder")
        }
    ) { paddingValues ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Magenta)
                .padding(paddingValues)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                Text(
                    text = "Product Information",
                    color = Color.Black,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(
                    value = productName,
                    onValueChange = { productName = it },
                    placeholder = "Product name",
                    maxLines = 1,
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomTextField(
                    value = productCategory,
                    onValueChange = { productCategory = it },
                    placeholder = "Product category",
                    maxLines = 1,
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomTextField(
                    value = productDescription,
                    onValueChange = { productDescription = it },
                    placeholder = "Product description",
                    maxLines = 4,
                    minLines = 4,
                    singleLine = false
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomTextField(
                    value = productPrice,
                    onValueChange = { productPrice = it },
                    placeholder = "Product price",
                    maxLines = 1,
                    singleLine = true,
                    keyboardType = KeyboardType.Number
                )

                Spacer(modifier = Modifier.height(20.dp))

//                ImagePickerButton(onImagesSelected = { uris ->
//                    selectedImages = uris
//                })

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(onClick = {
                        multiplePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }) {
                        Text(text = "Select images")
                    }

                    Button(onClick = {
                        Toast.makeText(context, "Uploading....", Toast.LENGTH_SHORT).show()
                    }) {
                        Text(text = "Upload")
                    }
                }

                LazyRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(selectedImages) { uri ->
                        AsyncImage(
                            model = uri, contentDescription = null,
                            modifier = Modifier.size(width = 60.dp, height = 90.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                }
            }
        }
    }

}

package com.example.testing

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testing.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var manager: LinearLayoutManager

    private val openDocument = registerForActivityResult(MyOpenDocumentContract()) { uri ->
        uri?.let { onImageSelected(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: 1 validate if a user exists, if not go to SignInActivity

        val simpleAdapter = SimpleFriendlyMessageAdapter(
            mutableListOf(),
            ""// TODO: 4b getUserName()
        )
        binding.progressBar.visibility = ProgressBar.INVISIBLE
        manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL
        binding.messageRecyclerView.layoutManager = manager
        binding.messageRecyclerView.adapter = simpleAdapter
        binding.messageEditText.addTextChangedListener(MyButtonObserver(binding.sendButton))
        binding.addMessageImageView.setOnClickListener {
            openDocument.launch(arrayOf("image/*"))
        }


        // TODO: 5 send messages to Firebase Real Time Database


        // TODO: 6 listen changes of Firebase Real Time Database

    }

    public override fun onStart() {
        super.onStart()
    }

    public override fun onPause() {
        super.onPause()
    }

    public override fun onResume() {
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sign_out_menu -> {
                signOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // TODO: 7 add implementation to send Image message
    private fun onImageSelected(uri: Uri) {

    }

    // TODO: 7b and upload file to Firebase Storage
    /*private fun putImageInStorage(storageReference: StorageReference, uri: Uri, key: String) {

    }*/

    private fun signOut() {
        // TODO: 3 SignOut using Firebase AuthUi
    }

    // TODO: 4 add helper functions

    companion object {
        private const val TAG = "MainActivity"
        const val MESSAGES_CHILD = "messages"
        const val ANONYMOUS = "anonymous"
        private const val LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif"
    }
}

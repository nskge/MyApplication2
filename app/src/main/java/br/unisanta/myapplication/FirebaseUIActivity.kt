package br.unisanta.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class FirebaseUIActivity : AppCompatActivity() {

    // Provedores disponíveis no FirebaseUI
    private val providers = listOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build()
    )

    private val signInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val response = IdpResponse.fromResultIntent(result.data)

        if (result.resultCode == RESULT_OK) {
            val user = FirebaseAuth.getInstance().currentUser
            Toast.makeText(this, "Logado como: ${user?.email}", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        } else {
            if (response == null) {
                Toast.makeText(this, "Login cancelado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Erro: ${response.error?.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_ui)

        val btnLancarFirebaseUI = findViewById<Button>(R.id.btnLancarFirebaseUI)

        btnLancarFirebaseUI.setOnClickListener {
            lancarFirebaseUI()
        }
    }

    private fun lancarFirebaseUI() {
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setLogo(R.mipmap.ic_launcher)
            .setTheme(R.style.Theme_MyApplication)
            .build()

        signInLauncher.launch(signInIntent)
    }
}

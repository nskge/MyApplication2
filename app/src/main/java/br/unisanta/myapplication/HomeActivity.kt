package br.unisanta.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()

        val txtBemVindo = findViewById<TextView>(R.id.txtBemVindo)
        val txtEmail = findViewById<TextView>(R.id.txtEmail)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val btnFirebaseUI = findViewById<Button>(R.id.btnFirebaseUI)

        val usuario = auth.currentUser
        txtBemVindo.text = "Bem-vindo!"
        txtEmail.text = "Logado como: ${usuario?.email ?: "Usuário desconhecido"}"

        btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        btnFirebaseUI.setOnClickListener {
            startActivity(Intent(this, FirebaseUIActivity::class.java))
        }
    }
}

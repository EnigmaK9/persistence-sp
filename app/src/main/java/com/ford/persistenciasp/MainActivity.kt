package com.ford.persistenciasp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.ford.persistenciasp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var navHostFragment: Fragment

    private lateinit var encryptedSp: SharedPreferences
    private lateinit var encryptedSpEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as Fragment

        // Inicializar MasterKey para encriptación
        val masterKey = MasterKey.Builder(
            this,
            MasterKey.DEFAULT_MASTER_KEY_ALIAS
        ).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

        // Inicializar EncryptedSharedPreferences
        encryptedSp = EncryptedSharedPreferences.create(
            this,
            "encrypted_settings",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        encryptedSpEditor = encryptedSp.edit()

        // Guardar el nombre de usuario en EncryptedSharedPreferences
        encryptedSpEditor.putString("username", "Carlos").apply()

        // Verificar si el nombre fue almacenado correctamente
        val storedUsername = encryptedSp.getString("username", "")
        if (storedUsername == "Carlos") {
            // Mostrar un Toast de confirmación al iniciar la app
            showToast(storedUsername)
        } else {
            // Mostrar un Toast de error si el nombre no se almacena correctamente
            showToast("Error al almacenar el nombre")
        }

        // Cargar el color guardado
        val color = encryptedSp.getInt("color", R.color.white)
        changeColor(color)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflar el menú con las opciones de colores
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Manejar las opciones de menú de colores
        return when (item.itemId) {
            R.id.action_blue -> {
                val color = R.color.my_blue
                changeColor(color)
                saveColor(color)
                true
            }
            R.id.action_red -> {
                val color = R.color.my_red
                changeColor(color)
                saveColor(color)
                true
            }
            R.id.action_green -> {
                val color = R.color.my_green
                changeColor(color)
                saveColor(color)
                true
            }
            R.id.action_yellow -> {
                val color = R.color.my_yellow
                changeColor(color)
                saveColor(color)
                true
            }
            R.id.action_purple -> {
                val color = R.color.my_purple
                changeColor(color)
                saveColor(color)
                true
            }
            R.id.action_orange -> {
                val color = R.color.my_orange
                changeColor(color)
                saveColor(color)
                true
            }
            R.id.action_cyan -> {
                val color = R.color.my_cyan
                changeColor(color)
                saveColor(color)
                true
            }
            R.id.action_pink -> {
                val color = R.color.my_pink
                changeColor(color)
                saveColor(color)
                true
            }
            R.id.action_black -> {
                val color = R.color.black
                changeColor(color)
                saveColor(color)
                true
            }
            R.id.action_white -> {
                val color = R.color.white
                changeColor(color)
                saveColor(color)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun changeColor(color: Int) {
        navHostFragment.view?.setBackgroundColor(getColor(color))
    }

    private fun saveColor(color: Int) {
        // Guardar el color seleccionado en EncryptedSharedPreferences
        encryptedSpEditor.putInt("color", color).apply()
    }

    private fun saveEncryptedCredentials(user: String, password: String){
        encryptedSpEditor.putString("user", user).apply()
        encryptedSpEditor.putString("password", password).apply()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

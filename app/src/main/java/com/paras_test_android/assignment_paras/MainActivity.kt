package com.paras_test_android.assignment_paras

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.paras_test_android.assignment_paras.databinding.ActivityMainBinding

/**
 * Clase para la actividad principal de la aplicación
 * Contiene los botones del menú principal y el panel de navegación.
 * */
class MainActivity : AppCompatActivity() {
    // View Binding
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout

    /**
     * OnCreate method que inicia subactividades al hacer clic en el botón
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        //this is the menu item on top left corner of the screen
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                //add expenses
                R.id.nav_add_expenses -> {
                    val intent = Intent(this, AddExpensesActivity::class.java)
                    startActivity(intent)
                }
                //show expenses
                R.id.nav_show_expenses -> {
                    val intent = Intent(this, ShowExpensesActivity::class.java)
                    startActivity(intent)
                }
                // edit expenses acitivity
                R.id.nav_edit_expenses -> {
                    val intent = Intent(this, EditExpensesActivity::class.java)
                    startActivity(intent)
                }
                //show statistics activity
                R.id.nav_show_statistics -> {
                    val intent = Intent(this, ShowStatisticsActivity::class.java)
                    startActivity(intent)
                }
                //exit the application
                R.id.nav_exit -> {
                    finishAffinity()
                }
            }
            drawerLayout.closeDrawers()
            true
        }

        // Set up toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

        // Set up original menu buttons using View Binding
        // listerne for add expenses button
        binding.addExpensesButton.setOnClickListener {
            val intent = Intent(this, AddExpensesActivity::class.java)
            startActivity(intent)
        }

        //show expenses button listener
        binding.showExpensesButton.setOnClickListener {
            val intent = Intent(this, ShowExpensesActivity::class.java)
            startActivity(intent)
        }

        //edit expenses button in main menu
        binding.editExpensesButton.setOnClickListener {
            val intent = Intent(this, EditExpensesActivity::class.java)
            startActivity(intent)
        }

        //show statistics button listener
        binding.showStatisticsButton.setOnClickListener {
            val intent = Intent(this, ShowStatisticsActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Metodo que abrirá el cajón de navegación al hacer clic en el botón de menú
     * */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}

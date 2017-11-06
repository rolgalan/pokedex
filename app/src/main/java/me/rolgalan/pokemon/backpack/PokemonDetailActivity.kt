package me.rolgalan.pokemon.backpack

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_pokemon_detail.*
import me.rolgalan.pokemon.R
import me.rolgalan.pokemon.detail.PokemonDetailsFragment
import me.rolgalan.pokemon.model.Pokemon

/**
 * An activity representing a single [Pokemon] detail screen.
 * This activity uses  [PokemonDetailsFragment] to show the data
 */
class PokemonDetailActivity : AppCompatActivity() {

    companion object {

        private val ARG_POKEMON = "ARG_POKEMON"

        fun getIntent(pokemon: Pokemon, context: Context): Intent {
            return Intent(context, PokemonDetailActivity::class.java).apply {
                putExtra(ARG_POKEMON, pokemon)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)
        setSupportActionBar(detail_toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {

            val pokemon = intent.getSerializableExtra(ARG_POKEMON) as Pokemon

            setTitle(pokemon.name)

            supportFragmentManager.beginTransaction()
                    .add(R.id.pokemon_detail_container, PokemonDetailsFragment.newInstance(pokemon, true))
                    .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    navigateUpTo(Intent(this, PokemonListActivity::class.java))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}

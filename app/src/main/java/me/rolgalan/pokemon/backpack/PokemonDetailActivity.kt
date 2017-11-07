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
import me.rolgalan.pokemon.provider.DataInterface
import me.rolgalan.pokemon.provider.DataProvider

/**
 * An activity representing a single [Pokemon] detail screen.
 * This activity uses  [PokemonDetailsFragment] to show the data
 */
class PokemonDetailActivity : AppCompatActivity() {
    lateinit var pokemon: Pokemon

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
            Snackbar.make(view, "Do you want to remove this pokemon from your backpack?" +
                    "\nThis action can NOT be undone.", Snackbar.LENGTH_LONG)
                    .setAction("Remove", { removePokemon() }).show()
        }
        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {

            pokemon = intent.getSerializableExtra(ARG_POKEMON) as Pokemon

            title = pokemon.name

            supportFragmentManager.beginTransaction()
                    .add(R.id.pokemon_detail_container, PokemonDetailsFragment.newInstance(pokemon))
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


    private fun removePokemon() {
        DataProvider.instance.removePokemon(pokemon,
                object : DataInterface<Boolean> {
                    override fun onReceived(isRemoved: Boolean) {
                        if (isRemoved) {
                            onPokemonRemoved()
                        }
                    }

                    override fun onError(error: String) {
                        onErrorRemoving()

                    }
                })
    }

    private fun onPokemonRemoved() {
        onBackPressed()
    }

    private fun onErrorRemoving() {
        Snackbar.make(fab, "Error removing ${pokemon.name}." +
                " Please retry.", Snackbar.LENGTH_LONG).show()
    }
}

package me.rolgalan.pokemon.backpack

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_pokemon_list.*
import kotlinx.android.synthetic.main.pokemon_list.*
import me.rolgalan.pokemon.MainActivity
import me.rolgalan.pokemon.R
import me.rolgalan.pokemon.model.Backpack
import me.rolgalan.pokemon.model.Pokemon
import me.rolgalan.pokemon.provider.DataInterface
import me.rolgalan.pokemon.provider.DataProvider

/**
 * An activity representing a list of [Pokemon] stored in the [Backpack]
 */
class PokemonListActivity : AppCompatActivity(), DataInterface<Backpack> {

    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, PokemonListActivity::class.java)
            //No args required yet
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Do you want to remove all pokemon in your backpack?" +
                    "\nThis action can NOT be undone.", Snackbar.LENGTH_LONG)
                    .setAction("Remove", { removeBackpack() }).show()
        }
        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        pokemon_list.layoutManager = GridLayoutManager(this, 3)

        loadBackpack()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            navigateUpTo(Intent(this, MainActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item);
    }

    private fun loadBackpack() {
        DataProvider.instance.getBackpack(this)
    }

    override fun onReceived(data: Backpack) {
        setupRecyclerView(data.values.toList())
    }

    override fun onError(error: String) {
        Snackbar.make(fab, "$error\nRetry loading the backpack?", Snackbar.LENGTH_INDEFINITE)
                .setAction("Reload!",
                        {
                            loadBackpack()
                        })
                .show()
    }

    private fun removeBackpack() {
        DataProvider.instance.removeBackpack()
        onBackPressed()
    }

    private fun setupRecyclerView(backpack: List<Pokemon>) {
        pokemon_list.adapter = PokemonRecyclerViewAdapter(backpack)
    }
}

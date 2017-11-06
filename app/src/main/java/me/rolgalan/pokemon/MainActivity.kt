package me.rolgalan.pokemon

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import me.rolgalan.pokemon.detail.PokemonDetailsFragment
import me.rolgalan.pokemon.model.Backpack
import me.rolgalan.pokemon.model.Pokemon

class MainActivity : AppCompatActivity(), MainView {

    private val presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab_ok.setOnClickListener { presenter.catchPokemon() }
        fab_no.setOnClickListener { presenter.getNewPokemon() }
        presenter.prepareView()
    }

    override fun showLoading() {
        loading_spinner.visibility = View.VISIBLE
    }

    override fun showMsg(msg: String) {
        Snackbar.make(main_container, msg, Snackbar.LENGTH_LONG).show()
    }

    override fun hideLoading() {
        loading_spinner.visibility = View.GONE
    }

    override fun showBackpack(data: Backpack) {
        //TODO
        hideFab()
        showMsg("Load backpack --> implement!")
    }

    override fun showPokemon(data: Pokemon) {
        showMsg("A Wild ${data.name} appears!")
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.main_container, PokemonDetailsFragment.newInstance(data, false))
        transaction.commit()
        showFab()
    }

    override fun hideFab() {
        fab_ok.visibility = View.GONE
        fab_no.visibility = View.GONE
    }

    override fun showFab() {
        fab_ok.visibility = View.VISIBLE
        fab_no.visibility = View.VISIBLE
    }

    override fun showPokemonCatch(name: String) {
        showSnackbarToCatchNewPokemon("You've catch $name.\n Do you want to search for a new pokemon?")
    }

    override fun showErrorNewPokemon(error: String) {
        showSnackbarToCatchNewPokemon("$error\n Do you want to search again?")
    }

    fun showSnackbarToCatchNewPokemon(msg: String) {
        Snackbar.make(fab_ok, msg, Snackbar.LENGTH_INDEFINITE)
                .setAction("Search!",
                        {
                            presenter.getNewPokemon()
                        })
                .show()
    }
}

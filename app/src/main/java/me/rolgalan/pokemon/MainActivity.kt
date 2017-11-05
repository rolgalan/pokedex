package me.rolgalan.pokemon

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import me.rolgalan.pokemon.model.Backpack
import me.rolgalan.pokemon.model.Pokemon

class MainActivity : AppCompatActivity(), MainView {

    private val presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { catchPokemon() }
        presenter.prepareView()
    }

    private fun catchPokemon() {
        presenter.catchPokemon()
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
        //TODO
        showMsg("Show pokemon ${data.name} ($data.id)")
        showFab()
    }

    override fun hideFab() {
        fab.visibility = View.GONE

    }

    override fun showFab() {
        fab.visibility = View.VISIBLE
    }

    override fun showPokemonCatch(name: String) {
        showMsg("You've catch " + name)
        Snackbar.make(fab, "You've catch $name.\n Do you want to search for a new pokemon?", Snackbar.LENGTH_INDEFINITE)
                .setAction("Search!",
                        {
                            presenter.getNewPokemon()
                        })
                .show()
    }
}

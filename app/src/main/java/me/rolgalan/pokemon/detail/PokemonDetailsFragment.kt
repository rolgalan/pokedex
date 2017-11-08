package me.rolgalan.pokemon.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_pokemon_details.*
import me.rolgalan.pokemon.R
import me.rolgalan.pokemon.model.Pokemon

/**
 * A simple [Fragment] subclass.
 * Use the [PokemonDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PokemonDetailsFragment : Fragment(), DetailsPresenter.DetailsView {

    //This presenter is going to be initialized in the onCreate
    private lateinit var presenter: DetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null
                && arguments!!.containsKey(ARG_POKEMON)
                && arguments!!.getSerializable(ARG_POKEMON) is Pokemon) {
            val pokemon = arguments!!.getSerializable(ARG_POKEMON) as Pokemon

            presenter = DetailsPresenter(this, pokemon)
        } else {
            throw RuntimeException(context.toString()
                    + " must include $ARG_POKEMON param in the Bundle")
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_pokemon_details, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.prepareView()
    }

    companion object {

        private val ARG_POKEMON = "ARG_POKEMON"

        /**
         * Use this factory method to create a new instance of
         * this fragment to display a [Pokemon]
         *
         * @param pokemon A Pokemon
         * @return A new instance of fragment PokemonDetailsFragment.
         */
        fun newInstance(pokemon: Pokemon): PokemonDetailsFragment {

            val fragment = PokemonDetailsFragment()
            val args = Bundle()
            args.putSerializable(ARG_POKEMON, pokemon)
            fragment.arguments = args
            return fragment
        }
    }

    override fun loadImage(url: String?) {
        if (url != null) {
            loadValidImage(url)
        } else {
            loadPlaceholder()
        }
    }

    private fun loadPlaceholder() {

        Glide.with(context)
                .load(R.drawable.placeholder)
                .fitCenter()
                .into(pokemon_image)
    }

    private fun loadValidImage(url: String) {
        Glide.with(context)
                .load(url)
                .fitCenter()
                .placeholder(R.drawable.placeholder)
                .crossFade()
                .into(pokemon_image)
    }

    override fun setName(text: String) {
        pokemon_name.setText(text)
    }

    override fun setWeight(text: String) {
        labelvalue_weight.setValue(text)
    }

    override fun setHeight(text: String) {
        labelvalue_height.setValue(text)
    }

    override fun showCapturedFields(show: Boolean) {
        var visibility = if (show) {
            View.VISIBLE
        } else {
            View.GONE
        }

        labelvalue_experience.visibility = visibility
        labelvalue_captured.visibility = visibility
        labelvalue_type.visibility = visibility
    }

    override fun setExperience(text: String) {
        labelvalue_experience.setValue(text)
    }

    override fun setCapturedTime(text: String) {
        labelvalue_captured.setValue(text)
    }

    override fun setType(text: String) {
        labelvalue_type.setValue(text)
    }
}
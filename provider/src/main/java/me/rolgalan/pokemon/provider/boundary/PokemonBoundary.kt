package me.rolgalan.pokemon.provider.boundary

import me.rolgalan.pokemon.model.NamedApiResource
import me.rolgalan.pokemon.model.Pokemon
import me.rolgalan.pokemon.model.PokemonSprites
import me.rolgalan.pokemon.model.PokemonType
import me.rolgalan.pokemon.provider.DataInterface
import me.rolgalan.pokemon.server.model.PokemonResponse

/**
 * Created by Roldán Galán on 03/11/2017.
 */
public class PokemonBoundary(listener: DataInterface<Pokemon>?) : DataBoundary<Pokemon, PokemonResponse>(listener) {

    override fun onResultsReceived(response: PokemonResponse) {
        listenerOnReceived(mapPokemon(response))
    }

    fun mapPokemon(response: PokemonResponse): Pokemon {
        val types = mapTypes(response.types)
        val sprites = mapSprites(response?.sprites)
        return Pokemon(response.id, response.name, response.baseExperience, response.height,
                response.order, response.weight, types, sprites, null)
    }

    fun mapSprites(sprites: me.rolgalan.pokemon.server.model.PokemonSprites?): PokemonSprites {
        return PokemonSprites(sprites?.backDefault, sprites?.frontDefault)
    }

    fun mapTypes(types: List<me.rolgalan.pokemon.server.model.PokemonType>): List<PokemonType> {
        return types.map { value -> mapType(value) }
    }

    fun mapType(type: me.rolgalan.pokemon.server.model.PokemonType): PokemonType {
        return PokemonType(type.slot, mapNamedApiResource(type.type))
    }

    fun mapNamedApiResource(type: me.rolgalan.pokemon.server.model.NamedApiResource): NamedApiResource {

        return NamedApiResource(type.name, type.url)
    }
}
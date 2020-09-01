package com.itechevo.breakingbad.ui.characters

import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.annotation.VisibleForTesting
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.itechevo.breakingbad.R
import com.itechevo.breakingbad.extension.hide
import com.itechevo.breakingbad.extension.show
import com.itechevo.breakingbad.ui.MainActivity
import com.itechevo.breakingbad.ui.adapters.CharacterListAdapter
import com.itechevo.breakingbad.ui.adapters.SeasonListAdapter
import com.itechevo.breakingbad.ui.model.LoadingError
import com.itechevo.breakingbad.ui.model.Loaded
import com.itechevo.breakingbad.ui.model.Loading
import com.itechevo.breakingbad.ui.model.ViewState
import com.itechevo.domain.model.Character
import kotlinx.android.synthetic.main.characters_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharactersFragment : Fragment() {

    private val charactersViewModel: CharactersViewModel by viewModel()

    private val characterListAdapter =
        CharacterListAdapter { character: Character -> characterItemClicked(character) }

    private val seasonListAdapter =
        SeasonListAdapter { selectedSeason: Int ->
            charactersViewModel.selectedSeason(selectedSeason)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.characters_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        (requireActivity() as? MainActivity)?.supportActionBar?.title = getString(R.string.app_name)

        charactersViewModel.viewState.observe(viewLifecycleOwner, {
            handleResult(it)
        })

        characterList.layoutManager = GridLayoutManager(requireContext(), 3)
        characterList.adapter = characterListAdapter

        seasonList.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        seasonList.adapter = seasonListAdapter

        //TODO Get list of seasons from response
        seasonListAdapter.selectedSeasons = charactersViewModel.selectedSeasons
        seasonListAdapter.data = listOf(1, 2, 3, 4, 5)

        charactersViewModel.getCharacters()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)

        val searchItem: MenuItem = menu.findItem(R.id.search_action)
        val searchView = MenuItemCompat.getActionView(searchItem) as? SearchView
        searchView?.setOnCloseListener { true }

        val searchPlate =
            searchView?.findViewById(androidx.appcompat.R.id.search_src_text) as? EditText
        searchPlate?.hint = getString(R.string.search_hint)
        val searchPlateView =
            searchView?.findViewById(androidx.appcompat.R.id.search_plate) as? View
        searchPlateView?.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                android.R.color.transparent
            )
        )

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                charactersViewModel.nameSearch(newText ?: "")
                return false
            }
        })
    }

    @VisibleForTesting
    fun handleResult(viewState: ViewState?) {
        when (viewState) {
            is Loading -> {
                progress.show()
            }
            is Loaded<*> -> {
                progress.hide()
                characterListAdapter.data = viewState.data as List<Character>
                characterList.show()
                filterTitle.show()
                seasonList.show()
            }
            is LoadingError -> {
                progress.hide()
                val snack =
                    Snackbar.make(seasonList, getString(R.string.error), Snackbar.LENGTH_LONG)
                snack.setAction(getString(R.string.retry)) {
                    charactersViewModel.getCharacters()
                }
                snack.show()
            }
        }
    }

    private fun characterItemClicked(character: Character) {
        findNavController().navigate(CharactersFragmentDirections.showCharacterDetails(character))
    }
}
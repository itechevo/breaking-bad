package com.itechevo.breakingbad.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.itechevo.breakingbad.R
import com.itechevo.breakingbad.extension.spannableBold
import com.itechevo.breakingbad.ui.MainActivity
import kotlinx.android.synthetic.main.character_details_fragment.*

class CharacterDetailsFragment : Fragment() {

    private val args: CharacterDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity).supportActionBar?.title = getString(R.string.details)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.character_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val character = args.character

        context?.let {

            name.text = spannableBold(
                getString(R.string.name_prefix, character.name),
                "Name:", getColor(it, R.color.purple_700)
            )

            nickname.text = spannableBold(
                getString(R.string.nickname_prefix, character.nickname),
                "Nickname:", getColor(it, R.color.purple_700)
            )

            occupation.text = spannableBold(
                getString(R.string.occupation_prefix, character.occupation.joinToString()),
                "Occupation:", getColor(it, R.color.purple_700)
            )
            status.text = spannableBold(
                getString(R.string.status_prefix, character.status),
                "Status:", getColor(it, R.color.purple_700)
            )
            seasons.text = spannableBold(
                getString(R.string.seasons_prefix, character.appearance.joinToString()),
                "Season appearance:", getColor(it, R.color.purple_700)
            )
        }

        Glide.with(this)
            .load(character.img)
            .transform(RoundedCorners(20))
            .placeholder(R.drawable.breaking_bad_title_card)
            .into(characterImage)
    }
}
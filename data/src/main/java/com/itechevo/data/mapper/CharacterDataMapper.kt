package com.itechevo.data.mapper

import com.itechevo.data.model.CharacterData
import com.itechevo.domain.model.Character

fun CharacterData.toCharacter() =
    Character(char_id, name, occupation, img, status, nickname, appearance)
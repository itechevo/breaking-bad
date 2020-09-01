package com.itechevo.breakingbad.ui.model

sealed class ViewState

object Loading : ViewState()
class Loaded<T>(val data: T) : ViewState()
class LoadingError(val message: String? = null) : ViewState()

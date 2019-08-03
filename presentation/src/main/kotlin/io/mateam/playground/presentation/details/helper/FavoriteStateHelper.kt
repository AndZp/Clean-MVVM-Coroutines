package io.mateam.playground.presentation.details.helper

import com.tinder.StateMachine
import io.mateam.playground.presentation.utils.logDebug

class FavoriteStateHelper {
    private val tag = this::class.java.simpleName

    var onChangeToFavorite: (() -> Unit)? = null
    var onChangeToRegular: (() -> Unit)? = null

    fun init(inFavorite: Boolean) {
        if (inFavorite) {
            stateMachine.transition(Event.OnInitStateFavorite)
        } else {
            stateMachine.transition(Event.OnInitStateRegular)
        }
    }

    fun onFavoriteClick() {
        stateMachine.transition(Event.OnFavoriteClick)
    }

    private val stateMachine = StateMachine.create<State, Event, SideEffect> {
        initialState(State.Unknown)

        state<State.Unknown> {
            on<Event.OnInitStateFavorite> {
                logDebug(tag, "State.Unknown: Event.OnInitStateFavorite")
                transitionTo(State.InFavorite, SideEffect.ChangeToFavorite)
            }

            on<Event.OnInitStateRegular> {
                logDebug(tag, "State.Unknown: Event.OnInitStateRegular")
                transitionTo(State.Regular, SideEffect.ChangeToRegular)
            }
        }

        state<State.InFavorite> {
            on<Event.OnFavoriteClick> {
                logDebug(tag, "State.InFavorite: Event.OnFavoriteClick")
                transitionTo(State.Regular, SideEffect.ChangeToRegular)
            }
        }
        state<State.Regular> {
            on<Event.OnFavoriteClick> {
                logDebug(tag, "State.Regular: Event.OnFavoriteClick")
                transitionTo(State.InFavorite, SideEffect.ChangeToFavorite)
            }
        }
        onTransition {
            val validTransition = it as? StateMachine.Transition.Valid ?: return@onTransition
            when (validTransition.sideEffect) {
                SideEffect.ChangeToFavorite -> onChangeToFavorite?.invoke()
                SideEffect.ChangeToRegular -> onChangeToRegular?.invoke()
            }
        }
    }
}

private sealed class State {
    object Unknown : State()
    object InFavorite : State()
    object Regular : State()
}

private sealed class Event {
    object OnFavoriteClick : Event()
    object OnInitStateFavorite : Event()
    object OnInitStateRegular : Event()
}

private sealed class SideEffect {
    object ChangeToFavorite : SideEffect()
    object ChangeToRegular : SideEffect()
}
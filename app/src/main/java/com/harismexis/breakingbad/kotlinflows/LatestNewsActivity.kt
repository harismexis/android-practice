package com.harismexis.breakingbad.kotlinflows

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LatestNewsActivity : AppCompatActivity() {

    private lateinit var latestNewsViewModel: LatestNewsViewModel2 // getViewModel()
    private var uiStateJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This coroutine will run the given block when the lifecycle
        // is at least in the Started state and will suspend when
        // the view moves to the Stopped state
        uiStateJob = lifecycleScope.launchWhenStarted {
            // Triggers the flow and starts listening for values
            latestNewsViewModel.uiState.collect { uiState ->
                when (uiState) {
                    is LatestNewsUiState.Success -> { } // showFavoriteNews(uiState.news)
                    is LatestNewsUiState.Error -> { } // showError(uiState.exception)
                }
            }
        }

        // NEVER collect a flow from the UI using launch or the launchIn
        // Below code NOT good
        lifecycleScope.launch {
            // Triggers the flow and starts listening for values
            latestNewsViewModel.uiState.collect { uiState ->
                when (uiState) {
                    is LatestNewsUiState.Success -> { } // showFavoriteNews(uiState.news)
                    is LatestNewsUiState.Error -> { } // showError(uiState.exception)
                }
            }
        }

    }

    // Instead of starting in onCreate, we can start in onStart. But here they use
    // launch, probably because it starts collecting form onStart()
    override fun onStart() {
        super.onStart()
        uiStateJob = lifecycleScope.launch {
            latestNewsViewModel.uiState.collect { uiState ->  }
        }
    }

    override fun onStop() {
        uiStateJob?.cancel()
        super.onStop()
    }

}
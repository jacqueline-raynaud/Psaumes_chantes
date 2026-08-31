package fr.quinquenaire.psaumes_chantes.data.player

import android.content.Context
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Enveloppe autour d'un unique lecteur Media3 (ExoPlayer) pour toute l'application.
 * Les mp3 restent en streaming distant : rien n'est jamais téléchargé sur l'appareil.
 */
@Singleton
class PlayerManager @Inject constructor(
    @ApplicationContext context: Context,
) {
    private val player: ExoPlayer = ExoPlayer.Builder(context).build()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var tickerJob: Job? = null
    private var fileNameCourant: String? = null

    private val _etat = MutableStateFlow(EtatLecture())
    val etat: StateFlow<EtatLecture> = _etat.asStateFlow()

    init {
        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _etat.update { it.copy(enLecture = isPlaying) }
                if (isPlaying) demarrerTicker() else arreterTicker()
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                _etat.update { it.copy(enChargement = playbackState == Player.STATE_BUFFERING) }
                if (playbackState == Player.STATE_READY) {
                    val duree = player.duration
                    if (duree != C.TIME_UNSET) {
                        _etat.update { it.copy(dureeMs = duree) }
                    }
                }
            }
        })
    }

    fun jouer(fileName: String, url: String) {
        if (fileNameCourant != fileName) {
            fileNameCourant = fileName
            _etat.value = EtatLecture(fileNameEnCours = fileName, enChargement = true)
            player.setMediaItem(MediaItem.fromUri(url))
            player.prepare()
        }
        player.play()
    }

    fun basculerLecturePause() {
        if (player.isPlaying) player.pause() else player.play()
    }

    fun rejouer() {
        player.pause()
        player.seekTo(0L)
        _etat.update { it.copy(positionMs = 0L) }
        player.play()
    }

    fun arreter() {
        player.pause()
        player.seekTo(0L)
        _etat.update { it.copy(positionMs = 0L) }
    }

    fun reculer(deltaMs: Long = DELTA_SEEK_MS) {
        player.seekTo((player.currentPosition - deltaMs).coerceAtLeast(0L))
        _etat.update { it.copy(positionMs = player.currentPosition) }
    }

    fun avancer(deltaMs: Long = DELTA_SEEK_MS) {
        val duree = player.duration.takeIf { it != C.TIME_UNSET } ?: return
        player.seekTo((player.currentPosition + deltaMs).coerceAtMost(duree))
        _etat.update { it.copy(positionMs = player.currentPosition) }
    }

    private fun demarrerTicker() {
        if (tickerJob?.isActive == true) return
        tickerJob = scope.launch {
            while (isActive) {
                _etat.update { it.copy(positionMs = player.currentPosition) }
                delay(INTERVALLE_TICK_MS)
            }
        }
    }

    private fun arreterTicker() {
        tickerJob?.cancel()
        tickerJob = null
        _etat.update { it.copy(positionMs = player.currentPosition) }
    }

    private companion object {
        const val DELTA_SEEK_MS = 15_000L
        const val INTERVALLE_TICK_MS = 500L
    }
}

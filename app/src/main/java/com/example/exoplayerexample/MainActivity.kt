package com.example.exoplayerexample

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.isVisible
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.AspectRatioFrameLayout.ResizeMode
import androidx.media3.ui.PlayerView
import com.example.exoplayerexample.data.Video
import com.example.exoplayerexample.data.data
import com.example.exoplayerexample.ui.theme.ExoPlayerExampleTheme
import java.util.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExoPlayerExampleTheme {
                List(
                    modifier = Modifier.padding(20.dp)
                )
            }
        }
    }
}

@kotlin.OptIn(ExperimentalFoundationApi::class)
@Composable
fun List(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {

        items(
            count = data.size,
        ) { index ->
            VideoPlayer(modifier = Modifier, data[index])
        }
    }
}

@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    video: Video,
) {
    val context = LocalContext.current
    val exoPlayer = ExoPlayer.Builder(context).build().apply {
        setMediaItem(MediaItem.fromUri(video.url))
        repeatMode = ExoPlayer.REPEAT_MODE_OFF
        prepare()
    }
    Box(
        modifier = modifier
            .height(420.dp)
    )
    {
        VideoPlayer(
            modifier = modifier.height(420.dp),
            player = exoPlayer,
        )
    }
}

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    player: ExoPlayer,
    modifier: Modifier = Modifier,
    resizeMode: @ResizeMode Int = AspectRatioFrameLayout.RESIZE_MODE_ZOOM,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Green),
    ) {
        AndroidView(
            modifier = Modifier.align(Alignment.BottomCenter),
            factory = {
                PlayerView(it).apply {
//                providePlayerView(it).apply {
                    Log.e("videoProvider", "MyStyledPlayerView init")
                    setUseController(useController)
                    this.player = player
                    keepScreenOn = true
                    this.resizeMode = resizeMode

                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                    )

                    // to show a boarders
                    val rnd = Random()
                    val color = android.graphics.Color.argb(
                        255,
                        rnd.nextInt(256),
                        rnd.nextInt(256),
                        rnd.nextInt(256)
                    )
                    clipToOutline = true
                    setBackgroundColor(color)

                    // the view ignores the padding of the parent without at least 1dp padding on it's own
                    setPadding(0, 0, 0, 1)
                    setupDefaultControlButtons()
                }
            },
        )
    }
}


@OptIn(UnstableApi::class)
private fun PlayerView.setupDefaultControlButtons() {
    setShowRewindButton(false)
    setShowFastForwardButton(false)
    setShowPreviousButton(false)
    setShowNextButton(false)
    setShowFastForwardButton(false)
}

private class MyStyledPlayerView(content: Context) : PlayerView(content) {

    var size: Int = 0
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightMeasureSpec1 = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec1)
    }
}


@OptIn(UnstableApi::class)
@SuppressLint("InflateParams")
private fun providePlayerView(context: Context): PlayerView {
    val view = LayoutInflater.from(context).inflate(R.layout.feed_list_video_view, null, false)
    return view.findViewById(R.id.styled_player_view)
}

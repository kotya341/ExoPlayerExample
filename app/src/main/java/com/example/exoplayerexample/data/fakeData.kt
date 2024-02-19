package com.example.exoplayerexample.data

val data = listOf(
    Video("https://d7c4jjeuqag9w.cloudfront.net/videos/204aa06ec02120985615e93811afd63e66cd8b11f46011a1ef9f7289821349ae/0/540p"),
    Video("https://d7c4jjeuqag9w.cloudfront.net/videos/40ecbb12ee3ef58bb4fdd28a6865b1e4b96a0154cd067e4fd31f4fc33a74cb8e/0/540p"),
    Video("https://d7c4jjeuqag9w.cloudfront.net/videos/8b7653e875bd4c33b9485e236fdf44963f9d086930457e065ad8de7d7684cf5f/0/540p"),
)

data class Video(val url: String)
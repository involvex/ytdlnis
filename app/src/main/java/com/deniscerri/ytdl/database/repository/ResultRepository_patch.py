import sys

file_path = "D:/repos/ytdlnis/app/src/main/java/com/deniscerri/ytdl/database/repository/ResultRepository.kt"
with open(file_path, "r", encoding="utf-8") as f:
    content = f.read()

old_block = """        // For album/playlist search, we must use yt-dlp to get _type and playlistURL
        val forceYTDLP = searchType == "album" || searchType == "playlist"

        val rawItems = if (forceYTDLP) {
            // Use yt-dlp with configured search engine prefix (ytsearch or ytsearchmusic)
            ytdlpUtil.getFromYTDL(inputQuery) {}
        } else {
            // Use NewPipe first, fallback to yt-dlp
            val res = when (engine) {
                "ytsearch" -> newPipeUtil.search(inputQuery)
                "ytsearchmusic" -> newPipeUtil.searchMusic(inputQuery)
                else -> Result.failure(Throwable("Unsupported search engine: $engine"))
            }
            if (res.isSuccess) res.getOrNull()!! else ytdlpUtil.getFromYTDL(inputQuery) {}
        }"""

new_block = """        // For playlist search, we must use yt-dlp to get _type and playlistURL
        // For album search with ytsearchmusic, use NewPipe to get album playlists
        val forceYTDLP = searchType == "playlist" || (searchType == "album" && engine != "ytsearchmusic")

        val rawItems = if (forceYTDLP) {
            // Use yt-dlp with configured search engine prefix (ytsearch or ytsearchmusic)
            ytdlpUtil.getFromYTDL(inputQuery) {}
        } else {
            // Use NewPipe first, fallback to yt-dlp
            val res = when (engine) {
                "ytsearch" -> newPipeUtil.search(inputQuery)
                "ytsearchmusic" -> {
                    if (searchType == "album") newPipeUtil.searchMusicAlbums(inputQuery)
                    else newPipeUtil.searchMusic(inputQuery)
                }
                else -> Result.failure(Throwable("Unsupported search engine: $engine"))
            }
            if (res.isSuccess) res.getOrNull()!! else ytdlpUtil.getFromYTDL(inputQuery) {}
        }"""

content = content.replace(old_block, new_block)

with open(file_path, "w", encoding="utf-8") as f:
    f.write(content)

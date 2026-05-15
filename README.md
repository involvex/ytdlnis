<h1 align="center">
	yt-mp3-downloader
</h1>

<h3 align="center">
	A free and open source video/audio downloader using yt-dlp for Android 7.0 and above.
</h3>
<h4 align="center">
	Based on YTDLnis by Denis Çerri
</h4>

## 💡 Features:

- Download audio/video files from more than <a href="https://github.com/yt-dlp/yt-dlp/blob/master/supportedsites.md">1000 websites</a>
- Process playlists
	- Edit every playlist item separately just like in a normal download item
	- Select a common format for all items and/or select multiple audio formats in case you are downloading them as a video
	- Select a download path for all items
	- Select a filename template for all items
	- Batch update download type to audio/video/custom command in one click
- Queue downloads and schedule them by date and time
	- You can also schedule multiple items at the same time
- Download multiple items at the same time
- Use custom commands and templates or use yt-dlp with the built-in terminal
	- You can backup and restore templates so you can share them with your buddies
- Supports cookies. Log in with your accounts and download private/unavailable videos, unlock premium formats etc.
- Cut videos based on timestamps and video chapters (experimental yt-dlp feature)
	- You can make unlimited cuts
- Remove SponsorBlock elements from downloaded items
	- Embed them as a chapters in your video 
- Embed subtitles/metadata/chapters etc
- Modify metadata such as title and author
- Split item into separate files depending on its chapters
- Select different download formats
- Bottom card right from the share menu, no need to open the app 
	- You can create a txt file and fill it with links/playlists/search queries separate by a new line and the app will process them
- Search or insert a link from the app
	- You can stack searches so you can process them at the same time
- Log downloads in case of problems
- Re-download cancelled or failed downloads
	- You can use gestures to swipe left to redownload and right to delete
	- You can long click the redownload button in the details sheet to show the download card for more functionality
- Incognito mode when you don't want to save a download history or logs
- Quick download mode
	- Download immediately without having to wait for data to process. Turn off the bottom card and it will instantly start
- Open / share downloaded files right from the finished notification
- Most yt-dlp features are implemented, suggestions are welcome
- Material You interface
- Theming options
- Backup and restore features
- MVVM architecture with WorkManager
- **New:** Album downloads with search type filtering

## 🤖 Connect with third-party apps using intents

You can use intents to push commands to the app to run downloads without user interaction.
Accepted variables:

<b>TYPE</b> -> it can be: audio,video,command <br/>
<b>BACKGROUND</b> -> it can be: true,false. If its true the app won't show the download card no matter what and run the download in the background <br/>

### An example of downloading an audio item in the background with Tasker
1. Create Send Intent task
2. Action: android.intent.action.SEND
3. Cat: Default
4. Mime Type: text/*
5. Extra: android.intent.extra.TEXT:url (instead of "url" write the URL of the video you want to download)
6. Extra: TYPE:audio
7. Extra: BACKGROUND:true

## 📦 Package name

The app's package name is **com.involvex.ytmp3dlp**.

## 📄 License

[GNU GPL v3.0](LICENSE)

Except for the source code licensed under the GPLv3 license, all other parties are prohibited from using the "yt-mp3-downloader" name as a downloader app, and the same is true for its derivatives. Derivatives include but are not limited to forks and unofficial builds.

## 🙏 Thanks

- [yt-dlp](https://github.com/yt-dlp/yt-dlp) and its contributors for making this tool possible.
- [youtubedl-android](https://github.com/yausername/youtubedl-android) for porting yt-dlp to Android.
- [JunkFood02](https://github.com/JunkFood02) for providing Android binaries (ffmpeg, aria2c).
- Original YTDLnis by [Denis Çerri](https://github.com/deniscerri).
- And to many other contributors.

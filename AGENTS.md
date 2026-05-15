# Agents Guidelines for YTDLnis

**Last Updated:** 2026-05-15  
**Project:** YTDLnis - Android video/audio downloader using yt-dlp  
**Primary Language:** Kotlin  
**Platform:** Android (minSdk 24, targetSdk 36)  
**Architecture:** MVVM with Jetpack components

---

## 📋 Table of Contents

1. [Project Overview](#project-overview)
2. [Useful Commands](#useful-commands)
3. [Technologies & Stack](#technologies--stack)
4. [Architecture & Structure](#architecture--structure)
5. [Best Practices](#best-practices)
6. [Coding Standards](#coding-standards)
7. [Testing](#testing)
8. [Security & Privacy](#security--privacy)
9. [Contribution Guidelines](#contribution-guidelines)
10. [AI Contribution Policy](#ai-contribution-policy)
11. [Troubleshooting](#troubleshooting)

---

## 📖 Project Overview

YTDLnis is a free and open-source video/audio downloader for Android 7.0+ that uses yt-dlp as its backend. The app provides a Material You interface with extensive yt-dlp functionality, supporting downloads from 1000+ websites.

**Key Features:**
- Audio/video downloads from 1000+ websites via yt-dlp
- Playlist processing with individual item editing
- Queue management and scheduling
- Custom command templates
- Terminal mode for CLI-like interface
- Cookie support for private content
- SponsorBlock integration
- Subtitle/metadata embedding
- MVVM architecture with WorkManager

**Package:** `com.deniscerri.ytdl`

---

## 💻 Useful Commands

### Building the Project

```bash
# Clean build
./gradlew clean

# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Build all variants (including flavors)
./gradlew assemble

# Build specific flavor
./gradlew assembleGithubDebug
./gradlew assembleFossRelease
```

### Installation

```bash
# Install debug APK on connected device/emulator
./gradlew installDebug

# Install release APK
./gradlew installRelease

# Install specific flavor
./gradlew installGithubDebug
```

### Testing

```bash
# Run unit tests
./gradlew test

# Run instrumentation tests (requires device/emulator)
./gradlew connectedAndroidTest

# Run specific test class
./gradlew test --tests "com.deniscerri.ytdl.ExampleUnitTest"

# Run with coverage
./gradlew jacocoTestReport
```

### Code Quality

```bash
# Run lint checks
./gradlew lint

# Run lint on specific variant
./gradlew lintGithubDebug

# Fix formatting issues (if using ktlint or spotless)
./gradlew ktlintFormat

# Run detekt (if configured)
./gradlew detekt
```

### Dependency Management

```bash
# List all dependencies
./gradlew dependencies

# List dependencies for specific configuration
./gradlew app:dependencies --configuration debugCompileClasspath

# Update dependency versions (manual)
# Check for updates on libraries and update build.gradle files
```

### Project Analysis

```bash
# Generate build scan
./gradlew build --scan

# Check for outdated dependencies
./gradlew dependencyUpdates

# Generate APK size report
./gradlew app:assembleRelease
```

### Fastlane (for releases)

```bash
# Run all fastlane lanes
fastlane all

# Deploy to GitHub Releases
fastlane github

# Deploy to IzzyOnDroid
fastlane izzy

# Deploy to F-Droid
fastlane fdroid

# Supply track (alpha/beta/internal)
fastlane supply
```

### Git Workflow

```bash
# Create a new branch
git checkout -b feature/description

# Commit with conventional commit
# Use the git-commit agent for standardized messages

# Push to remote
git push origin branch-name

# Create pull request
gh pr create --title "Title" --body "Description"
```

---

## 🛠 Technologies & Stack

### Core Platform
- **Android SDK:** 36 (target), 24 (min)
- **Kotlin:** 2.3.0+
- **Java:** 17 (source/target compatibility)

### UI & Graphics
- **Jetpack Compose:** 1.10.1 (with Compose Compiler 1.10.1)
- **Material Design:** Material3 1.4.0 (Android version)
- **View System:** ViewBinding enabled (legacy views coexist with Compose)
- **ConstraintLayout:** 2.2.1
- **RecyclerView:** 1.4.0
- **SwipeRefreshLayout:** 1.2.0
- **CardView:** 1.0.0
- **CoordinatorLayout:** 1.3.0

### Architecture Components
- **MVVM:** Model-View-ViewModel pattern
- **Room:** 2.8.4 (SQLite ORM with Paging support)
- **ViewModel:** lifecycle-viewmodel-ktx 2.10.0
- **LiveData:** lifecycle-livedata-ktx 2.10.0
- **WorkManager:** 2.11.0 (background tasks, alarms)
- **Navigation:** navigation-fragment-ktx 2.9.6
- **Paging:** paging-runtime-ktx 3.3.6

### Concurrency
- **Kotlin Coroutines:** 1.10.2
- **RxJava:** rxandroid 2.1.1 (legacy usage in some modules)

### Networking & Data
- **OkHttp:** 5.3.2
- **Gson:** 2.13.2
- **Kotlin Serialization:** 1.9.0
- **Picasso:** 2.71828 (image loading)
- **NewPipeExtractor:** v0.26.1 (alternative extractor)

### Media Playback
- **ExoPlayer (Media3):** 1.9.0
  - media3-exoplayer
  - media3-exoplayer-dash
  - media3-exoplayer-hls
  - media3-exoplayer-rtsp
  - media3-ui

### File Storage & Utilities
- **Storage Access Framework:** anggrayudi:storage:1.5.5
- **Apache Commons:** commons-io 2.5, commons-compress 1.12
- **Markdown Rendering:** markwon 4.6.2
- **Fast Scroll:** me.zhanghai.android.fastscroll 1.3.0
- **Highlight View:** com.neoutils.highlight 2.3.0
- **Swipe Decorator:** it.xabaras.android:recyclerview-swipedecorator 1.4
- **WebView:** accompanist-webview 0.36.0
- **Shimmer Effect:** facebook.shimmer 0.5.0

### yt-dlp Integration
- **youtubedl-android library:** io.github.junkfood02:library:0.18.1
- **ffmpeg binary:** io.github.junkfood02:ffmpeg:0.17.2
- **aria2c binary:** io.github.junkfood02:aria2c:0.18.1

### Testing
- **JUnit:** 4.13.2
- **AndroidX Test:** junit-ktx 1.3.0, espresso-core 3.7.0
- **Truth:** 1.4.5 (assertions)
- **Room Testing:** room-testing 2.8.4

### Development Tools
- **KSP:** 2.3.4 (Kotlin Symbol Processing)
- **Parcelize:** Kotlin plugin
- **Serialization:** Kotlin plugin
- **Compose Compiler:** 1.10.1

### Build Tools
- **AGP (Android Gradle Plugin):** 8.13.2
- **Gradle:** 8.x (wrapper version may vary)
- **Kotlin Gradle Plugin:** 2.3.0
- **Secrets Gradle Plugin:** 2.0.1

---

## 🏗 Architecture & Structure

### Project Modules
```
ytdlnis/
├── app/              # Main Android application module
├── common/           # Shared code (if present)
├── library/          # Custom libraries (if present)
├── ffmpeg/           # FFmpeg wrapper module (if present)
└── build.gradle      # Root build configuration
```

### App Package Structure
```
com.deniscerri.ytdl/
├── App.kt                    # Application class (initialization)
├── MainActivity.kt          # Main activity
├── work/                    # WorkManager workers (background tasks)
│   ├── DownloadWorker.kt
│   ├── TerminalDownloadWorker.kt
│   ├── UpdateYTDLWorker.kt
│   └── ...
├── ui/                      # UI layer (Fragments, Activities, Adapters)
│   ├── HomeFragment.kt
│   ├── more/                # Settings, terminal, cookies, etc.
│   ├── downloadcard/        # Download configuration dialogs
│   ├── adapter/             # RecyclerView adapters
│   └── ...
├── database/                # Room database & ViewModels
│   ├── viewmodel/           # ViewModel classes
│   ├── entities/            # Room entities
│   ├── dao/                 # Data Access Objects
│   └── repository/          # Repository pattern
├── util/                    # Utility classes
│   ├── FileUtil.kt
│   ├── FormatUtil.kt
│   ├── ThemeUtil.kt
│   ├── NotificationUtil.kt
│   └── extractors/          # Video extraction (yt-dlp, NewPipe, etc.)
└── receiver/                # Broadcast receivers
```

### Key Patterns

1. **MVVM Architecture:**
   - `Fragment`/`Activity` observes `ViewModel`
   - `ViewModel` exposes `LiveData` or `StateFlow`
   - Repository pattern abstracts data sources

2. **WorkManager Integration:**
   - Background downloads use `DownloadWorker`
   - Scheduled downloads use `AlarmScheduler` + `CancelScheduledDownloadWorker`
   - Library updates use `UpdateYTDLWorker`
   - Events communicated via `WorkerEventBus`

3. **Navigation:**
   - Primary: Jetpack Navigation Component (2.9.6)
   - Fragment-based navigation with safe args

4. **Persistence:**
   - Room database with migrations
   - SharedPreferences for settings (`PreferenceManager`)
   - Filesystem for downloads, logs, templates

5. **Network Operations:**
   - OkHttp for direct HTTP calls
   - yt-dlp library for extraction (primary)
   - NewPipeExtractor as alternative

---

## ✅ Best Practices

### General Guidelines
1. **Read the CONTRIBUTING.MD** before starting work
2. **Follow the AI Contribution Policy** explicitly (see below)
3. **Do not break existing functionality** - maintain backward compatibility
4. **Write meaningful commit messages** using conventional commits
5. **Test on real devices** with different Android versions (7.0+)
6. **Respect user privacy** - no telemetry, no data collection
7. **Keep the app lightweight** - avoid unnecessary dependencies
8. **License compliance** - GPLv3 only, no proprietary code

### Code Quality
1. **Kotlin Idioms:**
   - Use nullable types where appropriate
   - Prefer `val` over `var` (immutability)
   - Use extension functions for utility methods
   - Leverage coroutines over callbacks

2. **Null Safety:**
   - Never use `!!` operator unless absolutely necessary
   - Use safe calls (`?.`) and Elvis operator (`?:`)
   - Annotate nullable parameters/returns with `@Nullable`/`@NonNull` if needed

3. **Error Handling:**
   - Use try-catch for expected exceptions
   - Log errors meaningfully (use `Log.e()` with context)
   - Show user-friendly messages, not stack traces
   - Use `ExecuteException` wrapper for yt-dlp errors

4. **Threading:**
   - Offload heavy work to `Dispatchers.IO`
   - Use `withContext(Dispatchers.Main)` for UI updates
   - Never block the main thread

5. **Resource Management:**
   - Close streams, cursors, and database resources in finally blocks or use try-with-resources
   - Release WebView resources properly
   - Use `applicationContext` when appropriate to avoid leaks

6. **Memory leaks:**
   - Avoid holding references to `Context` in static fields
   - Use `WeakReference` for long-running operations
   - Clear observers in `onCleared()` of ViewModels

### Android-Specific

1. **Lifecycle Awareness:**
   - Observe LiveData/Flows in `onCreate` or `onViewCreated`
   - Remove observers in `onDestroy`/`onDestroyView`
   - Use `repeatOnLifecycle` for flows

2. **Permissions:**
   - Request only necessary permissions
   - Explain why permissions are needed
   - Handle permission denial gracefully

3. **Notifications:**
   - Create notification channels for different types
   - Use proper importance levels
   - Provide actions (pause, cancel, open)

4. **Background Work:**
   - Use `WorkManager` for deferrable background tasks
   - Set appropriate constraints (network, charging)
   - Use `ForegroundService` for long-running downloads

5. **Configuration Changes:**
   - Use ViewModel to retain data across rotations
   - Handle locale changes appropriately
   - Save/restore instance state for UI state

---

## 📐 Coding Standards

### Kotlin Style
- Follow official [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use 4-space indentation (no tabs)
- Maximum line length: 100-120 characters
- Class/interface names: `PascalCase`
- Function/variable names: `camelCase`
- Constants: `UPPER_SNAKE_CASE`
- Package names: all lowercase, no underscores

### File Organization
- One top-level class/interface per file
- Order: package → imports → class/object → companion object
- Group related functions together
- Use region comments for large files (optional)

### Imports
- Remove unused imports (IDE will help)
- Use wildcard imports (`import foo.*`) only for static imports
- Order: java → kotlin → android → third-party → project-specific
- Alphabetize within each section

### Naming Conventions
- `Activity` classes end with `Activity` (e.g., `SettingsActivity`)
- `Fragment` classes end with `Fragment` (e.g., `HomeFragment`)
- `ViewModel` classes end with `ViewModel`
- `Adapter` classes end with `Adapter`
- `Worker` classes end with `Worker`
- `Util` classes should be stateless; consider extension functions

### Comments
- **Public APIs:** KDoc with `@param`, `@return`, `@throws`
- **Complex logic:** Inline comment explaining "why", not "what"
- **Avoid obvious comments:** Code should be self-documenting
- **TODO comments:** Include issue/ticket number if applicable

### String Resources
- ** NEVER hardcode user-facing strings** - always use `strings.xml`
- Use string resources with placeholders: `<string name="format">%1$s %2$d</string>`
- For dynamic content, use `getString(R.string.xxx, arg1, arg2)`
- Context-specific strings go in `values-<language>/strings.xml`

### Color & Dimensions
- **NEVER hardcode colors** - use `colors.xml` and `ContextCompat.getColor()`
- **NEVER hardcode dimensions** - use `dimens.xml`
- Use theme attributes (`?attr/...`) for Material Design colors

### Logging
- Use `Log.d()`, `Log.i()`, `Log.w()`, `Log.e()` with a TAG constant
- TAG = class name: `private val TAG = this::class.java.simpleName`
- Never log sensitive data (URLs with tokens, cookies, user info)
- Remove verbose logging before committing (or use `BuildConfig.DEBUG` guards)

### ProGuard / R8
- Keep rules in `proguard-rules.pro`
- Document why each rule is needed with a comment
- Test release builds to ensure no crashes from obfuscation

---

## 🧪 Testing

### Test Types
1. **Unit Tests:** `app/src/test/java/`
   - Test business logic, utilities, models
   - Mock dependencies with MockK or Mockito
   - Fast, no Android dependencies

2. **Instrumentation Tests:** `app/src/androidTest/java/`
   - Test UI, database, WorkManager
   - Requires device/emulator
   - Use Espresso for UI interactions

### Running Tests
```bash
./gradlew test                    # Unit tests only
./gradlew connectedAndroidTest   # Both unit and instrumentation
./gradlew testDebugUnitTest      # Specific variant
```

### Test Best Practices
- **Write tests for new features** before or alongside code
- **Test edge cases and error scenarios**
- **Use descriptive test names:** `methodName_condition_expectedResult()`
- **Mock external dependencies:** yt-dlp, network, file system
- **Keep tests independent** - no shared state
- **Use factories and builders** for test data

### Example Test Structure
```kotlin
class DownloadWorkerTest {
    @Test
    fun `download with valid url should succeed`() = runTest {
        // Arrange
        val worker = TestListenableWorkerBuilder<DownloadWorker>(context)
            .setInputData(createInputData("https://valid.url"))
            .build()
        
        // Act
        val result = worker.startWork()
        
        // Assert
        assertThat(result).isEqualTo(Result.success())
    }
}
```

---

## 🔒 Security & Privacy

### Privacy Policy Requirements (from SECURITY.md)
- **No data collection:** The app does not gather usage information
- **No user profiles:** No tracking or profiling
- **No third-party sharing:** Data stays on device
- **Permissions:** Only media access for file operations
- **User control:** Users can disable storage features (logging, cookies)

### Security Best Practices
1. **Secrets Management:**
   - API keys, signatures in `keystore.properties` (git-ignored)
   - Use `BuildConfig` for compile-time constants
   - Never commit secrets to version control

2. **Network Security:**
   - Validate all URLs before processing
   - Use HTTPS when possible
   - Sanitize user input to prevent injection attacks
   - yt-dlp handles most site-specific security

3. **File Operations:**
   - Validate paths to prevent directory traversal
   - Use app-specific directories for downloads
   - Check file permissions before access
   - Clean up temporary files

4. **WebView Security:**
   - Disable JavaScript unless required
   - Use `WebViewClient` to handle navigation securely
   - Clear cache/cookies on logout (if implemented)

5. **Code Obfuscation:**
   - ProGuard/R8 enabled for release builds
   - Keep rules for reflection-heavy libraries
   - Test release builds thoroughly

6. **Dependency Security:**
   - Use `gradle dependencies` to audit
   - Update dependencies regularly
   - Check for CVEs in third-party libraries
   - Prefer reputable sources (Maven Central, Google)

### Signature Verification
- Releases are signed with Denis Cerri's certificate
- Fingerprint provided in README.md
- Users should verify signatures of third-party builds

---

## 🤝 Contribution Guidelines

### Before You Start
1. **Search existing issues** to avoid duplicates
2. **Read the issue templates** carefully
3. **Provide complete information** for bug reports:
   - App version
   - Device model & Android version
   - Steps to reproduce
   - Expected vs actual behavior
   - Logs or screenshots
4. **For feature requests:** Ensure the feature is within scope (GUI for yt-dlp)

### Pull Request Process
1. **Comment on the issue** you're addressing
2. **Fork and create a feature branch**
3. **Follow coding standards** (see above)
4. **Ensure tests pass** locally
5. **Update documentation** if needed (README, changelog)
6. **Submit PR with clear description:**
   - What changed
   - Why it changed
   - How to test
   - Screenshots for UI changes
7. **Be responsive** to review feedback

### Code Review
- All PRs require at least one review
- Address review comments promptly
- Squash commits if requested
- Rebase if necessary to keep history clean

### Commit Guidelines
- Use [Conventional Commits](https://www.conventionalcommits.org/):
  ```
  feat: add batch download scheduler
  fix: resolve crash on empty playlist
  chore: update dependencies
  docs: update README
  refactor: simplify format selection logic
  ```
- Commits should be atomic and focused
- First line ≤ 50 characters, body wrapped at 72

---

## 🤖 AI Contribution Policy

From [CONTRIBUTING.MD](CONTRIBUTING.MD) Section: "AI Contribution Policy"

### ✅ ALLOWED
- Using AI tools for **assistance** (auto-complete, suggestions)
- Generating boilerplate code (after review)
- Debugging help
- Documentation drafting

### ❌ REQUIRED
1. **Human Responsibility:** You must understand **every line** of submitted code
2. **No "Dry" Submissions:** Code must be:
   - Locally compiled
   - Tested on device/emulator
   - Verified to work
3. **Disclosure:** If significant portion (new module, complex function) is AI-generated, **state it in PR description**
4. **Licensing Compliance:** Ensure AI tool's output complies with GPLv3
5. **Succinctness:** Prune unnecessary comments/boilerplate

### ⚠️ CONSEQUENCES
Failure to follow guidelines → PR closed without review

### For AI Agents (like OpenCode, Claude, etc.)
When contributing autonomously:
- **Always disclose AI generation** in commit messages or PR body
- **Include test evidence** (logs, screenshots, test results)
- **Explain rationale** for design decisions
- **Do not submit untested code**
- **Respect the project's architecture** (MVVM, WorkManager, etc.)

---

## 🐛 Troubleshooting

### Common Build Issues

**Issue:** `Execution failed for task ':app:mergeDebugResources'.`  
**Cause:** Resource conflict or missing dependency  
**Solution:** 
```bash
./gradlew clean
./gradlew :app:dependencies | grep conflict  # Check for conflicts
```

**Issue:** `Kotlin version X is too old`  
**Cause:** Gradle wrapper using old Kotlin  
**Solution:** Update Kotlin version in root `build.gradle`

**Issue:** `Could not resolve all files for configuration`  
**Cause:** Repository unreachable or version typo  
**Solution:** Check internet, verify versions in `build.gradle`

**Issue:** `AGP version mismatch`  
**Cause:** Gradle wrapper incompatible with AGP  
**Solution:** Use Android Studio's "Gradle Settings" to sync versions

**Issue:** `ProGuard rules removing needed code`  
**Solution:** Add keep rules, test with `-dontobfuscate` first

### Runtime Issues

**Issue:** App crashes on startup  
**Check:**
- `logcat` for stack trace
- Room database migrations (if schema changed)
- ProGuard rules for reflection
- Missing permissions

**Issue:** Downloads fail silently  
**Check:**
- Storage permissions
- yt-dlp library initialization (`RuntimeManager`)
- Network connectivity
- Worker constraints (battery, network)
- Logs in `DownloadLogFragment`

**Issue:** Formats not showing  
**Check:**
- yt-dlp version (`UpdateYTDLWorker`)
- NewPipeExtractor compatibility
- Network calls to extractors (`YTDLPUtil.kt`)
- Logging enabled in settings

**Issue:** WebView crashes or blank  
**Fix:**
- Enable hardware acceleration in manifest
- Clear cache/cookies
- Update WebView on device
- Check JavaScript settings

---

## 📚 Additional Resources

- **Main README:** [README.md](README.md)
- **Contributing Guide:** [CONTRIBUTING.MD](CONTRIBUTING.MD)
- **Security Policy:** [SECURITY.md](SECURITY.md)
- **Issue Tracker:** https://github.com/deniscerri/ytdlnis/issues
- **Discord:** https://discord.gg/WW3KYWxAPm
- **Telegram:** https://t.me/ytdlnis
- **Website:** https://ytdlnis.org

---

## 🔄 Workflow Summary for Agents

1. **Understand the task** → Check issue, discuss in Discord/Telegram
2. **Plan implementation** → Consider architecture, existing patterns
3. **Write code** → Follow best practices, write tests
4. **Test locally** → On emulator/device, `./gradlew test`
5. **Review yourself** → Run lint, check code quality
6. **Submit PR** → Clear description, disclose AI use
7. **Address feedback** → Be responsive, make requested changes
8. **Await merge** → Do not self-merge

---

**Remember:** YTDLnis is a privacy-respecting, open-source project. Every contribution should align with these values. When in doubt, ask in the community channels.

*Generated for agent assistance. Last updated: 2026-05-15*
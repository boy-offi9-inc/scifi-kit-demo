# SciFiKit Demo

Demo app for [SciFiKit](https://github.com/boy-offi9-inc/scifi-ui-kit), a library of sci-fi HUD components for Android drawn on Canvas.

The app has two screens that show the same components built two ways:

- **Views demo:** XML layouts with `HudFrameView`, `GlitchTextView` and `HoloRadarView`
- **Compose demo:** the same screen with `HudFrame`, `GlitchText` and `HoloRadar`

Tap any glitch text to trigger a glitch. On the Views screen, the amber button opens the Compose demo. On the Compose screen, amber replays the decode and green goes back.

## Get the app

Every push builds a debug APK. Open the latest run in the **Actions** tab and download `scifikit-demo-debug` from **Artifacts**.

## Build it yourself

Open the project in Android Studio and run the `app` configuration, or from the command line:

```bash
gradle :app:assembleDebug
```

Requires JDK 17.

## How it uses the library

The app depends on SciFiKit through JitPack, the same way any other app would. See `app/build.gradle.kts`:

```kotlin
implementation("com.github.boy-offi9-inc:scifi-ui-kit:v0.2.1")
```

The version is pinned to the `v0.2.1` release. To try unreleased changes, use `main-SNAPSHOT` instead, which builds the latest commit on `main`.

The library keeps Jetpack Compose as `compileOnly`, so this app declares its own Compose dependencies.

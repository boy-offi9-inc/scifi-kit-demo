# SciFiKit Demo

Demo app for [SciFiKit](https://github.com/boy-offi9-inc/scifi-ui-kit), a library of sci-fi HUD components for Android drawn on Canvas.

The app has two screens that show the same components built two ways:

- **Views demo:** XML layouts with every component in the kit
- **Compose demo:** the same components as composables

Each screen is split into pages, chosen from the tab strip under the title:

| Page | Shows |
|---|---|
| HUD | Frame variants: default, chamfered, no sweep, no scanlines |
| TEXT | Decode, glitch and touch modes, each with a dropdown |
| RADAR | Radar modes with a dropdown |
| BUTTON | Neon button styles with a dropdown, plus a disabled button |
| CARD | Sci-fi card in all three styles: panel, outline, plain |
| STATUS | Neon progress, gauges, badges and alerts |
| THEME | Palettes, applied through the theme |
| LIVE | A panel you tap to change every setting at runtime |
| ALL | Every page above on one screen |

Glitch text stays still until you touch it, apart from the glitch sample on the TEXT page, so each feature can be seen on its own. Tap any glitch text to trigger a glitch. `COMPOSE >` on the Views screen opens the Compose demo, and `< VIEWS` on the Compose screen goes back.

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
implementation("com.github.boy-offi9-inc:scifi-ui-kit:v0.9.0")
```

The demo pins the same release tag that ships the components it shows. To track the latest library commit instead, use `main-SNAPSHOT`.

The library keeps Jetpack Compose as `compileOnly`, so this app declares its own Compose dependencies.

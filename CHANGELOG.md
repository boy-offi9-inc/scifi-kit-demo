# Changelog

Notable changes to the demo app. Its version follows the SciFiKit release it is built against.

## 0.4.0 - unreleased

### Added
- Built against SciFiKit v0.11.0: the sonar radar mode, charge, angled and slab button styles, targeted and terminal card styles, segmented progress bars and the reworked alerts.
- Button pages show the three new button styles next to the classics.
- Card pages show the targeted (red) and terminal (green) styles.
- Status pages add a segmented LED progress bar.
- Radar mode dropdowns include sonar.

## 0.3.0 - unreleased

### Added
- Card page in both screens: the sci-fi card in its panel, outline and plain styles, previously absent from the demo.
- Status page in both screens: neon progress bars (with a tap-to-cycle live one), donut and speedometer gauges, badge state rows and alert banners including the marching hazard strip.
- Button page in both screens: outline, solid and ghost neon buttons, plus a disabled one.
- Pages in both screens: HUD, Text, Radar, Theme and Live, plus an All page that shows everything.
- Theme picker in both screens: cyan, amber, phosphor, magenta and mono palettes restyle the themed sample, and in the Compose screen the whole page.
- Decode modes section in both screens, with a button to replay them.
- Glitch modes section in both screens: subtle, standard, violent and signal loss.
- Touch modes section in both screens: tap burst, glitch that follows the finger, hold to charge, and both together.
- Radar modes section in both screens: tactical, cyberpunk, phosphor and hologram.
- Button page in both screens: outline, solid and ghost neon buttons, plus a disabled one.
- Live panel in both screens: tap to change colours, decode and glitch mode, radar mode, rings and scanlines at runtime.

### Changed
- Mode sections moved from one long scroll onto their own pages.
- Tracks the latest library commit (`main-SNAPSHOT`) instead of a pinned release.

## 0.2.1

### Added
- Views screen and Compose screen showing the HUD frame, glitch text and holo radar side by side.
- GitHub Actions workflow that builds a debug APK on every push.

Built against SciFiKit v0.2.1.

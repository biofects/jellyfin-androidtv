# Enhanced Biofects Installation

Enhanced Biofects is built into a custom Jellyfin Android TV APK. It cannot be installed as a standalone theme on the official app.

## Download

1. Open the [Biofects releases page](https://github.com/biofects/jellyfin-androidtv/releases/latest).
2. Download the APK whose name ends in `-debug.apk`.
3. Optionally verify its SHA-256 checksum against the checksum shown in the release notes.

The debug build uses the package name `org.jellyfin.androidtv.debug`, so it can be installed beside the official Jellyfin app without
replacing it.

## Install With a File Manager

1. Transfer the APK to the Android TV device with a USB drive, network share, or trusted file-transfer application.
2. Open the APK with a file manager.
3. When prompted, allow that file manager to install unknown apps.
4. Select **Install**, then open **Jellyfin Debug** from the app launcher.

## Install With ADB

Enable Developer options and USB or network debugging on the Android TV device. On a computer with Android Platform Tools installed, run:

```sh
adb connect TV_IP_ADDRESS:5555
adb install -r jellyfin-androidtv-v0.19.0-biofects.1-debug.apk
```

Replace `TV_IP_ADDRESS` with the television's address and adjust the APK path if necessary.

If Android reports `INSTALL_FAILED_UPDATE_INCOMPATIBLE`, an older debug build was signed with a different key. Remove only the debug
package and install again:

```sh
adb uninstall org.jellyfin.androidtv.debug
adb install jellyfin-androidtv-v0.19.0-biofects.1-debug.apk
```

Uninstalling the debug package removes its local settings and sign-in information. It does not affect the official
`org.jellyfin.androidtv` installation.

## Enable the Theme

1. Sign in to the Jellyfin server.
2. Open **Preferences**.
3. Select **Customization**.
4. Select **App theme**.
5. Choose **Enhanced Biofects**.

## Update

Download the newer APK from the releases page and install it over the existing build. Updates work only when both APKs use the same signing
key. The `adb install -r` command preserves the app's local data when the signatures match.

## Build From Source

```sh
git clone https://github.com/biofects/jellyfin-androidtv.git
cd jellyfin-androidtv
git switch feature/enhanced-biofects-theme
JELLYFIN_VERSION=0.19.0-biofects.1 ./gradlew assembleDebug
```

The APK is written to `app/build/outputs/apk/debug/`.

Enhanced Biofects is an unofficial, AI-assisted community modification. Jellyfin is a trademark of the Jellyfin project, which does not
provide support for this build.
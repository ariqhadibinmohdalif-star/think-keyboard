# Think Keyboard: Technical Compilation Guide

This document maps the localized workflow to compile both the low-latency hardware matrix firmware and the system-level Android overlay package.

---

## 🛠️ Part 1: Compiling the RP2040 Microcontroller Firmware

The physical key tracking engine utilizes open-source QMK/ZMK standards to process hardware keystrokes with under 1ms response latency.

### Prerequisites
Install the low-level dependencies using your desktop terminal environment:
```bash
python3 -m pip install --user qmk
qmk setup
```

### Building the Firmware Binary
1. Symlink or copy the `/firmware` directory inside this repository directly into your local `qmk_firmware/keyboards/think_keyboard` directory structure.
2. Run the target compilation macro rule from your terminal room:
```bash
qmk compile -kb think_keyboard -km default
```
3. Locate the generated output file named `think_keyboard_default.uf2` in your build cache directory. 
4. Hold down the physical `BOOTSEL` button on your hardware microchip, plug it in via USB, and drop the `.uf2` file onto the storage volume to flash the controller.

---

## 📱 Part 2: Compiling the Android Keyboard Service

The background interaction module runs natively at the AOSP input method layout level to handle system-wide overlay operations.

### Prerequisites
* Android Studio (Ladybug or newer distribution variant)
* Android SDK Platform API Level 34+ (Android 14 / 15)
* Java Development Kit (JDK 17)

### Packaging the Input Engine
1. Launch Android Studio and open the `/android-ime` project folder.
2. Allow Gradle to synchronize local component dependencies smoothly.
3. Open your project terminal window pane and run the building compiler assembly script line:
```bash
./gradlew assembleDebug
```
4. Find the flashable output executable container package at this destination path:
`android-ime/build/outputs/apk/debug/android-ime-debug.apk`

### Initializing the Software
Deploy the app directly onto your physical target motherboard module using the Android Debug Bridge interface tool:
```bash
# Push app to target storage partition
adb install android-ime-debug.apk

# Authorize system level inputs
adb shell ime enable com.think.keyboard/.ThinkKeyboardService
adb shell ime set com.think.keyboard/.ThinkKeyboardService
```


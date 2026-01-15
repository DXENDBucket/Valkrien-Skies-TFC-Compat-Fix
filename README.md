# VS x TFC Compatibility Patch

A small Forge patch mod that restores compatibility between **Valkyrien Skies (VS2)** and **TerraFirmaCraft (TFC)** on specific versions where VS's old TFC-compat mixin crashes due to method signature changes.

This project uses a **ModLauncher TransformationService** to patch VS's mixin bytecode at load time (no fork required).

## What it fixes

On some VS2 + TFC combinations, VS's built-in TFC compatibility mixin targets an outdated method signature (e.g. `getBaseColumn`), causing Mixin apply errors and a crash on startup.

This patch updates the injection target/signature so VS and TFC can load together again.

## Requirements

- Minecraft **1.20.1**
- Forge **47.x**
- Valkyrien Skies 2 (VS2) **2.3.0-beta.12**
- TerraFirmaCraft (TFC) **3.2.20**

> This is intended as a *version-pinned compatibility patch*.  
> If you change VS/TFC versions, the patch may become unnecessary or may need adjustments.

## Installation

1. Download the built `.jar` from Modrinth: https://modrinth.com/mod/valkrien-skies-tfc-compat-fix
2. Put it into your instance's `mods/` folder.
3. Launch the game.

## Notes

- It does **not** modify VS or TFC files on disk; all changes happen in-memory at load time.

## Building

```bash
./gradlew build
```
The output jar will be in `build/libs/`.

## License

MIT License. See `LICENSE`.

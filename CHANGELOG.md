# Changelog

## [1.1.0] - 2026-02-23

### Added
- **Dynamic Water Properties:** Water is no longer just "water". Using world seed and chunk coordinates, water now contains hidden properties for Saltiness, Muddiness, and Pollution based on biome.
- **Cold Water Bottle:** A new item specific to extremely cold biomes, yielding purer water.
- **Water Analyzer tool:** A new item that allows players to right-click water blocks to reveal their exact Salt/Mud/Pollution properties.
- **New Water Bottles:** Replaced generic dirty water gathering. Depending on the biome properties, gathering water will now yield specific variant bottles (Dirty, Cold, Salty, Muddy, Toxic) with exact percent data stored in NBT tooltips.
- **Salt and Mud Ball items:** Byproducts gained from purifying heavily contaminated water.
- **Procedural Item Textures:** Implemented an automated asset pipeline utilizing python to color-tint vanilla textures for the analyzer, byproducts, and bottle variants.

### Changed
- **Water Purifier Screen & Entity:** Upgraded the Water Purifier block entity to support 4 slots instead of 2. It now correctly processes NBT-tagged input bottles and deposits the appropriate amount of Salt and Mud Balls depending on the fluid's properties.
- **Custom Block Textures:** Overhauled the raw placeholder textures of the WaterPurifier block, mapping procedurally tinted furnace panels to the respective `.json` models.

### Fixed
- Fixed bug where the Water Analyzer tool ignored fluid sources completely during block raycasting. Now properly utilizes `ClipContext.Fluid.SOURCE_ONLY`.

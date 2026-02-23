# Polydipsia Mod Roadmap

## Phase 1: Foundation & Infrastructure (Current)

- [x] Initial Mod Setup (Forge 1.20.1)
- [x] Basic Gradle and IDE Configurations
- [x] CI/CD Pipeline (Jenkinsfile in Docker)
- [x] Make scripts for easy local development
- [ ] Fix dependencies and unresolved imports
- [ ] Ensure build consistency across local and CI environments

## Phase 2: Core Thirst Mechanics

- [X] Implement custom "Thirst" capability/data attachment to Player entities
- [X] Define thirst HUD elements (GUI overlay)
- [X] Add basic hydration logic (drinking water replenishes thirst)
- [ ] Add thirst depletion mechanics (sprinting, jumping, heat)
- [ ] Integrate Cold Sweat API for temperature-based thirst effects

## Phase 3: Items & Blocks

- [ ] Canteen/Water Flask item for storing water
- [ ] Water Purifier block (to safely drink from custom water sources)
- [ ] Dirty/Salt water variants and their status effects (e.g., Thirst, Poison)
- [ ] Custom fluids (Juices, purified water)

## Phase 4: Compatibility & Refinement

- [ ] Deep integration with Survive, Cold Sweat, and UnionLib (if used)
- [ ] Ensure mod compatibility with JEI for any custom crafting/purifying recipes
- [ ] Sound design and visual effects for drinking/exhaustion
- [ ] Balance testing (Thirst depletion rates vs. Replenishment)

## Phase 5: Release

- [ ] Finalize localizations (en_us, etc.)
- [ ] Comprehensive playtesting on dedicated server
- [ ] First alpha release on CurseForge/Modrinth

# Polydipsia Mod

Polydipsia is a Minecraft Forge mod that integrates a complex thirst system and water purification mechanics into the game. 

## Features

### Thirst System
* **Thirst HUD Overlay:** A dedicated HUD bar added to track your thirst. Dehydration can be dangerous, as it can cause damage and eventually death ("Drink water u fool").
* **Dehydration Mechanic:** Take damage when you let your water drop too low.

### Blocks and Workstations
* **Water Purifier:** A custom built machine block. It allows you to transform unusable water into clean, pure drinking water.
    * **How to use:** Place the block, right-click to open its GUI. Place your unpurified water bottles (Dirty, Muddy, Salty) into the top slot, and the machine will slowly process them into Purified Water Bottles on the bottom slot.

### Items
* **Camelpack:** A wearable or usable item intended for carrying surplus amounts of hydration blocks or tracking large water stores.
* **Dirty Water Bucket:** Gathered from stagnant or dirty water sources, useless without cleaning.
* **Dirty Water Bottle:** Cannot be consumed safely without purification.
* **Salty Water Bottle:** Obtained from oceanic biomes, highly dehydrated salt water.
* **Muddy Water Bottle:** Swampy or dirt-mixed water.
* **Toxic Water Bottle:** Heavily contaminated water bottle.
* **Purified Water Bottle:** The primary clean drinking water used to replenish your thirst bar safely.

## Recipes
* **Water Purifier Recipe:** Crafted with a bucket surrounded by 8 Iron Ingots.
* **Water Purifying Recipe:** (Processed purely through the Water Purifier machine) 
    * `Dirty Water Bottle` -> `Purified Water Bottle`
    * `Salty Water Bottle` -> `Purified Water Bottle`
    * `Muddy Water Bottle` -> `Purified Water Bottle`

## How it works
The mod hooks into the player tick events to decrement thirst over time based on physical exertion (sprinting, jumping, typical Minecraft mechanics). Players must navigate the world while considering fresh water sources, craft a Water Purifier, and start stockpiling Purified Water Bottles to survive.

## Build and Run
This mod uses Forge and Gradle.
```bash
# Build the project
./gradlew build

# Run the Minecraft client
./gradlew runClient
```

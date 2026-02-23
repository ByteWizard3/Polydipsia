import urllib.request
import os
import json
from PIL import Image
import ssl

ssl._create_default_https_context = ssl._create_unverified_context

BASE_URL = "https://raw.githubusercontent.com/InventivetalentDev/minecraft-assets/1.20.1/assets/minecraft/textures/item/"
TEXTURE_DIR = "src/main/resources/assets/polydipsia/textures/item"
MODEL_DIR = "src/main/resources/assets/polydipsia/models/item"

os.makedirs(TEXTURE_DIR, exist_ok=True)
os.makedirs(MODEL_DIR, exist_ok=True)

def fetch_image(name):
    try:
        req = urllib.request.Request(BASE_URL + name, headers={'User-Agent': 'Mozilla/5.0'})
        with urllib.request.urlopen(req) as response:
            with open("temp.png", "wb") as f:
                f.write(response.read())
        return Image.open("temp.png").convert("RGBA")
    except Exception as e:
        print(f"Failed to fetch {name}: {e}")
        return Image.new("RGBA", (16, 16), (255, 0, 255, 255))

def tint_image(img, color):
    r, g, b, a = img.split()
    r = r.point(lambda i: int(i * color[0] / 255))
    g = g.point(lambda i: int(i * color[1] / 255))
    b = b.point(lambda i: int(i * color[2] / 255))
    return Image.merge("RGBA", (r, g, b, a))

def create_model_json(name):
    data = {
        "parent": "minecraft:item/generated",
        "textures": {
            "layer0": f"polydipsia:item/{name}"
        }
    }
    with open(os.path.join(MODEL_DIR, f"{name}.json"), "w") as f:
        json.dump(data, f, indent=2)

def main():
    # Fetch base assets
    bottle_base = fetch_image("potion.png")
    bottle_overlay = fetch_image("potion_overlay.png")
    sugar = fetch_image("sugar.png")
    snowball = fetch_image("snowball.png")
    spyglass = fetch_image("spyglass.png")
    
    # 1. Salt (tint sugar greyish)
    salt = tint_image(sugar, (220, 220, 220))
    salt.save(os.path.join(TEXTURE_DIR, "salt.png"))
    create_model_json("salt")
    
    # 2. Mud Ball (tint snowball brown)
    mud_ball = tint_image(snowball, (90, 60, 40))
    mud_ball.save(os.path.join(TEXTURE_DIR, "mud_ball.png"))
    create_model_json("mud_ball")
    
    # 3. Water Analyzer (tint spyglass cyan)
    analyzer = tint_image(spyglass, (100, 255, 255))
    analyzer.save(os.path.join(TEXTURE_DIR, "water_analyzer.png"))
    create_model_json("water_analyzer")
    
    # 4. Water bottles
    bottles = {
        "dirty_water_bottle": (120, 110, 100),
        "salty_water_bottle": (230, 240, 255),
        "purified_water_bottle": (50, 150, 255),
        "muddy_water_bottle": (139, 69, 19),
        "toxic_water_bottle": (50, 255, 50),
        "cold_water_bottle": (180, 220, 255)
    }
    
    for name, color in bottles.items():
        tinted_liquid = tint_image(bottle_overlay, color)
        # composited bottle
        final_bottle = bottle_base.copy()
        final_bottle.alpha_composite(tinted_liquid)
        final_bottle.save(os.path.join(TEXTURE_DIR, f"{name}.png"))
        create_model_json(name)
        
    if os.path.exists("temp.png"):
        os.remove("temp.png")
    
    print("Item textures and JSON models generated successfully.")

if __name__ == "__main__":
    main()

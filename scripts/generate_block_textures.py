import urllib.request
import urllib.error
from PIL import Image, ImageEnhance, ImageOps
import os
import ssl

ssl._create_default_https_context = ssl._create_unverified_context

BASE_URL = "https://raw.githubusercontent.com/InventivetalentDev/minecraft-assets/1.20.1/assets/minecraft/textures/block/"
OUTPUT_DIR = "src/main/resources/assets/polydipsia/textures/block"

files = {
    "furnace_front.png": "water_purifier_front.png",
    "furnace_front_on.png": "water_purifier_front_on.png",
    "furnace_side.png": "water_purifier_side.png",
    "furnace_top.png": "water_purifier_top.png"
}

os.makedirs(OUTPUT_DIR, exist_ok=True)

for vanilla, custom in files.items():
    try:
        req = urllib.request.Request(BASE_URL + vanilla, headers={'User-Agent': 'Mozilla/5.0'})
        with urllib.request.urlopen(req) as response:
            with open("temp.png", "wb") as f:
                f.write(response.read())
                
        img = Image.open("temp.png").convert("RGBA")
        
        # Colorizer: tint towards cyan/blue for 'water' theme
        # We'll split channels and boost blue/green
        r, g, b, a = img.split()
        r = r.point(lambda i: i * 0.7)  # reduce red
        g = g.point(lambda i: min(255, int(i * 1.2)))  # boost green
        b = b.point(lambda i: min(255, int(i * 1.5)))  # boost blue
        
        tinted = Image.merge("RGBA", (r, g, b, a))
        
        out_path = os.path.join(OUTPUT_DIR, custom)
        tinted.save(out_path)
        print(f"Generated {out_path}")
    except Exception as e:
        print(f"Failed to process {vanilla}: {e}")
        # Fallback to simple colored blocks if download fails
        fallback_color = (40, 100, 150, 255)
        if "top" in vanilla: fallback_color = (60, 120, 170, 255)
        if "front" in vanilla: fallback_color = (30, 90, 140, 255)
        
        img = Image.new("RGBA", (16, 16), fallback_color)
        out_path = os.path.join(OUTPUT_DIR, custom)
        img.save(out_path)
        print(f"Created fallback texture for {out_path}")

if os.path.exists("temp.png"):
    os.remove("temp.png")

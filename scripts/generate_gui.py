import sys
from PIL import Image, ImageDraw

def draw_slot(draw, x, y, size=18):
    # Slot background and borders
    draw.rectangle([x, y, x + size - 1, y + size - 1], fill=(139, 139, 139))
    draw.rectangle([x, y, x + size - 1, y], fill=(55, 55, 55)) # top
    draw.rectangle([x, y, x, y + size - 1], fill=(55, 55, 55)) # left
    draw.rectangle([x + 1, y + size - 1, x + size - 1, y + size - 1], fill=(255, 255, 255)) # bottom
    draw.rectangle([x + size - 1, y + 1, x + size - 1, y + size - 1], fill=(255, 255, 255)) # right

def main():
    width = 256
    height = 256
    
    img = Image.new('RGBA', (width, height), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    
    # Base background (176x166)
    gui_width, gui_height = 176, 166
    draw.rectangle([0, 0, gui_width - 1, gui_height - 1], fill=(198, 198, 198, 255))
    
    # Outer Borders
    draw.rectangle([0, 0, gui_width - 1, 0], fill=(255, 255, 255, 255)) # top
    draw.rectangle([0, 0, 0, gui_height - 1], fill=(255, 255, 255, 255)) # left
    draw.rectangle([1, gui_height - 1, gui_width - 1, gui_height - 1], fill=(85, 85, 85, 255)) # bottom
    draw.rectangle([gui_width - 1, 1, gui_width - 1, gui_height - 1], fill=(85, 85, 85, 255)) # right
    
    # Player Inventory (8, 84) to (168, 138)
    for row in range(3):
        for col in range(9):
            draw_slot(draw, 7 + col * 18, 83 + row * 18)
    
    # Player Hotbar (8, 142)
    for col in range(9):
        draw_slot(draw, 7 + col * 18, 141)

    # Machine Slots
    # Our menu mapped:
    # id 0: 56, 34 => top-left of slot is actually -1 (55, 33)
    draw_slot(draw, 55, 33)
    
    # id 1: 116, 35
    draw_slot(draw, 115, 34)
    # id 2: 116, 55 (Salt)
    draw_slot(draw, 115, 54)
    # id 3: 116, 15 (Mud)
    draw_slot(draw, 115, 14)
    
    # Empty progress arrow base
    arrow_x = 85
    arrow_y = 30
    arrow_width = 8
    arrow_height = 26
    draw.rectangle([arrow_x, arrow_y, arrow_x + arrow_width - 1, arrow_y + arrow_height - 1], fill=(150, 150, 150, 255))
    draw.rectangle([arrow_x, arrow_y, arrow_x + arrow_width - 1, arrow_y], fill=(90, 90, 90, 255))
    draw.rectangle([arrow_x, arrow_y, arrow_x, arrow_y + arrow_height - 1], fill=(90, 90, 90, 255))
    
    # Full progress arrow at (176, 0)
    full_arrow_x = 176
    full_arrow_y = 0
    draw.rectangle([full_arrow_x, full_arrow_y, full_arrow_x + arrow_width - 1, full_arrow_y + arrow_height - 1], fill=(255, 255, 255, 255))
    
    img.save("src/main/resources/assets/polydipsia/textures/gui/water_purifier_gui.png")

if __name__ == "__main__":
    main()

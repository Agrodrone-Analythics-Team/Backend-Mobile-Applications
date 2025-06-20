import os
from PIL import Image

# Cambia estos paths por los tuyos
folders = {
    'train': r'C:\Users\bruce\PROYECTOS\EMERGENTES\AgroDrone Analytics(Code)visonXcomputadora\modelo_entrenar\datasets\train\images',
    'valid': r'C:\Users\bruce\PROYECTOS\EMERGENTES\AgroDrone Analytics(Code)visonXcomputadora\modelo_entrenar\datasets\valid\images',
    'test': r'C:\Users\bruce\PROYECTOS\EMERGENTES\AgroDrone Analytics(Code)visonXcomputadora\modelo_entrenar\datasets\test\images'
}

for split, path in folders.items():
    widths, heights = [], []
    for file in os.listdir(path):
        if file.lower().endswith(('.jpg', '.jpeg', '.png')):
            img_path = os.path.join(path, file)
            with Image.open(img_path) as img:
                w, h = img.size
                widths.append(w)
                heights.append(h)
    if widths and heights:
        print(f"\n--- {split.upper()} ---")
        print(f"Total imágenes: {len(widths)}")
        print(f"Resolución mínima: {min(widths)}x{min(heights)}")
        print(f"Resolución máxima: {max(widths)}x{max(heights)}")
        print(f"Resolución promedio: {sum(widths)//len(widths)}x{sum(heights)//len(heights)}")
    else:
        print(f"{split}: No se encontraron imágenes.")

import os

MAPEO = {
    0: 2,
    1: 2,
    2: 2,
    3: 2,
    4: 2,
}

RAIZ = './Yolo_Cow.v7-misbah_test_3.yolov8'
for split in ['train', 'valid', 'test']:
    carpeta_labels = os.path.join(RAIZ, split, 'labels')
    carpeta_mod = os.path.join(RAIZ, split, 'labels_modificados')
    os.makedirs(carpeta_mod, exist_ok=True)
    for nombre_archivo in os.listdir(carpeta_labels):
        if nombre_archivo.endswith('.txt'):
            ruta = os.path.join(carpeta_labels, nombre_archivo)
            ruta_mod = os.path.join(carpeta_mod, nombre_archivo)
            lineas_nuevas = []
            with open(ruta, 'r') as f:
                for linea in f:
                    partes = linea.strip().split()
                    if len(partes) > 0:
                        clase_vieja = int(partes[0])
                        clase_nueva = MAPEO.get(clase_vieja, clase_vieja)
                        nueva_linea = ' '.join([str(clase_nueva)] + partes[1:])
                        lineas_nuevas.append(nueva_linea)
            with open(ruta_mod, 'w') as f:
                for ln in lineas_nuevas:
                    f.write(ln + '\n')

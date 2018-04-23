========================================================
Editor de imágenes por Raquel Escalante y Rafael Vasquez
========================================================

Índice:
-Implementación
-Funciones


===============
Implementación:
===============

Para la segunda fase, el programa implementa una clase Kernel.java encargada de generar un Kernel para cada funcionalidad que implemente operaciones locales, recibiendo como parámetros un arreglo de valores para el kernel, coordenadas X e Y del pivote en el Kernel y la suma de los valores del kernel, para realizar la división entre este parámetro y dejar el valor final en el pixel que se esté modificando.

También se generó una clase Histogram.java, para manejar la creación de histogramas y operaciones relacionadas a los mismos, como normalización de los bins para su muestra gráfica o ecualización de histogramas para mejora del contraste.

Finalmente se implementó una clase FiltersController, que se encarga de realizar ahora todas las operaciones sobre las imágenes, tanto las operaciones básicas implementadas en la primera tarea, como las operaciones locales que utilizan los kernels, de forma pública. Así mismo, esta clase también contiene las funciones de convolución, inicialización de las ventanas deslizantes, construcción de los kernels gaussianos, operaciones para la mediana, parámetros para la función de rotación libre y una pequeña función de clamping.

En el caso de la operación de Ecualización de Histograma, se implementó usando una tabla LookUp para hacer el mapeo de los valores al momento de crear la nueva imagen a mostrar.

Se realizaron ciertas modificaciones a nivel de la interfaz para añadir las nuevas operaciones así como mostrar la información de la imagen adecuadamente.

==========
Funciones:
==========
-------------------------------------
1.- Archivo -> Abrir Imagen (Ctrl+O):
-------------------------------------
El programa abre todos los formatos requeridos por la aplicación, ya sean BMP, JPG, PNG, PPM, PGM, PBM y RLE.

-----------------------------------
2.- Archivo -> Guardar(Ctrl+S):
-----------------------------------
El programa guarda la imagen desplegada en el visor en la dirección indicada y con el nombre
de archivo especificado en un explorador de archivos, en formato BMP, JPG o PNG, según indique el usuario.

----------------------------------------------------------
3.- Archivo -> Guardar Netpbm -> Sin Compresión (Shift+S):
----------------------------------------------------------
El programa guarda la imagen desplegada en el visor en la dirección indicada y con el nombre
de archivo especificado en un explorador de archivos, en formato PBM, PGM o PPM dependiendo de la
paleta de colores de la imagen.

-------------------------------------------------------------------
4.- Archivo -> Guardar Netpbm -> Con Compresión RLE (Ctrl+Shift+S):
-------------------------------------------------------------------
El programa guarda la imagen desplegada en el visor en la dirección indicada y con el nombre
de archivo especificado en un explorador de archivos, en formato RLE, es decir, con Run Length Encoding.

---------------------------------------------
5.- Editar -> Rotación -> 90°CW (Ctrl+Right):
---------------------------------------------
El programa realiza la rotación de la imagen en ángulo recto (90°) hacia la derecha, o en sentido de las agujas del reloj (Clockwise).

---------------------------------------------
6.- Editar -> Rotación -> 90°CCW (Ctrl+Left):
---------------------------------------------
El programa realiza la rotación de la imagen en ángulo recto (90°) hacia la izquierda, o en sentido contrario a las agujas del reloj (Counter Clockwise).

--------------------------------
7.- Editar -> Escalamiento
--------------------------------
El programa reescala la imagen tomando como parámetros dos valores correspondientes al porcentaje de reescalado en altura y anchura (de 1 a 1000%) y el método de interpolación a usar: Vecinos más cercanos o Interpolación Bilineal.

--------------------------------------
8.- Ver -> Aplicar Zoom (Alt+Z):
--------------------------------------
El programa permite acercar o alejar una imagen según cierto rango (1-1000%).

----------------------------------------
9.- Filtros -> Color -> Negativo (Ctrl+N):
----------------------------------------
El programa invierte los colores de la imagen.

----------------------------------------
10.- Filtros -> Color -> Escala de Grises (Ctrl+G):
----------------------------------------
El programa convierte la imagen a escala de grises de 256 colores.

----------------------------------------
11.- Filtros -> Color -> Blanco y Negro (Ctrl+B):
----------------------------------------
El programa convierte la imagen a blanco y negro de dos colores, de acuerdo a un umbral ingresado por el usuario.

----------------------------------------
12.- Filtros -> Color -> Brillo 
----------------------------------------
El programa modifica el brillo aumentando o disminuyendo en un rango de -127 a 127.

----------------------------------------
13.- Filtros -> Color -> Contraste 
----------------------------------------
El programa modifica el contraste automáticamente usando Ecualización del histograma.

----------------------------------------
14.- Filtros -> Suavizado -> Suavizado Gaussiano
----------------------------------------
El programa aplica suavizado gaussiano a la imagen de acuerdo a un valor de kernel ingresado por el usuario usando un slider.

----------------------------------------
15.- Filtros -> Suavizado -> Suavizado Promedio
----------------------------------------
El programa aplica suavizado promedio a la imagen de acuerdo a un valor de ancho y alto del kernel ingresado por el usuario usando spinners.

----------------------------------------
16.- Filtros -> Suavizado -> Suavizado Promedio
----------------------------------------
El programa aplica suavizado promedio a la imagen de acuerdo a un valor de ancho y alto del kernel ingresado por el usuario usando spinners.

----------------------------------------
17.- Filtros -> Suavizado -> Mediana
----------------------------------------
El programa aplica el filtro de Mediana a la imagen de acuerdo a un valor de ancho y alto del kernel ingresado por el usuario usando spinners.

----------------------------------------------
18.- Filtros -> Detección de Bordes -> Roberts
----------------------------------------------
El programa aplica el filtro Roberts de forma automática.

----------------------------------------------
19.- Filtros -> Detección de Bordes -> Sobel
----------------------------------------------
El programa aplica el filtro Sobel a partir de 3 parámetros: Horizontal, Vertical o ambos. Con estos parametros se construye el kernel correspondiente para aplicar sobre la imagen.

----------------------------------------------
20.- Filtros -> Detección de Bordes -> Prewitt
----------------------------------------------
El programa aplica el filtro Prewitt a partir de 5 parámetros: Horizontal, Vertical, Diagonal hacia la derecha, hacia la izquierda o todos. Con estos parametros se construye el kernel correspondiente para aplicar sobre la imagen.

---------------------------------------------------------------
21.- Filtros -> Detección de Bordes -> Laplaciano del Gaussiano
---------------------------------------------------------------
El programa aplica el filtro Laplaciano del Gaussiano de forma automática. Para poder realizar los cálculos correctamente, primero se aplica el filtro Gaussiano, y sobre esta imagen se aplica la operacion dada por una máscara Laplaciana, a través de una convolución.

------------------------------
22.- Filtros -> Personalizado 
------------------------------
El programa despliega una interfaz que permite que el usuario seleccione el tamaño del kernel, el valor de cada campo del kernel y las coordenadas donde se ubicará el pivote. Por defecto ésta se asume lo más al centro posible, pero el usuario puede cambiar este parámetro.
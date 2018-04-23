========================================================
Editor de im�genes por Raquel Escalante y Rafael Vasquez
========================================================

�ndice:
-Implementaci�n
-Funciones


===============
Implementaci�n:
===============

Para la segunda fase, el programa implementa una clase Kernel.java encargada de generar un Kernel para cada funcionalidad que implemente operaciones locales, recibiendo como par�metros un arreglo de valores para el kernel, coordenadas X e Y del pivote en el Kernel y la suma de los valores del kernel, para realizar la divisi�n entre este par�metro y dejar el valor final en el pixel que se est� modificando.

Tambi�n se gener� una clase Histogram.java, para manejar la creaci�n de histogramas y operaciones relacionadas a los mismos, como normalizaci�n de los bins para su muestra gr�fica o ecualizaci�n de histogramas para mejora del contraste.

Finalmente se implement� una clase FiltersController, que se encarga de realizar ahora todas las operaciones sobre las im�genes, tanto las operaciones b�sicas implementadas en la primera tarea, como las operaciones locales que utilizan los kernels, de forma p�blica. As� mismo, esta clase tambi�n contiene las funciones de convoluci�n, inicializaci�n de las ventanas deslizantes, construcci�n de los kernels gaussianos, operaciones para la mediana, par�metros para la funci�n de rotaci�n libre y una peque�a funci�n de clamping.

En el caso de la operaci�n de Ecualizaci�n de Histograma, se implement� usando una tabla LookUp para hacer el mapeo de los valores al momento de crear la nueva imagen a mostrar.

Se realizaron ciertas modificaciones a nivel de la interfaz para a�adir las nuevas operaciones as� como mostrar la informaci�n de la imagen adecuadamente.

==========
Funciones:
==========
-------------------------------------
1.- Archivo -> Abrir Imagen (Ctrl+O):
-------------------------------------
El programa abre todos los formatos requeridos por la aplicaci�n, ya sean BMP, JPG, PNG, PPM, PGM, PBM y RLE.

-----------------------------------
2.- Archivo -> Guardar(Ctrl+S):
-----------------------------------
El programa guarda la imagen desplegada en el visor en la direcci�n indicada y con el nombre
de archivo especificado en un explorador de archivos, en formato BMP, JPG o PNG, seg�n indique el usuario.

----------------------------------------------------------
3.- Archivo -> Guardar Netpbm -> Sin Compresi�n (Shift+S):
----------------------------------------------------------
El programa guarda la imagen desplegada en el visor en la direcci�n indicada y con el nombre
de archivo especificado en un explorador de archivos, en formato PBM, PGM o PPM dependiendo de la
paleta de colores de la imagen.

-------------------------------------------------------------------
4.- Archivo -> Guardar Netpbm -> Con Compresi�n RLE (Ctrl+Shift+S):
-------------------------------------------------------------------
El programa guarda la imagen desplegada en el visor en la direcci�n indicada y con el nombre
de archivo especificado en un explorador de archivos, en formato RLE, es decir, con Run Length Encoding.

---------------------------------------------
5.- Editar -> Rotaci�n -> 90�CW (Ctrl+Right):
---------------------------------------------
El programa realiza la rotaci�n de la imagen en �ngulo recto (90�) hacia la derecha, o en sentido de las agujas del reloj (Clockwise).

---------------------------------------------
6.- Editar -> Rotaci�n -> 90�CCW (Ctrl+Left):
---------------------------------------------
El programa realiza la rotaci�n de la imagen en �ngulo recto (90�) hacia la izquierda, o en sentido contrario a las agujas del reloj (Counter Clockwise).

--------------------------------
7.- Editar -> Escalamiento
--------------------------------
El programa reescala la imagen tomando como par�metros dos valores correspondientes al porcentaje de reescalado en altura y anchura (de 1 a 1000%) y el m�todo de interpolaci�n a usar: Vecinos m�s cercanos o Interpolaci�n Bilineal.

--------------------------------------
8.- Ver -> Aplicar Zoom (Alt+Z):
--------------------------------------
El programa permite acercar o alejar una imagen seg�n cierto rango (1-1000%).

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
El programa modifica el contraste autom�ticamente usando Ecualizaci�n del histograma.

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
18.- Filtros -> Detecci�n de Bordes -> Roberts
----------------------------------------------
El programa aplica el filtro Roberts de forma autom�tica.

----------------------------------------------
19.- Filtros -> Detecci�n de Bordes -> Sobel
----------------------------------------------
El programa aplica el filtro Sobel a partir de 3 par�metros: Horizontal, Vertical o ambos. Con estos parametros se construye el kernel correspondiente para aplicar sobre la imagen.

----------------------------------------------
20.- Filtros -> Detecci�n de Bordes -> Prewitt
----------------------------------------------
El programa aplica el filtro Prewitt a partir de 5 par�metros: Horizontal, Vertical, Diagonal hacia la derecha, hacia la izquierda o todos. Con estos parametros se construye el kernel correspondiente para aplicar sobre la imagen.

---------------------------------------------------------------
21.- Filtros -> Detecci�n de Bordes -> Laplaciano del Gaussiano
---------------------------------------------------------------
El programa aplica el filtro Laplaciano del Gaussiano de forma autom�tica. Para poder realizar los c�lculos correctamente, primero se aplica el filtro Gaussiano, y sobre esta imagen se aplica la operacion dada por una m�scara Laplaciana, a trav�s de una convoluci�n.

------------------------------
22.- Filtros -> Personalizado 
------------------------------
El programa despliega una interfaz que permite que el usuario seleccione el tama�o del kernel, el valor de cada campo del kernel y las coordenadas donde se ubicar� el pivote. Por defecto �sta se asume lo m�s al centro posible, pero el usuario puede cambiar este par�metro.
Proyecto 3EnRaya_Futbol
Propósito y Alcance
Este documento ofrece una introducción de alto nivel al proyecto 3EnRaya_Futbol, un juego de tres en raya con temática de fútbol que se integra con MongoDB para validar el conocimiento de los jugadores. El sistema combina la jugabilidad tradicional del tres en raya con trivia de fútbol, requiriendo que los jugadores nombren futbolistas reales que coincidan con combinaciones de categorías específicas para reclamar posiciones en el tablero.

Para información detallada sobre la aplicación principal del juego, consulta la sección Aplicación del Juego. Para detalles específicos sobre la gestión de la base de datos, consulta Sistema de Gestión de Datos. Para instrucciones de configuración del proyecto, consulta Configuración e Instalación del Proyecto.

Concepto del Juego
A diferencia del tres en raya tradicional donde los jugadores simplemente colocan X's y O's, este juego requiere que los jugadores demuestren su conocimiento de fútbol. Cada posición del tablero representa la intersección de dos categorías (por ejemplo, un equipo y una posición), y los jugadores deben introducir el nombre de un futbolista real que coincida con ambas categorías para reclamar esa casilla.

Mecánicas de Juego Principales
El juego genera una cuadrícula de 3x3 donde:

Categorías Horizontales: Equipos, países, posiciones, números de camiseta o rangos de edad.
Categorías Verticales: Diferentes tipos de categorías que las horizontales para crear intersecciones significativas.
Validación del Jugador: Cada movimiento se valida contra una base de datos MongoDB que contiene más de 276 registros de jugadores reales.
Condiciones de Victoria: Reglas tradicionales del tres en raya más detección de "juego atascado" cuando no quedan movimientos válidos.
Arquitectura del Sistema
Fragmento de código

graph TD
    A[Sistemas Externos] --> B[Capa de Importación de Datos]
    B --> C[Capa de Acceso a Datos]
    C --> D[Capa de Lógica del Juego]
    D --> E[Capa de Interfaz de Usuario]
Componentes Clave
FutbolEnRaya:

Ventana Principal del Juego (JFrame)
Componentes de la Interfaz de Usuario: JButton[3][3] botones, JTextField nombreJugadorField, JLabel[] categoriaLabels
Gestión del Estado del Juego: String[][] tablero, String turnoActual, Set jugadoresUtilizadosEnPartida
Selección de Categorías: seleccionarCategoriasParaTableroJugable(), List[][] jugadoresDisponiblesPorCasilla
Validación de Movimientos: manejarClick(int fila, int columna), quedanOpcionesParaJugadorActual()
TicTacToeDB:

Gestor de Base de Datos: precargarJugadores(), getPlayersByCategories()
Jugador:

Modelo de Datos del Jugador: String nombre, List clubs
JsonManipulator: Importación de BD local.

MongoDBImporter: Importación de BD en la nube.

players.json: Dataset de jugadores.

Base de Datos MongoDB: futbol_en_raya.jugadores

Flujo del Juego y Validación de Movimientos
Fragmento de código

graph TD
    subgraph Inicialización del Juego
        A[precargarJugadores()] --> B[Cargar todos los jugadores en memoria]
        B --> C[Datos de la colección de jugadores]
        C --> D[seleccionarCategoriasParaTableroJugable()]
        D --> E[Inicializar jugadoresDisponiblesPorCasilla[3][3]]
    end

    subgraph Intento de Movimiento del Jugador
        F[Click en celda (fila, columna) + introducir nombre del jugador] --> G{Check if jugadoresUtilizadosEnPartida.contains(name)}

        G -- Jugador ya usado --> H[Mensaje de error, sin cambio de turno]
        G -- Jugador no usado --> I[Get jugadoresDisponiblesPorCasilla[fila][columna]]
        I --> J[Lista de jugadores válidos para la celda]

        alt Valid Player for Cell
            J -- Jugador válido --> K[Marcar celda, añadir a jugadoresUtilizadosEnPartida]
            K --> L[hayGanador() / tableroLleno()]
            L -- No hay ganador / tablero lleno --> M[Cambiar turnoActual]
            L -- Ganador / tablero lleno --> N[Mensaje de éxito, actualizar UI]
            M --> O[Mensaje de éxito, actualizar UI]
        end

        alt Invalid Player for Cell
            J -- Jugador inválido --> P[Cambiar turnoActual (penalización)]
            P --> Q[Mensaje de error, cambio de turno]
        end

        K --> R[quedanOpcionesParaJugadorActual()]
        R -- No quedan opciones --> S[Mensaje de juego atascado, mostrar reinicio]
        R -- Quedan opciones --> T[Continuar juego]
Sistema de Categorías y Generación del Tablero
El juego utiliza un algoritmo sofisticado de selección de categorías para asegurar que cada posición del tablero tenga al menos un jugador válido:

Tipos de Categorías
Categorías Horizontales:
PAIS (Países)
CLUB (Equipos)
Categorías Verticales:
POSICION (Posiciones)
DORSAL (Números de Camiseta)
PAIS (Países)
EDAD_RANGO (Rangos de Edad)
Algoritmo de Selección y Proceso de Validación
Fragmento de código

graph TD
    A[seleccionarCategoriasParaTableroJugable()] --> B[Máximo 1000 intentos]
    B --> C[Pares de Tipos de Categoría]
    C --> D{Evitar horizontal = vertical}
    D -- Sí --> E[3 Valores por Categoría]
    E --> F[getMostFrequentValues()]
    F --> G[Para cada celda (r,c):]
    G --> H[getPlayersByCategories()]
    H --> I[vertical[r] + horizontal[c]]
    I --> J[jugadoresDisponiblesPorCasilla[r][c]]
    J --> K[Almacenar jugadores válidos por celda]
    K --> L[Todas las 9 celdas deben tener al menos 1 jugador válido]
Pila Tecnológica y Dependencias
Componente	Tecnología	Versión	Propósito
Plataforma	Java SE	21	Plataforma principal de la aplicación
UI Framework	Swing	N/A	Componentes GUI de escritorio integrados
Base de Datos	MongoDB	5.4.0	Almacenamiento de datos de jugadores
Driver de BD	MongoDB Sync Driver	5.4.0	Operaciones síncronas de BD
Formato de Datos	JSON	20240303	Importación/exportación de datos
Datos Binarios	BSON	5.4.0	Formato de documentos MongoDB

Exportar a Hojas de cálculo
Librerías Externas Clave
El proyecto depende de cuatro archivos JAR principales para la integración con MongoDB:

bson-5.4.0.jar - Manejo de documentos JSON binarios.
mongodb-driver-core-5.4.0.jar - Funcionalidad del driver principal de MongoDB.
mongodb-driver-sync-5.4.0.jar - Operaciones síncronas de base de datos.
json-20240303.jar - Análisis y manipulación de JSON.
Arquitectura de Datos
Fragmento de código

graph TD
    A[Fuente de Datos Externa] --> B[Extracción manual de datos]
    B --> C[Transfermarkt.es]
    C --> D[Jugadores de la Liga Española]
    D --> E[players.json]
    E --> F[276+ Registros de Jugadores]
    F --> G[JsonManipulator]
    F --> H[MongoDBImporter]
    G --> I[precargarJugadores()]
    H --> J[MongoDB Collection]
    J --> K[futbol_en_raya.jugadores]
    K --> L[Almacenamiento basado en documentos]
    I --> M[Caché de la Aplicación]
    M --> N[Caché de TicTacToeDB]
    N --> O[List clubsPosibles]
    N --> P[List nacionalidadesPosibles]
    N --> Q[List posicionesPosibles]
    N --> R[List dorsalesPosibles]

    subgraph F[players.json]
        F1[nombre: String]
        F2[nacionalidad: String]
        F3[clubs: List]
        F4[posicion: String]
        F5[numero_camiseta: String]
        F6[edad: Integer]
    end
El sistema mantiene una caché completa en memoria de todos los valores de categoría distintos para permitir una selección rápida de categorías y la validación del tablero sin consultas repetidas a la base de datos.

Puntos de Entrada y Clases Principales
Clase	Propósito	Métodos Clave
FutbolEnRaya	Aplicación principal del juego y UI	main(), manejarClick(), iniciarNuevaPartida()
TicTacToeDB	Conexión y consultas a la base de datos	precargarJugadores(), getPlayersByCategories()
JsonManipulator	Utilidad de importación de base de datos local	Importación de datos de JSON a MongoDB local
MongoDBImporter	Utilidad de importación de base de datos en la nube	Importación de datos de JSON a MongoDB Atlas

Exportar a Hojas de cálculo
La aplicación se inicia a través de FutbolEnRaya.main(), que inicializa la conexión a la base de datos y lanza la GUI de Swing en el Event Dispatch Thread.







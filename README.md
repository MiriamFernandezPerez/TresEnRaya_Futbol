
# Proyecto 3EnRaya_Futbol

## Flujo del Juego y Validación de Movimientos

```mermaid
graph TD
  A[precargarJugadores()]
  B[Cargar todos los jugadores en memoria]
  C[Datos de la colección de jugadores]
  D[seleccionarCategoriasParaTableroJugable()]
  E[Inicializar jugadoresDisponiblesPorCasilla[3][3]]

  A --> B --> C --> D --> E

  F[Click en celda + nombre del jugador]
  G{¿Jugador ya usado?}
  H[Error, sin cambio de turno]
  I[Obtener jugadores válidos para celda]
  J[Lista de jugadores válidos]

  K[Marcar celda, añadir a usados]
  L{¿Ganador o tablero lleno?}
  M[Cambiar turno]
  N[Mostrar ganador]
  O[Actualizar UI]

  P[Penalización, cambiar turno]
  Q[Mostrar error]

  R[quedanOpcionesParaJugadorActual()]
  S[Mostrar "juego atascado"]
  T[Continuar juego]

  F --> G
  G -- Sí --> H
  G -- No --> I --> J

  J -- Válido --> K --> L
  L -- No --> M --> O
  L -- Sí --> N

  J -- Inválido --> P --> Q

  K --> R
  R -- No --> S
  R -- Sí --> T
```

## Algoritmo de Selección y Proceso de Validación

```mermaid
graph TD
  A[seleccionarCategoriasParaTableroJugable()]
  B[Máximo 1000 intentos]
  C[Pares de Tipos de Categoría]
  D{Evitar mismo tipo horizontal y vertical}
  E[3 Valores por Categoría]
  F[getMostFrequentValues()]
  G[Para cada celda (r,c)]
  H[getPlayersByCategories()]
  I[Combinar vertical[r] + horizontal[c]]
  J[jugadoresDisponiblesPorCasilla[r][c]]
  K[Almacenar jugadores válidos por celda]
  L[Todas las 9 celdas deben tener al menos 1 jugador válido]

  A --> B --> C --> D --> E --> F --> G --> H --> I --> J --> K --> L
```

## Arquitectura de Datos

```mermaid
graph TD
  A[Fuente de Datos Externa] --> B[Extracción manual de datos]
  B --> C[Transfermarkt.es] --> D[Jugadores de la Liga Española]
  D --> E[players.json] --> F[276+ Registros de Jugadores]
  F --> G[JsonManipulator]
  F --> H[MongoDBImporter]
  G --> I[precargarJugadores()]
  H --> J[MongoDB Collection] --> K[futbol_en_raya.jugadores]
  K --> L[Almacenamiento basado en documentos]
  I --> M[Caché de la Aplicación] --> N[Caché de TicTacToeDB]
  N --> O[List clubsPosibles]
  N --> P[List nacionalidadesPosibles]
  N --> Q[List posicionesPosibles]
  N --> R[List dorsalesPosibles]
```

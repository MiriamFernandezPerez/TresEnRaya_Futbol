# ⚽ Proyecto 3EnRaya_Futbol

---

## 🎯 Propósito y Alcance

Este documento ofrece una introducción de alto nivel al proyecto **3EnRaya_Futbol**, un juego de tres en raya con temática de fútbol que se integra con **MongoDB** para validar el conocimiento de los jugadores.

El sistema combina la jugabilidad tradicional del tres en raya con **trivia de fútbol**, requiriendo que los jugadores nombren futbolistas reales que coincidan con combinaciones de categorías específicas para reclamar posiciones en el tablero.

### 📌 Accesos rápidos:

* 🔗 [Aplicación del Juego](#aplicación-del-juego)
* 🔗 [Sistema de Gestión de Datos](#sistema-de-gestión-de-datos)
* 🔗 [Configuración del Proyecto](#configuración-del-proyecto)

---

## 🎮 Concepto del Juego

A diferencia del tres en raya tradicional donde los jugadores simplemente colocan X y O, este juego requiere que los jugadores demuestren su conocimiento de fútbol.

Cada celda del tablero representa la intersección de dos categorías (por ejemplo, un equipo y una posición), y los jugadores deben introducir el nombre de un futbolista real que coincida con ambas categorías para reclamar la casilla.

### 🧩 Mecánicas de Juego Principales

* **🔲 Tablero de 3x3** con combinaciones únicas.
* **📚 Categorías horizontales y verticales** (equipos, posiciones, países, dorsales, edades).
* **🧠 Validación de futbolistas reales** usando **MongoDB** con más de **276 registros**.
* **🏆 Reglas clásicas de victoria** del tres en raya + detección de empate/atasco.

---

## 🏗️ Arquitectura del Sistema

```mermaid
graph TD
  A[Sistemas Externos] --> B[Capa de Importación de Datos]
  B --> C[Capa de Acceso a Datos]
  C --> D[Capa de Lógica del Juego]
  D --> E[Capa de Interfaz de Usuario]

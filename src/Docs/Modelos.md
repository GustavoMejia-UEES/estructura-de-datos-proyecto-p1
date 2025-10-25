# Documentación detallada del diseño y modelos de interfaces de ToDo Lists App

## Introducción

El presente documento describe en profundidad el **diseño arquitectónico** y el **modelo de datos** del proyecto **ToDo Lists App**. La aplicación ha sido estructurada siguiendo principios sólidos de ingeniería de software, aplicando **Programación Orientada a Objetos (POO)** y el patrón **MVC (Modelo-Vista-Controlador)**. La intención es proveer un entorno modular, extensible y escalable, facilitando el trabajo colaborativo y la comparación de diferentes enfoques de implementación.

Todos los componentes centrales del dominio (modelos de datos y estructuras auxiliares) han sido definidos como **interfaces** en Java, lo que permite a cada integrante del equipo realizar su propia implementación concreta, manteniendo la interoperabilidad a través de contratos estrictamente definidos.

---

## Justificación del enfoque por interfaces

1. **Flexibilidad y extensibilidad:**  
   Las interfaces permiten que múltiples implementaciones coexistan y evolucionen sin romper el contrato principal. Cada integrante puede experimentar con diferentes estructuras de datos, algoritmos y enfoques, facilitando la comparación y el aprendizaje.

2. **Desacoplamiento:**  
   La lógica de negocio está completamente separada de la presentación (UI) y de la infraestructura, permitiendo que la interfaz gráfica funcione de manera transparente con cualquier implementación que cumpla los contratos definidos.

3. **Pruebas y mantenibilidad:**  
   Al definir los métodos esenciales en interfaces, se facilita la creación de pruebas unitarias, la refactorización y el mantenimiento general del sistema.

4. **Cumplimiento de requerimientos académicos:**  
   El uso de interfaces permite cubrir los objetivos de los talleres (manejo de TDAs, comparadores, pilas, colas de prioridad, etc.) de manera estructurada y coherente.

---

## Descripción de las interfaces principales

### 1. **App**

**Propósito:**  
Es el punto de entrada y gestión principal de la lógica de la aplicación. La interfaz `App` define todos los métodos necesarios para operar sobre el conjunto de listas de tareas (`ToDos`), facilitando la interacción entre la vista y el modelo. Centraliza las operaciones de alto nivel, tales como la administración de listas, búsqueda global de tareas, manejo de historial, persistencia en archivos y reversión de acciones.

**Responsabilidades principales:**

- Agregar, eliminar y editar listas de tareas
- Mover tareas entre listas
- Agregar, eliminar y editar tareas en listas específicas
- Filtrar tareas por prioridad, estado, descripción o rango de fechas
- Obtener historial de tareas completadas por período
- Revertir la última tarea completada
- Persistir y recuperar datos desde archivos
- Marcar todas las tareas como completadas o eliminar tareas completadas

**Relación:**  
`App` depende de `ToDos` para la gestión de listas, de `Task` para operaciones sobre tareas, y de estructuras auxiliares como pilas y colas para manejo avanzado de historial y prioridades.

---

### 2. **ToDos**

**Propósito:**  
Representa una **lista de tareas**. Modela el concepto de un conjunto de tareas agrupadas bajo un nombre y fecha de creación, permitiendo la administración de tareas individuales y la realización de operaciones agregadas sobre el conjunto.

**Responsabilidades principales:**

- Obtener y modificar el nombre y la fecha de la lista
- Administrar tareas (agregar, eliminar, editar)
- Marcar todas las tareas como completadas
- Filtrar tareas por estado o prioridad
- Eliminar tareas completadas
- Mover tareas entre listas

**Relación:**  
`ToDos` contiene múltiples objetos `Task` y puede interactuar con otras instancias de `ToDos` para mover tareas. Funciona como la unidad básica sobre la que opera la lógica de alto nivel definida en `App`.

---

### 3. **Task**

**Propósito:**  
Define la estructura y comportamiento de una **tarea individual** dentro de la aplicación. Cada tarea representa una acción pendiente con atributos como descripción, fecha límite, prioridad y estado de completitud.

**Responsabilidades principales:**

- Acceso y modificación de atributos (id, descripción, fecha de entrega, prioridad, estado)
- Marcar la tarea como completada
- Editar atributos relevantes de la tarea
- Representación textual (`toString`) para visualización y depuración

**Relación:**  
Cada `Task` es gestionada por una instancia de `ToDos` y utilizada por estructuras auxiliares como `TaskQueue`, `TaskStack` y `TaskComparator` para ordenamiento, historial y búsqueda.

---

### 4. **TaskQueue**

**Propósito:**  
Modela una **cola de prioridad** para la gestión eficiente de tareas según su urgencia. Permite la organización, acceso y procesamiento secuencial de tareas, priorizando aquellas más importantes o próximas a vencer.

**Responsabilidades principales:**

- Encolar tareas
- Desencolar y consultar la tarea de mayor prioridad
- Obtener las tareas ordenadas por prioridad
- Consultar estado y tamaño de la cola

**Relación:**  
`TaskQueue` opera sobre instancias de `Task` y puede ser utilizada tanto por `App` como por `ToDos` para la visualización y procesamiento de tareas en función de la prioridad.

---

### 5. **TaskStack**

**Propósito:**  
Representa una **pila (stack) de historial de tareas completadas**, permitiendo guardar y revertir acciones recientes. Es clave para implementar funcionalidades de “deshacer” y para visualizar el historial de actividades.

**Responsabilidades principales:**

- Apilar y desapilar tareas completadas
- Consultar la tarea completada más recientemente
- Obtener el historial completo de tareas
- Consultar estado y tamaño de la pila

**Relación:**  
`TaskStack` opera sobre objetos `Task` y es utilizada por `App` para la gestión del historial y la reversión de acciones.

---

### 6. **TaskComparator**

**Propósito:**  
Define métodos de **comparación, ordenamiento y búsqueda** sobre tareas, facilitando la organización de listas y la implementación de filtros avanzados. Permite comparar tareas por prioridad, descripción o estado, y ordenar colecciones de tareas según criterios específicos.

**Responsabilidades principales:**

- Comparar dos tareas
- Ordenar listas de tareas por prioridad
- Buscar tareas por descripción (patrón)
- Buscar tareas por estado (completadas, pendientes)

**Relación:**  
`TaskComparator` opera sobre colecciones de `Task` y es utilizada por `App`, `ToDos` y estructuras auxiliares para la visualización y búsqueda eficiente.

---

## Integración y flujo de trabajo

1. **La interfaz gráfica (JavaFX)** interactúa exclusivamente con una instancia de `App`, lo que permite cambiar fácilmente la implementación concreta (por integrante o versión).
2. **App** canaliza todas las operaciones hacia las listas (`ToDos`), que a su vez gestionan las tareas individuales (`Task`).
3. **TaskQueue** y **TaskStack** se utilizan como estructuras auxiliares para implementar funcionalidades avanzadas de prioridad e historial.
4. **TaskComparator** provee los mecanismos necesarios para organizar y buscar tareas según los requerimientos del usuario.

---

## Beneficios de este diseño

- **Modularidad:** Cada componente puede ser implementado, probado y evolucionado por separado.
- **Colaboración:** Permite que cada integrante proponga y compare diferentes soluciones, enriqueciendo el aprendizaje grupal.
- **Escalabilidad:** Nuevas funcionalidades pueden integrarse fácilmente añadiendo nuevos métodos a las interfaces o nuevas interfaces auxiliares.
- **Reutilización:** Las interfaces pueden ser utilizadas en otros proyectos o contextos, fomentando la reutilización de código y diseño.

---

## Conclusión

El diseño basado en interfaces garantiza un marco sólido, flexible y mantenible para la ToDo Lists App. Cada interfaz cumple un propósito específico y bien definido, y la relación entre ellas asegura el cumplimiento de todos los requerimientos funcionales y técnicos del proyecto. Este enfoque prepara el terreno para una implementación colaborativa, eficiente y fácilmente extensible.

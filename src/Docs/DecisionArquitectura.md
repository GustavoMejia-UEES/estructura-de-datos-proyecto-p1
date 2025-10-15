# Documentación mínima de la decisión de arquitectura y enfoque

## Enfoque general del proyecto

Optamos por desarrollar la aplicación **ToDo Lists App** utilizando **JavaFX** y **Maven** como tecnologías base, ya que nos proveen robustez, modularidad y facilidad de integración de todas las funcionalidades requeridas (interfaz gráfica, manejo de archivos, estructuras de datos, etc).

### Arquitectura seleccionada

Decidimos estructurar el proyecto siguiendo el patrón **MVC (Modelo-Vista-Controlador)**:

- **Model:** Aquí reside toda la lógica de negocio y las estructuras de datos.
- **View:** Componentes de la interfaz gráfica (JavaFX/FXML).
- **Controller:** Lógica de unión entre modelo y vista.

Esta organización permite una integración más sencilla, una mejor división de responsabilidades y facilita las pruebas y el mantenimiento.

### División por integrantes y lógica modular

Por motivos de sustentación futura, trabajo integral y precisión, **decidimos que cada integrante del grupo tenga su propia implementación de la lógica de negocio**. Sin embargo, para asegurar coherencia y facilitar la integración, realizamos previamente una reunión donde se definieron los **TDAs y métodos comunes entre todos**.

### Definición de TDAs e interfaces comunes

En conjunto, y basándonos en los requerimientos de los talleres, acordamos que los TDAs principales serían:

- **Task**: Representa una tarea.
- **ToDos**: Representa una lista de tareas.
- **App**: Maneja las listas de tareas y funcionalidades generales.
- **TaskComparator**: Para búsquedas y ordenamiento.
- **TaskStack**: Para historial y reversar acciones.
- **TaskQueue**: Para organización por prioridad.

Cada uno de estos TDAs fue definido como **interfaz en el paquete `model/api`**, asegurando que cada integrante implemente la misma estructura de métodos, pero permitiendo libertad en la implementación interna (uso de for, while, atributos personalizados, etc). Este enfoque respeta los principios de la POO y permite comparar distintas soluciones individuales.

### Relación con los talleres

- **Taller 1:** Creación de TDAs, manejo de atributos y métodos CRUD, inicialización de la aplicación.
- **Taller 2:** Uso de Comparator para búsquedas y ordenamiento.
- **Taller 3:** Implementación de historial (Stack) y reversar acciones.
- **Taller 4:** Manejo de colas de prioridad.

La estructura modular de interfaces asegura que todos los requerimientos de los talleres sean cubiertos de forma homogénea pero flexible.

### Integración con la interfaz gráfica

Gracias a la definición común de interfaces, la integración con la interfaz gráfica será directa y funcional para todas las implementaciones individuales, permitiendo que el sistema funcione igual pero con diferentes estrategias internas.

### Próximos pasos

El trabajo inicial se centrará en la **implementación de los modelos (modelos de negocio)** por cada integrante. Una documentación posterior explicará el diseño y desarrollo de los controladores, vistas y otros detalles arquitectónicos.

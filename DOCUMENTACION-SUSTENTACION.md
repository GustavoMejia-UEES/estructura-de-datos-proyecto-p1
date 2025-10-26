# 📋 PENDO Task Organizer - Guía de Sustentación

## 🎯 Introducción

Esta documentación está diseñada para **sustentaciones académicas** donde necesitas modificar el código en vivo y mostrar cambios inmediatos en la aplicación.

---

## 🚀 Métodos Clave para Demostraciones

### 1. **Agregar Tareas por Código**

**Ubicación:** `TaskController.java` - Método `initialize()` (después de línea 76)

```java
// Agregar al final del constructor, antes de actualizarListas()
agregarTareasDemostracion();

// Nuevo método a agregar:
private void agregarTareasDemostracion() {
    ToDos lista = (ToDos) appBackend.getTodoList(listaActual);

    // Tareas de prioridad ALTA
    Task tarea1 = new Task(9001, "Terminar proyecto final",
        LocalDateTime.now().plusDays(3), Task.Priority.HIGH);
    Task tarea2 = new Task(9002, "Estudiar para examen",
        LocalDateTime.now().plusDays(1), Task.Priority.HIGH);

    // Tarea de prioridad MEDIA
    Task tarea3 = new Task(9003, "Revisar documentación",
        LocalDateTime.now().plusDays(7), Task.Priority.MEDIUM);

    lista.restoreTask(tarea1);
    lista.restoreTask(tarea2);
    lista.restoreTask(tarea3);

    guardar();
}
```

### 2. **Crear Nueva Lista por Código**

**Ubicación:** `TaskController.java` - Método `initialize()` (después de línea 43)

```java
// Agregar después de crear la lista "Default"
crearListaDemostracion();

// Nuevo método a agregar:
private void crearListaDemostracion() {
    String nuevaLista = "Proyecto Final";
    appBackend.createTodoList(nuevaLista);

    // Agregar tareas a la nueva lista
    ToDos lista = (ToDos) appBackend.getTodoList(nuevaLista);
    Task tarea = new Task(8001, "Implementar funcionalidad X",
        LocalDateTime.now().plusDays(2), Task.Priority.HIGH);
    lista.restoreTask(tarea);

    guardar();
    listas.setAll(appBackend.getAllListNames());
}
```

### 3. **Modificar Títulos/Textos de la UI**

**Ubicación:** `TaskController.java` - línea 81

```java
// Cambiar título de la ventana
stage.setTitle("PENDO - Demo para Sustentación");

// Para cambiar textos de botones, ir a las clases Panel correspondientes
// TasksPanel.java, CompletedPanel.java, etc.
```

### 4. **Cambiar Filtros por Defecto**

**Ubicación:** `TaskController.java` - líneas 67-70

```java
// Cambiar filtro por defecto
organizer.tasksPanel.comboRangoPendientes.getSelectionModel().select("Semana");
// Opciones: "Todas", "Semana", "15 días", "Mes"
```

---

## ⚡ Scripts de Demostración Rápida

### **Escenario 1: "Crear 2 tareas altas y 1 media"**

```java
private void demo_TareasUrgentes() {
    ToDos lista = (ToDos) appBackend.getTodoList(listaActual);

    // 2 Tareas ALTAS
    lista.restoreTask(new Task(9001, "Entregar informe URGENTE",
        LocalDateTime.now().plusHours(6), Task.Priority.HIGH));
    lista.restoreTask(new Task(9002, "Llamar al cliente",
        LocalDateTime.now().plusHours(12), Task.Priority.HIGH));

    // 1 Tarea MEDIA
    lista.restoreTask(new Task(9003, "Organizar escritorio",
        LocalDateTime.now().plusDays(3), Task.Priority.MEDIUM));

    guardar();
    actualizarListas();
}
```

### **Escenario 2: "Crear lista de trabajo con tareas vencidas"**

```java
private void demo_TareasVencidas() {
    appBackend.createTodoList("Trabajo Urgente");
    ToDos lista = (ToDos) appBackend.getTodoList("Trabajo Urgente");

    // Tareas que ya vencieron (fechas en el pasado)
    lista.restoreTask(new Task(9004, "Tarea vencida 1",
        LocalDateTime.now().minusDays(2), Task.Priority.HIGH));
    lista.restoreTask(new Task(9005, "Tarea vencida 2",
        LocalDateTime.now().minusDays(1), Task.Priority.MEDIUM));

    guardar();
    listas.setAll(appBackend.getAllListNames());
    listaActual = "Trabajo Urgente";
    organizer.listsPanel.listasView.getSelectionModel().select(listaActual);
}
```

---

## 🔧 Métodos de Utilidad para Demos

### **Limpiar todas las tareas**

```java
private void limpiarTodasLasTareas() {
    ToDos lista = (ToDos) appBackend.getTodoList(listaActual);
    List<Task> todasLasTareas = new ArrayList<>();
    todasLasTareas.addAll(lista.getPendingTasksSorted());
    todasLasTareas.addAll(lista.getCompletedTasksSorted());

    for (Task t : todasLasTareas) {
        lista.removeTask(t.getId());
    }
    guardar();
    actualizarListas();
}
```

### **Completar tareas automáticamente**

```java
private void completarTareasDemo() {
    ToDos lista = (ToDos) appBackend.getTodoList(listaActual);
    List<Task> pendientes = lista.getPendingTasksSorted();

    // Completar las primeras 2 tareas
    for (int i = 0; i < Math.min(2, pendientes.size()); i++) {
        lista.markTaskAsCompleted(pendientes.get(i).getId());
    }
    guardar();
    actualizarListas();
}
```

---

## 📊 Datos de Prueba Predefinidos

### **Dataset Completo para Demo**

```java
private void cargarDatasetDemo() {
    // Listas
    appBackend.createTodoList("Trabajo");
    appBackend.createTodoList("Personal");
    appBackend.createTodoList("Estudios");

    // Tareas de Trabajo
    ToDos trabajo = (ToDos) appBackend.getTodoList("Trabajo");
    trabajo.restoreTask(new Task(1001, "Reunión con cliente", LocalDateTime.now().plusHours(2), Task.Priority.HIGH));
    trabajo.restoreTask(new Task(1002, "Revisar propuesta", LocalDateTime.now().plusDays(1), Task.Priority.MEDIUM));
    trabajo.restoreTask(new Task(1003, "Actualizar sistema", LocalDateTime.now().plusDays(3), Task.Priority.LOW));

    // Tareas Personales
    ToDos personal = (ToDos) appBackend.getTodoList("Personal");
    personal.restoreTask(new Task(2001, "Comprar víveres", LocalDateTime.now().plusHours(4), Task.Priority.MEDIUM));
    personal.restoreTask(new Task(2002, "Ejercicio", LocalDateTime.now().plusDays(1), Task.Priority.LOW));

    // Tareas de Estudios
    ToDos estudios = (ToDos) appBackend.getTodoList("Estudios");
    estudios.restoreTask(new Task(3001, "Estudiar para final", LocalDateTime.now().plusDays(2), Task.Priority.HIGH));
    estudios.restoreTask(new Task(3002, "Leer capítulo 5", LocalDateTime.now().plusDays(5), Task.Priority.MEDIUM));

    guardar();
    listas.setAll(appBackend.getAllListNames());
}
```

---

## 🎯 Estrategia de Sustentación

### **Antes de la Presentación:**

1. **Backup:** Copia la carpeta `data/`
2. **Código limpio:** Comentar/eliminar métodos de demo anteriores
3. **Preparar:** Tener los métodos listos para copiar/pegar

### **Durante la Sustentación:**

1. **Mostrar código:** Explicar la estructura while navegas
2. **Agregar método:** Copy/paste del método requerido
3. **Llamar método:** Agregar la llamada en `initialize()`
4. **Ejecutar:** `mvn clean javafx:run` o usar `run-pendo.bat`
5. **Mostrar resultado:** La aplicación se actualiza automáticamente

### **Tips para la Demo:**

- ✅ Usar nombres descriptivos en las tareas
- ✅ Mencionar fechas relativas (hoy +2 días, etc.)
- ✅ Explicar la lógica mientras codificas
- ✅ Mostrar diferentes prioridades y filtros
- ✅ Demostrar persistencia (cerrar y reabrir app)

---

## 🚨 Solución de Problemas Comunes

**❌ Error: "Cannot convert Object to ToDos"**

```java
// ✅ Solución: Siempre usar cast explícito
ToDos lista = (ToDos) appBackend.getTodoList(listaActual);
```

**❌ Error: "List is null"**

```java
// ✅ Solución: Verificar que la lista existe
if (appBackend.getTodoList(listaActual) == null) {
    appBackend.createTodoList(listaActual);
}
```

**❌ Cambios no se ven en la UI**

```java
// ✅ Solución: Siempre llamar después de modificar datos
guardar();
actualizarListas();
```

---

## 📁 Archivos Importantes

- **`TaskController.java`** - Lógica principal, aquí haces la mayoría de cambios
- **`Task.java`** - Estructura de las tareas
- **`ToDos.java`** - Manejo de listas de tareas
- **`data/`** - Archivos de persistencia
- **`run-pendo.bat`** - Script para ejecutar fácilmente

¡Con esta guía estarás preparado para cualquier sustentación! 🎓

# 📋 PENDO Task Organizer

> **Sistema de gestión de tareas desarrollado en JavaFX con arquitectura MVC**

![Version](https://img.shields.io/badge/version-1.0-blue.svg)
![Java](https://img.shields.io/badge/Java-17+-orange.svg)
![JavaFX](https://img.shields.io/badge/JavaFX-17+-green.svg)
![Maven](https://img.shields.io/badge/Maven-3.6+-red.svg)

---

## 🚀 Características Principales

- ✅ **Gestión completa de tareas** con prioridades (Alta, Media, Baja)
- ✅ **Múltiples listas** de tareas organizadas
- ✅ **Filtrado avanzado** por prioridad, fecha y estado
- ✅ **Interfaz intuitiva** con JavaFX
- ✅ **Persistencia de datos** en archivos locales
- ✅ **Splash screen profesional** con carga automática
- ✅ **Exportación** de historial de tareas completadas

---

## 🛠️ Tecnologías Utilizadas

- **Java 17+** - Lenguaje de programación
- **JavaFX 17** - Framework de interfaz gráfica
- **Maven** - Gestión de dependencias y build
- **CSS3** - Estilos personalizados
- **FXML** - Definición de interfaces

---

## 📋 Requisitos del Sistema

### Mínimos:

- **Java JDK 17** o superior
- **Apache Maven 3.6+**
- **4GB RAM** mínimo
- **Windows 10+** / **macOS 10.14+** / **Linux Ubuntu 18.04+**

### Recomendados:

- **Java JDK 21**
- **8GB RAM**
- **Resolución mínima:** 1280x832

---

## ⚡ Instalación y Ejecución

### **Método 1: Script Simple (Recomendado)**

```bash
# Windows - La forma más fácil:
.\ejecutar.bat

# Solo ejecuta: mvn clean javafx:run
```

### **Método 2: Manual con Maven**

```bash
# 1. Clonar/descargar el proyecto
git clone [URL_DEL_REPOSITORIO]
cd todo-list-app

# 2. Compilar y ejecutar directamente
mvn clean javafx:run

# O paso a paso:
mvn clean compile
mvn javafx:run
```

### **Método 3: Scripts Avanzados (Opcionales)**

```bash
# Script con diagnósticos completos
.\run-pendo.bat

# Generar ejecutable (.exe) - Requiere Java 17+
.\create-exe.bat
```

---

## 🎯 Guía de Uso

### **1. Inicio de la Aplicación**

- Al ejecutar, aparece un **splash screen** con el logo de PENDO
- La aplicación carga automáticamente después de 2.5 segundos
- Ventana principal se abre con tamaño **1280x832**

### **2. Gestión de Listas**

```
📁 Panel Izquierdo (Listas)
├── Crear nueva lista: Botón "Nuevo"
├── Eliminar lista: Botón "Eliminar"
└── Seleccionar lista: Click en el nombre
```

### **3. Gestión de Tareas**

```
📝 Panel Central (Tareas Pendientes)
├── Crear tarea: Botón "Crear Tarea"
├── Buscar: Campo de búsqueda
├── Filtrar por prioridad: ComboBox
├── Filtrar por tiempo: "Todas", "Semana", "15 días", "Mes"
└── Acciones por tarea:
    ├── ✅ Marcar completada
    ├── ✏️ Editar
    └── 🗑️ Eliminar
```

### **4. Historial de Tareas**

```
📊 Panel Derecho (Completadas)
├── Ver tareas completadas
├── Exportar historial: Botón "Exportar historial"
└── Limpiar completadas: Botón "Limpiar completadas"
```

---

## 🏗️ Arquitectura del Proyecto

```
src/main/java/org/uees/
├── 📁 controller/          # Controladores MVC
│   ├── TaskController.java     # Controlador principal
│   ├── SplashController.java   # Controlador del splash
│   └── ...
├── 📁 model/               # Modelos de datos
│   └── TasksModels/
│       ├── Task.java           # Clase Task
│       ├── ToDos.java          # Gestión de listas
│       ├── App.java            # Lógica de aplicación
│       └── ...
├── 📁 view/                # Vistas y componentes UI
│   ├── MainApp.java            # Clase principal
│   ├── TaskOrganizer.java      # Layout principal
│   ├── TaskCell.java           # Celda de tarea
│   └── ...
└── 📁 resources/           # Recursos estáticos
    ├── css/                    # Hojas de estilo
    ├── images/                 # Imágenes y logos
    └── org/uees/              # Archivos FXML
```

### **Patrón de Diseño: MVC (Model-View-Controller)**

- **Model:** Gestión de datos y lógica de negocio
- **View:** Componentes JavaFX y FXML
- **Controller:** Lógica de control y eventos

---

## 🗃️ Persistencia de Datos

### **Ubicación de Archivos:**

```
data/
├── Default.txt             # Lista por defecto
├── [NombreLista].txt       # Archivos por lista
└── ...
```

### **Formato de Datos:**

- **Serialización:** Automática con ObjectOutputStream
- **Backup:** Los archivos se guardan localmente
- **Recuperación:** Carga automática al iniciar

---

## 🎨 Personalización

### **Estilos CSS:**

```
src/main/resources/css/
├── primary.css             # Estilos principales
├── splash.css              # Estilos del splash
├── taskform.css            # Estilos del formulario
└── styles.css              # Estilos generales
```

### **Colores Principales:**

- **Primario:** `#4787ED` (Azul)
- **Secundario:** `#65C1CD` (Turquesa)
- **Acento:** `#4CAF50` (Verde)
- **Fondo:** `#F5F9F7` (Gris claro)

---

## 🧪 Para Desarrolladores

### **Estructura de Clases Principales:**

```java
// Crear nueva tarea
Task tarea = new Task(id, "Descripción", fechaLimite, prioridad);

// Agregar a lista
ToDos lista = appBackend.getTodoList("NombreLista");
lista.restoreTask(tarea);

// Guardar cambios
appBackend.saveAllToFiles();
```

### **Agregar Funcionalidades:**

Ver `DOCUMENTACION-SUSTENTACION.md` para ejemplos detallados de:

- Agregar tareas por código
- Crear listas automáticamente
- Modificar comportamiento de filtros
- Scripts para demostraciones

---

## 🐛 Solución de Problemas

### **Error común: "Module not found"**

```bash
# Solución: Limpiar y recompilar
mvn clean compile
```

### **Error: "JavaFX runtime components are missing"**

```bash
# Solución: Usar el plugin de JavaFX
mvn clean javafx:run
```

### **Error: "Cannot create data directory"**

- Verificar permisos de escritura en la carpeta del proyecto
- Ejecutar como administrador si es necesario

---

## 📊 Especificaciones Técnicas

| Componente         | Especificación              |
| ------------------ | --------------------------- |
| **JDK Mínimo**     | Java 17                     |
| **JavaFX**         | 17.0.2+                     |
| **Maven**          | 3.6+                        |
| **Resolución**     | 1280x832                    |
| **Memoria RAM**    | 4GB mínimo, 8GB recomendado |
| **Almacenamiento** | 50MB para la aplicación     |

---

## 🤝 Contribución

### **Para contribuir:**

1. Fork del repositorio
2. Crear branch de feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push al branch (`git push origin feature/nueva-funcionalidad`)
5. Crear Pull Request

---

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver `LICENSE` para más detalles.

---

## 👥 Autores

- **[Tu Nombre]** - Desarrollo principal
- **UEES** - Universidad Espíritu Santo

---

## 📞 Soporte

Para soporte técnico o preguntas:

- 📧 **Email:** [tu-email@uees.edu.ec]
- 🐛 **Issues:** [GitHub Issues]
- 📚 **Documentación:** `DOCUMENTACION-SUSTENTACION.md`

---

**⭐ Si te gusta este proyecto, dale una estrella en GitHub!**

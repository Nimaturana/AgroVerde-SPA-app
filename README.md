[![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-purple.svg)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.5+-blue.svg)](https://developer.android.com/jetpack/compose)
[![Android](https://img.shields.io/badge/Android-7.0+-green.svg)](https://www.android.com)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

Aplicación móvil para la distribución de productos agrícolas orgánicos, conectando productores rurales con ciudades a través de un sistema digital eficiente.

---


## 👨‍💻 **Autores**

**[Nicolas Maturana Y Andy Navarrete]**

---

## 🌾 **Descripción del Proyecto**

**AgroVerde SPA** es una aplicación móvil desarrollada en **Kotlin** con **Jetpack Compose** que permite a los usuarios:

- 🛒 Explorar y comprar productos agrícolas orgánicos
- 👤 Gestionar su perfil con foto personalizada
- 📦 Realizar y consultar pedidos
- 🌤️ Consultar información del clima en tiempo real
- 📍 Visualizar ubicación (integración GPS)
- 🔐 Autenticación segura con validaciones

---

## 🎯 **Caso de Negocio**

**AgroVerde SPA** apuesta por la distribución de productos agrícolas orgánicos, conectando productores rurales con ciudades a través de un sistema digital eficiente. 

### **Objetivo:**
Optimizar logística y gestión de pedidos en simultáneo, fortaleciendo la cadena de suministro sustentable y reduciendo la huella de carbono en la distribución.

### **Entidades del Dominio:**
- 🥬 **Producto Agrícola**: Productos orgánicos certificados
- 👨‍🌾 **Productor**: Agricultores y proveedores locales
- 📦 **Pedido**: Órdenes de compra con seguimiento
- 👤 **Cliente**: Usuarios finales
- 🚚 **Ruta de Entrega**: Optimización de logística

---

## ✨ **Características Principales**

### **Autenticación y Seguridad**
- ✅ Registro de usuarios con validaciones completas
- ✅ Login con credenciales seguras
- ✅ Persistencia de sesión con DataStore
- ✅ Validaciones en tiempo real (email, contraseña, teléfono)

### **Gestión de Perfil**
- 📷 **Captura de foto con cámara** (recurso nativo 1)
- 🖼️ **Selección desde galería** con permisos manejados
- 💾 **Almacenamiento local persistente** de imagen de perfil
- ✏️ Edición de datos personales

### **Catálogo de Productos**
- 🌱 Lista de productos orgánicos certificados
- 🔍 Filtrado por categorías (Verduras, Frutas)
- 🏷️ Información detallada (precio, stock, región, productor)
- ⭐ Calificaciones y certificación orgánica

### **Recursos Nativos**
- 📸 **Cámara/Galería** para foto de perfil (IE3.7)
- 🌤️ **Integración con API del clima** (wttr.in)
- 📍 **GPS/Ubicación** para rutas de entrega (preparado)
- 🔔 **Notificaciones** (permisos configurados)

### **Gestión de Estado**
- 🔄 Estados de carga, éxito y error
- ⚡ StateFlow para reactividad
- 🎨 Animaciones fluidas con Jetpack Compose

### **Diseño UI/UX**
- 🎨 Diseño coherente con tema AgroVerde
- 🌈 Paleta de colores verde naturaleza
- ✨ Animaciones con propósito (fadeIn, slideIn, animateItem)
- 📱 Interfaz responsive y moderna

---

## 💻 **Requisitos Técnicos**

### **Requisitos de Desarrollo**
- **Android Studio**: Hedgehog | 2023.1.1 o superior
- **Kotlin**: 1.9.0 o superior
- **JDK**: 11 o superior
- **Gradle**: 8.0+
- **SDK mínimo**: Android 7.0 (API 24)
- **SDK objetivo**: Android 14 (API 34)

### **Dispositivo/Emulador**
- Mínimo: Android 7.0 (Nougat)
- Recomendado: Android 10+ para mejor experiencia
- Permisos necesarios: Cámara, Almacenamiento, Internet, Ubicación

---

##  **Arquitectura**

La aplicación sigue el patrón **MVVM (Model-View-ViewModel)** con **Clean Architecture**:
```
┌─────────────────────────────────────────┐
│            UI Layer (Compose)            │
│  ┌─────────────┐  ┌─────────────┐      │
│  │   Screens   │  │ Components  │      │
│  └─────────────┘  └─────────────┘      │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│         ViewModel Layer                  │
│  ┌─────────────┐  ┌─────────────┐      │
│  │ AuthViewModel│  │ProductoVM   │      │
│  └─────────────┘  └─────────────┘      │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│         Repository Layer                 │
│  ┌─────────────┐  ┌─────────────┐      │
│  │UsuarioRepo  │  │ProductoRepo │      │
│  └─────────────┘  └─────────────┘      │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│          Data Layer                      │
│  ┌─────────────┐  ┌─────────────┐      │
│  │ Local (DS)  │  │Remote (API) │      │
│  └─────────────┘  └─────────────┘      │
└─────────────────────────────────────────┘
```

### **Capas principales:**
1. **UI (Jetpack Compose)**: Pantallas y componentes visuales
2. **ViewModel**: Gestión de estado y lógica de presentación
3. **Repository**: Abstracción del origen de datos
4. **Data Sources**: Local (DataStore) y Remote (Retrofit/HttpURLConnection)

---

## 🚀 **Instalación**

### **Clonar el repositorio:**
```bash
git clone https://github.com/tu-usuario/agroverde-spa-movil.git
cd agroverde-spa-movil
```

### **Abrir en Android Studio:**
1. Abrir Android Studio
2. File → Open → Seleccionar carpeta del proyecto
3. Esperar a que Gradle sincronice
4. Build → Make Project

### **Ejecutar en emulador/dispositivo:**
```bash
# Conectar dispositivo físico o iniciar emulador
# Luego ejecutar:
./gradlew installDebug
```

O simplemente presionar **Run (▶️)** en Android Studio.

---

## ⚙️ **Configuración**

### **1. Permisos (AndroidManifest.xml)**
Ya configurados en el proyecto:
```xml




```

### **2. FileProvider (para cámara)**
Configurado en `res/xml/file_paths.xml` y referenciado en el manifest.

### **3. Credenciales de prueba**
Puedes crear un usuario cualquiera este funcionara
por ejemplo:
```
Email: demo@agroverde.cl
Password: 123456
```

### **4. API del Clima**
Usa **wttr.in** (sin API key requerida). Para cambiar a OpenWeatherMap:
1. Obtener API key en https://openweathermap.org/api
2. Reemplazar en `util/ClimaUtils.kt`


---

## 🌐 **Endpoints API**

### **Base URL:** 
```
https://x8ki-letl-twmt.n7.xano.io/api:Rfm_61dW
```

### **Autenticación**

| Método | Endpoint | Body | Respuesta |
|--------|----------|------|-----------|
| POST | `/auth/signup` | `{ email, password, name }` | `201 { authToken, user }` |
| POST | `/auth/login` | `{ email, password }` | `200 { authToken, user }` |
| GET | `/auth/me` | Header: `Authorization: Bearer {token}` | `200 { user }` |

### **Productos** (Preparado)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/productos` | Lista de productos |
| GET | `/productos/{id}` | Detalle de producto |
| GET | `/productos/categoria/{categoria}` | Filtrar por categoría |

### **Clima (wttr.in)**
```
GET https://wttr.in/{ciudad}?format=j1
```

---

## 📸 **Capturas de Pantalla**

### **Login y Registro**
Pantalla de inicio de sesión con validaciones en tiempo real y animaciones fluidas.

<img width="399" height="903" alt="image" src="https://github.com/user-attachments/assets/b7c5fa47-d2e9-426f-b9c8-b8b00c29d71f" />
<img width="392" height="898" alt="image" src="https://github.com/user-attachments/assets/471fe12f-8497-420b-a89f-658928a9d630" />


### **Home / Catálogo**
Lista de productos orgánicos con filtros por categoría, certificación y región.

<img width="386" height="896" alt="image" src="https://github.com/user-attachments/assets/24a22833-d316-43be-9c9b-0cea8aa67376" />

### **Perfil con Cámara**
Gestión de perfil con captura de foto usando cámara nativa o selección desde galería.

<img width="388" height="893" alt="image" src="https://github.com/user-attachments/assets/729e6d92-3b2f-47c9-bab3-1be03d7b9b2d" />


### **Clima en Tiempo Real**
Consulta del clima actual con datos de temperatura, humedad y viento.

<img width="390" height="892" alt="image" src="https://github.com/user-attachments/assets/698d93d9-215e-405d-ab7f-885877f1dc4d" />


---

## 🛠️ **Tecnologías Utilizadas**

### **Frontend**
- **Jetpack Compose**: UI moderna y declarativa
- **Material Design 3**: Componentes y theming
- **Navigation Compose**: Navegación entre pantallas
- **Coil**: Carga de imágenes asíncrona

### **Arquitectura**
- **MVVM Pattern**: Separación de responsabilidades
- **StateFlow**: Gestión de estado reactiva
- **Coroutines**: Operaciones asíncronas
- **ViewModel**: Gestión de ciclo de vida

### **Almacenamiento**
- **DataStore Preferences**: Persistencia de token y sesión
- **File System**: Almacenamiento local de imágenes

### **Networking**
- **Retrofit**: Cliente HTTP (preparado)
- **HttpURLConnection**: Llamadas API simples
- **OkHttp**: Interceptores y logging
- **Gson**: Serialización JSON

### **Recursos Nativos**
- **CameraX**: Integración con cámara del dispositivo
- **Activity Result API**: Permisos y resultados
- **FileProvider**: Compartir archivos de forma segura
- **wttr.in API**: Datos meteorológicos en tiempo real

### **Testing y Debug**
- **Android Logcat**: Debugging
- **Compose Preview**: Vista previa de componentes

---


## 📝 **User Flows**

### **Flujo Principal: Compra de Productos**
```
1. Usuario abre app → LoginScreen
2. Ingresa credenciales → Validación
3. Login exitoso → HomeScreen (lista de productos)
4. Selecciona producto → DetalleProductoScreen
5. Agrega al carrito → Confirmación
6. Realiza pedido → PedidosScreen
```

### **Flujo: Gestión de Perfil con Foto**
```
1. Usuario en HomeScreen → Click en avatar
2. Selecciona "Mi Perfil" → PerfilScreen
3. Click en botón cámara → ImagePicker Dialog
4. Selecciona "Tomar foto" → Solicitud de permisos
5. Acepta permisos → Cámara nativa se abre
6. Captura foto → Imagen se guarda localmente
7. Avatar actualizado → Persistencia tras reinicio ✅
```

### **Flujo: Consulta del Clima**
```
1. Usuario en HomeScreen → Click botón clima ☀️
2. Solicitud a wttr.in API → Loading indicator
3. Respuesta exitosa → Dialog con datos reales
4. Muestra: temperatura, humedad, viento, presión
```

---

## 🔐 **Manejo de Errores**

### **Validaciones de Formularios**
- Email: Formato válido requerido
- Contraseña: Mínimo 6 caracteres
- Teléfono: Formato chileno (+56XXXXXXXXX)
- Confirmación de contraseña: Coincidencia obligatoria

### **Estados de Red**
- Loading: Indicadores visuales
- Success: Actualización de UI
- Error: Mensajes descriptivos con opción de reintentar

### **Permisos**
- Denegados: Dialogs informativos con instrucciones
- Fallback: Funcionalidad alternativa disponible

---

## 🚧 **Trabajo Futuro**

### **Funcionalidades Pendientes**
- [ ] Implementar carrito de compras completo
- [ ] Integración con pasarela de pagos
- [ ] Tracking GPS en tiempo real de entregas
- [ ] Notificaciones push para estado de pedidos
- [ ] Chat en vivo con productores
- [ ] Sistema de calificaciones y reviews
- [ ] Modo oscuro
- [ ] Soporte multiidioma

### **Mejoras Técnicas**
- [ ] Room Database para caché local
- [ ] Testing unitario e integración
- [ ] CI/CD con GitHub Actions
- [ ] Optimización de imágenes
- [ ] Implementación de paginación

---


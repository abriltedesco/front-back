# 📃 Documento de Alcance del Proyecto 📃
## ComidaApp 🍔

---

## 1. Objetivos del Sistema

ComidaApp tiene como objetivo ofrecer un servicio de pedido y entrega de comida rápida a través de una aplicación web moderna y eficiente. La plataforma centralizará la operación de los 5 locales del cliente, permitiendo a los usuarios explorar menús, realizar pedidos y efectuar pagos.

Los objetivos principales del sistema son:

- Brindar una experiencia de pedido online simple, rápida y confiable para los usuarios finales.
- Centralizar la gestión de los 5 locales de hamburguesas del cliente en una sola plataforma.
- Facilitar el proceso de pago mediante múltiples métodos disponibles.

---

## 2. Recursos Tecnológicos del Proyecto

El stack tecnológico ha sido seleccionado para garantizar rendimiento, escalabilidad y mantenibilidad del sistema.

### 2.1 Base de Datos

Se utilizará **MySQL** como motor relacional para almacenar la información de clientes, pedidos, productos, menús por local, historial de transacciones y datos de repartidores. Se diseñará un esquema normalizado con índices adecuados para garantizar consultas eficientes bajo carga.

### 2.2 Frontend

La interfaz de usuario será desarrollada con **React.js** y **TypeScript**, usando HTML y CSS para la estructura y estilos. Se priorizará una experiencia de usuario ágil mediante lazy loading, caché de componentes y optimización de renders.

### 2.3 Backend y API

El backend expondrá una **API REST** con autenticación de usuarios mediante **JWT (JSON Web Tokens)**. Se implementarán endpoints para gestión de menús, pedidos, pagos, usuarios y seguimiento de repartidores.

---

## 3. Funcionalidades del Sistema

Las siguientes funcionalidades forman parte del alcance comprometido del proyecto.

### 3.1 Gestión de Usuarios

- Registro e inicio de sesión mediante email y contraseña.
- Autenticación segura con JWT y manejo de sesiones.
- Perfil de usuario con historial de pedidos.

### 3.2 Menú y Catálogo de Productos

- Visualización del menú diferenciado por cada uno de los 5 locales.
- Catálogo de hamburguesas con descripción, precio e imágenes.
- Gestión de disponibilidad de productos por local.

### 3.3 Carrito y Pedidos

- Agregar, modificar y eliminar productos del carrito de compras.
- Confirmación de pedido con resumen detallado.
- Seguimiento del estado del pedido (recibido, en preparación, en camino, entregado).

### 3.4 Pagos

- Pago con tarjeta de crédito y débito (integración con pasarela de pago).
- Pago en efectivo contra entrega, con confirmación por parte del repartidor.

### 3.5 Incorporación de un Repartidor

- Notificaciones de estado durante el proceso de entrega.


---

## 4. Requisitos No Funcionales

El sistema deberá cumplir con los siguientes atributos de calidad:

- **Rendimiento:** tiempos de carga optimizados mediante caché, compresión de assets y carga progresiva. El objetivo es una experiencia fluida para el usuario.
- **Disponibilidad:** el sistema deberá estar operativo durante el horario de atención de los locales, con tolerancia a fallos básica.
- **Seguridad:** autenticación JWT, comunicación HTTPS, y protección de datos de pago delegada a la pasarela de pago (PCI-DSS compliance tercerizado).
- **Usabilidad:** interfaz responsiva optimizada para dispositivos móviles y desktop.

---
---

## 5. Límites del Proyecto

Los siguientes aspectos quedan explícitamente fuera del alcance de la versión inicial de ComidaApp.

### 5.1 Límites de Negocio

- El sistema contempla únicamente los 5 locales actuales del cliente. La incorporación de nuevos locales requerirá una nueva iteración de alcance.
- El catálogo de productos está limitado a hamburguesas y los productos actualmente ofrecidos en los locales. La expansión a otras categorías gastronómicas no está contemplada.
- No se incluye un sistema de fidelización, puntos, cupones ni descuentos automáticos.

### 5.2 Límites Técnicos y de Plataforma

- La aplicación es exclusivamente web (responsive). No se desarrollarán aplicaciones nativas para iOS ni Android en esta versión.
- No se desarrollará una versión para smartwatches ni dispositivos wearables.
- La infraestructura inicial no está diseñada para soportar picos masivos de usuarios (como 1.000.000 usuarios simultáneos). La arquitectura será dimensionada acorde a la base real del cliente (~500 clientes actuales) con capacidad de escalar de forma planificada.
- El tiempo de carga de la aplicación tendrá objetivos realistas según los estándares web modernos. No es técnicamente posible garantizar cargas en menos de 1 milisegundo; el compromiso es optimizar para obtener un **LCP (Largest Contentful Paint) inferior a 2,5 segundos**.


### 5.3 Elementos Descartados por Viabilidad

Los siguientes requerimientos fueron relevados del cliente pero no se incorporan al alcance por no aportar valor funcional o ser técnicamente inviables:

- **Animación 3D del logo al inicio:** no se incluye. Las animaciones de carga pesadas impactan negativamente en el rendimiento y la experiencia del usuario.
- **Soporte para relojes inteligentes:** no se incluye. La complejidad de desarrollo y el bajo impacto en la base de usuarios actual no justifican el esfuerzo en esta etapa.
- **Asistente de IA**: no se incluye. Conseguir información sobre el clima y en base a eso generar un pedido no está dentro de las capacidades de la tecnología que pensamos usar.
- **Seguimiento GPS de los repartidores**: no se incluye. El rastreo del repartidor y la tecnología que esto implica está fuera de nuestro alcance por el momento.

---

## 6. Criterios de Aceptación

El proyecto se considerará exitoso cuando se verifiquen los siguientes criterios:

- Un usuario puede registrarse, iniciar sesión, navegar el menú de cualquiera de los 5 locales, agregar productos al carrito y completar un pedido con pago por tarjeta o efectivo.
- La aplicación carga correctamente en dispositivos móviles y desktop, con tiempos de respuesta aceptables.

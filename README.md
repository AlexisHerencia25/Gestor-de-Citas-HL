# 🏥 Gestor de Citas Hospital Arzobispo Loayza

**Gestor de Citas** es una aplicación de escritorio desarrollada en **Java**, fue un proyecto solicitado por la Universidad Privada del Norte diseñado con la finalidad de prevenir una futura caida de red ya que la mayoria de sistemas que manejan las empresas son mediante sistemas webs que dependen de internet para funcionar, asi que creamos este sistema para prevenir inconvenientes y salvaguardar los datos almacenados que ya hay en el hospital 
Para este proyecto se utilizo la metodologia SCRUM para que el uso eficiente y el sistema modular de este programa amerite mucho al tiempo que se le dedico haciendolo, mas no se ignora las posibles futuras mejoras hacia este ya que no es completo del todo y aun se pueden añadir mas cosas.

## 🛠️ Tecnologías Utilizadas

- **Lenguaje**: Java  
- **Interfaz Gráfica**: Windows Forms (Swing)
- **Paradigma**: Programación orientada a objetos  
- **Arquitectura**: Modular y escalable
- **Base de datos**: JSON

---

### 🔐 Autenticación y Seguridad

- Iniciar sesión de forma segura con usuario y contraseña.
- Mostrar mensajes claros en caso de error o credenciales incorrectas.
- Recuperar contraseña desde un enlace accesible.
- Asignar roles y permisos a usuarios con acceso restringido por funciones.
- Bloquear, desbloquear o revocar accesos de usuarios.
- Registrar logs y auditoría de acciones y cambios de permisos.
- Generar alertas ante intentos de acceso no autorizados.

---

### 🧑‍⚕️ Gestión de Médicos

- Registrar nuevos médicos con sus datos completos (nombre, especialidad, contacto).
- Listar, buscar, editar o eliminar médicos por nombre, apellido o especialidad.
- Visualizar y modificar horarios disponibles por médico.
- Impedir asignación de citas en horarios no disponibles.

---

### 🧑‍💼 Gestión de Pacientes

- Registrar datos personales de pacientes con generación automática de ID único.
- Validar formatos y campos obligatorios al registrar o editar pacientes.
- Buscar, mostrar, actualizar o eliminar información de pacientes existentes.
- Mostrar historial clínico general vinculado a cada paciente.

---

### 📅 Agendamiento de Citas Médicas

- Buscar pacientes por nombre, apellido o ID.
- Filtrar médicos por especialidad para asignar citas.
- Ver disponibilidad médica organizada por día y hora.
- Seleccionar fechas y horarios disponibles para agendar citas.
- Registrar cita médica con todos los datos necesarios.
- Evitar citas duplicadas y mostrar alternativas si no hay disponibilidad (criterios generales).

---

### 📄 Cancelación y Reprogramación de Citas

- Buscar citas por paciente, médico o fecha.
- Cancelar citas existentes y notificar a los involucrados.
- Reprogramar citas validando disponibilidad médica y evitar duplicados.
- Registrar historial de cambios de cada cita.

---

### 🗂️ Historia Clínica del Paciente

- Registrar y actualizar información médica vinculada al paciente.
- Validar campos antes de guardar los registros clínicos.
- Mostrar la historia clínica completa y cronológica por paciente.
- Controlar acceso a esta información según el rol del usuario.
- Almacenar de forma segura la información clínica.

---

### 🧾 Generación de Comprobantes

- Generar comprobantes con datos de la cita médica: paciente, médico, especialidad, fecha y hora.
- Incluir un código único de comprobante para referencia.
- Mostrar logo institucional y nombre del centro médico.
- Exportar comprobantes en PDF o imprimir directamente desde la aplicación.
- Validar permisos del usuario antes de generar comprobantes.

---

✅ **Estado del proyecto**:  
- Funcionalidades principales implementadas  
- Módulos en desarrollo continuo  
- Interfaz intuitiva con validaciones, roles y experiencia de usuario optimizada

---

## 🖼️ Interfaz del Sistema

*He aqui algunas funcionalidades destacables del programa*

###  **Almacenamiento de datos pacientes/medicos/citas/administradores en JSON**:
<img width="734" height="547" alt="image" src="https://github.com/user-attachments/assets/a4fe1883-5b20-4f29-9bf7-a6d5fa674266" />
<img width="1049" height="510" alt="image" src="https://github.com/user-attachments/assets/1b2c8089-02dd-4159-bda4-7a273b7d4ac6" />

###  **Recuperacion de contraseña (Administrador)**:
<img width="1364" height="729" alt="image" src="https://github.com/user-attachments/assets/4c2fa681-90f7-4311-b6ee-4982d9a68314" />
<img width="1310" height="209" alt="image" src="https://github.com/user-attachments/assets/a3d63c2e-0288-4447-b6e9-b895a7a64c6b" />

### **Agendar y generar ticket de cita programada**:
<img width="1365" height="725" alt="image" src="https://github.com/user-attachments/assets/b4fc8f42-f347-4ae4-abc0-64d084885219" />
<img width="1358" height="566" alt="image" src="https://github.com/user-attachments/assets/abf15a81-880c-4821-ab40-de265bd86cbc" />



## 🚀 Cómo Ejecutarlo

1. Clona este repositorio:
   ```bash
   git clone https://github.com/AlexisHerencia25/Gestor-de-Citas-HL

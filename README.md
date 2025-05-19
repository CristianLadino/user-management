## 🚀 Instrucciones para ejecutar el proyecto
Este proyecto está desarrollado con:
- Java 21
- Maven 3.4.5
- Spring Boot
- MySQL
- IDE recomendado: IntelliJ IDEA
- Control de versiones: Git

## ✅ Requisitos previos
Antes de ejecutar el proyecto, asegúrate de tener instalado lo siguiente:
- Java JDK 21
- Apache Maven 3.4.5
- IntelliJ IDEA

## ⚙️ Configuración de la base de datos

Este proyecto utiliza **MySQL** como sistema de gestión de base de datos. A continuación se muestra la configuración incluida en el archivo `application.yml`:

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://{HOSTNAME}:{PORT}/{SCHEMA}
    username: {USERNAME_DATABASE}
    password: {PASSWORD_DATABASE}
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    database-platform: org.hibernate.dialect.MySQLDialect
```

## 🧱 Script para crear la base de datos

Antes de ejecutar la aplicación, asegúrate de crear la base de datos y la tabla `users` ejecutando el siguiente script en tu servidor **MySQL**:

```sql
CREATE SCHEMA `user_management`;

CREATE TABLE `user_management`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `create_at` DATETIME NOT NULL,
  `update_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE
);
```

## ▶️ Pasos para ejecutar
### 1️⃣ Clonar el repositorio:
   ```bash
   git clone https://github.com/CristianLadino/user-management.git
   cd user-management
```
### 2️⃣ Importar el proyecto en IntelliJ IDEA

1. Abre IntelliJ y selecciona **File > Open**.
2. Navega hasta el directorio del proyecto clonado.
3. Asegúrate de que el SDK esté configurado en **Java 21**.
4. Maven debería sincronizarse automáticamente. Si no lo hace, ve a la pestaña **Maven** y selecciona *Reload All Maven Projects*.

### 3️⃣ Construir aplicación

Desde la barra de menú, haz clic en:
   - `Build > Build Project` o usa el atajo `Ctrl+F9` (Windows/Linux) o `Cmd+F9` (macOS).

### 4️⃣ Ejecutar la aplicación

1. Abre la clase `UserManagementApplication`.
2. Haz clic derecho sobre la clase y selecciona **Run 'UserManagementApplication'**.

### 5️⃣ Verificar ejecución

- La aplicación debería iniciar en: [http://localhost:8080](http://localhost:8080).

## 📊 Generar informe de cobertura con JaCoCo

Este proyecto utiliza **JaCoCo** para medir la cobertura de pruebas.


## ▶️ Ejecutar el informe de cobertura

Para generar el reporte de cobertura con **JaCoCo**, ejecuta el siguiente comando en la raíz del proyecto:

```bash
mvn clean verify
```

Esto generará un informe en la siguiente ruta:
```bash
target/site/jacoco/index.html
```
Abre ese archivo en tu navegador para visualizar el reporte de cobertura de pruebas.

## ⚙️ Decisiones de diseño

### 📁 Estructura del proyecto

Este proyecto implementa una **arquitectura hexagonal (ports and adapters)**, inspirada en principios de **Clean Architecture** y **separación de responsabilidades**, lo que facilita la escalabilidad, mantenibilidad y testeo del sistema.

- `domain/`  
  Contiene la **lógica de negocio** pura y central de la aplicación.  
  Incluye:
  - Casos de uso (`usecases`), que encapsulan la lógica específica del sistema.
  - Modelos (`models`) que representan las entidades del dominio.
  - Excepciones personalizadas del dominio (`DomainException`), utilizada tambien para personalizar la respuesta en el `HandlerException`.
  - Validaciones personalizadas como `@ValidPassword`.

- `infrastructure/`  
  Alberga la implementación concreta de los adaptadores, incluyendo:
  - Controladores REST (adapters de entrada).
  - Implementaciones de gateways (adapters de salida).
  - Entidades JPA (`UserEntity`) para la persistencia.
  - Mapeadores (`MapStruct`) para convertir entre capas.



---

### 🔐 Validaciones personalizadas

Se implementó una anotación `@ValidPassword` para aplicar reglas de validación específicas a contraseñas.  
Estas validaciones se utilizan directamente en los DTOs (`CreateUserRequest`, `UpdateUserRequest`) y se prueban con **JUnit 5** y el validador de **Jakarta Bean Validation**.

---

### 🧪 Tests

- Se utilizan **JUnit 5** y **Mockito** para pruebas unitarias.
- Se cubren:
  - Casos de uso.
  - Controladores.
  - Mapeadores (`MapStruct`).
  - Validaciones personalizadas.
- Clases como entidades JPA o `UserManagementApplication` pueden ser excluidas del reporte de cobertura usando configuración en `pom.xml`.

---

### 🧰 Librerías y herramientas utilizadas

- **Lombok**: Para reducir el Código genérico (getters, setters, builders, etc.).
- **MapStruct**: Para mapeo automático entre entidades, modelos y respuestas.
- **JaCoCo**: Para generar reportes de cobertura de pruebas.
- **Spring Boot**: Para facilitar la configuración y ejecución del proyecto.

## 📖 Documentación de la API – Swagger

Este proyecto incluye documentación interactiva generada automáticamente con **Swagger UI**.

Una vez que la aplicación está en ejecución, puedes acceder a la documentación desde:

🔗 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

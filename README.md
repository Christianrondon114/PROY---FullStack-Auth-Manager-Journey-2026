Primer intento de realizar un proyecto FullStack con Spring Security: reaprendiendo HTML CSS y Javascript y aplicando mi conocimiento en Backend con Java Springboot. JavaScript como agente para la conexión Side-client con el Backend.

Este proyecto lo iré actualizando continuamente hasta acabarlo...

        07/01/2026:
        - Hasta el momento este proyecto se va tornando a la authentication y authorization, no muy alejado a un microservicio,
        pero como aún no sé del tema, se hará un monolito.
        - Implementación de UserDetailsService con la base de datos JDBC MySQL. Encriptación de contraseñas BCrypt. Definición
        más delgada del Role Hierarchy.
        - He leído e integrado:
                1) UserDetailsService: https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/user-
                details-service.html
                2) Password Storage: https://docs.spring.io/spring-security/reference/features/authentication/password-
                storage.html
        - Ahora toca hacer:
                1) Parte técnica: creación y adaptación de la Entidad Authorities en el sistema
                2) Parte técnica: creación del register
                3) Front-end: interfaces para manejo de errores cómo: forbidden access, error 404, etc.
                4) Parte técnica: creación de la recuperación de contraseña tanto frontend/backend

        
        06/01/2026: 
        - Implementación del Logout (de manera muy básica porque al no usar Thymeleaf no tengo automáticamente el CSRF para 
        configuraciones más avanzadas)
        - He leído e integrado: 
                1) Servlet Authentication Architecture: https://docs.spring.io/spring-security/reference/servlet/authentication/
                architecture.html
                2) Username/Password Authentication: https://docs.spring.io/spring-security/reference/servlet/authentication/
                passwords/index.html
                3) UserDetailsService: https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/
                user-details-service.html
                4) Spring Security Features Authentication Password Storage: https://docs.spring.io/spring-security/
                reference/features/authentication/password-storage.html#authentication-password-storage-configuration
                5) Handling Logouts: https://docs.spring.io/spring-security/reference/servlet/authentication/logout.html
        - Ahora toca:
                1) Parte técnica: implementar usuarios reales con UserDetailsService
                2) Parte técnica: Encriptar contraseñas de los usuarios con BCrypt
                3) Parte teórica: leer Password Storage https://docs.spring.io/spring-security/reference/features/authentication/password-storage.html
        

        03/01/2026: 
        - Hasta ahora solamente tengo un Login funcional con un usuario en memoria de UserDetailsService (admin, 123) y 
        una pagina de inicio para administradores incluido con CRUD de usuarios 'zombie' porque estos usuarios 
        no están conectados en la base de datos.
        - Frontend:  HTML, CSS básico y JavaScript para la conexión entre el back y el front
        - Integración de Spring Security y desglosamiento del SecurityFilterChain en 3 partes para mejor acoplamiento
        y recomendación misma de la documentación.
        - He leído e integrado hasta ahora: 
                1) Servlet Applications Architecture: https://docs.spring.io/spring-security/reference/servlet/
                architecture.html#servlet-delegatingfilterproxy
                2) Authorization Architecture: https://docs.spring.io/spring-security/reference/servlet/
                authorization/architecture.html
                3) Authorize HttpServletRequests: https://docs.spring.io/spring-security/reference/servlet/
                authorization/authorize-http-requests.html
        - Ahora toca leer: 
                1) Servlet Authentication Architecture: https://docs.spring.io/spring-security/reference/servlet/
                authentication/architecture.html
                2) Username/Password Authentication: https://docs.spring.io/spring-security/reference/servlet/
                authentication/passwords/index.html

       

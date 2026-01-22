Primer intento de realizar un proyecto FullStack con Spring Security: reaprendiendo HTML CSS y Javascript y aplicando mi conocimiento en Backend con Java Springboot. JavaScript como agente para la conexión Side-client con el Backend.

Este proyecto lo iré actualizando continuamente hasta acabarlo...

Backend: Java 17+, Spring Boot 3, Spring Security (JDBC Auth), Hibernate/JPA.
Frontend: Vanilla JS, HTML5, CSS3 (Próximamente TS/Angular).
DB: MySQL.
        22/01/2026:
        - Probando el Servicio del Carrito, me di cuenta que mis métodos están muy mal y no estoy aprove-
        chando el Spring Security :P ... Cualquiera podría poner IdUser?=5 (ID's de diferentes usuarios) 
        en el URL y tendría acceso (getShoppingCart/AddToCart) al carrito de otro usuario cómo si de un 
        administrador se tratara. En fin... tuve que investigar y leer de nuevo para manejar las sesiones
        individuales y evitar esto y lo logré con @AuthenticationPrincipal
                Leí con más entendimiento esta segunda vez:
                - Servlet Authentication Architecture: https://docs.spring.io/spring-security/reference/
                servlet/authentication/architecture.html
                - Spring MVC Integration: https://docs.spring.io/spring-security/reference/servlet/
                integrations/mvc.html#mvc-authentication-principal



        20/01/2026 - 21/01/2026: 
        - Frontend: Creación de tarjetas funcionales conectadas al Product Management del Administrador
        - Backend: Creación del servicio del Carrito, carrito para usuario
        que futuramente se creará al intentar meter un producto al carrito. Eliminación de Items del 
        carrito mediante id del CartItem. Eliminación de todos los items del Carrito de Compras. 
                Toca hacer:
                - Controlador del Carrito. 
                - Implementar respuestas ResponseEntity
                - Armar frontend carrito de compras para los usuarios y conectarlo al backend.
                Próximamente:
                - Tocará manejar excepciones globales personalizadas.
                - Creación de los pedidos y sus respectivo manegement en el administrador
                - Seguir leyendo la arquitectura y recomendaciones de Spring MVC.
                
        
        19/01/2026:
        - Commit del día: recién creando e integrando la interfaz de la página principal del usuario 
        (USER, GUEST) haciendo uso del bean AuthenticationSuccessHandler del contener de Spring Security
        para dirigir a páginas correspondientes según tu role y authorities
                        guest/user -> home_user
                        mod/admin -> home_admin
                Meta:
                1) Crear catálogo con tarjetas funcional para que el usuario vea los productos registrados
                reflejando lo creado y editado en el Product Management del Administrador.
                2) Creación de la lógica del Carrito.
                3) Implementación de la lógica de Pedidos y sus entidades.
        

        
        18/01/2026:
        - Hoy quise avanzar código porque cuanto más leo más se me acumula cosas que aplicar, así que...
        hoy implementé y cree el frontend product management del administrador, con la conexión hacia 
        el backend y la exposición de sus datos mediante DTO's al front. 
                Logro:
                1) Mi Product Controller mejorado con las buenas prácticas de la documentación oficial 
                de Spring MVC sirve correctamente.
                

        16/01/2026:
        - Sigo leyendo documentación oficial de Spring MVC, ya comprendiendo ResponseEntity y Controller
        Advice para el control de Exceptiones globales. A la par aprendiendo Typescript para luego inte-
        grarlo en un futuro con Angular
        - No he estado avanzando código porque la teoría es más importante, cuando llegue el momento de 
        hacer código de nuevo tendré como tarea:
                1) Manejo de Excepciones
                2) Mejorar Controllers y Services con ResponseEntity
                3) Creación del carrito y la lógica del negocio correspondiente

        
        13/01/2026:
        - Estos días he estado leyendo documentación oficial de Spring MVC ya que decidí mejorar mi forma
        de hacer controllers
         - He leído:
                1) DispatcherServlet: https://docs.spring.io/spring-framework/reference/web/webmvc/
                mvc-servlet.html (Context Hierarchy, Special Bean Types, Web MVC Config, Servlet Config, 
                Processing, Path Matching, Interception, Exceptions View Resolution. Ahora entiendo
                lo que puede ofrecer Spring MVC.
                2) Especificamente Mapping Requests: https://docs.spring.io/spring-framework/reference
                /web/webmvc/mvc-controller/ann-requestmapping.html donde explican las mejores prácticas


        11/01/2026:
        - Ya terminado la autorización y autenticación (de manera no refinada) por fin he decidido la di-
        rección de este proyecto, ya que el Spring Security está implementado de manera básica, intentaré
        implementar una tienda virtual con carritos, users, roles, itemcarts, products.
        - Integrado:
                1) Creación del model -> repository -> service -> controller del Product.
                2) Listar productos, crear productos y eliminar productos guiandome con mis anteriores
                proyectos
        - Toca hacer:
                1) backend: Completar CRUD
                2) front: implementar las interfaces en el panel de administrador (crud productos)
                3) conexión backend -> frontend

                
        08/01/2026:
        - Hoy día he integrado la creación de interfaz de registro de usuarios, la lógica de negocio del 
        register habilitado a todos los usuarios sin importar su authentication además de que los nuevos
        usuarios se reflejan en el CRUD de administradores y en la base de datos MySQL.
        - No he leído ni integrado nada nuevo, ya tengo entendido el flujo front -> backend con 
        JavaScript, la lógica de negocio del Registro de usuarios lo logré con conocimiento apren-
        dido hasta ahora
        - Ahora toca hacer:
                1) Parte técnica: creación y adaptación de la Entidad Authorities en la base de datos para 
                el sistema
                2) Parte técnica: creación de la recuperación de contraseña.
                3) Parte teórica: Password Storage (terminar de leer recuperación de contraseña): https:
                //docs.spring.io/spring-security/reference/features/authentication/password-storage.html
        
        07/01/2026:
        - Hasta el momento este proyecto se va tornando a la authentication y authorization, no muy alejado
        a un microservicio, pero como aún no sé del tema, se hará un monolito.
        - Implementación de UserDetailsService con la base de datos JDBC MySQL. Encriptación de 
        contraseñas BCrypt. Definición más delgada del Role Hierarchy y Authorities.
        - He leído e integrado:
                1) UserDetailsService: https://docs.spring.io/spring-security/reference/servlet/
                authentication/passwords/user-details-service.html
                2) Password Storage: https://docs.spring.io/spring-security/reference/features/
                authentication/password-storage.html
        - Ahora toca hacer:
                1) Parte técnica: creación y adaptación de la Entidad Authorities en el sistema
                2) Parte técnica: creación del register
                3) Front-end: interfaces para manejo de errores cómo: forbidden access, error 404, etc.
                4) Parte técnica: creación de la recuperación de contraseña tanto frontend/backend

        
        06/01/2026: 
        - Implementación del Logout (de manera muy básica porque al no usar Thymeleaf no tengo 
        automáticamente el CSRF para configuraciones más avanzadas)
        - He leído e integrado: 
                1) Servlet Authentication Architecture: https://docs.spring.io/spring-security/
                reference/servlet/authentication/architecture.html
                2) Username/Password Authentication: https://docs.spring.io/spring-security/
                reference/servlet/authentication/passwords/index.html
                3) UserDetailsService: https://docs.spring.io/spring-security/reference/servlet/
                authentication/passwords/user-details-service.html
                4) Spring Security Features Authentication Password Storage: 
                https://docs.spring.io/spring-security/reference/features/authentication/
                password-storage.html#authentication-password-storage-configuration
                5) Handling Logouts: https://docs.spring.io/spring-security/reference/servlet/
                authentication/logout.html
        - Ahora toca:
                1) Parte técnica: implementar usuarios reales con UserDetailsService
                2) Parte técnica: Encriptar contraseñas de los usuarios con BCrypt
                3) Parte teórica: leer Password Storage https://docs.spring.io/spring-security/
                reference/features/authentication/password-storage.html
        

        03/01/2026: 
        - Hasta ahora solamente tengo un Login funcional con un usuario en memoria de UserDetailsService
        (admin, 123) y una pagina de inicio para administradores incluido con CRUD de usuarios 'zombie'
        porque estos usuarios no están conectados en la base de datos.
        - Frontend:  HTML, CSS básico y JavaScript para la conexión entre el back y el front
        - Integración de Spring Security y desglosamiento del SecurityFilterChain en 3 partes para 
        mejor acoplamiento y recomendación misma de la documentación.
        - He leído e integrado hasta ahora: 
                1) Servlet Applications Architecture: https://docs.spring.io/spring-security/reference/
                servlet/architecture.html#servlet-delegatingfilterproxy
                2) Authorization Architecture: https://docs.spring.io/spring-security/reference/servlet/
                authorization/architecture.html
                3) Authorize HttpServletRequests: https://docs.spring.io/spring-security/reference/servlet/
                authorization/authorize-http-requests.html
        - Ahora toca leer: 
                1) Servlet Authentication Architecture: https://docs.spring.io/spring-security/reference/servlet/
                authentication/architecture.html
                2) Username/Password Authentication: https://docs.spring.io/spring-security/reference/servlet/
                authentication/passwords/index.html

       

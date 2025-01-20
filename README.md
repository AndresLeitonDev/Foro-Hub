# Foro-Hub
Proyecto creado para recrear el funcionamiento de un foro, creando topicos relacionados a usuarios, se aplicarán conocimientos a nivel de Backend haciendo uso de Springboot y RES API.

## Configuración

El proyecto esta enfocado a la realización del Backend por lo cual no se tiene una interfaz amigable al usuario.
Para la realización de las pruebas se hace uso de la herramienta Postman, que facilita la realización de pruebas de los endpoints creados.

Para desplegar la aplicación realice los siguientes pasos:

1. Cree un directorio donde desea descargar la app
2. Ingrese al bach de git y clone el proyecto con el siguiente comando:
```bash
git clone https://github.com/AndresLeitonDev/Foro-Hub.git
```
3. Abra el proyecto con el editor de preferencia (En el ejemplo visualcode)
4. Realice la compilación del proyecto y ejecutelo.

## Funcionamiento

Esta aplicación requiere MYSQL para la persistencia de datos, por favor asegurese de tenerlo instalado, si ya cuenta con este motor modifique los datos en el archivo application.properties con sus datos, nombre de la base de datos, ip, puerto, usuario y contraseña.

![image](https://github.com/user-attachments/assets/b670f7dd-aa21-456b-8987-71edf13c3614)

Una vez inicie la aplicación esta intentara crear todo el modelo de base de datos requerido para el funcionamiento, para efecto de pruebas la aplicacion viene con un usuario creado con el cual puede generar un token de acceso.

Para generar un toquen ingrese a postman a la siguiente URL:

- http://localhost:8080/login a través de un POST y en el cuerpo de la petición el siguiente objeto.
```bash
{
    "nombre" : "admin",
    "password" : "123qweZ!"
}
```

![image](https://github.com/user-attachments/assets/26523db3-60b2-411b-b0fd-db7bb20fdd87)

- Con este token puede realizar peticiones tipo POST, PUT, DELETE
- Las peticiones GET no están restringidas
- Todas las peticiones se realizan de la misma forma, POST para crear, PUT para actualizar y DELETE para eliminar, solo debes cambiar la url dependiendo del tipo

Ejemplo con Topico

- Se debe crear un curso, la aplicación valida que se envien los datos necesarios, en otro caso no permite la creación, no olvide enviar el token generado en el paso anterior.

http://localhost:8080/curso (POST)

```bash
{
    "id":"1",
    "nombre" : "springboot",
    "categoria" : "software"
}
```

ERROR SIN TOKEN 

![image](https://github.com/user-attachments/assets/c2b8b2ea-631c-416b-8af2-b87cefcd4682)

RESPUESTA ENVIANDO EL TOKEN

![image](https://github.com/user-attachments/assets/45c08edf-e9e3-49d6-ade2-ec850d7e91bf)

1. Ver todos los Topicos - http://localhost:8080/topico
   
![image](https://github.com/user-attachments/assets/8460b418-1b47-45f9-b52c-a7f3b44c34d0)

3. Ver Topicos por ID - http://localhost:8080/topico/1
4. Ver Topico por nombre de curso - http://localhost:8080/topico/curso/springboot
5. Ver Topico por año - http://localhost:8080/topico/anio/2025
6. Ver Topico por orden de creación - http://localhost:8080/topico/order
7. Crear Topico - http://localhost:8080/topico (POST)
```bash
{
    "titulo" : "topico",
    "mensaje": "mensaje",
    "fechaCreacion" : "2025-01-19",
    "status" : "creado",
    "curso":
        {
            "id":"1",
            "nombre" : "springboot",
            "categoria" : "software"
        },
    "autor":
        {
            "id": 1,
            "nombre": "admin",
            "email": "admin@alura.com",
            "password": "123qweZ!",
            "perfiles": [
                {
                    "id": 1,
                    "nombre": "desarrollo"
                }
            ]
        }
}
```
7. Modificar topico - http://localhost:8080/topico (PUT)
8. Eliminar topico - http://localhost:8080/topico/1 (DELETE)






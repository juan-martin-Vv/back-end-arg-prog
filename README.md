# BackEndArgProgJwt
BackEnd implementado con Jwt

## Pruebas 
Para probar la aplicacion en desarrollo se 
usaran las configuraciones para una DB H2 por ser mas rapida

### Usar el archivo "application-dev.properties" y configurar su IDE para que use este archivo en modo DEBUG

-Dspring.profiles.active=dev

### Para executar el JAR en modo desarrollo

java -jar -Dspring.profiles.active=dev XXX.jar

## Para el despliegue 
La aplicacion usara el archivo

"application.properties"

Este archivo esta prepardo para recivir las variables de entorno de HEROKU como dice sus documentacion
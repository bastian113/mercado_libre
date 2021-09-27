# Prueba Mercado Libre

Este código fuente fue creado para dar solución al reto propuesto por MercadoLibre

# Instrucciones de ejecución
## Prerrequisitos
* Java 11
* MySQL Database Version 5.6.50

## Ejecución local
1. Clonar el proyecto
2. Importar en la base de datos el script ubicado en "**\mercado-libre\src\main\resources\scripts\initDBScript.sql"
3. Ejecutar los siguienes comandos en la carpeta raíz del proyecto
     - `mvn clean install`
     - `mvn spring-boot:run -Dspring-boot.run.profiles=`
4. Para generar el reporte de cobertura de los test, ejecutar el siguiente comando en la carpeta raíz del proyecto
     - `mvn clean test`
     - El archivo con los reportes se genera en la siguiente ruta:
        - "\mercado-libre\target\site\jacoco\index.html"
  
Con esto el proyecto estaría listo para ser probado localmente.


## Ejecución ambiente productivo
* URL del servicio POST para evaluar la cadena:
     - https://mercado-prod.herokuapp.com/mutant/
     
    - Ejemplo del formato de la cadena:

    `{
         "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
      }`
     
* URL del servicio para obtener las estadísticas:
     - https://mercado-prod.herokuapp.com/stats/
     
## Documentación
* [Swagger](https://mercado-prod.herokuapp.com/swagger-ui/)

Ejercicio de hace la entidad coches para trabajar con ella en BBDD y CON MAVEN

Los coches tendrán las siguientes atributos
id
matricula;
marca;
modelo;
kilometros;

Se mostrará un menu con las siguientes opciones:

1. Alta de coche
2. Eliminar coche por id
3. Modificar coche por id
4. buscar coche por id
5. buscar coche por matricula
6. buscar coches por marca
7. buscar coches por modelo
8. listar todos los coches
9. Salir de la aplicación
10. Exportar los coches a un fichero en formato JSON
11. Exportar los coches a un fichero excel (o PDF)
	https://github.com/fdepablo/WorkspaceJava/tree/master/30_Maven

Tendremos las siguientes reglas de negocio

La matricula tendrá una longitud de 7
No puede haber dos matriculas repetidas
Los kilometros no podrán ser negativos

Hacerlo mediante el modelo de las 3 capas que hemos visto en clase

Quiero que probéis mediante JUNIT un método de vuestros.(probar el metodo obtener coche por id del DAO)
Quiero que generéis un ejecutable del proyecto mediante maven y que se pasen tambien las pruebas 
unitarias


IMPORTANTE!: 

EN EL EJERCICIO 14 FALTABA EL MENÚ. SE CAMBIÓ Y AHORA ESTA IMPLEMENTADO PARA AÑADIR LAS OPCIONES 10 Y 11. 

EXTRA: 

Se ha añadido 12. Exportar los coches a un fichero PDF 
En el PDF se ha añadido una imagen
Se han modificado algunos bucles for por funciones lambda para practicar.
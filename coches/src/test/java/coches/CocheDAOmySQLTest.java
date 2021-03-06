package coches;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import modelo.entidades.Coche;
import modelo.persistencia.CocheDAOmySQL;
import modelo.persistencia.interfaces.DAOCocheInterfaz;

/*Quiero que prob?is mediante JUNIT un m?todo de vuestros.(obtener coche por id del DAO)
 * Quiero que gener?is un ejecutable del proyecto mediante maven y que se pasen tambien las pruebas 
unitarias*/

public class CocheDAOmySQLTest {
	
	private DAOCocheInterfaz daoCocheI = new CocheDAOmySQL();

	
	@Test
	void buscarCocheIdExistente(){
		
		int idEsperado = 2;
		
		int idObtenido = daoCocheI.buscarCocheId(2).getId();
		
		assertEquals(idEsperado, idObtenido);

	}
	@Test
	void buscarCocheIdNoExistente(){
		//assertThrows(Exception, funcion lambda). 
		//Comprueba a partir de una funcion lambda si ha ocurrido una excepcion especifica.
		assertThrows(NullPointerException.class, () -> {
					int idObtenido = daoCocheI.buscarCocheId(1110).getId();
				});

	}
}

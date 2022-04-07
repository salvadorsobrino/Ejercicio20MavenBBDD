package modelo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import com.google.gson.Gson;

import modelo.entidades.Coche;
import modelo.negocio.GestorCoche;

public class mainCoche {

	public static void main(String[] args) {

		System.out.println("Bienvenidos a nuestra CRUD de coches");
		Scanner sc = new Scanner(System.in);
		boolean fin = false;
		GestorCoche gc = new GestorCoche();
		List<Coche> lista = null;

		do {
			menu();
			int opcion = sc.nextInt();
			switch (opcion) {
			case 1:
				System.out.println("Dar alta coche (matricula/marca/modelo/km). Pulsa ENTER por cada valor");

				String matricula = sc.next();
				String marca = sc.next();
				String modelo = sc.next();
				int km = sc.nextInt();

				Coche c = new Coche(matricula, marca, modelo, km);
				int alta = gc.alta(c);
				if (alta == 0) {
					System.out.println("Coche dado de alta");
				} else if (alta == 1) {
					System.out.println("Error de conexión con la BBDD");
				} else if (alta == 2) {
					System.out.println("La matricula no tiene 7 carácteres o esta repetida o los KM<0");
				}
				break;
			case 2:
				System.out.println("Eliminar coche (id). Introduce la id ");
				int id = sc.nextInt();
				if (gc.baja(id)) {
					System.out.println("Coche eliminado");
				} else {
					System.out.println("No se ha podido eliminar");
				}
				break;
			case 3:
				System.out.println("Modificar coche (id). Introduce la id ");
				id = sc.nextInt();
				if (gc.buscarId(id) == null) {
					System.out.println("No se ha encontrado el coche");
				} else {
					System.out.println(gc.buscarId(id));
					System.out.println("Modificar valores (matricula/marca/modelo/km). Pulsa ENTER por cada valor");

					matricula = sc.next();
					marca = sc.next();
					modelo = sc.next();
					km = sc.nextInt();

					c = new Coche(id, matricula, marca, modelo, km);
					int modificar = gc.modificar(c);
					if (modificar == 0) {
						System.out.println("Coche modificado");
					} else if (modificar == 1) {
						System.out.println("Error de conexión con la BBDD");
					} else if (modificar == 2) {
						System.out.println("La matricula no tiene 7 carácteres o esta repetida o los KM<0");
					}
					System.out.println(gc.buscarId(id));
				}

				break;
			case 4:
				System.out.println("Buscar coche por ID: ");
				id = sc.nextInt();
				if (gc.buscarId(id) == null) {
					System.out.println("No se ha encontrado el coche");
				} else {
					System.out.println(gc.buscarId(id));
				}
				break;
			case 5:
				System.out.println("Buscar coche por matricula: ");
				matricula = sc.next();
				if (gc.buscarMatricula(matricula) == null) {
					System.out.println("No se ha encontrado el coche");
				} else {
					System.out.println(gc.buscarMatricula(matricula));
				}

				break;
			case 6:
				System.out.println("Buscar coches por marca: ");
				marca = sc.next();
				lista = gc.buscarMarca(marca);
				if (lista.size() > 0) {
					for (int i = 0; i < lista.size(); i++) {
						System.out.println(lista.get(i));
					}
				} else {
					System.out.println("No se ha encontrado la marca");
				}
				break;
			case 7:
				System.out.println("Buscar coches por modelo: ");
				modelo = sc.next();
				lista = gc.buscarModelo(modelo);
				if (lista.size() > 0) {
					for (int i = 0; i < lista.size(); i++) {
						System.out.println(lista.get(i));
					}
				} else {
					System.out.println("No se ha encontrado el modelo");
				}
				break;
			case 8:
				System.out.println("A continuaci�n se listan todos los coches de la bbdd: ");
				lista = gc.listar();
				lista.forEach(s -> {
					System.out.println(s);
				});
				break;
			case 9:
				fin = true;
				break;
			case 10:
				Gson gson = new Gson();
				lista = gc.listar();
				FileWriter fileWriter = null;
				try {
					fileWriter = new FileWriter("Fichero json creado en src/main/resources/coches.json");
					gson.toJson(lista, fileWriter);
					fileWriter.close();
					System.out.println("Fichero Json creado");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 11:
				String[] columns = { "Id", "Matricula", "Marca", "Modelo", "Kilometros" };

				Workbook workbook = new HSSFWorkbook();

				Sheet sheet = (Sheet) workbook.createSheet("Coches");
				Row row = sheet.createRow(0);

				for (int i = 0; i < columns.length; i++) {
					Cell cell = row.createCell(i);
					cell.setCellValue(columns[i]);
				}

				lista = gc.listar();
				int initRow = 1;
				for (Coche cx : lista) {
					row = sheet.createRow(initRow);
					row.createCell(0).setCellValue(cx.getId());
					row.createCell(1).setCellValue(cx.getMatricula());
					row.createCell(2).setCellValue(cx.getMarca());
					row.createCell(3).setCellValue(cx.getModelo());
					row.createCell(4).setCellValue(cx.getKilometros());
					initRow++;
					
				}
				
				try (OutputStream fileOut = new FileOutputStream("src/main/resources/coches.xls")) {
					workbook.write(fileOut);
					workbook.close();
					System.out.println("Fichero excel creado en src/main/resources/coches.xls");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case 12:
				lista = gc.listar();
				try (PDDocument doc = new PDDocument()) {

					PDPage myPage = new PDPage();
					doc.addPage(myPage);

					try (PDPageContentStream cont = new PDPageContentStream(doc, myPage)) {

						cont.beginText();
						cont.setFont(PDType1Font.TIMES_ROMAN, 12);
						cont.setLeading(14.5f);
												
						cont.newLineAtOffset(20, 700);
						String titulo = "COCHES EN FORMATO PDF:";
						cont.showText(titulo);
						//We start a new line of text with newLineAtOffset method. 
						//The origin of a page is at the bottom-left corner.
						/*for (int i=0;i<lista.size();i++) {
							String line = lista.get(i).toString();
							cont.newLine();
							cont.showText(line);
						}*/
						lista.forEach(s -> {
							String line = s.toString();
							try {
								cont.newLine();
								cont.showText(line);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						});
						cont.endText();
						String imgFileName = "src/main/resources/teslacar.jpg";
			            PDImageXObject pdImage = PDImageXObject.createFromFile(imgFileName, doc);
			                
			            cont.drawImage(pdImage, 200, 100, 250, 250);
						cont.close();
						
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
		            
					doc.save("src/main/resources/coches.pdf");
					System.out.println("Fichero pdf creado en src/main/resources/coches.pdf");
					System.out.println("Refresque el proyecto en caso de que no aparezca");
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}

				break;
			}
		} while (!fin);

		sc.close();
		System.out.println("Fin de programa");

	}

	private static void menu() {
		System.out.println("Elija una opci�n:");
		System.out.println("1- Alta de coche");
		System.out.println("2- Eliminar coche por id");
		System.out.println("3- Modificar coche por id");
		System.out.println("4- Buscar coche por id");
		System.out.println("5- Buscar coche por matricula");
		System.out.println("6- Buscar coches por marca");
		System.out.println("7- Buscar coches por modelo");
		System.out.println("8- Listar todos los coches");
		System.out.println("9- Salir del programa");
		System.out.println("10. Exportar los coches a un fichero en formato JSON");
		System.out.println("11. Exportar los coches a un fichero Excel");
		System.out.println("12. Exportar los coches a un fichero PDF");
	}
}

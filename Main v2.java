import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.stream.Stream;

public class Main {
	
	public static final String tempFilePath = System.getProperty("user.dir")+"/";
	
	public static void intro() {
		System.out.println("ALGORITMOS CRIPTOGRAFICOS");
		System.out.println("");
		System.out.println("Sintaxis: java <algoritmo>");
		System.out.println("");
		System.out.println("<algoritmo>:");
		System.out.println("");
		System.out.println("\t-vig\t Algoritmo Vigenere");
		System.out.println("\t-dif\t Algporitmo Diffie Hellman");
		System.out.println("");
		System.out.println("Consultar la ayuda del algoritmo en especifico:");
		System.out.println("Sintaxis: java Main <algoritmo> -a\n");
		System.out.println("UNIVERSIDAD AUTONOMA DE OCCIDENTE");
		System.out.println("Gustavo Florez y Crhistian Delgado");
		System.out.println("gustavo.florez@uao.edu.co & crhistian.delgado@uao.edu.co" );
	}

	public static void main(String[] args) {
		
		//
		/*
		try {
		
			File archivo1 = new File (tempFilePath+"MobyDick.txt");
			File archivo2 = new File (tempFilePath+"salida.txt");
			
			String text1 = leerArchivo(archivo1);
			String text2 = leerArchivo(archivo2);
			
			for(int i = 0; i<text1.length(); i++) {
				
				if(text1.charAt(i) != text2.charAt(i)) {
					System.out.println("index "+i);
					System.out.println("Caracter 1 "+text1.charAt(i));
					System.out.println("Caracter 2 "+text2.charAt(i));
					break;
				}
				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		System.exit(0);*/
		
		//
		
		//-dif -p primo.txt -b base.txt -s1 sec1.txt  -s2 sec2.txt
		
		if(args.length == 0) {
			intro();
			System.exit(0);
		}
		
		//validaciones
		if(args.length != 2 && args.length != 6 && args.length != 9) {
			System.out.println("Faltan argumentos (ver ayudas)\n");
			intro();
			System.exit(0);
		}
		
		if(args.length == 2) {
			if(args[1].equals("-a") && args[0].equals("-vig")) {
				mostrarAyuda("-vig");
				System.exit(0); 
			}
			if(args[1].equals("-a") && args[0].equals("-dif")) {
				mostrarAyuda("-dif");
				System.exit(0); 
			}		
		}
		
		if(args.length == 6) {
			
			/* [0] -vig
			 * [1] <modo> 
			 * [2] -e
			 * [3] <ArchivoEntrada> 
			 * [4] -k 
			 * [5] <ArchivoClave>
			 * [6] <alfabeto>
			 */
			
			if(args[0].equals("-vig") == false) {
				System.out.println("Algoritmo no soportado: "+args[0]);
				System.exit(0); 
			}
			
			if(args[1].equals("-c") == false && args[1].equals("-d") == false) {
				System.out.println("Modo no soportado: "+args[1]);
				System.exit(0); 
			}
			
			if(args[2].equals("-e") == false) {
				System.out.println("Falta el -e");
				System.exit(0); 
			}
			
			if(args[3].equals("")) {
				System.out.println("Por favor ingrese el nombre del archivo de entrada con extension");
				System.exit(0); 
			}
			
			if(args[4].equals("-k") == false) {
				System.out.println("Falta el -k");
				System.exit(0); 
			}
			
			if(args[5].equals("")) {
				System.out.println("Por favor ingrese el nombre del archivo de clave");
				System.exit(0); 
			}
			
			
			
			String archivoEntrada = args[3];
			String archivoKey = args[5];
			
			File archivoE = new File (tempFilePath+archivoEntrada);
			File archivoK = new File (tempFilePath+archivoKey);
			
			String[] parametros = new String[4];
			parametros[0] = args[1];
			try {
				parametros[1] = leerArchivo(archivoK);
			} catch (Exception e) {
				System.out.println("Error leyendo el archivo de clave");
				System.exit(0); 
			}
			try {
				parametros[2] = leerArchivo(archivoE);
			} catch (Exception e) {
				System.out.println("Error leyendo el archivo de entrada");
				System.exit(0); 
			}
			parametros[3] = "26";
			
			
			try {
				
				
				if(args[1].equals("-c")) {
				
					System.out.println("Procesando...\n");
					long inicio = System.currentTimeMillis();
					escribirArchivo(procesarVigenere(parametros), "cifrado", "cif");
					long fin = System.currentTimeMillis();
					System.out.println("Operacion terminada con exito!\n");
					System.out.println("Se tardo "+(fin-inicio)/1000.0 + " segundos");
					
				}
				
				if(args[1].equals("-d")) {
					
					System.out.println("Procesando...\n");
					long inicio = System.currentTimeMillis();
					escribirArchivo(procesarVigenere(parametros), "salida", "des");
					long fin = System.currentTimeMillis();
					System.out.println("Operacion terminada con exito!\n");
					System.out.println("Se tardo "+(fin-inicio)/1000.0 + " segundos");
					
				}
				
				
				
				
			} catch (Exception e) {
				System.out.println("Error escribiendo el archivo de salida");
			}
			
		}
		
		if(args.length == 9) {
			
			/* [0] -dif
			 * [1] -p 
			 * [2] <ArchivoPrimo>
			 * [3] -b 
			 * [4] <ArchivoBase> 
			 * [5] -s1
			 * [6] <ArchivoSecreto1>
			 * [7] -s2
			 * [8] <ArchivoSecreto2>
			 */
			
			if(args[0].equals("-dif") == false) {
				System.out.println("Algoritmo no soportado: "+args[0]);
				System.exit(0); 
			}
			
			if(args[1].equals("-p") == false) {
				System.out.println("Falta el -p");
				System.exit(0); 
			}
			
			if(args[2].equals("")) {
				System.out.println("Por favor ingrese el nombre del archivo que contiene el numero primo");
				System.exit(0); 
			}
			
			if(args[3].equals("-b") == false) {
				System.out.println("Falta el -b");
				System.exit(0); 
			}
			
			if(args[4].equals("")) {
				System.out.println("Por favor ingrese el nombre del archivo que contiene el numero base");
				System.exit(0); 
			}
			
			if(args[5].equals("-s1") == false) {
				System.out.println("Falta el -s1");
				System.exit(0); 
			}
			
			if(args[6].equals("")) {
				System.out.println("Por favor ingrese el nombre del archivo que contiene el secreto 1");
				System.exit(0); 
			}
			
			if(args[7].equals("-s2") == false) {
				System.out.println("Falta el -s2");
				System.exit(0); 
			}
						
			if(args[8].equals("")) {
				System.out.println("Por favor ingrese el nombre del archivo que contiene el secreto 2");
				System.exit(0); 
			}
			
			//String[] arg = {"primo.txt", "base.txt", "sec1.txt", "sec2.txt"};
			
			String primo = args[2];
			String base = args[4];
			String secreto1 = args[6];
			String secreto2 = args[8];
			
			File archivoPrimo = new File (tempFilePath+primo);
			File archivoBase = new File (tempFilePath+base);
			File archivoSecreto1 = new File (tempFilePath+secreto1);
			File archivoSecreto2 = new File (tempFilePath+secreto2);
			
			String[] parametros = new String[4];
			try {
				parametros[0] = leerArchivo(archivoPrimo);
			} catch (Exception e) {				
				System.out.println("Error leyendo el archivo que contiene el numero primo");
				System.exit(0); 
			}
			try {
				parametros[1] = leerArchivo(archivoBase);
			} catch (Exception e) {
				System.out.println("Error leyendo el archivo que contiene el numero base");
				System.exit(0); 
			}
			try {
				parametros[2] = leerArchivo(archivoSecreto1);
			} catch (Exception e) {				
				System.out.println("Error leyendo el archivo que contiene el secreto 1");
				System.exit(0); 
			}
			try {
				parametros[3] = leerArchivo(archivoSecreto2);
			} catch (Exception e) {
				System.out.println("Error leyendo el archivo que contiene el secreto 2");
				System.exit(0); 
			}
			
			try {
				System.out.println("Procesando...\n");
				long inicio = System.currentTimeMillis();
				escribirArchivo(procesarDiffie(parametros), "salida", "txt");
				long fin = System.currentTimeMillis();
				System.out.println("Operacion terminada con exito!\n");
				System.out.println("Se tardo "+(fin-inicio)/1000.0 + " segundos");
			}
			catch (Exception e) {
				System.out.println("Error al procesar el algoritmo");
			}
		
		}
		
		System.exit(0);	
		
	}
	
	public static void mostrarAyuda(String algoritmo) {
		System.out.println("-----------------------------------------------------------------------------------------------------------");
		if(algoritmo.equals("-vig")) {
			System.out.println("                        ALGORITMO VIGENERE\n");
			System.out.println("Sintaxis: java Main -vig <modo> -e <ArchivoEntrada> -k <ArchivoClave>  \n");
			System.out.println("<modo>");
			System.out.println("\t -c para cifrar el archivo <ArchivoEntrada>");
			System.out.println("\t -d para descifrar el archivo <ArchivoEntrada>\n");
			System.out.println("<ArchivoEntrada>: nombre del archivo de entrada\n");
			System.out.println("El archivo de salida es <salida> con extensión .txt\n");			
			System.out.println("<ArchivoClave>: nombre del archivo que contiene la clave con extension .key\n");
			System.out.println("Ejemplo:");
			System.out.println("\t Cifrar: java Main -vig -c -e archivo.txt -k archivo.key");
			System.out.println("\t Descifrar: java Main -vig -d -e archivo.txt -k archivo.key\n");
			System.out.println("Elaborado por: Gustavo Florez y Christian Delgado");
			System.out.println("gustavo.florez@uao.edu.co & crhistian.delgado@uao.edu.co" );
		}
		if(algoritmo.equals("-dif")) {
			System.out.println("                     ALGORITMO DIFFIE HELLMAN\n");
			System.out.println("Sintaxis: java Main -dif -p <ArchivoPrimo> -b <ArchivoBase> -s1 <ArchivoSecreto1> -s2 <ArchivoSecreto2>\n");			
			System.out.println("<ArchivoPrimo>: nombre del archivo que contiene el numero primo\n");
			System.out.println("<ArchivoBase>: nombre del archivo que contiene el numero base\n");
			System.out.println("<ArchivoSecreto1>: nombre del archivo que contiene el secreto 1\n");
			System.out.println("<ArchivoSecreto2>: nombre del archivo que contiene el secreto 2\n");			
			System.out.println("Ejemplo:");
			System.out.println("\t java Main -dif -p primo.txt -b base.txt -s1 secreto1.txt -s2 secreto2.txt\n");			
			System.out.println("Elaborado por: Gustavo Florez y Christian Delgado");
			System.out.println("gustavo.florez@uao.edu.co & crhistian.delgado@uao.edu.co" );
		}		
		System.out.println("--------------------------------------------------------------------------------------------------------------");
	}
	
	public static String leerArchivo(File file) throws Exception {
		
		try {
		
			String texto = "";
				
			Stream<String> multilineas = Files.lines(file.toPath());
			for (Iterator<String> iterator = multilineas.iterator(); iterator.hasNext();) {
				texto += (iterator.next());
			}
			return texto;
		
		}catch(Exception e) {
			throw new Exception("Archivo no encontrado");
		}
		
	}
	
	public static void escribirArchivo(String texto, String nombreArchivo, String ext) throws Exception {
		
		String[] lines = new String[] { texto };
	    Path path = Paths.get(tempFilePath+nombreArchivo+"."+ext);
	    //Path path = Paths.get("C:\\Test\\"+nombreArchivo+"."+ext);
	    
	    try (BufferedWriter br = Files.newBufferedWriter(path,
	    	 Charset.defaultCharset(), StandardOpenOption.CREATE)) {
	         Arrays.stream(lines).forEach((s) -> {
	            try {
	               br.write(s);
	              // br.newLine();
	            } catch (IOException e) {
	               throw new UncheckedIOException(e);
	            }

	         });
	      } catch (Exception e) {
	    	  throw e;
	      }
	}
	
	public static String procesarVigenere(String[] args) {
		
		String salida = "";
	
		if ((args.length == 3) || (args.length == 4))
		{
			boolean encode;
			boolean randomKey;
			if (args[0].equals("-c"))
			{
				encode = true;
				randomKey = false;
			}
			else if (args[0].equals("ER"))
			{
				encode = true;
				randomKey = true;
			}
			else if (args[0].equals("-d"))
			{
				encode = false;
				randomKey = false;
			}
			else if (args[0].equals("DR"))
			{
				encode = false;
				randomKey = true;
			}
			else
			{
				throw new IllegalArgumentException("The first parameter must be either 'E' or 'ER' to encode a string or 'D' or 'DR' to decode a string.");
			}

			//System.out.println("Operation: " + (encode ? "Encode string" : "Decode string"));
			Vigenere c;
			if (randomKey == false)
			{
				c = new Vigenere(args[1]);
			}
			else
			{
				long seed = Long.parseLong(args[1]);
				c = new Vigenere(seed, args[2].length());
			}
			//System.out.println("Key: " + c.getKey());
			//System.out.println();

			int group = 0;
			if (args.length == 4)
			{
				group = Integer.parseInt(args[3]);
			}

			if (encode)
			{
				//System.out.println("Text to encode: " + args[2]);
				//System.out.println("Encoded text  : " + c.encode(args[2], group));
				salida = c.encode(args[2], group);
			}
			else
			{
				//System.out.println("Text to decode: " + args[2]);
				//System.out.println("Decoded text  : " + c.decode(args[2], group));
				salida = c.decode(args[2], group);
			}
		}
		else
		{
			//System.out.println("Three parameters are required: operation, key/seed, text to encode/decode, and (optionally) an output group length.");
			System.out.println("Error de parametros");
		}
		
		return salida;
	}
	
	public static String procesarDiffie(String[]args)throws IOException
    {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        
        BigInteger p=new BigInteger(args[0]);
        
        if (p.isProbablePrime(1)==false){
            System.out.println("El numero "+p+" no es primo ");
            System.exit(0);
        }
        
        BigInteger g=new BigInteger(args[1]);
        if (g.compareTo(p)>=0){
          System.out.println("\nEl numero "+g+" no es menor que "+p);
          System.exit(0);
        }
        
        BigInteger x=new BigInteger(args[2]);
        if(x.compareTo(p)>=0){
            System.out.println("El numero "+x+" no es menor que " +p);
            System.exit(0);
        }
        
        //p^x mod g
        BigInteger R1=g.modPow(x,p);
        
        BigInteger y=new BigInteger(args[3]);
        while(y.compareTo(p)>=0){
            System.out.println("El numero "+y+" no es menor que "+p);
            System.exit(0);
        }
       //p^y mod g
        BigInteger R2=g.modPow(y,p);
        
        // p^x mod R2        
        BigInteger k1=R2.modPow(x,p);
        
        System.out.println("la clave calculada 1 es: "+k1);
        BigInteger k2=R1.modPow(y,p);
        
        System.out.println("La clave calculada 2 es: "+k2);
        if (k1.compareTo(k2)==0){
            System.out.println("Ambas claves calculadas coinciden, todo ha funcionado!");
            return k1.toString();
        }
        else{
            System.out.println("Ha ocurrido un error");
            return "error";
        }
    }
	
}


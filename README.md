                                                      MENU:

ALGORITMOS CRIPTOGRAFICOS

Sintaxis: java < algoritmo >

< algoritmo > :

	-vig	 Algoritmo Vigenere
	-dif	 Algporitmo Diffie Hellman

Consultar la ayuda del algoritmo en especifico:
Sintaxis: java Main < algoritmo > -a

--------------------------------------------------------------------------------------------------------------
                                                ALGORITMO VIGENERE

Sintaxis: java Main -vig <modo> -e < ArchivoEntrada > -k < ArchivoClave >  

< modo >
	 - c para cifrar el archivo <ArchivoEntrada>
	 - d para descifrar el archivo <ArchivoEntrada>

< ArchivoEntrada >: nombre del archivo de entrada

El archivo de salida es <salida> con extensión .txt

< ArchivoClave >: nombre del archivo que contiene la clave con extension .key

Ejemplo:
	 Cifrar: java Main -vig -c -e archivo.txt -k archivo.key
	 Descifrar: java Main -vig -d -e archivo.txt -k archivo.key

Elaborado por: Gustavo Florez y Christian Delgado
--------------------------------------------------------------------------------------------------------------


--------------------------------------------------------------------------------------------------------------
                                            ALGORITMO DIFFIE HELLMAN

Sintaxis: java Main -dif -p < ArchivoPrimo > -b < ArchivoBase > -s1 < ArchivoSecreto1 > -s2 < ArchivoSecreto2 >

< ArchivoPrimo >: nombre del archivo que contiene el numero primo

< ArchivoBase >: nombre del archivo que contiene el numero base

< ArchivoSecreto1 >: nombre del archivo que contiene el secreto 1

< ArchivoSecreto2 >: nombre del archivo que contiene el secreto 2

Ejemplo:
	 java Main -dif -p primo.txt -b base.txt -s1 secreto1.txt -s2 secreto2.txt

Elaborado por: Gustavo Florez y Christian Delgado
--------------------------------------------------------------------------------------------------------------


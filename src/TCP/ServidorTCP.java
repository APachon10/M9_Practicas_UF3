package TCP;

import java.io.*;
import java.net.*;
class ServidorTCP{
	public static void main(String args[]){
		// Primero indicamos la dirección IP local
		InetAddress direcc =null;
		try {
			direcc = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		try{
			System.out.println("LocalHost = " +direcc.toString());
		}
		catch (Exception uhe){
			System.err.println("No puedo saber la dirección IPlocal : " + uhe);
		}
		// Abrimos un "Socket de Servidor" TCP en el puerto 1234.
		ServerSocket ss = null;
		try{
			ss = new ServerSocket(1234,0,InetAddress.getByName(null));
			System.out.println("Socket: "+ss);
		}
		catch (IOException ioe){
			System.err.println("Error al abrir el socket de servidor : " + ioe);
			System.exit(-1);
		}
		int entrada;
		long salida;
		// Bucle infinito
		while(true){
			try{
				// Esperamos a que alguien se conecte a nuestro
				Socket sckt = ss.accept();
				System.out.println("Socket 1 : " +sckt);
				// Extraemos los Streams de entrada y de salida
				DataInputStream dis = new DataInputStream(sckt.getInputStream());
				System.out.println("Dis : "+dis);
				DataOutputStream dos = new DataOutputStream(sckt.getOutputStream());
				System.out.println("Dos : "+dos);
				// Podemos extraer información del socket
				// Nº de puerto remoto
				int puerto = sckt.getPort();
				System.out.println("Puerto: "+puerto);
				// Dirección de Internet remota
				InetAddress direcc2 = sckt.getInetAddress();
				// Leemos datos de la peticion
				entrada = dis.readInt();
				// Calculamos resultado
				salida = (long)entrada*(long)entrada;
				// Escribimos el resultado
				dos.writeLong(salida);
				// Cerramos los streams
				dis.close();
				dos.close(); 
				sckt.close();
				// Registramos en salida estandard
				System.out.println( "Cliente = " + direcc + ":"+ puerto+ "\tEntrada = " + entrada + "\tSalida = " +salida );
			}
			catch(Exception e){
				System.err.println("Se ha producido laexcepción : " +e);
			}
		}
	}
} 

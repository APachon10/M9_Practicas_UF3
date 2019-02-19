package UDP;

import java.net.*;
import java.io.*;
class clienteUDP
{
	public static void main(String args[])
	{
		// Leemos el primer parámetro, donde debe ir la dirección
		// IP del servidor
		InetAddress direcc = null;
		try{
			direcc = InetAddress.getByName(args[0]);
		}
		catch(UnknownHostException uhe){
			System.err.println("Host no encontrado : " + uhe);
			System.exit(-1);
		}
		// Puerto que hemos usado para el servidor
		int puerto = 1234;
		// Creamos el Socket
		DatagramSocket ds = null;
		try{
			ds = new DatagramSocket();
		}
		catch(SocketException se){
			System.err.println("Error al abrir el socket : " +
					se);
			System.exit(-1);
		}
		// Para cada uno de los argumentos...
		for (int n=1;n<args.length;n++){
			try{
				//creamos un buffer para escribir
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				DataOutputStream dos = new DataOutputStream(baos);
				// Convertimos el texto en número
				int numero = Integer.parseInt(args[n]);
				// Lo escribimos
				dos.writeInt(numero);
				// y cerramos el buffer
				dos.close();
				// Creamos paquete
				DatagramPacket dp = new DatagramPacket(baos.toByteArray(),4,direcc,puerto);
				// y lo mandamos
				ds.send(dp);
				// Preparamos buffer para recibir número de 8bytes
				byte bufferEntrada[] = new byte[8];
				// Creamos el contenedor del paquete
				dp = new DatagramPacket(bufferEntrada,8); 
				// y lo recibimos
				ds.receive(dp);
				// Creamos un stream de lectura a partir delbuffer
				ByteArrayInputStream bais = new ByteArrayInputStream(bufferEntrada);
				DataInputStream dis = new DataInputStream(bais);
				// Leemos el resultado final
				long resultado = dis.readLong();
				// Indicamos en pantalla
				System.out.println( "Solicitud = " + numero +"\tResultado = " +resultado );
			}
			catch (Exception e)
			{
				System.err.println("Se ha producido un error :" + e);
			}
		}
	}
} 


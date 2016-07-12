package tower;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Client {
	Socket socket = null; //create a new socket
	final String IP = "http://localhost:8001"; //8001 does not require permissions
	public Client(){
		try {
			 socket = IO.socket(IP); //create socket
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} 
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
			//data is in javascript object notation
			public void call(Object... data) {
				
			}
	    }).on("inserteventhere", new Emitter.Listener(){
			public void call(Object... data) {

			}
	    });
    	socket.connect(); //connect
	}
}

package client;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.tools.remoteapi.RemoteApiInstaller;
import com.google.appengine.tools.remoteapi.RemoteApiOptions;

import java.io.IOException;

public class TestClient {
  public static void main(String[] args) throws IOException {
    if (args.length != 6) {
      System.err.println("ERROR: Wrong number of args.");
      System.err.println("Usage:\n\tjava -cp ... " + TestClient.class.getCanonicalName()
          + " <server> <port> <username> <password> <memcache-key> <new-value>");
      System.exit(1);
    }

    String server = args[0];
    int port = 0;
    try {
      port = Integer.parseInt(args[1]);
    } catch (NumberFormatException e) {
      System.err.println("ERROR: Port number must be numeric");
      System.exit(1);
    }
    String username = args[2];
    String password = args[3];
    String key = args[4];
    String value = args[5];

    System.out.println("new RemoteApiOptions().server(\"" + server + "\", " + port
        + ").credentials(\"" + username + "\", \"" + password + "\")");
    RemoteApiOptions options =
        new RemoteApiOptions().server(server, port).credentials(username, password);

    RemoteApiInstaller installer = new RemoteApiInstaller();
    installer.install(options);

    MemcacheService mc = MemcacheServiceFactory.getMemcacheService();

    System.out.println("mc.get(\"" + key + "\") -> " + mc.get(key));

    System.out.println("mc.put(\"" + key + "\", \"" + value + "\")");
    mc.put(key, value);

    System.out.println("mc.get(\"" + key + "\") -> " + mc.get(key));

    installer.uninstall();
    System.exit(0);
  }
}

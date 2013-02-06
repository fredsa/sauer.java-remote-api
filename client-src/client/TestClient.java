package client;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.tools.remoteapi.RemoteApiInstaller;
import com.google.appengine.tools.remoteapi.RemoteApiOptions;

import java.io.IOException;

public class TestClient {
  public static void main(String[] args) throws IOException {

    RemoteApiOptions options =
        new RemoteApiOptions().server("fredsa-remote-api.appspot.com", 443).credentials(args[0], args[1]);

    RemoteApiInstaller installer = new RemoteApiInstaller();
    installer.install(options);

    MemcacheService mc = MemcacheServiceFactory.getMemcacheService();
    if (args.length == 3) {
      System.out.println("mc.get(\"" + args[2] + "\") -> " + mc.get(args[2]));
    } else if (args.length == 4) {
      mc.put(args[2], args[3]);
      System.out.println("mc.put(\"" + args[2] + "\", \"" + args[3] + "\")");
    } else {
      System.err.println("Wrong number of args");
    }

    installer.uninstall();
  }
}

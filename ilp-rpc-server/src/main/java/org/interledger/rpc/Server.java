package org.interledger.rpc;

import static spark.Spark.*;

import spark.Request;

import java.util.Map;

public class Server {

  private static Map<String, Peer> peers;


  public static void main(String[] args) {

    configureRoutes();

  }

  private static void configureRoutes() {

    post("/rpc", (req, res) -> {

      String method = req.queryParams("method");
      String prefix = req.queryParams("prefix");

      if(method == null || method.isEmpty() || prefix == null || prefix.isEmpty()){
        halt(400, "Both method and prefix must be defined");
      }

      authenticate(req);

      return new byte[32];
    });

  }

  private static void authenticate(Request req) {
    String prefix = req.queryParams("prefix");
    String token = req.headers("Authorization");

    if(token == null || token.isEmpty() || prefix == null || prefix.isEmpty()){
      halt(401, "Unauthorized");
    }

  }

}

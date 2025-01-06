package com.web;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class SimpleServer {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/solve", new SolveHandler());
        server.setExecutor(null); // Default executor
        server.start();
        System.out.println("Server started on port 8080");
    }

    static class SolveHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws java.io.IOException {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                // Read input
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

                // Parse the inputs
                String[] inputs = body.split("&");
                String equation1 = inputs[0].split("=")[1];
                String equation2 = inputs[1].split("=")[1];

                // Concatenate the inputs
                String result = "Concatenated Result: " + equation1 + " " + equation2;

                // Send response
                exchange.getResponseHeaders().set("Content-Type", "text/plain");
                exchange.sendResponseHeaders(200, result.length());
                OutputStream os = exchange.getResponseBody();
                os.write(result.getBytes(StandardCharsets.UTF_8));
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }
}

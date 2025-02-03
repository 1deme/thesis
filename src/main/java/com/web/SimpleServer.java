package com.web;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.example.Sim;
import com.example.dnf.Conjunction;
import com.sun.net.httpserver.HttpExchange;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.net.URLDecoder;

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
    System.out.println("Received request: " + exchange.getRequestMethod());

    exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

    if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
        exchange.sendResponseHeaders(200, -1);
        return;
    }

    if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
        try {
            // Read input
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("Received body: " + body);  // Log request body

            // Parse inputs safely
            String[] inputs = body.split("&");
            String equation1 = URLDecoder.decode(inputs[0].split("=")[1], StandardCharsets.UTF_8);
            String relations = URLDecoder.decode(inputs[1].split("=")[1], StandardCharsets.UTF_8);
            String cutValue = URLDecoder.decode(inputs[2].split("=")[1], StandardCharsets.UTF_8);

            // System.out.println("Equations:" + equation1);
            // System.out.println("Relations: " + relations);
            // System.out.println("Cut Value: " + cutValue);

            // // Call processing functions (ensure they don't crash)
            // setUpMain.setUpAbstractData(equation1, relations, cutValue);
            // Sim.solve();

            // Create response
            String result = equation1 + relations + cutValue;

            // Send response
            exchange.getResponseHeaders().set("Content-Type", "text/plain");
            exchange.sendResponseHeaders(200, result.length());
            OutputStream os = exchange.getResponseBody();
            os.write(result.getBytes(StandardCharsets.UTF_8));
            os.close();
        } catch (Exception e) {
            System.err.println("Error processing request: " + e.getMessage());
            e.printStackTrace();
            exchange.sendResponseHeaders(500, -1); // Internal Server Error
        }
    } else {
        exchange.sendResponseHeaders(405, -1); // Method Not Allowed
    }
}

    }
}
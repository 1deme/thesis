package com.web;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.setUpMain;
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
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                // Read input
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

                // Parse the inputs
                String[] inputs = body.split("&");
                String equation1 = URLDecoder.decode(inputs[0].split("=")[1], StandardCharsets.UTF_8);
                String equation2 = URLDecoder.decode(inputs[1].split("=")[1], StandardCharsets.UTF_8);
                String cutValue = URLDecoder.decode(inputs[2].split("=")[1], StandardCharsets.UTF_8);

                // Log or use the inputs
                // System.out.println("Equation 1: " + equation1);
                // System.out.println("Equation 2: " + equation2);
                // System.out.println("Cut Value: " + cutValue);

                setUpMain.setUpAbstractData(equation1,  equation2,  cutValue);

                Sim.solve();

    

                // Concatenate the inputs for response
                String result = "Received Inputs:\n" +
                                "Equation 1: " + equation1 + "\n" +
                                "Equation 2: " + equation2 + "\n" +
                                "Cut Value: " + cutValue;

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

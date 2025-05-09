package com.example;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.example.algorithm.SolPc;
import com.example.algorithm.SolSc;
import com.example.algorithm.SolTransformation;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.net.InetSocketAddress;
import java.net.URLDecoder;

public class Main {

    public static void main(String[] args) throws Exception {

        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("Server started on port " + port);

        server.createContext("/", new FileHandler());
        server.createContext("/solve", new SolveHandler());

        server.setExecutor(null); // Default executor
        server.start();
    }

    static class FileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Adjust the path to point to the "static" directory
            Path filePath = Path.of("src/main/webapp/index.html");
            
            if (Files.exists(filePath)) {
                byte[] response = Files.readAllBytes(filePath);
                exchange.getResponseHeaders().set("Content-Type", "text/html");
                exchange.sendResponseHeaders(200, response.length);
                OutputStream os = exchange.getResponseBody();
                os.write(response);
                os.close();
            } else {
                exchange.sendResponseHeaders(404, 0);
                exchange.close();
            }
        }
    }


    static class SolveHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Handle CORS preflight requests
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
            exchange.getResponseHeaders().add("Access-Control-Allow-Credentials", "true");

            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1); // No Content
                return;
            }

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                try {
                    String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                    System.out.println("Received body: " + body);

                    String[] inputs = body.split("&");
                    String equation1 = URLDecoder.decode(inputs[0].split("=")[1], StandardCharsets.UTF_8);
                    String relations = URLDecoder.decode(inputs[1].split("=")[1], StandardCharsets.UTF_8);
                    String proximityValue = URLDecoder.decode(inputs[2].split("=")[1], StandardCharsets.UTF_8);

                    com.example.algorithm.SolveSim.disjunction = com.example.parser.DisjunctionParser.parse(equation1);
                    com.example.parser.RelationsParser.parse(relations);

                    String result = "";
                    boolean isProximity = "true".equalsIgnoreCase(proximityValue);

                    if (isProximity && !com.example.relations.relationCollection.checkTransitivity()) {
                        result = "The relation is not transitive.";
                    } else {
                        SolTransformation solverInstance = isProximity ? new SolPc() : new SolSc();
                        result = com.example.algorithm.SolveSim.solve(solverInstance);
                    }

                    com.example.algorithm.SolveSim.solution.clear();
                    com.example.algorithm.SolveSim.disjunction.conjunctions.clear();
                    com.example.relations.relationCollection.collection.clear();

                    exchange.getResponseHeaders().set("Content-Type", "text/plain");
                    exchange.sendResponseHeaders(200, result.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(result.getBytes(StandardCharsets.UTF_8));
                    os.close();
                } catch (Exception e) {
                    System.err.println("Error processing request: " + e.getMessage());
                    e.printStackTrace();
                    exchange.sendResponseHeaders(500, -1);
                }
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }

}
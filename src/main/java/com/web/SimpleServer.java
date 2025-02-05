package com.web;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
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
                    String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                    System.out.println("Received body: " + body);

                    String[] inputs = body.split("&");
                    String equation1 = URLDecoder.decode(inputs[0].split("=")[1], StandardCharsets.UTF_8);
                    String relations = URLDecoder.decode(inputs[1].split("=")[1], StandardCharsets.UTF_8);


                    com.example.Sim.disjunction = com.example.parser.DisjunctionParser.parse(equation1);
                    com.example.parser.RelationsParser.parse(relations);

                    String result = com.example.Sim.solve();

                    com.example.Sim.solution.clear();

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
                exchange.sendResponseHeaders(405, -1);
            }
        }

    }
}
package com.student;

import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class App {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8081), 0);

        // Display Registration Form
        server.createContext("/", exchange -> {

            try {
                InputStream input = App.class.getResourceAsStream("/index.html");

                if (input == null) {
                    throw new FileNotFoundException("index.html not found");
                }

                byte[] html = input.readAllBytes();
                input.close();

                exchange.getResponseHeaders().set(
                        "Content-Type", "text/html; charset=UTF-8");

                exchange.sendResponseHeaders(200, html.length);

                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(html);
                }

            } catch (Exception e) {
                e.printStackTrace();
                exchange.sendResponseHeaders(500, -1);
                exchange.close();
            }
        });

        // Handle Registration
        server.createContext("/register", exchange -> {

            if (!exchange.getRequestMethod().equals("POST")) {
                exchange.sendResponseHeaders(405, -1);
                exchange.close();
                return;
            }

            String data = new String(
                    exchange.getRequestBody().readAllBytes(),
                    StandardCharsets.UTF_8);

            String response =
                    "<html><body>" +
                    "<h2>Registration Successful!</h2>" +
                    "<p>Student Details: " + data + "</p>" +
                    "<a href='/'>Go Back</a>" +
                    "</body></html>";

            byte[] result = response.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().set(
                    "Content-Type", "text/html; charset=UTF-8");

            exchange.sendResponseHeaders(200, result.length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(result);
            }
        });

        server.start();

        System.out.println(
                "Student Registration App running on port 8081");
    }
}
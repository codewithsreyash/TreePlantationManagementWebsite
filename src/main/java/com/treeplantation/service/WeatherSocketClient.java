package com.treeplantation.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Service
public class WeatherSocketClient {

    private static final String HOST = "localhost";
    private static final int PORT = 9090; // Dummy weather server port

    public String fetchWeatherAlerts(String location) {
        // Attempt to connect to a dummy socket server
        // In a real scenario, this connects to a live server daemon.
        try (Socket socket = new Socket(HOST, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            // Send request payload to server
            out.println("GET_WEATHER:" + location);
            
            // Read weather alert response
            return in.readLine();
            
        } catch (Exception e) {
            // Simulated fallback response when server is not running
            return "Alert: Default clear weather assumed for " + location + ". Socket server not reachable.";
        }
    }
}

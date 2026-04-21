package com.plantree.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class WeatherSocketClient {

    private static final String HOST = "localhost";
    private static final int PORT = 9090; // Dummy weather server port

    private String cachedWeatherAlert;
    private ScheduledExecutorService scheduler;

    @PostConstruct
    public void init() {
        System.out.println("[WeatherSocketClient] Initializing background weather polling...");
        this.cachedWeatherAlert = "Checking weather system asynchronously...";
        
        // Option B: ScheduledExecutorService that polls the socket every 5 minutes in background
        scheduler = Executors.newSingleThreadScheduledExecutor();
        
        // Initial delay 0, repeat every 5 minutes
        scheduler.scheduleAtFixedRate(this::pollNetworkForWeather, 0, 5, TimeUnit.MINUTES);
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("[WeatherSocketClient] Shutting down weather polling thread pool...");
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(2, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    private void pollNetworkForWeather() {
        System.out.println("[WeatherSocketClient] Execution Check: " + new Date());
        try (Socket socket = new Socket(HOST, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            out.println("GET_WEATHER:ALL");
            this.cachedWeatherAlert = in.readLine();
            System.out.println("[WeatherSocketClient] Cache updated: " + this.cachedWeatherAlert);
            
        } catch (Exception e) {
            this.cachedWeatherAlert = "Alert: Default clear weather assumed. Socket server not reachable.";
        }
    }

    public String fetchWeatherAlerts(String location) {
        // Returns the cached weather state without blocking the UI thread
        return this.cachedWeatherAlert + " (Location request: " + location + ")";
    }
}

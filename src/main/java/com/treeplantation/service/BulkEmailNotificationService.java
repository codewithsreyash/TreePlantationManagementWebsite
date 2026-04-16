package com.treeplantation.service;

import com.treeplantation.model.Volunteer;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class BulkEmailNotificationService {

    // Thread pool to handle async email sending
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void notifyVolunteersDateChange(Set<Volunteer> volunteers, String date, String location) {
        for (Volunteer volunteer : volunteers) {
            executorService.submit(new EmailTask(volunteer, date, location));
        }
    }

    private static class EmailTask implements Runnable {
        private final Volunteer volunteer;
        private final String date;
        private final String location;

        public EmailTask(Volunteer volunteer, String date, String location) {
            this.volunteer = volunteer;
            this.date = date;
            this.location = location;
        }

        @Override
        public void run() {
            try {
                // Simulate email sending delay
                Thread.sleep(1000);
                System.out.println("Email sent asynchronously to " + volunteer.getEmail() 
                                    + " for drive at " + location + " on " + date);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Email task interrupted for " + volunteer.getEmail());
            }
        }
    }

    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
    }
}

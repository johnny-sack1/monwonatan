package com.codecool.queststore;

import com.codecool.queststore.backend.webControllers.InvalidPageHandler;
import com.codecool.queststore.backend.webControllers.Static;
import com.codecool.queststore.backend.webControllers.adminController.AdminController;
import com.codecool.queststore.backend.webControllers.loginController.LoginController;
<<<<<<< HEAD
import com.codecool.queststore.backend.webControllers.loginController.Logout;
=======
import com.codecool.queststore.backend.webControllers.studentController.StudentController;
>>>>>>> backendController
import com.codecool.queststore.backend.webControllers.mentorController.MentorController;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class App {
    public static void main( String args[] ) {
        try {
            // create a server on port 8001
            HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);

            // set routes
            server.createContext("/login", new LoginController());
            server.createContext("/static", new Static());
            server.createContext("/mentor", new MentorController());
            server.createContext("/admin", new AdminController());
            server.createContext("/logout", new Logout());
            server.createContext("/student", new StudentController());

            server.createContext("/", new InvalidPageHandler());
            
            server.setExecutor(null); // creates a default executor

            // start listening
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
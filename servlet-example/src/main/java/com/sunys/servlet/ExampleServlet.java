package com.sunys.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//@WebServlet("/")
public class ExampleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Protocol: [" + req.getProtocol() + "]\n");
        resp.getWriter().write("RequestURI: [" + req.getRequestURI() + "]\n");
    }
}

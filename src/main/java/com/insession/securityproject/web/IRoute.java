package com.insession.securityproject.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IRoute {

    String loader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    String action(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

}

package com.treeplantation.web;

import com.treeplantation.dao.PlantationDriveDAO;
import com.treeplantation.model.PlantationDrive;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {

    private PlantationDriveDAO driveDAO;

    @Override
    public void init() throws ServletException {
        // Accessing Spring context from regular Servlet
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        this.driveDAO = context.getBean(PlantationDriveDAO.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Fetch all active drives
        List<PlantationDrive> drives = driveDAO.findAll();
        
        req.setAttribute("activeDrives", drives);
        
        // Forward to the JSP for public viewing
        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }
}

package com.revature.util;

import com.revature.services.LoginService;
import com.revature.services.ReimbursementService;
import com.revature.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Arrays;
import javax.servlet.http.*;

public class FrontController extends HttpServlet {
    private static Logger logger = LogManager.getLogger(FrontController.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String[] uriSplit = request.getRequestURI().toLowerCase().split("/");

        String jsonResponse = null;

        if (uriSplit.length >= 3) {
            switch (uriSplit[2]) {
                case "users":
                    jsonResponse = UserService
                            .getInstance()
                            .getResource(Arrays.copyOfRange(uriSplit, 2, uriSplit.length));

                    if (jsonResponse != null) {
                        logger.info("Got valid GET request for login: " + request.getRequestURI());
                        response.setStatus(200);
                    } else {
                        logger.error("Got unknown GET request: " + request.getRequestURI());
                        response.sendError(404, "Path not found");
                    }

                    break;
                case "reimbursements":
                    jsonResponse = ReimbursementService
                            .getInstance()
                            .getResource(Arrays.copyOfRange(uriSplit, 2, uriSplit.length));

                    if (jsonResponse != null) {
                        logger.info("Got valid GET request for login: " + request.getRequestURI());
                        response.setStatus(200);
                    } else {
                        logger.error("Got unknown GET request: " + request.getRequestURI());
                        response.sendError(404, "Path not found");
                    }

                    break;
                default:
                    logger.error("Got unknown GET request: " + request.getRequestURI());
                    response.sendError(404, "Path not found");
                    break;
            }
        } else {
            logger.error("Got unknown GET request: " + request.getRequestURI());
            response.sendError(404, "Path not found");
        }

        response.setContentType("text/json");
        PrintWriter out = response.getWriter();
        out.println(jsonResponse);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String[] uriSplit = request.getRequestURI().toLowerCase().split("/");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = bufferedReader.readLine();
        bufferedReader.close();

        String jsonResponse = null;

        if (uriSplit.length >= 3) {
            switch (uriSplit[2]) {
                case "login":
                    jsonResponse = LoginService.getInstance().tryLogin(json);
                    if (jsonResponse != null) {
                        logger.info("Got valid POST request for login: " + request.getRequestURI());
                        HttpSession session = request.getSession();
                        session.setAttribute("currentUser", jsonResponse);
                        response.setStatus(200);
                    } else {
                        logger.error("Got unknown POST request: " + request.getRequestURI());
                        response.sendError(404, "Path not found");
                    }

                    break;
                // TODO: User registration functionality
                case "reimbursements":
                    if (ReimbursementService.getInstance().createReimb(json)) {
                        logger.info("Got valid POST request for reimbursements: " + request.getRequestURI());
                        response.setStatus(200);
                    } else {
                        logger.error("Got unknown POST request: " + request.getRequestURI());
                        response.sendError(404, "Path not found");
                    }

                    break;
                default:
                    logger.error("Got unknown POST request: " + request.getRequestURI());
                    response.sendError(404, "Path not found");
                    break;
            }
        } else {
            logger.error("Got unknown POST request: " + request.getRequestURI());
            response.sendError(404, "Path not found");
        }

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println(jsonResponse);
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String[] uriSplit = request.getRequestURI().toLowerCase().split("/");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = bufferedReader.readLine();
        bufferedReader.close();

        if (uriSplit.length >= 3) {
            switch (uriSplit[2]) {
                case "reimbursements":
                    if (ReimbursementService.getInstance().updateReimb(json)) {
                        logger.error("Got valid PUT request: " + request.getRequestURI());
                        response.setStatus(200);
                    } else {
                        logger.error("Got unknown PUT request: " + request.getRequestURI());
                        response.sendError(404, "Path not found");
                    }

                    break;
                default:
                    logger.error("Got unknown PUT request: " + request.getRequestURI());
                    response.sendError(404, "Path not found");
                    break;
            }
        } else {
            logger.error("Got unknown PUT request: " + request.getRequestURI());
            response.sendError(404, "Path not found");
        }
    }
}

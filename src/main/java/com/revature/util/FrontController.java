package com.revature.util;

import com.revature.services.LoginService;
import com.revature.services.ReimbursementService;
import com.revature.services.UserService;

import java.io.*;
import java.util.Arrays;
import javax.servlet.http.*;

public class FrontController extends HttpServlet {
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
                    break;
                case "reimbursements":
                    jsonResponse = ReimbursementService
                            .getInstance()
                            .getResource(Arrays.copyOfRange(uriSplit, 2, uriSplit.length));
                    break;
                default:
                    response.sendError(404, "Path not found");
                    break;
            }
        } else response.sendError(404, "Path not found");

        response.setContentType("text/json");
        PrintWriter out = response.getWriter();
        out.println(jsonResponse);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(request.getInputStream())
        );
        String json = bufferedReader.readLine();
        bufferedReader.close();

        String jsonResponse = null;

        String[] uriSplit = request.getRequestURI().toLowerCase().split("/");
        if (uriSplit.length >= 3) {
            switch (uriSplit[2]) {
                case "login":
                    jsonResponse = LoginService
                            .getInstance()
                            .tryLogin(json);
                    if (jsonResponse != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("currentUser", jsonResponse);
                        response.setStatus(200);
                    }
                    break;
                // TODO: User registration functionality
                case "reimbursements":
                    if (ReimbursementService
                            .getInstance()
                            .createReimb(json))
                        response.setStatus(200);
                    break;
                default:
                    response.sendError(404, "Path not found");
                    break;
            }
        } else response.sendError(404, "Path not found");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println(jsonResponse);
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(request.getInputStream())
        );
        String json = bufferedReader.readLine();
        bufferedReader.close();

        String[] uriSplit = request.getRequestURI().toLowerCase().split("/");
        if (uriSplit.length >= 3) {
            switch (uriSplit[2]) {
                case "reimbursements":
                    if (ReimbursementService
                            .getInstance()
                            .updateReimb(json))
                        response.setStatus(200);
                    break;
                default:
                    response.sendError(404, "Path not found");
                    break;
            }
        } else response.sendError(404, "Path not found");
    }
}

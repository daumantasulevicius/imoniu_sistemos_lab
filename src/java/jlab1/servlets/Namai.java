/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlab1.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.*;
import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.Entity;

/**
 *
 * @author Daumantas
 */
@WebServlet(name = "Namai", urlPatterns = {"/"})
public class Namai extends HttpServlet {
    
    private jlab1.beans.Message msg = new jlab1.beans.Message();
    
    @PersistenceUnit
    private EntityManagerFactory emf;
    
    @Resource
    private UserTransaction utx;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        EntityManager em = null;
        
        try {
            em = emf.createEntityManager();
            List messages = em.createQuery("select m from Message m").getResultList();
            request.setAttribute("msg", this.msg);
            request.setAttribute("msg_list", messages);
            request.getRequestDispatcher("namai.jsp").forward(request, response);
        } catch (Exception ex) {
            throw new ServletException(ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Namai</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Namai at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

                processRequest(request, response);
    }

//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String l_name = "";
//        l_name = request.getParameter("name");
//        String l_msg = "";
//        l_msg = request.getParameter("message");
//        String l_papildomas = "";
//        l_papildomas = request.getParameter("papildomas");
//        
//        
//            
//            
//        if (l_papildomas.length() > 20)
//        {
//            throw new IOException("Papildomas yra per ilgas " + l_papildomas.length());
//        }
//        if ( l_msg != null && l_name != null)
//        {
//            this.msg.setName(l_name);
//            this.msg.setMsg(l_msg);
//            this.msg.setPapildomas(l_papildomas);
//            this.msg.setTime(new Date());
//            
//            EntityManager em = null;
//            try {
//                jlab1.entities.Message e_msg = new jlab1.entities.Message();
//                e_msg.setMessage(l_msg);
//                e_msg.setName(l_name);
//                e_msg.setTime(new Date());
//                e_msg.setPapildomas(l_papildomas);
//                utx.begin();
//                em = emf.createEntityManager();
//                em.persist(e_msg);
//                utx.commit();
//
//            } catch (Exception ex) {
//                throw new ServletException(ex);
//            } finally {
//                if(em != null){
//                    em.close();
//                }
//            }
//        }
//        processRequest(request, response);
//    }
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String l_name = "";
        l_name = request.getParameter("name");
        String l_msg = "";
        l_msg = request.getParameter("message");
        String l_papildomas = "";
        l_papildomas = request.getParameter("papildomas");
        
        
            
            
        if (l_papildomas.length() > 20)
        {
            throw new IOException("Papildomas yra per ilgas " + l_papildomas.length());
        }
        if ( l_msg != null && l_name != null)
        {
//            this.msg.setName(l_name);
//            this.msg.setMsg(l_msg);
//            this.msg.setPapildomas(l_papildomas);
//            this.msg.setTime(new Date());
            jlab1.entities.Message mesg = new jlab1.entities.Message();
            
            mesg.setName(l_name);
            mesg.setMessage(l_msg);
            mesg.setPapildomas(l_papildomas);
            mesg.setTime(new Date());

            //Jax-rs
            Client client = ClientBuilder.newClient();

            WebTarget target = client.
                  target("http://localhost:8080/jlab1/rest/msg/");

            target.request()
                  .post(Entity.entity(mesg, MediaType.APPLICATION_JSON)
                            , String.class); // Message padaro null, kitos reikšmės gerai
            
            //Jax-rs bandymai
            //target.request(MediaType.APPLICATION_XML)
               //   .post(Entity.xml(msg));
               
            //target.request(MediaType.APPLICATION_JSON)
                  //.accept(MediaType.TEXT_PLAIN_TYPE)
                  //.post(Entity.json(msg), String.class);
            
            

        }
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

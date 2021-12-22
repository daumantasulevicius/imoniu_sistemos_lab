/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlab1.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.WebServiceRef;
import service_client.Message;
import service_client.MessageWS_Service;

/**
 *
 * @author Daumantas
 */
@WebServlet(name = "Filtruoti", urlPatterns = {"/Filtruoti"})
public class Filtruoti extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/jlab1/MessageWS.wsdl")
    private MessageWS_Service service;
    
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
        //Paprastai
//        EntityManager em = null;
//        
//        try {
//            em = emf.createEntityManager();
//            List messages = em.createQuery("select m from Message m").getResultList();
//            request.setAttribute("msg", this.msg);
//            request.setAttribute("msg_list", messages);
//            request.getRequestDispatcher("namai.jsp").forward(request, response);
//        } catch (Exception ex) {
//            throw new ServletException(ex);
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
            //Filtruoti su JAX-WS
          String id = request.getParameter("id");
          int iD = Integer.parseInt(id);
          List messagesFiltr = new ArrayList();
          java.util.List<service_client.Message> result = findAll();
          for (int i = 0; i < result.size(); i++) {
              if(result.get(i).getId() > iD)
              {
                    service_client.Message tmsg = result.get(i);
                    messagesFiltr.add(tmsg);
                    
              }
            
          }

          request.setAttribute("msg", this.msg);
          request.setAttribute("msg_listFiltr", messagesFiltr);
          request.getRequestDispatcher("namai.jsp").forward(request, response);
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Filtruoti</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Filtruoti at " + request.getContextPath() + "</h1>");
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
        
        //Paprastas
//        String id = request.getParameter("nurID");
//        EntityManager em = null;
//        
//        try {
//            em = emf.createEntityManager();
//            List messagesFiltr = em.createQuery("select m from Message m where m.id < " + id).getResultList();
//            request.setAttribute("msg", this.msg);
//            request.setAttribute("msg_listFiltr", messagesFiltr);
//            request.getRequestDispatcher("namai.jsp").forward(request, response);
//        } catch (Exception ex) {
//            throw new ServletException(ex);
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }

        //Jax-ws edit
//        String id = request.getParameter("id");
//        String vardas = request.getParameter("vardas");
//        String zinute = request.getParameter("zinute");
//        String papildomas = request.getParameter("papildomas");
//        
//        
//        int i=Integer.parseInt(id);
//        service_client.Message messg = find(i);
//        messg.setMessage(zinute);
//        messg.setName(vardas);
//        messg.setPapildomas(papildomas);      
//        edit(messg);

        //Jax-rs edit
        
        String iD = request.getParameter("id");
        String vardas = request.getParameter("vardas");
        String zinute = request.getParameter("zinute");
        String papildomas = request.getParameter("papildomas");
        
        
        //Paduoda viska, bet nepasiima msg
//        this.msg.setName(vardas);
//        this.msg.setMsg(zinute);
//        this.msg.setPapildomas(papildomas);
//        this.msg.setTime(new Date());

            jlab1.entities.Message mesg = new jlab1.entities.Message();
            mesg.setName(vardas);
            mesg.setMessage(zinute);
            mesg.setPapildomas(papildomas);
            mesg.setTime(new Date());
        
        int id = Integer.parseInt(iD);
        
        Client client = ClientBuilder.newClient();

            WebTarget target = client.
                  target("http://localhost:8080/jlab1/rest/msg/");

            //target.path(iD).request(MediaType.APPLICATION_XML)
                  //.put(Entity.xml(msg));

           String resp = target.path(iD).request(MediaType.APPLICATION_XML)
                        .accept(MediaType.TEXT_PLAIN_TYPE)
                        .put(Entity.xml(mesg), String.class);

        
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

    private void edit(service_client.Message entity) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        service_client.MessageWS port = service.getMessageWSPort();
        port.edit(entity);
    }

    private Message find(java.lang.Object id) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        service_client.MessageWS port = service.getMessageWSPort();
        return port.find(id);
    }

    private java.util.List<service_client.Message> findAll() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        service_client.MessageWS port = service.getMessageWSPort();
        return port.findAll();
    }


}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

/**
 * REST Web Service
 *
 * @author Daumantas
 */
@Path("messagewsport")
public class MessageWSPort {

    private service_client.MessageWS port;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MessageWSPort
     */
    public MessageWSPort() {
        port = getPort();
    }

    /**
     * Invokes the SOAP method remove
     * @param entity resource URI parameter
     */
    @PUT
    @Consumes("application/xml")
    @Path("remove/")
    public void putRemove(JAXBElement<service_client.Message> entity) {
        try {
            // Call Web Service Operation
            if (port != null) {
                port.remove(entity.getValue());
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
    }

    /**
     * Invokes the SOAP method count
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("count/")
    public String getCount() {
        try {
            // Call Web Service Operation
            if (port != null) {
                int result = port.count();
                return new java.lang.Integer(result).toString();
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method find
     * @param id resource URI parameter
     * @return an instance of javax.xml.bind.JAXBElement<service_client.Message>
     */
    @POST
    @Produces("application/xml")
    @Consumes("application/xml")
    @Path("find/")
    public JAXBElement<service_client.Message> postFind(JAXBElement<Object> id) {
        try {
            // Call Web Service Operation
            if (port != null) {
                service_client.Message result = port.find(id.getValue());
                return new JAXBElement<service_client.Message>(new QName("http//service_client/", "message"), service_client.Message.class, result);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method create
     * @param entity resource URI parameter
     */
    @PUT
    @Consumes("application/xml")
    @Path("create/")
    public void putCreate(JAXBElement<service_client.Message> entity) {
        try {
            // Call Web Service Operation
            if (port != null) {
                port.create(entity.getValue());
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
    }

    /**
     * Invokes the SOAP method findAll
     * @return an instance of javax.xml.bind.JAXBElement<service_client.FindAllResponse>
     */
    @GET
    @Produces("application/xml")
    @Consumes("text/plain")
    @Path("findall/")
    public JAXBElement<service_client.FindAllResponse> getFindAll() {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.util.List<service_client.Message> result = port.findAll();

                class FindAllResponse_1 extends service_client.FindAllResponse {

                    FindAllResponse_1(java.util.List<service_client.Message> _return) {
                        this._return = _return;
                    }
                }
                service_client.FindAllResponse response = new FindAllResponse_1(result);
                return new service_client.ObjectFactory().createFindAllResponse(response);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method edit
     * @param entity resource URI parameter
     */
    @PUT
    @Consumes("application/xml")
    @Path("edit/")
    public void putEdit(JAXBElement<service_client.Message> entity) {
        try {
            // Call Web Service Operation
            if (port != null) {
                port.edit(entity.getValue());
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
    }

    /**
     * Invokes the SOAP method findRange
     * @param range resource URI parameter
     * @return an instance of javax.xml.bind.JAXBElement<service_client.FindRangeResponse>
     */
    @POST
    @Produces("application/xml")
    @Consumes("application/xml")
    @Path("findrange/")
    public JAXBElement<service_client.FindRangeResponse> postFindRange(JAXBElement<List<Integer>> range) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.util.List<service_client.Message> result = port.findRange(range.getValue());

                class FindRangeResponse_1 extends service_client.FindRangeResponse {

                    FindRangeResponse_1(java.util.List<service_client.Message> _return) {
                        this._return = _return;
                    }
                }
                service_client.FindRangeResponse response = new FindRangeResponse_1(result);
                return new service_client.ObjectFactory().createFindRangeResponse(response);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     *
     */
    private service_client.MessageWS getPort() {
        try {
            // Call Web Service Operation
            service_client.MessageWS_Service service = new service_client.MessageWS_Service();
            service_client.MessageWS p = service.getMessageWSPort();
            return p;
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }
}

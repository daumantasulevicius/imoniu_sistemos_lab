<%-- 
    Document   : namai
    Created on : Oct 8, 2020, 12:46:53 PM
    Author     : Daumantas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title> My first JSP   </title>
                <style>
                    TABLE, TD, TH, TR {
                        border-collapse:collapse;
                        border-width: 1px;
                        border-style: solid;
                        border-spacing: 5px;
                        padding: 2px 5px;
                    }
                </style>
                
	
        <script type="text/javascript">
            function getXMLObject()  //XML OBJECT
            {
               var xmlHttp = false;
               try {
                 xmlHttp = new ActiveXObject("Msxml2.XMLHTTP")  // For Old Microsoft Browsers
               }
               catch (e) {
                 try {
                   xmlHttp = new ActiveXObject("Microsoft.XMLHTTP")  // For Microsoft IE 6.0+
                 }
                 catch (e2) {
                   xmlHttp = false   // No Browser accepts the XMLHTTP Object then false
                 }
               }
               if (!xmlHttp && typeof XMLHttpRequest != 'undefined') {
                 xmlHttp = new XMLHttpRequest();        //For Mozilla, Opera Browsers
               }
               return xmlHttp;  // Mandatory Statement returning the ajax object created
            }
            function getMessages()
            {
                deleteTableRow(-1);
                var xmlHttp = getXMLObject();
                xmlHttp.onreadystatechange=function()
                {
                    if(xmlHttp.readyState==4)
                    {
                        var xmldoc = xmlHttp.responseXML;
                        var msgs = xmldoc.getElementsByTagName("messages")[0].childNodes;
                        console.log(msgs);
                        for(var i=0;i<msgs.length;i++)
                        {
                            var t_id="", t_n="", t_m="", t_d="", t_p = "";
                            if(msgs[i].getElementsByTagName("id")[0])
                                t_id = msgs[i].getElementsByTagName("id")[0].childNodes[0].textContent;
                            if(msgs[i].getElementsByTagName("name")[0])
                                t_n = msgs[i].getElementsByTagName("name")[0].childNodes[0].textContent;
                            if(msgs[i].getElementsByTagName("message")[0])
                                t_m = msgs[i].getElementsByTagName("message")[0].childNodes[0].textContent;
                            if(msgs[i].getElementsByTagName("time")[0])
                                t_d = msgs[i].getElementsByTagName("time")[0].childNodes[0].textContent;
                            if(msgs[i].getElementsByTagName("papildomas")[0])
                                t_p = msgs[i].getElementsByTagName("papildomas")[0].childNodes[0].textContent;
                            drawMessageTableRow(t_id,t_n,t_m,t_d,t_p);
                        }
                    }
                }
                xmlHttp.open("GET","jlab1/rest/msg",true);
                xmlHttp.send(null);
            }
            function getMessagesFiltr()
            {
                var id = document.getElementById('id').value;
                deleteTableRow(-1);
                var xmlHttp = getXMLObject();
                xmlHttp.onreadystatechange=function()
                {
                    if(xmlHttp.readyState==4)
                    {
                        var xmldoc = xmlHttp.responseXML;
                        var msgs = xmldoc.getElementsByTagName("messages")[0].childNodes;
                        console.log(msgs);
                        for(var i=0;i<msgs.length;i++)
                        {
                            var t_id="", t_n="", t_m="", t_d="", t_p = "";
                            if (msgs[i].getElementsByTagName("id")[0].childNodes[0].textContent > id)
                            {
                                if(msgs[i].getElementsByTagName("id")[0])
                                    t_id = msgs[i].getElementsByTagName("id")[0].childNodes[0].textContent;
                                if(msgs[i].getElementsByTagName("name")[0])
                                    t_n = msgs[i].getElementsByTagName("name")[0].childNodes[0].textContent;
                                if(msgs[i].getElementsByTagName("message")[0])
                                    t_m = msgs[i].getElementsByTagName("message")[0].childNodes[0].textContent;
                                if(msgs[i].getElementsByTagName("time")[0])
                                    t_d = msgs[i].getElementsByTagName("time")[0].childNodes[0].textContent;
                                if(msgs[i].getElementsByTagName("papildomas")[0])
                                    t_p = msgs[i].getElementsByTagName("papildomas")[0].childNodes[0].textContent;
                                drawMessageTableRow(t_id,t_n,t_m,t_d,t_p);
                            }
                        }
                    }
                }
                xmlHttp.open("GET","jlab1/rest/msg",true);
                xmlHttp.send(null);
            }
            //JAX-RS remove id
            function removeID()
            {
                var id = document.getElementById('id').value;
                var xmlHttp = getXMLObject();
                xmlHttp.open("DELETE","jlab1/rest/msg/" + id,true);
                xmlHttp.send(null);
            }
            function drawMessageTableRow(id, name, msg, time, papildomas)
            {
                var body = document.getElementById("msgs_table_body");
                if( body )
                {
                    var c_i = document.createElement('TD');
                    c_i.innerHTML = id;
                    var c_n = document.createElement('TD');
                    c_n.innerHTML = name;
                    var c_m = document.createElement('TD');
                    c_m.innerHTML = msg;
                    var c_t = document.createElement('TD');
                    c_t.innerHTML = time;
                    var c_p = document.createElement('TD');
                    c_p.innerHTML = papildomas;
                    var r = document.createElement('TR');
                    r.appendChild(c_i);
                    r.appendChild(c_n);
                    r.appendChild(c_m);
                    r.appendChild(c_t);
                    r.appendChild(c_p);
                    body.appendChild(r);
                }
            }
            function deleteTableRow(i)
            {
                var b = document.getElementById("msgs_table_body");
                if( i > -1) b.deleteRow(i);
                else b.innerHTML = "";
            }
        </script>
        </head>	
        <body align="center"  onload="getMessages()">		
            <form action="/jlab1" method="POST">			
			Vardas
			<input type="text" name="name"size="20px">
                        Komentaras
			<input type="text" name="message"size="20px">
                        Papildomas
			<input type="text" name="papildomas"size="20px">
			<input type="submit" value="Siųsti su JAX-RS">					
		</form>	
            <form action="${pageContext.request.contextPath}/Remove" method="POST">
            ID
            <input type="text" name="name" size="20px">
            <input type="submit" value="Trinti JAX-WS">
            </form>
            <hr>
            <div>
                <c:if test="${not empty msg}">
                    <jsp:getProperty name="msg" property="name"/>:
                    <jsp:getProperty name="msg" property="msg"/>
                </c:if>
            </div>
            <hr>
            <div align="center">
                ID
		<input type="text" name="id" id="id" size="20px">
                <button onclick="removeID()">Trinti pranešimus JAX-RS</button>
                <button onclick="getMessages()">Atnaujinti pranešimus JAX-RS</button>
                <button onclick="getMessagesFiltr()">Filtruoti pranešimus nuo ID JAX-RS</button>
                <form action="${pageContext.request.contextPath}/Filtruoti" method="POST">	
                        ID
                        <input type="text" name="id" size="20px">
			Vardas
			<input type="text" name="vardas"size="20px">
                        Komentaras
			<input type="text" name="zinute"size="20px">
                        Papildomas
			<input type="text" name="papildomas"size="20px">
			<input type="submit" value="Keisti JAX-RS ir Filtruoti nuo ID su JAX-WS">					
		</form>

                <div id="messages">
                    <table id="msgs_table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Vardas</th>
                                <th>Pranesimas</th>
                                <th>Data</th>
                                <th>Papildomas</th>
                            </tr>
                        </thead>
                        <tbody id="msgs_table_body"></tbody>
                    </table>
                    
                    <table id="msgs_table_Filtr">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Vardas</th>
                                <th>Pranesimas</th>
                                <th>Data</th>
                                <th>Papildomas</th>
                            </tr>
                            <c:if test = "${not empty msg_listFiltr}">
                                <c:forEach var="m" begin="0" items="${msg_listFiltr}">
                                    <tr>
                                        <td>${m.id}</td>
                                        <td>${m.name}</td>
                                        <td>${m.message}</td>
                                        <td>${m.time}</td>
                                        <td>${m.papildomas}</td>
                                    </tr>
                                    </c:forEach>
                                </c:if>
                        </thead>
                        <tbody id="msgs_table_Filtr_body"></tbody>
                    </table>
                    <%-- start web service invocation --%><hr/>
                    <%
                        try {
                            service_client.MessageWS_Service service = new service_client.MessageWS_Service();
                            service_client.MessageWS port = service.getMessageWSPort();
                            // TODO process result here
                            java.util.List<service_client.Message> result = port.findAll();
                            for (int i = 0; i < result.size(); i++) {
                                service_client.Message tmsg = result.get(i);
                                out.println(tmsg.getId() + "-" + tmsg.getName() + "-" + tmsg.getMessage() + "<br>");
                            }

                        } catch (Exception ex) {
                            // TODO handle custom exceptions here
                        }
                    %>
                    <%-- end web service invocation --%><hr/>

                </div>
            </div>
	</body>	
</html>

/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags.
 * See the copyright.txt in the distribution for a full listing
 * of individual contributors.
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 *
 * (C) 2005-2006,
 * @author JBoss Inc.
 */
package org.jboss.narayana.quickstarts.wsba.participantcompletion.simple;

import com.arjuna.mw.wst11.client.JaxWSHeaderContextProcessor;
import org.jboss.narayana.quickstarts.wsba.participantcompletion.simple.jaxws.OrderServiceBA;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.Handler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A Client stub to the OrderService.
 * 
 * @author paul.robinson@redhat.com, 2012-01-04
 */
@ClientStub
public class Client implements OrderServiceBA {
    private OrderServiceBA order;

    /**
     * Default constructor with hard-coded values for the OrderService endpoint details (wsdl url, service name & port name)
     * 
     * @throws MalformedURLException if the WSDL url is malformed.
     */
    public Client() throws MalformedURLException {
        URL wsdlLocation = new URL("http://localhost:8080/test/OrderServiceBA?wsdl");
        QName serviceName = new QName("http://www.jboss.org/as/quickstarts/helloworld/wsba/participantcompletion/order",
                "OrderServiceBAService");
        QName portName = new QName("http://www.jboss.org/as/quickstarts/helloworld/wsba/participantcompletion/order",
                "OrderServiceBA");

        Service service = Service.create(wsdlLocation, serviceName);
        order = service.getPort(portName, OrderServiceBA.class);

        /*
         * Add client handler chain so that XTS can add the transaction context to the SOAP messages.
         *
         * This will be automatically added by the TXFramework in the future.
         */
        BindingProvider bindingProvider = (BindingProvider) order;
        List<Handler> handlers = new ArrayList<Handler>(1);
        handlers.add(new JaxWSHeaderContextProcessor());
        bindingProvider.getBinding().setHandlerChain(handlers);
    }

    /**
     * Place an order
     *
     * @param emailAddress The email address of the person placing the order
     * @param item Item to order
     * @throws OrderServiceException if an error occurred when making the order
     */
    public void placeOrder(String emailAddress, String item) throws OrderServiceException {
        order.placeOrder(emailAddress, item);
    }
}

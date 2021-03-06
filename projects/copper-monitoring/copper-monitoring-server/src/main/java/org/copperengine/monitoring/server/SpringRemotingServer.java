/*
 * Copyright 2002-2014 SCOOP Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.copperengine.monitoring.server;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.apache.shiro.spring.remoting.SecureRemoteInvocationExecutor;
import org.copperengine.monitoring.core.CopperMonitoringService;
import org.copperengine.monitoring.core.LoginService;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.server.nio.NetworkTrafficSelectChannelConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.GzipFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.remoting.support.RemoteInvocationExecutor;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class SpringRemotingServer {

    static final Logger logger = LoggerFactory.getLogger(SpringRemotingServer.class);
    private Server server;
    private final CopperMonitoringService copperMonitoringService;
    private final int port;
    private final String host;
    private final LoginService loginService;

    private RemoteInvocationExecutor remoteInvocationExecutor = new SecureRemoteInvocationExecutor();

    public SpringRemotingServer(CopperMonitoringService copperMonitoringService, int port, String host, LoginService loginService) {
        super();
        this.copperMonitoringService = copperMonitoringService;
        this.port = port;
        this.host = host;
        this.loginService = loginService;
    }

    public void start() {
        logger.info("Starting Copper-Monitor-Server (jetty)");

        server = new Server();
        NetworkTrafficSelectChannelConnector connector = new NetworkTrafficSelectChannelConnector();
        connector.setPort(port);
        connector.setHost(host);
        // connector.addNetworkTrafficListener();

        server.setConnectors(new Connector[] { connector });

        ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/", true, false);

        // Servlet adress is defined with the bean name
        // try to avoid xml config (dont sacrifice type safety)
        GenericWebApplicationContext genericWebApplicationContext = new GenericWebApplicationContext();
        genericWebApplicationContext.registerBeanDefinition("/loginService",
                BeanDefinitionBuilder.genericBeanDefinition(HttpInvokerServiceExporter.class).
                        addPropertyValue("service", loginService).
                        addPropertyValue("serviceInterface", LoginService.class.getName()).
                        getBeanDefinition());
        genericWebApplicationContext.registerBeanDefinition("/copperMonitoringService",
                BeanDefinitionBuilder.genericBeanDefinition(HttpInvokerServiceExporter.class).
                        addPropertyValue("service", copperMonitoringService).
                        addPropertyValue("serviceInterface", CopperMonitoringService.class.getName()).
                        addPropertyValue("remoteInvocationExecutor", remoteInvocationExecutor).
                        getBeanDefinition());
        genericWebApplicationContext.refresh();

        DispatcherServlet dispatcherServlet = new DispatcherServlet(genericWebApplicationContext);
        ServletHolder servletHolder = new ServletHolder(dispatcherServlet);
        servletContextHandler.addServlet(servletHolder, "/*");

        FilterHolder filterHolder = new FilterHolder();
        GzipFilter filter = new GzipFilter();
        filterHolder.setFilter(filter);
        EnumSet<DispatcherType> types = EnumSet.allOf(DispatcherType.class);
        servletContextHandler.addFilter(filterHolder, "/*", types);

        HandlerCollection handlers = new HandlerCollection();
        final RequestLogHandler requestLogHandler = new RequestLogHandler();
        NCSARequestLog requestLog = new NCSARequestLog();
        requestLog.setAppend(true);
        requestLog.setExtended(true);
        requestLog.setLogLatency(true);
        requestLogHandler.setRequestLog(requestLog);
        handlers.setHandlers(new Handler[] { servletContextHandler, requestLogHandler });
        server.setHandler(handlers);

        try {
            server.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isRunning() {
        return server.isRunning();
    }

    public void setRemoteInvocationExecutor(RemoteInvocationExecutor remoteInvocationExecutor) {
        this.remoteInvocationExecutor = remoteInvocationExecutor;
    }

}

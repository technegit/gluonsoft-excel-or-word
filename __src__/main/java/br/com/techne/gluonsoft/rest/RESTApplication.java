package br.com.techne.gluonsoft.rest;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * Realiza disponibiliza os pacotes rest.
 * @generated
 */

@ApplicationPath("/api/rest/gluonsoft/")
public class RESTApplication extends ResourceConfig {

    /**
     * @generated
     */
	public RESTApplication() {
		property("org.glassfish.jersey.server.ServerProperties.PROVIDER_SCANNING_RECURSIVE", Boolean.TRUE);
		packages("br.com.techne.gluonsoft.rest");
	}

}

package knowyourtown.client.rest;

import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

/**
 * Created by matteo on 10/05/17.
 */
public class RestClient {

    WebTarget service;
    private String URI;
    private ClientConfig clientConfig;
    private Client client;

    public RestClient(String serviceURI) {
        this.URI = serviceURI;
        this.clientConfig = new ClientConfig();
        this.client = ClientBuilder.newClient(clientConfig);
        this.service = client.target(UriBuilder.fromUri(this.URI).build());
    }
}

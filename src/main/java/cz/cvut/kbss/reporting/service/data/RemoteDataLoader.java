/**
 * Copyright (C) 2017 Czech Technical University in Prague
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package cz.cvut.kbss.reporting.service.data;

import cz.cvut.kbss.reporting.exception.WebServiceIntegrationException;
import cz.cvut.kbss.reporting.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

@Service("remoteDataLoader")
public class RemoteDataLoader implements DataLoader {

    private static final Logger LOG = LoggerFactory.getLogger(RemoteDataLoader.class);

    /**
     * Configurable HTTP headers supported by {@link #loadData(String, Map)}.
     */
    public static final String[] SUPPORTED_HEADERS = {HttpHeaders.ACCEPT, HttpHeaders.CONTENT_TYPE};

    @Autowired
    private RestTemplate restTemplate;

    /**
     * {@inheritDoc}
     * <p>
     * The parameters are processed in the following way:
     * <p>
     * <pre>
     * <ul>
     *     <li>Known and supported HTTP headers are extracted and,</li>
     *     <li>The rest of the parameters are used as query params in the request.</li>
     * </ul>
     * </pre>
     *
     * @param remoteUrl Remote data source (URL)
     * @param params    Query parameters
     * @return Loaded data
     */
    public String loadData(String remoteUrl, Map<String, String> params) {
        final HttpHeaders headers = processHeaders(params);
        final URI urlWithQuery = prepareUri(remoteUrl, params);
        final HttpEntity<Object> entity = new HttpEntity<>(null, headers);
        if (LOG.isTraceEnabled()) {
            LOG.trace("Getting remote data using {}", urlWithQuery.toString());
        }
        try {
            final ResponseEntity<String> result = restTemplate.exchange(urlWithQuery, HttpMethod.GET, entity,
                    String.class);
            return result.getBody();
        } catch (HttpServerErrorException e) {
            LOG.error("Error when requesting remote data, url: {}. Response Status: {}\n, Body:",
                    urlWithQuery.toString(), e.getStatusCode(), e.getResponseBodyAsString());
            throw new WebServiceIntegrationException("Unable to fetch remote data.", e);
        } catch (Exception e) {
            LOG.error("Error when requesting remote data, url: {}.", urlWithQuery.toString(), e);
            throw new WebServiceIntegrationException("Unable to fetch remote data.", e);
        }
    }

    private HttpHeaders processHeaders(Map<String, String> params) {
        final HttpHeaders headers = new HttpHeaders();
        // Set default accept type to JSON-LD
        headers.set(HttpHeaders.ACCEPT, Constants.APPLICATION_JSON_LD_TYPE);
        for (String header : SUPPORTED_HEADERS) {
            if (params.containsKey(header)) {
                headers.set(header, params.get(header));
                params.remove(header);
            }
        }
        return headers;
    }

    private URI prepareUri(String remoteUrl, Map<String, String> queryParams) {
        final StringBuilder sb = new StringBuilder(remoteUrl);
        boolean containsQueryString = remoteUrl.matches("^.+\\?.+=.+$");
        for (Map.Entry<String, String> e : queryParams.entrySet()) {
            sb.append(!containsQueryString ? '?' : '&');
            sb.append(e.getKey()).append('=').append(e.getValue());
            containsQueryString = true;
        }
        return URI.create(sb.toString());
    }
}

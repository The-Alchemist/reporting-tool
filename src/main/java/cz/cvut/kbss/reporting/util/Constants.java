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
package cz.cvut.kbss.reporting.util;

import cz.cvut.kbss.reporting.model.Person;
import cz.cvut.kbss.reporting.model.Vocabulary;

public final class Constants {

    /**
     * Language used by the persistence unit.
     */
    public static final String PU_LANGUAGE = "en";

    /**
     * Base URI for Persons in the Reporting tool.
     */
    public static final String PERSON_BASE_URI = "http://www.inbas.cz/ontologies/reporting-tool/people#";

    /**
     * Base URI for Organizations in the Reporting tool.
     */
    public static final String ORGANIZATION_BASE_URI = "http://www.inbas.cz/ontologies/reporting-tool/organizations#";

    /**
     * Base URI for temporary contexts used by the form generation.
     */
    public static final String FORM_GEN_CONTEXT_BASE = "http://www.inbas.cz/ontologies/reporting-tool/formGen";

    /**
     * Initial revision number for reports.
     */
    public static final Integer INITIAL_REVISION = 1;

    /**
     * UTF-8 encoding identifier.
     */
    public static final String UTF_8_ENCODING = "UTF-8";

    /**
     * JSON-LD MIME type.
     */
    public static final String APPLICATION_JSON_LD_TYPE = "application/ld+json";

    /**
     * text/boolean MIME type. Useful for ASK queries.
     */
    public static final String TEXT_BOOLEAN_TYPE = "text/boolean";

    /**
     * Prefix for basic authentication for the Authorization HTTP header.
     */
    public static final String BASIC_AUTHORIZATION_PREFIX = "Basic ";

    /**
     * Company ID cookie name, used for portal authentication.
     */
    public static final String COMPANY_ID_COOKIE = "COMPANY_ID";

    /**
     * Default location of the index.html file, relative to the application classpath.
     */
    public static final String INDEX_FILE_LOCATION = "../../index.html";

    // File upload

    /**
     * Temporary location where uploaded files will be stored.
     */
    public static final String UPLOADED_FILE_LOCATION = "/tmp/";

    /**
     * Max uploaded file size. Currently 10MB.
     */
    public static final long MAX_UPLOADED_FILE_SIZE = 10 * 1024 * 1024;

    /**
     * Total request size containing Multi part. 20MB.
     */
    public static final long MAX_UPLOAD_REQUEST_SIZE = 20 * 1024 * 1024;

    /**
     * Size threshold after which files will be written to disk.
     */
    public static final int UPLOADED_FILE_SIZE_THRESHOLD = 0;

    // Query files

    /**
     * Folder containing query files for the application.
     */
    public static final String QUERY_FILES_DIRECTORY = "query";

    /**
     * Folder containing options files for the application.
     * <p>
     * Some options can be stored directly in the application.
     */
    public static final String OPTION_FILES_DIRECTORY = "option";

    /**
     * File with the full text fullTextSearch query.
     */
    public static final String FULL_TEXT_SEARCH_QUERY_FILE = "query/fullTextSearch.sparql";

    /**
     * Name of the URL parameter specifying type of options for loading.
     */
    public static final String OPTIONS_TYPE_QUERY_PARAM = "type";

    /**
     * Name of the URL parameter specifying query sent to remote repository.
     */
    public static final String QUERY_QUERY_PARAM = "query";

    /**
     * Maximum length of a description used by the {@code toString} method.
     * <p>
     * Anything longer that this threshold may be trimmed.
     */
    public static final int DESCRIPTION_TO_STRING_THRESHOLD = 50;

    /**
     * Default system administrator account.
     */
    public static final Person SYSTEM_ADMIN = initAdmin();

    public static final String ADMIN_CREDENTIALS_FILE = ".inbas-admin";

    private static Person initAdmin() {
        final Person admin = new Person();
        admin.setFirstName("System");
        admin.setLastName("Administrator");
        admin.setUsername("admin@inbas.cz");
        admin.addType(Vocabulary.s_c_admin);
        return admin;
    }

    private Constants() {
        throw new AssertionError();
    }
}

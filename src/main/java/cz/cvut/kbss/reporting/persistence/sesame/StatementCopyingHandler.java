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
package cz.cvut.kbss.reporting.persistence.sesame;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.eclipse.rdf4j.rio.RDFHandlerException;
import org.eclipse.rdf4j.rio.helpers.AbstractRDFHandler;

import java.util.Objects;

/**
 * Sesame RDF handler which takes the provided statements and adds them into the target connection.
 * <p>
 * A target context can be provided, but is optional.
 */
public class StatementCopyingHandler extends AbstractRDFHandler {

    private final RepositoryConnection connection;
    private final IRI context;

    public StatementCopyingHandler(RepositoryConnection connection) {
        Objects.requireNonNull(connection);
        this.connection = connection;
        this.context = null;
    }

    public StatementCopyingHandler(RepositoryConnection connection, java.net.URI context) {
        Objects.requireNonNull(connection);
        Objects.requireNonNull(context);
        this.connection = connection;
        this.context = connection.getValueFactory().createIRI(context.toString());
    }

    @Override
    public void handleStatement(Statement st) throws RDFHandlerException {
        try {
            connection.add(st, context);
        } catch (RepositoryException e) {
            throw new RDFHandlerException(e);
        }
    }
}

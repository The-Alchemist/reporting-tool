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
package cz.cvut.kbss.reporting.model;

import cz.cvut.kbss.jopa.model.annotations.*;
import cz.cvut.kbss.reporting.model.textanalysis.ExtractedItem;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static cz.cvut.kbss.reporting.util.Constants.DESCRIPTION_TO_STRING_THRESHOLD;

@OWLClass(iri = Vocabulary.s_c_initial_report)
public class InitialReport extends AbstractEntity {

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.s_p_description, readOnly = true)
    private String description;

    @OWLObjectProperty(iri = Vocabulary.s_p_has_text_analysis_result, readOnly = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ExtractedItem> extractedItems;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ExtractedItem> getExtractedItems() {
        return extractedItems;
    }

    public void setExtractedItems(Set<ExtractedItem> extractedItems) {
        this.extractedItems = extractedItems;
    }

    public void addExtractedItem(ExtractedItem item) {
        Objects.requireNonNull(item);
        if (extractedItems == null) {
            this.extractedItems = new HashSet<>();
        }
        extractedItems.add(item);
    }

    @Override
    public String toString() {
        return "InitialReport{" +
                "description='" + (description.length() > DESCRIPTION_TO_STRING_THRESHOLD ?
                                   description.substring(0, DESCRIPTION_TO_STRING_THRESHOLD) + "..." :
                                   description) + '\'' + "}";
    }
}

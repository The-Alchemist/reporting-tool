/*
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
'use strict';

import React from "react";
import {Label} from "react-bootstrap";
import JsonLdUtils from "jsonld-utils";
import {FormattedMessage} from "react-intl";
import I18nWrapper from "../../i18n/I18nWrapper";
import injectIntl from "../../utils/injectIntl";
import OptionsStore from "../../stores/OptionsStore";
import Utils from "../../utils/Utils";
import Vocabulary from "../../constants/Vocabulary";

class ReportProvenance extends React.Component {
    static propTypes = {
        report: React.PropTypes.object.isRequired,
        revisions: React.PropTypes.object
    };

    constructor(props) {
        super(props);
        this.i18n = props.i18n;
        this.state = {
            phase: this._resolvePhase()
        }
    }

    componentDidMount() {
        if (!this.state.phase) {
            this.unsubscribe = OptionsStore.listen(this._onPhasesLoaded);
        }
    }

    componentWillUnmount() {
        if (this.unsubscribe) {
            this.unsubscribe();
        }
    }

    _resolvePhase() {
        const phase = this.props.report.phase;
        return OptionsStore.getOptions('reportingPhase').find((item) => {
            return item['@id'] === phase;
        });
    }

    _onPhasesLoaded = (type) => {
        if (type === 'reportingPhase') {
            this.setState({phase: this._resolvePhase()});
        }
    };

    _renderProvenanceInfo() {
        const report = this.props.report;
        if (report.isNew) {
            return null;
        }
        const author = report.author ? report.author.firstName + ' ' + report.author.lastName : '',
            created = Utils.formatDate(new Date(report.dateCreated));
        let lastEditor, lastModified;
        if (!report.lastModified) {
            return <div className='notice-small'>
                <FormattedMessage id='report.created-by-msg'
                                  values={{date: created, name: <b>{author}</b>}}/>
            </div>;
        }
        lastEditor = report.lastModifiedBy ? report.lastModifiedBy.firstName + ' ' + report.lastModifiedBy.lastName : '';
        lastModified = Utils.formatDate(new Date(report.lastModified));
        return <div className='notice-small'>
            <FormattedMessage id='report.created-by-msg'
                              values={{date: created, name: <b>{author}</b>}}/>
            &nbsp;
            <FormattedMessage id='report.last-edited-msg'
                              values={{date: lastModified, name: <b>{lastEditor}</b>}}/>
        </div>;
    }

    _renderPhaseInfo() {
        const phaseSpec = this.state.phase;
        if (phaseSpec) {
            return <div className='form-group'>
                <Label
                    title={this.i18n('reports.phase')}>{JsonLdUtils.getLocalized(phaseSpec[Vocabulary.RDFS_LABEL], this.props.intl)}</Label>
            </div>;
        } else {
            return null;
        }
    }

    render() {
        return <div>
            {this.props.children}
            {this._renderPhaseInfo()}
            {this._renderProvenanceInfo()}
        </div>;
    }
}

export default injectIntl(I18nWrapper(ReportProvenance));

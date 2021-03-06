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
import Actions from "../../actions/Actions";
import Constants from "../../constants/Constants";
import Logger from "../../utils/Logger";
import Report from "./Report";
import ReportFactory from "../../model/ReportFactory";
import ReportStore from "../../stores/ReportStore";
import RouterStore from "../../stores/RouterStore";
import Routes from "../../utils/Routes";


export default class ReportController extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            key: null,
            report: this._isNew() ? ReportController.initNewReport() : null,
            revisions: null,
            loading: false
        };
    }

    _isNew() {
        return !this.props.params.reportKey;
    }

    static initNewReport() {
        const payload = RouterStore.getTransitionPayload(Routes.createReport.name);
        return payload ? payload : ReportFactory.createOccurrenceReport();
    }

    componentDidMount() {
        this.unsubscribe = ReportStore.listen(this.onReportStoreTrigger);
        Actions.loadOptions(Constants.OPTIONS.OCCURRENCE_CLASS);
        Actions.loadOptions(Constants.OPTIONS.OCCURRENCE_CATEGORY);
        Actions.loadOptions(Constants.OPTIONS.FACTOR_TYPE);
        if (!this._isNew()) {
            this._loadReport(this.props.params.reportKey);
        }
    }

    componentWillUnmount() {
        this.unsubscribe();
    }

    _loadReport(reportKey) {
        Actions.loadReport(reportKey);
        this.setState({loading: true, key: reportKey});
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.params.reportKey && this.state.key !== nextProps.params.reportKey) {
            this._loadReport(nextProps.params.reportKey);
        }
    }

    onReportStoreTrigger = (data) => {
        if (data.action === Actions.loadReport) {
            this._onReportLoaded(data.report);
        } else if (data.action === Actions.loadRevisions) {
            this.setState({revisions: data.revisions});
        }
    };

    _onReportLoaded = (report) => {
        if (!report) {
            this.setState({loading: false});
        } else {
            Logger.log('Loaded report ' + report.uri);
            this.setState({report: report, loading: false});
            Actions.loadRevisions(report.fileNumber);
        }
    };


    render() {
        return <Report report={this.state.report} revisions={this.state.revisions} loading={this.state.loading}/>;
    }
}

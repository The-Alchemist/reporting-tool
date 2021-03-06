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
import {Button, Table} from "react-bootstrap";
import EditableAttachmentRow from "./EditableAttachmentRow";
import I18nWrapper from "../../../i18n/I18nWrapper";
import injectIntl from "../../../utils/injectIntl";
import Utils from "../../../utils/Utils";

class AttachmentTable extends React.Component {
    static propTypes = {
        attachments: React.PropTypes.array,
        onChange: React.PropTypes.func.isRequired,
        onRemove: React.PropTypes.func.isRequired
    };

    static defaultProps = {
        attachments: []
    };

    constructor(props) {
        super(props);
        this.i18n = props.i18n;
        this.state = {
            editedRow: -1
        };
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.attachments && nextProps.attachments.length > this.props.attachments.length) {
            this._onEditCancel();
        }
    }

    _onEdit = (attachment) => {
        this.setState({editedRow: this.props.attachments.indexOf(attachment)});
    };

    _onEditFinish = (attachment) => {
        let original = this.props.attachments[this.state.editedRow];
        this.props.onChange(original, attachment);
        this._onEditCancel();
    };

    _onEditCancel = () => {
        this.setState({editedRow: -1});
    };

    render() {
        return <Table striped bordered condensed hover>
            <thead>
            <tr>
                <th className='col-xs-4 content-center'>{this.i18n('report.attachments.table.reference')}</th>
                <th className='col-xs-6 content-center'>{this.i18n('report.attachments.create.description-label')}</th>
                <th className='col-xs-2 content-center'>{this.i18n('table-actions')}</th>
            </tr>
            </thead>
            <tbody>
            {this._renderRows()}
            </tbody>
        </Table>;
    }

    _renderRows() {
        let rows = [],
            attachments = this.props.attachments;
        for (let i = 0, len = attachments.length; i < len; i++) {
            const key = Utils.stringHashCode(attachments[i].reference + attachments[i].description);
            if (i === this.state.editedRow) {
                rows.push(<EditableAttachmentRow key={key + '_edited'} attachment={attachments[i]}
                                                 onSave={this._onEditFinish} onCancel={this._onEditCancel}/>);
            } else {
                rows.push(<AttachmentRow key={key} attachment={attachments[i]} onEdit={this._onEdit}
                                         onRemove={this.props.onRemove}/>);
            }
        }
        return rows;
    }
}

/**
 * This will match http(s) and ftp(s) urls.
 */
const URL_REGEXP = /^(https?:\/\/|ftps?:\/\/)/i;

let AttachmentRow = (props) => {
    let attachment = props.attachment,
        reference = URL_REGEXP.test(attachment.reference) ?
            <a href={attachment.reference} target='_blank'>{attachment.reference}</a> : attachment.reference;
    return <tr>
        <td className='vertical-middle'>{reference}</td>
        <td className='vertical-middle'>{attachment.description}</td>
        <td className='vertical-middle actions'>
            <Button bsStyle='primary' bsSize='small'
                    onClick={(e) => props.onEdit(attachment)}>{props.i18n('table-edit')}</Button>
            <Button bsStyle='warning' bsSize='small'
                    onClick={(e) => props.onRemove(attachment)}>{props.i18n('remove')}</Button>
        </td>
    </tr>;
};

AttachmentRow.propTypes = {
    attachment: React.PropTypes.object.isRequired,
    onEdit: React.PropTypes.func.isRequired,
    onRemove: React.PropTypes.func.isRequired
};

AttachmentRow = injectIntl(I18nWrapper(AttachmentRow));

export default injectIntl(I18nWrapper(AttachmentTable));

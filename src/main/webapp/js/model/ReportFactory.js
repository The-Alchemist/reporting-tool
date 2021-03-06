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

const Constants = require('../constants/Constants');
const Utils = require('../utils/Utils');

const MILLIS_IN_MINUTE = 60 * 1000;

function roundToMinutes(time) {
    return Math.floor(time / MILLIS_IN_MINUTE) * MILLIS_IN_MINUTE;
}

module.exports = {
    createOccurrenceReport: function () {
        return {
            occurrence: {
                javaClass: Constants.OCCURRENCE_JAVA_CLASS,
                referenceId: Utils.randomInt(),
                name: '',
                // Round the time to whole seconds
                startTime: roundToMinutes(Date.now()),
                endTime: roundToMinutes(Date.now())
            },
            isNew: true,
            javaClass: Constants.OCCURRENCE_REPORT_JAVA_CLASS
        };
    },

    createFactor: function (parent = null) {
        const factor = {
            javaClass: Constants.EVENT_JAVA_CLASS,
            types: []
        };
        if (parent) {
            factor.startTime = parent.startTime;
            factor.endTime = parent.endTime;
        }
        return factor;
    }
};

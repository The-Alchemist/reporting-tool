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

const Reflux = require('reflux');

const Actions = require('../actions/Actions');
const Ajax = require('../utils/Ajax');
const Constants = require('../constants/Constants');

const SearchStore = Reflux.createStore({
    init: function () {
        this.listenTo(Actions.fullTextSearch, this.onFullTextSearch);
    },

    onFullTextSearch: function (expr) {
        Ajax.get(Constants.REST_PREFIX + 'search?expression=' + encodeURIComponent(expr)).end((data) => {
            this.trigger({
                action: Actions.fullTextSearch,
                data: data
            });
        });
    }
});

module.exports = SearchStore;

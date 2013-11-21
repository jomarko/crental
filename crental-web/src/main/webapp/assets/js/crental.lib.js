var cr  = cr || {
    // localization map
    locale: {}
};

/**
 * JS string localization
 * @param {type} code
 * @returns {unresolved}
 */
cr.i18n = function(code) {
    if (cr.locale[code]) {
        return cr.locale[code];
    } else {
        return code;
    }
};
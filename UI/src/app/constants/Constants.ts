import { environment } from "src/environments/environment";

const SERVER_URL = environment.API;

const urls = {
    TOKEN: SERVER_URL + 'oauth/token',
    REVOKE: SERVER_URL + 'oauth/revoke',
    DOCUMENT: {
        GET: SERVER_URL + '/component/document',
    },
    PING: SERVER_URL + '/component/ping',
    DATA: {
        SAVE: SERVER_URL + 'file'
    },
    REF: {
        COUNTRY: SERVER_URL + 'data/component/countries',
        STATE: SERVER_URL + 'data/component/states',
        CITY: SERVER_URL + 'data/component/cities',
    },
    USER: {
        GET: SERVER_URL + 'data/component/users',
        SAVE: SERVER_URL + 'users',
        UPDATE: SERVER_URL + '/updateUser',
        DELETE: SERVER_URL + '/user/delete/{userId}',
        UNDELETE: SERVER_URL + '/user/undelete/{userId}',
        ACTIVATE_INACTIVATE: SERVER_URL + '/activateInactivateUser',
        RESET_PASSWORD: SERVER_URL + 'resetPassword',
        FORGOT_PASSWORD: SERVER_URL + 'forgotPassword',
        RESEND_REGISTRATION_MAIL: SERVER_URL + '/sendUserRegistrationMail',
        LOGIN: SERVER_URL + '/login',
        LOGOUT: SERVER_URL + '/userLogout'
    },
    IMAGE: {
        GET: SERVER_URL + 'data/component/images'
    }
}

const REQUEST = {
    FILTER: 'FILTER',
    FILTER_KEY: 'FILTER_KEY',
    FILTER_VALUE: 'FILTER_VALUE',
    FILTER_TYPE: 'FILTER_TYPE',
    FILTER_TYPE_LIST: 'LIST',
    FILTER_TYPE_NUMBER: 'NUMBER',
    FILTER_TYPE_CHARS: 'CHARS',
    FILTER_TYPE_DATE: 'DATE',
    IS_NOT: 'IS_NOT',
    IS_NULL: 'IS_NULL',
    IS_EXACT: 'IS_EXACT',
    PAGE_NO: 'PAGE_NO',
    PAGE_SIZE: 'PAGE_SIZE',
    ORDER_BY: 'ORDER_BY',
    ORDER_TYPE: 'ORDER_TYPE',
    ORDER_TYPE_ASC: 'ASC',
    ORDER_TYPE_DESC: 'DESC'
};


const ROUTES = {
    HOME: 'home',
    LOGIN: 'login',
    RESET_PASSWORD: 'resetp',
    RESET_PASSWORD_HOME: 'resetp/home',
    SOW: 'sow',
    SOW_HISTORY: 'sowHistory',
    ADMIN: 'admin',
    USER: 'user'
  }

const ADMIN_TABS = {
    USER: 'User'
}

const ERROR_MSG = {
    SERVER_ERROR: 'Something went wrong, please retry login',
    INVALID_CREDENTIALS: 'Incorrect username or password.'
}

const AUTH_TOKEN = "family_tree_token";
const CLIENT_AUTHORIZATION = "Basic Y2xpZW50OnBhc3N3b3Jk";

export {
    urls,
    REQUEST,
    ROUTES,
    ADMIN_TABS,
    ERROR_MSG,
    AUTH_TOKEN,
    CLIENT_AUTHORIZATION
}


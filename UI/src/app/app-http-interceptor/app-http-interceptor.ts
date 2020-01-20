import { Injectable } from '@angular/core';
import { finalize, tap } from 'rxjs/operators';

import { HttpResponse, HttpEventType } from '@angular/common/http';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';

import _ from 'lodash';
import { Observable } from 'rxjs';
import { AppBlockUiService } from '../app-block-ui/app-block-ui.service';
import { AppMessageService, MESSAGE_TYPE } from '../message/message.service';
import { NavigationService } from '../navigation.service';
import { LocalStorageService } from '../services/local-storage.service';
import { AUTH_TOKEN, ERROR_MSG, urls, CLIENT_AUTHORIZATION } from '../constants/Constants';

class ResponseCache {

    cache = {};

    get(req: HttpRequest<any>): HttpResponse<any> | null {
        return this.cache[req.url];
    }

    put(req: HttpRequest<any>, resp: HttpResponse<any>): HttpResponse<any> | null {
        this.cache[req.url] = resp;
        return null;
    }
}

@Injectable()
export class AppHttpInterceptor implements HttpInterceptor {

    cachingUrls = [];
    cache: ResponseCache = new ResponseCache();

    constructor(private appBlockUiService: AppBlockUiService,
        private messageService: AppMessageService,
        private navigation: NavigationService,
        private localStorageService: LocalStorageService) {
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        const started = Date.now();
        let url = req.url;

        const cachedResponse = this.cache.get(req);
        if (cachedResponse) {
            // A cached response exists. Serve it instead of forwarding
            // the request to the next handler.
        }

        let headers = req.headers;
        if (url !== urls.TOKEN && url !== urls.USER.FORGOT_PASSWORD && url !== urls.USER.RESET_PASSWORD) {
            const token = this.localStorageService.get(AUTH_TOKEN);
            headers = req.headers.set('Authorization', 'Bearer ' + token);
        } else {
            headers = req.headers.set('Authorization', CLIENT_AUTHORIZATION);
        }

        const request = req.clone({
            url: url,
            body: req.body,
            headers: headers
        });


        this.appBlockUiService.blockUi();

        let ok: string;
        // extend server response observable with logging
        return next.handle(request)
            .pipe(
                tap(
                    // Succeeds when there is a response; ignore other events
                    event => {

                        if (event instanceof HttpResponse) {
                            this.appBlockUiService.unblockUi();
                            const elapsed = Date.now() - started;
                            console.log(`Request for ${req.urlWithParams} took ${elapsed} ms.`);
                            const index = this.cachingUrls.findIndex(function (item) {
                                return item.endsWith(req.url);
                            });

                            if (index > -1) {
                                console.log(req.url + ' cached');
                                this.cache.put(req, event);
                            }
                        } else {
                            switch (event.type) {
                                case HttpEventType.Sent:
                                    break;
                                case HttpEventType.UploadProgress:
                                    break;
                                case HttpEventType.ResponseHeader:
                                    break;
                                case HttpEventType.DownloadProgress:
                                    break;
                                case HttpEventType.User:
                                    break;
                            }
                        }
                    },
                    // Operation failed; error is an HttpErrorResponse
                    error => {
                        if (error.status === 0) {
                            this.messageService.showMessage(
                                MESSAGE_TYPE.ERROR,
                                ERROR_MSG.SERVER_ERROR
                            );
                            // this.navigation.openLoginPage();
                        }

                        if (error.status === 500 || error.status === 503) {
                            this.messageService.showMessage(
                                MESSAGE_TYPE.ERROR,
                                ERROR_MSG.SERVER_ERROR
                            );
                            // this.navigation.openLoginPage();
                        }


                        if (error.status === 401) {
                            this.showError()
                            this.localStorageService.set(AUTH_TOKEN, null);
                            // this.navigation.openLoginPage();
                            this.messageService.showMessage(MESSAGE_TYPE.ERROR, ERROR_MSG.INVALID_CREDENTIALS);
                        }


                        // License expired error code 403
                        if (error.status === 403) {
                            this.showError()
                            this.localStorageService.set(AUTH_TOKEN, null);
                            // this.navigation.openLoginPage();
                        }

                        if (error.status === 406) {
                            this.showError()
                            // this.navigation.openLoginPage();
                            this.localStorageService.set(AUTH_TOKEN, null);
                        }
                        this.appBlockUiService.unblockUi();
                        this.localStorageService.set(AUTH_TOKEN, null);
                        // this.navigation.openLoginPage();
                    }
                ),
                // Log when response observable either completes or errors
                finalize(() => {
                    const elapsed = Date.now() - started;
                    const msg = `${req.method} "${req.urlWithParams}" ${ok} in ${elapsed} ms.`;
                })
            );

    }

    showError = () => {
        this.messageService.showMessage(
            MESSAGE_TYPE.ERROR,
            ERROR_MSG.SERVER_ERROR
        );
    }
}

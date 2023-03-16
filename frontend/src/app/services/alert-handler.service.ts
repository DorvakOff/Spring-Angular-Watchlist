import {Injectable} from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class AlertHandlerService {

    alerts: Alert[] = [];

    constructor() {
    }

    public raiseError(error: any | string) {
        if (error.error && typeof error.error === 'string') {
            error.error = JSON.parse(error.error)
        }
        let message = typeof error === 'string' ? error : (error.error && error.error.message ? error.error.message : error.message)
        if (message === 'Http failure response for (unknown url): 0 Unknown Error') {
            message = 'The server is not reachable. Please check your internet connection.'
        }

        this.addAlert({
            severity: 'danger',
            title: 'An error occurred',
            body: message
        })
    }

    public sendSuccess(title: string, body: string) {
        this.addAlert({
            severity: 'success',
            title: title,
            body: body
        })
    }

    private addAlert(alert: Alert) {
        this.alerts.push(alert)
        setTimeout(() => {
            this.alerts.shift()
        }, 5000)
    }
}

export interface Alert {
    severity: 'primary' | 'danger' | 'success' | 'warning'
    title: string
    body: string
    additionalInfos?: string
}

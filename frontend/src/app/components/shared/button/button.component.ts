import {Component, Input, OnInit} from '@angular/core';

@Component({
    selector: 'cmp-button',
    templateUrl: './button.component.html',
    styleUrls: ['./button.component.scss']
})
export class ButtonComponent implements OnInit {

    @Input() type: 'danger' | 'success' | 'info' | 'outline-wide-info' = 'info'
    @Input() disabled: boolean = false

    constructor() {
    }

    ngOnInit(): void {
    }

    getClass(type: "danger" | "success" | "info" | "outline-wide-info"): string {
        let classes = ['button']

        let types = ['danger', 'success', 'info'];

        for (let t of types) {
            if (type.includes(t)) {
                classes.push(`button-${t}`)
            }

            if (type.includes('outline')) {
                classes.push(`button-outline`)
            }

            if (type.includes('wide')) {
                classes.push(`button-wide`)
            }
        }

        return classes.join(' ')
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPublication } from 'app/shared/model/publication.model';

@Component({
    selector: 'jhi-publication-detail',
    templateUrl: './publication-detail.component.html'
})
export class PublicationDetailComponent implements OnInit {
    publication: IPublication;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ publication }) => {
            this.publication = publication;
        });
    }

    previousState() {
        window.history.back();
    }
}

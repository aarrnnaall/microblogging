import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPublication } from 'app/shared/model/publication.model';
import { Principal } from 'app/core';
import { PublicationService } from './publication.service';

@Component({
    selector: 'jhi-publication',
    templateUrl: './publication.component.html'
})
export class PublicationComponent implements OnInit, OnDestroy {
    publications: IPublication[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private publicationService: PublicationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.publicationService.query().subscribe(
            (res: HttpResponse<IPublication[]>) => {
                this.publications = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPublications();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPublication) {
        return item.id;
    }

    registerChangeInPublications() {
        this.eventSubscriber = this.eventManager.subscribe('publicationListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

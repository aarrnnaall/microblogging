import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPublication } from 'app/shared/model/publication.model';
import { PublicationService } from './publication.service';
import { IPublisher } from 'app/shared/model/publisher.model';
import { PublisherService } from 'app/entities/publisher';
import { ITag } from 'app/shared/model/tag.model';
import { TagService } from 'app/entities/tag';

@Component({
    selector: 'jhi-publication-update',
    templateUrl: './publication-update.component.html'
})
export class PublicationUpdateComponent implements OnInit {
    private _publication: IPublication;
    isSaving: boolean;

    republishedbies: IPublication[];

    publishers: IPublisher[];

    tags: ITag[];
    dateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private publicationService: PublicationService,
        private publisherService: PublisherService,
        private tagService: TagService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ publication }) => {
            this.publication = publication;
        });
        this.publicationService.query({ filter: 'publication-is-null' }).subscribe(
            (res: HttpResponse<IPublication[]>) => {
                if (!this.publication.republishedBy || !this.publication.republishedBy.id) {
                    this.republishedbies = res.body;
                } else {
                    this.publicationService.find(this.publication.republishedBy.id).subscribe(
                        (subRes: HttpResponse<IPublication>) => {
                            this.republishedbies = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.publisherService.query().subscribe(
            (res: HttpResponse<IPublisher[]>) => {
                this.publishers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.tagService.query().subscribe(
            (res: HttpResponse<ITag[]>) => {
                this.tags = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.publication.id !== undefined) {
            this.subscribeToSaveResponse(this.publicationService.update(this.publication));
        } else {
            this.subscribeToSaveResponse(this.publicationService.create(this.publication));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPublication>>) {
        result.subscribe((res: HttpResponse<IPublication>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPublicationById(index: number, item: IPublication) {
        return item.id;
    }

    trackPublisherById(index: number, item: IPublisher) {
        return item.id;
    }

    trackTagById(index: number, item: ITag) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get publication() {
        return this._publication;
    }

    set publication(publication: IPublication) {
        this._publication = publication;
    }
}

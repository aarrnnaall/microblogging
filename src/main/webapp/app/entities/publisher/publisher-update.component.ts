import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPublisher } from 'app/shared/model/publisher.model';
import { PublisherService } from './publisher.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-publisher-update',
    templateUrl: './publisher-update.component.html'
})
export class PublisherUpdateComponent implements OnInit {
    private _publisher: IPublisher;
    isSaving: boolean;

    users: IUser[];

    publishers: IPublisher[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private publisherService: PublisherService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ publisher }) => {
            this.publisher = publisher;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.publisherService.query().subscribe(
            (res: HttpResponse<IPublisher[]>) => {
                this.publishers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.publisher.id !== undefined) {
            this.subscribeToSaveResponse(this.publisherService.update(this.publisher));
        } else {
            this.subscribeToSaveResponse(this.publisherService.create(this.publisher));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPublisher>>) {
        result.subscribe((res: HttpResponse<IPublisher>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackPublisherById(index: number, item: IPublisher) {
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
    get publisher() {
        return this._publisher;
    }

    set publisher(publisher: IPublisher) {
        this._publisher = publisher;
    }
}

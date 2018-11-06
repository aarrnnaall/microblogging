import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Publication } from 'app/shared/model/publication.model';
import { PublicationService } from './publication.service';
import { PublicationComponent } from './publication.component';
import { PublicationDetailComponent } from './publication-detail.component';
import { PublicationUpdateComponent } from './publication-update.component';
import { PublicationDeletePopupComponent } from './publication-delete-dialog.component';
import { IPublication } from 'app/shared/model/publication.model';

@Injectable({ providedIn: 'root' })
export class PublicationResolve implements Resolve<IPublication> {
    constructor(private service: PublicationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((publication: HttpResponse<Publication>) => publication.body));
        }
        return of(new Publication());
    }
}

export const publicationRoute: Routes = [
    {
        path: 'publication',
        component: PublicationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microbloggingApp.publication.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'publication/:id/view',
        component: PublicationDetailComponent,
        resolve: {
            publication: PublicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microbloggingApp.publication.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'publication/new',
        component: PublicationUpdateComponent,
        resolve: {
            publication: PublicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microbloggingApp.publication.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'publication/:id/edit',
        component: PublicationUpdateComponent,
        resolve: {
            publication: PublicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microbloggingApp.publication.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const publicationPopupRoute: Routes = [
    {
        path: 'publication/:id/delete',
        component: PublicationDeletePopupComponent,
        resolve: {
            publication: PublicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microbloggingApp.publication.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Publicacion } from 'app/shared/model/publicacion.model';
import { PublicacionService } from './publicacion.service';
import { PublicacionComponent } from './publicacion.component';
import { PublicacionDetailComponent } from './publicacion-detail.component';
import { PublicacionUpdateComponent } from './publicacion-update.component';
import { PublicacionDeletePopupComponent } from './publicacion-delete-dialog.component';
import { IPublicacion } from 'app/shared/model/publicacion.model';

@Injectable({ providedIn: 'root' })
export class PublicacionResolve implements Resolve<IPublicacion> {
    constructor(private service: PublicacionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((publicacion: HttpResponse<Publicacion>) => publicacion.body));
        }
        return of(new Publicacion());
    }
}

export const publicacionRoute: Routes = [
    {
        path: 'publicacion',
        component: PublicacionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microbloggingApp.publicacion.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'publicacion/:id/view',
        component: PublicacionDetailComponent,
        resolve: {
            publicacion: PublicacionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microbloggingApp.publicacion.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'publicacion/new',
        component: PublicacionUpdateComponent,
        resolve: {
            publicacion: PublicacionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microbloggingApp.publicacion.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'publicacion/:id/edit',
        component: PublicacionUpdateComponent,
        resolve: {
            publicacion: PublicacionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microbloggingApp.publicacion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const publicacionPopupRoute: Routes = [
    {
        path: 'publicacion/:id/delete',
        component: PublicacionDeletePopupComponent,
        resolve: {
            publicacion: PublicacionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microbloggingApp.publicacion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

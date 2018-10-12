import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MicrobloggingSharedModule } from 'app/shared';
import {
    PublicacionComponent,
    PublicacionDetailComponent,
    PublicacionUpdateComponent,
    PublicacionDeletePopupComponent,
    PublicacionDeleteDialogComponent,
    publicacionRoute,
    publicacionPopupRoute
} from './';

const ENTITY_STATES = [...publicacionRoute, ...publicacionPopupRoute];

@NgModule({
    imports: [MicrobloggingSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PublicacionComponent,
        PublicacionDetailComponent,
        PublicacionUpdateComponent,
        PublicacionDeleteDialogComponent,
        PublicacionDeletePopupComponent
    ],
    entryComponents: [PublicacionComponent, PublicacionUpdateComponent, PublicacionDeleteDialogComponent, PublicacionDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MicrobloggingPublicacionModule {}

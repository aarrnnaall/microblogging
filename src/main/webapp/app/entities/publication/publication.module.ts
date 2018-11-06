import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MicrobloggingSharedModule } from 'app/shared';
import {
    PublicationComponent,
    PublicationDetailComponent,
    PublicationUpdateComponent,
    PublicationDeletePopupComponent,
    PublicationDeleteDialogComponent,
    publicationRoute,
    publicationPopupRoute
} from './';

const ENTITY_STATES = [...publicationRoute, ...publicationPopupRoute];

@NgModule({
    imports: [MicrobloggingSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PublicationComponent,
        PublicationDetailComponent,
        PublicationUpdateComponent,
        PublicationDeleteDialogComponent,
        PublicationDeletePopupComponent
    ],
    entryComponents: [PublicationComponent, PublicationUpdateComponent, PublicationDeleteDialogComponent, PublicationDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MicrobloggingPublicationModule {}

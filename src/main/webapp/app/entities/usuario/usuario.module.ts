import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MicrobloggingSharedModule } from 'app/shared';
import {
    UsuarioComponent,
    UsuarioDetailComponent,
    UsuarioUpdateComponent,
    UsuarioDeletePopupComponent,
    UsuarioDeleteDialogComponent,
    usuarioRoute,
    usuarioPopupRoute
} from './';

const ENTITY_STATES = [...usuarioRoute, ...usuarioPopupRoute];

@NgModule({
    imports: [MicrobloggingSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UsuarioComponent,
        UsuarioDetailComponent,
        UsuarioUpdateComponent,
        UsuarioDeleteDialogComponent,
        UsuarioDeletePopupComponent
    ],
    entryComponents: [UsuarioComponent, UsuarioUpdateComponent, UsuarioDeleteDialogComponent, UsuarioDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MicrobloggingUsuarioModule {}

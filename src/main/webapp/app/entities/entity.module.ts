import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MicrobloggingUsuarioModule } from './usuario/usuario.module';
import { MicrobloggingTagModule } from './tag/tag.module';
import { MicrobloggingPublicacionModule } from './publicacion/publicacion.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        MicrobloggingUsuarioModule,
        MicrobloggingTagModule,
        MicrobloggingPublicacionModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MicrobloggingEntityModule {}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MicrobloggingPublisherModule } from './publisher/publisher.module';
import { MicrobloggingTagModule } from './tag/tag.module';
import { MicrobloggingPublicationModule } from './publication/publication.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        MicrobloggingPublisherModule,
        MicrobloggingTagModule,
        MicrobloggingPublicationModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MicrobloggingEntityModule {}

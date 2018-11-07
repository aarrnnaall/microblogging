/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MicrobloggingTestModule } from '../../../test.module';
import { PublicationComponent } from 'app/entities/publication/publication.component';
import { PublicationService } from 'app/entities/publication/publication.service';
import { Publication } from 'app/shared/model/publication.model';

describe('Component Tests', () => {
    describe('Publication Management Component', () => {
        let comp: PublicationComponent;
        let fixture: ComponentFixture<PublicationComponent>;
        let service: PublicationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MicrobloggingTestModule],
                declarations: [PublicationComponent],
                providers: []
            })
                .overrideTemplate(PublicationComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PublicationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PublicationService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Publication(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.publications[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MicrobloggingTestModule } from '../../../test.module';
import { PublicationDetailComponent } from 'app/entities/publication/publication-detail.component';
import { Publication } from 'app/shared/model/publication.model';

describe('Component Tests', () => {
    describe('Publication Management Detail Component', () => {
        let comp: PublicationDetailComponent;
        let fixture: ComponentFixture<PublicationDetailComponent>;
        const route = ({ data: of({ publication: new Publication(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MicrobloggingTestModule],
                declarations: [PublicationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PublicationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PublicationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.publication).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MicrobloggingTestModule } from '../../../test.module';
import { PublicationUpdateComponent } from 'app/entities/publication/publication-update.component';
import { PublicationService } from 'app/entities/publication/publication.service';
import { Publication } from 'app/shared/model/publication.model';

describe('Component Tests', () => {
    describe('Publication Management Update Component', () => {
        let comp: PublicationUpdateComponent;
        let fixture: ComponentFixture<PublicationUpdateComponent>;
        let service: PublicationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MicrobloggingTestModule],
                declarations: [PublicationUpdateComponent]
            })
                .overrideTemplate(PublicationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PublicationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PublicationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Publication(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.publication = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Publication();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.publication = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});

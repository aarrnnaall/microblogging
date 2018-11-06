/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MicrobloggingTestModule } from '../../../test.module';
import { PublicationDeleteDialogComponent } from 'app/entities/publication/publication-delete-dialog.component';
import { PublicationService } from 'app/entities/publication/publication.service';

describe('Component Tests', () => {
    describe('Publication Management Delete Component', () => {
        let comp: PublicationDeleteDialogComponent;
        let fixture: ComponentFixture<PublicationDeleteDialogComponent>;
        let service: PublicationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MicrobloggingTestModule],
                declarations: [PublicationDeleteDialogComponent]
            })
                .overrideTemplate(PublicationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PublicationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PublicationService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});

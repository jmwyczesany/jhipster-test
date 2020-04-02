import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterTestTestModule } from '../../../test.module';
import { BikerUpdateComponent } from 'app/entities/biker/biker-update.component';
import { BikerService } from 'app/entities/biker/biker.service';
import { Biker } from 'app/shared/model/biker.model';

describe('Component Tests', () => {
  describe('Biker Management Update Component', () => {
    let comp: BikerUpdateComponent;
    let fixture: ComponentFixture<BikerUpdateComponent>;
    let service: BikerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestTestModule],
        declarations: [BikerUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BikerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BikerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BikerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Biker(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Biker();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});

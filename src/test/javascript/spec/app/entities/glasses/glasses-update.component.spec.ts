import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterTestTestModule } from '../../../test.module';
import { GlassesUpdateComponent } from 'app/entities/glasses/glasses-update.component';
import { GlassesService } from 'app/entities/glasses/glasses.service';
import { Glasses } from 'app/shared/model/glasses.model';

describe('Component Tests', () => {
  describe('Glasses Management Update Component', () => {
    let comp: GlassesUpdateComponent;
    let fixture: ComponentFixture<GlassesUpdateComponent>;
    let service: GlassesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestTestModule],
        declarations: [GlassesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GlassesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GlassesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GlassesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Glasses(123);
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
        const entity = new Glasses();
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

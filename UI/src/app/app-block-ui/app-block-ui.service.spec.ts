import { TestBed } from '@angular/core/testing';

import { AppBlockUiService } from './app-block-ui.service';

describe('AppBlockUiService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AppBlockUiService = TestBed.get(AppBlockUiService);
    expect(service).toBeTruthy();
  });
});

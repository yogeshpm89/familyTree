import { AppGridModule } from './app-grid.module';

describe('AppGridModule', () => {
  let appGridModule: AppGridModule;

  beforeEach(() => {
    appGridModule = new AppGridModule();
  });

  it('should create an instance', () => {
    expect(appGridModule).toBeTruthy();
  });
});

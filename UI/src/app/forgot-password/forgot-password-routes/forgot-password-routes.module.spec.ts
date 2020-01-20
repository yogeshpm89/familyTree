import { ForgotPasswordRoutesModule } from './forgot-password-routes.module';

describe('ForgotPasswordRoutesModule', () => {
  let forgotPasswordRoutesModule: ForgotPasswordRoutesModule;

  beforeEach(() => {
    forgotPasswordRoutesModule = new ForgotPasswordRoutesModule();
  });

  it('should create an instance', () => {
    expect(forgotPasswordRoutesModule).toBeTruthy();
  });
});

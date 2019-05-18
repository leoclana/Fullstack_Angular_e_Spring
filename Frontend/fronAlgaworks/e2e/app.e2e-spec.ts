import { FronAlgaworksPage } from './app.po';

describe('fron-algaworks App', () => {
  let page: FronAlgaworksPage;

  beforeEach(() => {
    page = new FronAlgaworksPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});

import { browser, by, element } from 'protractor';

export class FronAlgaworksPage {
  navigateTo() {
    return browser.get('/');
  }

  getParagraphText() {
    return element(by.css('app-root h1')).getText();
  }
}

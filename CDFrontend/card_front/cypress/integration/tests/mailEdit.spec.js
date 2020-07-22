/// <reference types="cypress" />

context('MailEdit', () => {
  let LOCAL_STORAGE_MEMORY = {};

  Cypress.Commands.add("saveLocalStorage", () => {
    Object.keys(localStorage).forEach(key => {
      LOCAL_STORAGE_MEMORY[key] = localStorage[key];
    });
  });

  Cypress.Commands.add("restoreLocalStorage", () => {
    Object.keys(LOCAL_STORAGE_MEMORY).forEach(key => {
      localStorage.setItem(key, LOCAL_STORAGE_MEMORY[key]);
    });
  });

  beforeEach(() => {
    cy.restoreLocalStorage();
  });

  afterEach(() => {
    cy.saveLocalStorage();
  });

  it('Login',  () => {
    cy.visit('http://localhost:9527/#/login?redirect=%2Fdashboard');

    cy.get('.username-input')
      .type('admin1');

    cy.get('.password-input')
      .type('111111');

    cy.get('.login-button').click();

    cy.get('.hamburger').click();

    cy.get(':nth-child(4) > .el-submenu > .el-submenu__title').click();

    cy.get(':nth-child(4) > .el-submenu > .el-menu > :nth-child(2) > a > .el-menu-item > span').click();

  });

  it('Mail Edit Invalid Form', () => {
    cy.get('.el-button--success > span').click();
    cy.get('.material-input')
      .type('Test Title');
    cy.get('.el-button--success > span').click();
    cy.get('iframe').its('0.contentDocument.body')
      .type('Test Content');
    cy.get('.material-input').clear();
    cy.get('.el-button--success > span').click();
  });

  it('Mail Edit Publish', () => {
    cy.get('.material-input')
      .type('Test Title');
    cy.get('.el-button--success > span').click();

  });

  it('Mail Update Invalid Form', () => {
    cy.get('#tab-second').click();
    cy.get(':nth-child(2) > .el-table_1_column_2 > .cell > .link-type').click();
    cy.get('.el-dialog__body > .el-form > .createPost-main-container > [style="grid-area: 1 / 1 / span 1 / span 2;"] > .el-col-24 > [style="margin-bottom: 40px;"] > .el-form-item__content > .material-input__component > div > .material-input')
      .clear();
    cy.get('[data-v-6dc435d2=""][style="z-index: 10; height: 50px;"] > .sub-navbar > .el-button--success').click();
    cy.get('.el-dialog__body > .el-form > .createPost-main-container > [style="grid-area: 1 / 1 / span 1 / span 2;"] > .el-col-24 > [style="margin-bottom: 40px;"] > .el-form-item__content > .material-input__component > div > .material-input')
      .type('New Title');
    // cy.get('iframe').its('0.contentDocument.body')
    //   .clear();
    // cy.get('.mce-content-body panel-body').clear();
    // cy.setTinyMceContent('1595384636272195', 'This is the new content');
    // cy.get('[data-v-6dc435d2=""][style="z-index: 10; height: 50px;"] > .sub-navbar > .el-button--success').click();
  });

  it('Mail Update Normal', () => {
    // cy.get('iframe').its('0.contentDocument.body')
    //   .type('Test Content');
    cy.get('[data-v-6dc435d2=""][style="z-index: 10; height: 50px;"] > .sub-navbar > .el-button--success').click();
  });

  it('Mail Delete', () => {
      cy.get(':nth-child(2) > .el-table_1_column_2 > .cell > .link-type').click();
      cy.get('.deleteOuterButton').click();
      cy.get('.el-dialog__body > .el-input > .el-input__inner')
        .type('222222');
      cy.get('.el-dialog__body > .el-button').click();
      cy.get('.cancelInnerButton').click();
      cy.get('.deleteOuterButton').click({force: true});
      cy.get('.el-dialog__body > .el-input > .el-input__inner')
        .type('111111');
      cy.get('.el-dialog__body > .el-button').click();
      cy.get('.deleteInnerButton').click({force: true});
  })

});
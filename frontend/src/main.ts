import { bootstrapApplication } from '@angular/platform-browser';
import { registerLocaleData } from '@angular/common';
import localePt from '@angular/common/locales/pt';
import { LOCALE_ID } from '@angular/core';

import { App } from './app/app';
import { appConfig } from './app/app.config';

// 1️⃣ Registra locale português (serve para pt-BR)
registerLocaleData(localePt);

// 2️⃣ Bootstrap com pt-BR
bootstrapApplication(App, {
  ...appConfig,
  providers: [
    ...(appConfig.providers ?? []),
    { provide: LOCALE_ID, useValue: 'pt-BR' }
  ]
}).catch(console.error);
